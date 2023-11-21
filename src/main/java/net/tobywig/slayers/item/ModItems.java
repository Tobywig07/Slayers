package net.tobywig.slayers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.item.custom.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Slayers.MOD_ID);

    // base
    public static final RegistryObject<Item> EMPTY_RUNE = ITEMS.register("empty_rune",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.SLAYERS_TAB)));

    public static final RegistryObject<Item> BASIC_RUNE = ITEMS.register("basic_rune",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.SLAYERS_TAB)));

    public static final RegistryObject<Item> ADVANCED_RUNE = ITEMS.register("advanced_rune",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.SLAYERS_TAB)));

    public static final RegistryObject<Item> SUPREME_RUNE = ITEMS.register("supreme_rune",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.SLAYERS_TAB)));


    // zombie slayer runes
    public static final RegistryObject<Item> DREADED_RUNE_T1 = ITEMS.register("dreaded_rune_t1",
            () -> new DreadedRuneItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DREADED_RUNE_T2 = ITEMS.register("dreaded_rune_t2",
            () -> new DreadedRuneItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DREADED_RUNE_T3 = ITEMS.register("dreaded_rune_t3",
            () -> new DreadedRuneItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.EPIC)));


    // zombie slayer misc
    public static final RegistryObject<Item> REACTOR_CORE = ITEMS.register("reactor_core",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> CORRUPTED_EYE = ITEMS.register("corrupted_eye",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.UNCOMMON)));


    // zombie slayer drops
    public static final RegistryObject<Item> DREADED_FLESH = ITEMS.register("dreaded_flesh",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> INFESTED_HEART = ITEMS.register("infested_heart",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> CONTAGED_HEART = ITEMS.register("contaged_heart",
            () -> new Item(new Item.Properties().stacksTo(16).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.EPIC)));


    // zombie slayer weaponry
    public static final RegistryObject<Item> RECOVERY_STAFF = ITEMS.register("recovery_staff",
            () -> new HealingStaffItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SALVATION_STAFF = ITEMS.register("salvation_staff",
            () -> new HealingStaffItem(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.RARE)));


    public static final RegistryObject<Item> UNDEAD_SWORD = ITEMS.register("undead_sword",
            () -> new UndeadSwordItem(Tiers.IRON, 4, -2.4f, new Item.Properties().stacksTo(1)
                    .tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.UNCOMMON).durability(512)));

    public static final RegistryObject<Item> DREADED_SWORD = ITEMS.register("dreaded_sword",
            () -> new DreadedSwordItem(Tiers.IRON, 4, -2.4f, new Item.Properties().stacksTo(1)
                    .tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.RARE).durability(1562)));

    public static final RegistryObject<Item> REAPER_SWORD = ITEMS.register("reaper_sword",
            () -> new ReaperSwordItem(Tiers.IRON, 4, -2.4f, new Item.Properties().stacksTo(1)
                    .tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.EPIC).durability(2032)));

    public static final RegistryObject<Item> NECROSWORD = ITEMS.register("necrosword",
            () -> new NecroswordItem(Tiers.NETHERITE, 3, -2.4f, new Item.Properties().stacksTo(1)
                    .tab(ModCreativeModeTab.SLAYERS_TAB).rarity(Rarity.EPIC).durability(3048)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
