package net.tobywig.slayers.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tobywig.slayers.item.ModItems;

import java.util.Objects;

@Mod.EventBusSubscriber
public class UndeadSwordEvents {

    // sets damage and cooldown of the sword
    @SubscribeEvent
    public static void onUseItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack item = event.getItemStack();
        Player player = event.getEntity();

        if (item.getItem() == ModItems.DREADED_SWORD.get()) {
            if (!player.getCooldowns().isOnCooldown(ModItems.REAPER_SWORD.get())) {
                player.getCooldowns().addCooldown(item.getItem(), 800);

                Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.125f);
                player.addTag("Enraged1");

            }
            else if (!event.getLevel().isClientSide()) {
                player.sendSystemMessage(Component.literal("You have already enraged recently!").withStyle(ChatFormatting.DARK_RED));
            }

        }

        if (item.getItem() == ModItems.REAPER_SWORD.get()) {
            if (!player.getCooldowns().isOnCooldown(ModItems.DREADED_SWORD.get())) {
                player.getCooldowns().addCooldown(item.getItem(), 800);

                Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.15f);
                player.addTag("Enraged2");

            }
            else if (!event.getLevel().isClientSide()) {
                player.sendSystemMessage(Component.literal("You have already enraged recently!").withStyle(ChatFormatting.DARK_RED));
            }
        }
    }

    // resets back to base attributes, else gives exhaustion
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;

            if (player.getTags().contains("Enraged1") || player.getTags().contains("Enraged2")) {
                if (player.getFoodData().getFoodLevel() < 1) {

                    Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.1f);
                    player.removeTag("Enraged1");
                    player.removeTag("Enraged2");
                }
                else player.getFoodData().addExhaustion(0.25f);

            }
    }

    // blocks you from eating to avoid infinite buffs
    @SubscribeEvent
    public static void onEat(LivingEntityUseItemEvent event) {
        if (event.getItem().isEdible()) {
            if (event.getEntity().getTags().contains("Enraged1") || event.getEntity().getTags().contains("Enraged2")) {
                event.setCanceled(true);
            }
        }
    }


    // increases damage to zombies
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            if (attacker.getMainHandItem().getItem() == ModItems.UNDEAD_SWORD.get() && event.getEntity() instanceof Zombie) {
                event.setAmount(event.getAmount() * 1.2f);
            }
            if (attacker.getMainHandItem().getItem() == ModItems.DREADED_SWORD.get() && event.getEntity() instanceof Zombie) {
                event.setAmount(event.getAmount() * 1.5f);
            }
            if (attacker.getMainHandItem().getItem() == ModItems.REAPER_SWORD.get() && event.getEntity() instanceof Zombie) {
                event.setAmount(event.getAmount() * 1.75f);
            }

            if (attacker.getTags().contains("Enraged1")) {
                event.setAmount(event.getAmount() + 2);
            }
            else if (attacker.getTags().contains("Enraged2")) {
                event.setAmount(event.getAmount() + 4);
            }
        }

    }
}
