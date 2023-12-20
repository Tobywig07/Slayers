package net.tobywig.slayers.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.capability.killTracker.PlayerKillTracker;
import net.tobywig.slayers.capability.killTracker.PlayerKillTrackerProvider;
import net.tobywig.slayers.enchantment.ModEnchantments;
import net.tobywig.slayers.enchantment.VitalityEnchantment;
import net.tobywig.slayers.item.ModItems;
import net.tobywig.slayers.network.PacketHandlerV2;
import net.tobywig.slayers.network.packet.s_to_c.SlayerDataSyncPacket;

import java.util.Objects;

@Mod.EventBusSubscriber
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerKillTrackerProvider.PLAYER_KILLS).isPresent()) {
                event.addCapability(new ResourceLocation(Slayers.MOD_ID, "kills"), new PlayerKillTrackerProvider());
            }

        }
    }


    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerKillTrackerProvider.PLAYER_KILLS).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerKillTrackerProvider.PLAYER_KILLS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

        }
    }


    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerKillTracker.class);
    }


    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerKillTrackerProvider.PLAYER_KILLS).ifPresent(kills -> {
                    if (kills.getKillsNeeded() != 0) {
                        PacketHandlerV2.sendToPlayer(new SlayerDataSyncPacket(kills.getCurrentKills(), kills.getKillsNeeded(), kills.getBossId()), player);
                    }
                    else PacketHandlerV2.sendToPlayer(new SlayerDataSyncPacket(0, 0, 0), player);

                });

                Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.1f);
                player.removeTag("Enraged1");
                player.removeTag("Enraged2");
            }
        }
    }


    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (!event.getEntity().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player player && event.getEntity().getMobType() == MobType.UNDEAD) {
                if (event.getEntity().getType() != EntityType.WITHER) {
                    ItemStack item = player.getMainHandItem();
                    if (item.is(ModItems.NECROSWORD.get()) && !event.getEntity().getTags().contains(player.getName().getString())) {
                        if (!item.getOrCreateTag().contains("Soul1")) {
                            item.getOrCreateTag().putString("Soul1", EntityType.getKey(event.getEntity().getType()).toString());
                        } else if (!item.getOrCreateTag().contains("Soul2")) {
                            item.getOrCreateTag().putString("Soul2", EntityType.getKey(event.getEntity().getType()).toString());
                        } else if (!item.getOrCreateTag().contains("Soul3")) {
                            item.getOrCreateTag().putString("Soul3", EntityType.getKey(event.getEntity().getType()).toString());
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void onTargetChange(LivingChangeTargetEvent event) {
        if (event.getNewTarget() instanceof Player player) {
            if (event.getEntity().getTags().contains(player.getName().getString())) {
                if (player.getLastHurtMob() != null && !player.getLastHurtMob().getTags().contains(player.getName().getString())) {
                    event.setNewTarget(player.getLastHurtMob());
                }
                else event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player) {
            int vitLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.VITALITY.get(), event.getEntity());
            if (vitLvl > 0) VitalityEnchantment.healPlayer((Player) event.getEntity(), vitLvl);

        }
    }
}
