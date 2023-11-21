package net.tobywig.slayers.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobywig.slayers.Slayers;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Slayers.MOD_ID);

    public static RegistryObject<Enchantment> VITALITY =
            ENCHANTMENTS.register("vitality",
                    () -> new VitalityEnchantment(Enchantment.Rarity.UNCOMMON,
                            EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.CHEST));



    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
