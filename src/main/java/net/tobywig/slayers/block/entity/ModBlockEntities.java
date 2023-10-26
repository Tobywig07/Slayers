package net.tobywig.slayers.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.block.ModBlocks;

public class ModBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Slayers.MOD_ID);

    public static final RegistryObject<BlockEntityType<RuneMolderBlockEntity>> RUNE_MOLDER_ENTITY =
            BLOCK_ENTITIES.register("rune_molder_entity", () ->
                    BlockEntityType.Builder.of(RuneMolderBlockEntity::new,
                            ModBlocks.RUNE_MOLDER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
