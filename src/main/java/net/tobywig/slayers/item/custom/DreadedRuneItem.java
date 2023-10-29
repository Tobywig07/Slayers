package net.tobywig.slayers.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tobywig.slayers.capability.killAmount.PlayerKillsProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DreadedRuneItem extends Item {
    public DreadedRuneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide() && pUsedHand == InteractionHand.MAIN_HAND) {

            ItemStack item = pPlayer.getMainHandItem();

            // tier 1
            if (item.getRarity() == Rarity.UNCOMMON) {
                pPlayer.getCapability(PlayerKillsProvider.PLAYER_KILLS).ifPresent(kills -> {
                    kills.resetKills();
                    kills.setKillsNeeded(10);
                });
            }

            // tier 2
            if (item.getRarity() == Rarity.RARE) {
                pPlayer.getCapability(PlayerKillsProvider.PLAYER_KILLS).ifPresent(kills -> {
                    kills.resetKills();
                    kills.setKillsNeeded(20);
                });
            }

            // tier 3
            if (item.getRarity() == Rarity.EPIC) {
                pPlayer.getCapability(PlayerKillsProvider.PLAYER_KILLS).ifPresent(kills -> {
                    kills.resetKills();
                    kills.setKillsNeeded(30);
                });
            }

            // add particles
            pLevel.playSound(null, pPlayer.blockPosition(), SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 1, 2);
            item.setCount(item.getCount() -1);

        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        // tier 1
        if (pStack.getRarity() == Rarity.UNCOMMON) {
            pTooltipComponents.add(Component.literal("Tier: 1").withStyle(ChatFormatting.DARK_GREEN));
            pTooltipComponents.add(Component.literal("Right click to start quest!").withStyle(ChatFormatting.DARK_RED));
        }

        // tier 2
        if (pStack.getRarity() == Rarity.RARE) {
            pTooltipComponents.add(Component.literal("Tier: 2").withStyle(ChatFormatting.DARK_GREEN));
            pTooltipComponents.add(Component.literal("Right click to start quest!").withStyle(ChatFormatting.DARK_RED));
        }

        // tier 3
        if (pStack.getRarity() == Rarity.EPIC) {
            pTooltipComponents.add(Component.literal("Tier: 3").withStyle(ChatFormatting.DARK_GREEN));
            pTooltipComponents.add(Component.literal("Right click to start quest!").withStyle(ChatFormatting.DARK_RED));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
