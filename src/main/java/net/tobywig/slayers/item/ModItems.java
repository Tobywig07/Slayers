package net.tobywig.slayers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
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

    public static final RegistryObject<Item> BASIC_RUNE = ITEMS.register("basic_rune",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.SLAYERS_TAB)));

    public static final RegistryObject<Item> ADVANCED_RUNE = ITEMS.register("advanced_rune",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.SLAYERS_TAB)));

    public static final RegistryObject<Item> SUPREME_RUNE = ITEMS.register("supreme_rune",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.SLAYERS_TAB)));

    public static final RegistryObject<Item> DREADED_RUNE_T1 = ITEMS.register("dreaded_rune_t1",
            () -> new DreadedRuneItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DREADED_RUNE_T2 = ITEMS.register("dreaded_rune_t2",
            () -> new DreadedRuneItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DREADED_RUNE_T3 = ITEMS.register("dreaded_rune_t3",
            () -> new DreadedRuneItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.EPIC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
