package net.tobywig.slayers.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.entity.custom.EmanTier1Entity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Slayers.MOD_ID);

    public static final RegistryObject<EntityType<EmanTier1Entity>> EMAN_TIER1 =
            ENTITY_TYPES.register("eman_tier1",
                    () -> EntityType.Builder.of(EmanTier1Entity::new, MobCategory.MONSTER)
                            .sized(1.5f, 3.5f)
                            .build(new ResourceLocation(Slayers.MOD_ID, "eman_tier1").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
