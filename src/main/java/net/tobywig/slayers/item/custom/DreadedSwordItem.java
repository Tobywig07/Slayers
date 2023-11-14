package net.tobywig.slayers.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.tobywig.slayers.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DreadedSwordItem extends SwordItem {
    public DreadedSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.getItem() == ModItems.DREADED_FLESH.get();
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        pTooltipComponents.add(Component.literal("Deals 50% more damage to zombies").withStyle(ChatFormatting.DARK_GREEN));

        pTooltipComponents.add(Component.literal("").withStyle(ChatFormatting.BLACK));

        pTooltipComponents.add(Component.literal("Right click to Enrage:").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.add(Component.literal("+ 1 base damage").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.add(Component.literal("+ 25% speed").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.add(Component.literal("- rapidly drains hunger").withStyle(ChatFormatting.DARK_RED));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BLOCK;
    }

}
