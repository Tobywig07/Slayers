package net.tobywig.slayers.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DreadedRuneItem extends Item {
    public DreadedRuneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide() && pUsedHand == InteractionHand.MAIN_HAND) {
            /* TODO:
            - add particle/sound effect
            - create player nbt for kill tracking
            - check if nbt int has reached certain amount -> spawn boss
            - add tier system (1 - 3)
             */

            // delete item
            ItemStack item = pPlayer.getMainHandItem();
            item.setCount(item.getCount() -1);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.hasTag()) {
            String tier = pStack.getTag().getString("slayers:tier");
            pTooltipComponents.add(Component.literal(tier));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
