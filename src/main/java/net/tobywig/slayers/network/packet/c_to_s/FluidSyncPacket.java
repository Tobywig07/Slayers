package net.tobywig.slayers.network.packet.c_to_s;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import net.tobywig.slayers.block.entity.RuneMolderBlockEntity;
import net.tobywig.slayers.screen.RuneMolderMenu;

import java.util.function.Supplier;

public class FluidSyncPacket {
    private final FluidStack fluidStack;
    private final BlockPos pos;

    public FluidSyncPacket(FluidStack fluidStack, BlockPos pos) {
        this.fluidStack = fluidStack;
        this.pos = pos;
    }

    public FluidSyncPacket(FriendlyByteBuf buf) {
        this.fluidStack = buf.readFluidStack();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFluidStack(fluidStack);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof RuneMolderBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);

                if(Minecraft.getInstance().player.containerMenu instanceof RuneMolderMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluid(this.fluidStack);
                }
            }
        });
        return true;
    }
}
