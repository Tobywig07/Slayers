package net.tobywig.slayers.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.tobywig.slayers.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HealingStaffItem extends Item {
    public HealingStaffItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);

        // server side
        if (!pLevel.isClientSide()) {
            if (item.getRarity() == Rarity.UNCOMMON) {
                pPlayer.heal(2);
                pPlayer.getCooldowns().addCooldown(pPlayer.getInventory().getItem(pPlayer.getInventory().findSlotMatchingItem(ModItems.SALVATION_STAFF.get().getDefaultInstance())).getItem(), 100);
            }

            if (item.getRarity() == Rarity.RARE) {
                pPlayer.heal(4);
                pPlayer.getCooldowns().addCooldown(pPlayer.getInventory().getItem(pPlayer.getInventory().findSlotMatchingItem(ModItems.RECOVERY_STAFF.get().getDefaultInstance())).getItem(), 100);

            }


            pPlayer.getCooldowns().addCooldown(item.getItem(), 100);

        }

        // client side
        if (pLevel.isClientSide()) {
            pLevel.playSound(pPlayer, pPlayer.blockPosition(), SoundEvents.AMETHYST_BLOCK_STEP, SoundSource.PLAYERS, 0.5f, 1);

            for (int i = 0; i < 360; i++) {
                if (i % 10 == 0) {
                        for (double j = -1; j < 1; j = j + 0.1d) {
                            pLevel.addParticle(ParticleTypes.END_ROD,
                                    pPlayer.getX(), pPlayer.getY() + 1.5d, pPlayer.getZ(),
                                    Math.cos(i) * j, j, Math.sin(i) * j);
                        }

                }
            }
        }


        return InteractionResultHolder.consume(item);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.getRarity() == Rarity.UNCOMMON) {
            pTooltipComponents.add(Component.literal("Right click to heal 2 health").withStyle(ChatFormatting.RED));
        }
        else pTooltipComponents.add(Component.literal("Right click to heal 4 health").withStyle(ChatFormatting.RED));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
