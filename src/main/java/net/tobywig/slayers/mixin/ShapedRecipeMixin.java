package net.tobywig.slayers.mixin;

import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.tobywig.slayers.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapedRecipe.class)
public abstract class ShapedRecipeMixin {

    @Shadow public abstract ItemStack getResultItem();

    @Inject(at = @At(value = "HEAD"), method = "assemble*", cancellable = true)
    public void assemble(CraftingContainer pInv, CallbackInfoReturnable<ItemStack> info) {
        if (getResultItem().is(ModItems.DREADED_SWORD.get()) || getResultItem().is(ModItems.REAPER_SWORD.get())) {
            ItemStack sword = pInv.getItem(7);
            ItemStack result = getResultItem().copy();

            EnchantmentHelper.setEnchantments(sword.getAllEnchantments(), result);
            info.setReturnValue(result);
        }
    }
}
