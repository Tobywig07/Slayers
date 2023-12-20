package net.tobywig.slayers.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.entity.ModEntities;
import net.tobywig.slayers.entity.custom.EmanTier1Entity;

@Mod.EventBusSubscriber(modid = Slayers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.EMAN_TIER1.get(), EmanTier1Entity.setAttributes());
    }
}
