package net.tobywig.slayers;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tobywig.slayers.block.ModBlocks;
import net.tobywig.slayers.block.entity.ModBlockEntities;
import net.tobywig.slayers.enchantment.ModEnchantments;
import net.tobywig.slayers.entity.ModEntities;
import net.tobywig.slayers.entity.client.EmanTier1Renderer;
import net.tobywig.slayers.item.ModItems;
import net.tobywig.slayers.network.PacketHandlerV2;
import net.tobywig.slayers.recipe.custom.ModRecipes;
import net.tobywig.slayers.screen.ModMenuTypes;
import net.tobywig.slayers.screen.RuneMolderScreen;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(Slayers.MOD_ID)
public class Slayers {
    public static final String MOD_ID = "slayers";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Slayers() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        GeckoLib.initialize();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModEnchantments.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModEntities.register(modEventBus);

        ModRecipes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandlerV2.register();
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.RUNE_MOLDER_MENU.get(), RuneMolderScreen::new);

            EntityRenderers.register(ModEntities.EMAN_TIER1.get(), EmanTier1Renderer::new);
        }
    }
}
