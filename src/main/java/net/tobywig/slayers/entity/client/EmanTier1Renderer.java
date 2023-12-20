package net.tobywig.slayers.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.entity.custom.EmanTier1Entity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EmanTier1Renderer extends GeoEntityRenderer<EmanTier1Entity> {
    public EmanTier1Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EmanTier1Model());

        this.shadowRadius = 0.75f;
    }

    @Override
    public ResourceLocation getTextureLocation(EmanTier1Entity animatable) {
        return new ResourceLocation(Slayers.MOD_ID, "textures/entity/eman_tier1_texture.png");
    }

    @Override
    public RenderType getRenderType(EmanTier1Entity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {

        poseStack.scale(1.25f, 1.25f, 1.25f);

        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
