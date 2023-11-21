package net.tobywig.slayers.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.GameRules;

public class VitalityEnchantment extends Enchantment {
    protected VitalityEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int p_44679_) {
        return 10 * p_44679_;
    }

    @Override
    public int getMaxCost(int p_44691_) {
        return this.getMinCost(p_44691_) + 30;
    }


    public static void healPlayer(Player player, int level) {
        if(level > 0 && player.getLevel().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION) && player.getFoodData().getFoodLevel() >= 18 && player.getHealth() < player.getMaxHealth() && player.tickCount % (60/level) == 0) {
            player.heal((0.5f));
        }
    }
}
