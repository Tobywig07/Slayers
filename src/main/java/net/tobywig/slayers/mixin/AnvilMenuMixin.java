package net.tobywig.slayers.mixin;

import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I", ordinal = 1), method = "createResult")
    public int allowTier6(Enchantment enchantment) {

        if (enchantment == Enchantments.SMITE || enchantment == Enchantments.SHARPNESS || enchantment == Enchantments.BANE_OF_ARTHROPODS) {
            return 6;
        }

        else return enchantment.getMaxLevel();
    }
}
