package net.tobywig.slayers.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.block.custom.RuneMolderBlock;
import net.tobywig.slayers.item.ModCreativeModeTab;
import net.tobywig.slayers.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Slayers.MOD_ID);

    public static RegistryObject<Block> RUNE_MOLDER = registerBlock("rune_molder",
            () -> new RuneMolderBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3.5f)
                    .requiresCorrectToolForDrops().lightLevel(state -> state.getValue(RuneMolderBlock.LIT) ? 13 : 0)), ModCreativeModeTab.SLAYERS_TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);

        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem (String name, Supplier<T> block, CreativeModeTab tab) {


        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
