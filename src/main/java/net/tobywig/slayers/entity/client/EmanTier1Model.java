package net.tobywig.slayers.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.entity.custom.EmanTier1Entity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class EmanTier1Model extends AnimatedGeoModel<EmanTier1Entity> {
    @Override
    public ResourceLocation getModelResource(EmanTier1Entity object) {
        return new ResourceLocation(Slayers.MOD_ID, "geo/eman_tier1.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EmanTier1Entity object) {
        return new ResourceLocation(Slayers.MOD_ID, "textures/entity/eman_tier1_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EmanTier1Entity animatable) {
        return new ResourceLocation(Slayers.MOD_ID, "animations/eman_tier1.animation.json");
    }


    @Override
    public void setCustomAnimations(EmanTier1Entity animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);

        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
