package net.tobywig.slayers.client;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.tobywig.slayers.Slayers;

public class KillTrackerHudOverlay {
    private static final ResourceLocation FILLED_SLAYER = new ResourceLocation(Slayers.MOD_ID,
            "textures/gui/slayer_hud_full.png");
    private static final ResourceLocation EMPTY_SLAYER = new ResourceLocation(Slayers.MOD_ID,
            "textures/gui/slayer_hud_empty.png");

    public static final IGuiOverlay HUD_SLAYER = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        if (ClientKillTrackerData.getPlayerMaxKills() != 0) {

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, EMPTY_SLAYER);
            GuiComponent.blit(poseStack,x + 9, y - 45,0,0,81,5,81,5);

                RenderSystem.setShaderTexture(0, FILLED_SLAYER);
                GuiComponent.blit(
                        poseStack,
                        x + 9, // where to draw x
                        y - 45, // where to draw y
                        0, // start x
                        0, // start y
                        (int) ClientKillTrackerData.getPlayerScaledKills(), // end x
                        5, // end y
                        81, // imageWidth
                        5); // imageHeight

        }

    });
    
}
