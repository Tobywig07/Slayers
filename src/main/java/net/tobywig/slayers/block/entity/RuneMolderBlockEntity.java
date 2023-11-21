package net.tobywig.slayers.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tobywig.slayers.block.custom.RuneMolderBlock;
import net.tobywig.slayers.network.PacketHandlerV2;
import net.tobywig.slayers.network.packet.c_to_s.FluidSyncPacket;
import net.tobywig.slayers.recipe.custom.RuneMolderRecipe;
import net.tobywig.slayers.screen.RuneMolderMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class RuneMolderBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> true;
                case 1 -> stack.getItem() == Items.ROTTEN_FLESH || stack.getItem() == Items.BONE || stack.getItem() == Items.ENDER_EYE || stack.getItem() == Items.BLAZE_ROD;
                case 2 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                case 3 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final FluidTank FLUID_TANK = new FluidTank(64000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                PacketHandlerV2.sendToClients(new FluidSyncPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.LAVA;
        }
    };

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluid() {
        return this.FLUID_TANK.getFluid();
    }


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 3, (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 0,
                            (index, stack) -> itemHandler.isItemValid(1, stack))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 3, (i, s) -> false)),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2,
                            (index, stack) -> itemHandler.isItemValid(3, stack))),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 1 || index == 2,
                            (index, stack) -> itemHandler.isItemValid(1, stack) || itemHandler.isItemValid(2, stack))));

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 500;

    public RuneMolderBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.RUNE_MOLDER_ENTITY.get(), p_155229_, p_155230_);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> RuneMolderBlockEntity.this.progress;
                    case 1 -> RuneMolderBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> RuneMolderBlockEntity.this.progress = value;
                    case 1 -> RuneMolderBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Rune Molder");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        PacketHandlerV2.sendToClients(new FluidSyncPacket(this.getFluid(), worldPosition));

        return new RuneMolderMenu(id, inv, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(RuneMolderBlock.FACING);

                if(side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }

        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("rune_molder.progress", this.progress);
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("rune_molder.progress");
        FLUID_TANK.readFromNBT(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }



    public static void tick(Level level, BlockPos blockPos, BlockState blockState, RuneMolderBlockEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }

        if (hasRecipe(pEntity)) {
            pEntity.progress++;

            if (!blockState.getValue(RuneMolderBlock.LIT)) {
                level.setBlock(blockPos, blockState.setValue(RuneMolderBlock.LIT, true), 3);
            }

            setChanged(level, blockPos, blockState);

            if (pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        }
        else {
            pEntity.resetProgress();

            if (blockState.getValue(RuneMolderBlock.LIT)) {
                level.setBlock(blockPos, blockState.setValue(RuneMolderBlock.LIT, false), 3);
            }

            setChanged(level, blockPos, blockState);
        }

        if (hasFluidItem(pEntity)) {
            transferItemToFluid(pEntity);
        }
    }

    private static void transferItemToFluid(RuneMolderBlockEntity pEntity) {
        pEntity.itemHandler.getStackInSlot(2).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int drainAmount = Math.min(pEntity.FLUID_TANK.getSpace(), 1000);

            FluidStack fluidStack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if (pEntity.FLUID_TANK.isFluidValid(fluidStack)) {
                fluidStack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithFluid(pEntity, fluidStack, handler.getContainer());
            }
        });
    }

    private static void fillTankWithFluid(RuneMolderBlockEntity pEntity, FluidStack fluidStack, ItemStack container) {
        pEntity.FLUID_TANK.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);

        pEntity.itemHandler.extractItem(2, 1, false);
        pEntity.itemHandler.insertItem(2, container, false);
    }

    private static boolean hasFluidItem(RuneMolderBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(2).getCount() > 0;
    }

    private void resetProgress() {
        this.progress = 0;
    }


    private static void craftItem(RuneMolderBlockEntity pEntity) {

        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<RuneMolderRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(RuneMolderRecipe.Type.INSTANCE, inventory, level);

        if (hasRecipe(pEntity)) {
            pEntity.FLUID_TANK.drain(recipe.get().getFluidStack().getAmount(), IFluidHandler.FluidAction.EXECUTE);
            pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.itemHandler.extractItem(1, 1, false);
            pEntity.itemHandler.setStackInSlot(3, new ItemStack(recipe.get().getResultItem().getItem(),
                    pEntity.itemHandler.getStackInSlot(3).getCount() + 1));

            pEntity.resetProgress();
        }
    }


    private static boolean hasRecipe(RuneMolderBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<RuneMolderRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(RuneMolderRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent() &&
                canInsertAmountInOutputSlot(inventory) &&
                canInsertItemInOutputSlot(inventory, recipe.get().getResultItem()) &&
                hasCorrectFluidInTank(pEntity, recipe) &&
                hasCorrectFluidAmountInTank(pEntity, recipe);
    }

    private static boolean hasCorrectFluidAmountInTank(RuneMolderBlockEntity pEntity, Optional<RuneMolderRecipe> recipe) {
        return pEntity.FLUID_TANK.getFluidAmount() >= recipe.get().getFluidStack().getAmount();
    }


    private static boolean hasCorrectFluidInTank(RuneMolderBlockEntity entity, Optional<RuneMolderRecipe> recipe) {
        return recipe.get().getFluidStack().equals(entity.FLUID_TANK.getFluid());
    }

    private static boolean canInsertItemInOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(3).getItem() == itemStack.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountInOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }
}
