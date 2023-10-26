package net.tobywig.slayers.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab SLAYERS_TAB = new CreativeModeTab("slayerstab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.EMPTY_RUNE.get());
        }
    };
}
