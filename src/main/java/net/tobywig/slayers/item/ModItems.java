package net.tobywig.slayers.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.item.custom.DreadedRuneItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Slayers.MOD_ID);

    public static final RegistryObject<Item> EMPTY_RUNE = ITEMS.register("empty_rune",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.SLAYERS_TAB)));

    public static final RegistryObject<Item> DREADED_RUNE = ITEMS.register("dreaded_rune",
            () -> new DreadedRuneItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.SLAYERS_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
