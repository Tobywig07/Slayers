package net.tobywig.slayers.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.client.KillTrackerHudOverlay;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = Slayers.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

    }

    @Mod.EventBusSubscriber(modid = Slayers.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("slayer_hud", KillTrackerHudOverlay.HUD_SLAYER);
        }
    }
}
