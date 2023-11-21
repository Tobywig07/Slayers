package net.tobywig.slayers.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.tobywig.slayers.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class NecroswordItem extends SwordItem {
    public NecroswordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);

    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.getItem() == ModItems.INFESTED_HEART.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);
        if (pUsedHand == InteractionHand.MAIN_HAND && !pLevel.isClientSide()) {

            assert item.getTag() != null;
            pPlayer.getCooldowns().addCooldown(item.getItem(), 200);

            EntityType.byString(item.getTag().getString("Soul1")).ifPresent(soul -> {
                Objects.requireNonNull(soul.spawn((ServerLevel) pLevel, null, Component.literal("Captured Soul"), null, pPlayer.blockPosition(), MobSpawnType.MOB_SUMMONED, true, false)).addTag(pPlayer.getName().getString());
                item.getOrCreateTag().remove("Soul1");
            });

            EntityType.byString(item.getTag().getString("Soul2")).ifPresent(soul -> {
                Objects.requireNonNull(soul.spawn((ServerLevel) pLevel, null, Component.literal("Captured Soul"), null, pPlayer.blockPosition(), MobSpawnType.MOB_SUMMONED, true, false)).addTag(pPlayer.getName().getString());
                item.getOrCreateTag().remove("Soul2");
            });

            EntityType.byString(item.getTag().getString("Soul3")).ifPresent(soul -> {
                Objects.requireNonNull(soul.spawn((ServerLevel) pLevel, null, Component.literal("Captured Soul"), null, pPlayer.blockPosition(), MobSpawnType.MOB_SUMMONED, true, false)).addTag(pPlayer.getName().getString());
                item.getOrCreateTag().remove("Soul3");
            });
        }
        return InteractionResultHolder.success(item);

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        pTooltipComponents.add(Component.literal("Kill an undead mob to capture it's soul, up to 3 souls").withStyle(ChatFormatting.DARK_PURPLE));
        pTooltipComponents.add(Component.literal("Right click to summon captured souls").withStyle(ChatFormatting.DARK_PURPLE));
        pTooltipComponents.add(Component.literal("").withStyle(ChatFormatting.BLACK));

        if (pStack.getTag() != null) {

            if (!pStack.getTag().getString("Soul1").isEmpty()) {
                pTooltipComponents.add(Component.literal("Souls:").withStyle(ChatFormatting.DARK_PURPLE));
                pTooltipComponents.add(Component.literal(pStack.getTag().getString("Soul1")).withStyle(ChatFormatting.DARK_PURPLE));
            }

            if (!pStack.getTag().getString("Soul2").isEmpty()) {
                pTooltipComponents.add(Component.literal(pStack.getTag().getString("Soul2")).withStyle(ChatFormatting.DARK_PURPLE));
            }
            if (!pStack.getTag().getString("Soul3").isEmpty()) {
                pTooltipComponents.add(Component.literal(pStack.getTag().getString("Soul3")).withStyle(ChatFormatting.DARK_PURPLE));
            }
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BLOCK;
    }

}
