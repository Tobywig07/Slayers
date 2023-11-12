package net.tobywig.slayers.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.capability.killTracker.PlayerKillTracker;
import net.tobywig.slayers.capability.killTracker.PlayerKillTrackerProvider;
import net.tobywig.slayers.network.PacketHandlerV2;
import net.tobywig.slayers.network.packet.s_to_c.SlayerDataSyncPacket;

@Mod.EventBusSubscriber
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerKillTrackerProvider.PLAYER_KILLS).isPresent()) {
                event.addCapability(new ResourceLocation(Slayers.MOD_ID, "properties"), new PlayerKillTrackerProvider());
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
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerKillTrackerProvider.PLAYER_KILLS).ifPresent(kills -> {
                    if (kills.getKillsNeeded() != 0) {
                        PacketHandlerV2.sendToPlayer(new SlayerDataSyncPacket(kills.getCurrentKills(), kills.getKillsNeeded()), player);
                    }
                    else PacketHandlerV2.sendToPlayer(new SlayerDataSyncPacket(0, 0), player);

                });
            }
        }
    }

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (!event.getEntity().level.isClientSide()) {
            if (event.getEntity() instanceof Zombie zombie && event.getSource().getEntity() instanceof Player player) {
                player.getCapability(PlayerKillTrackerProvider.PLAYER_KILLS).ifPresent(kills -> {
                    kills.addKill(1);

                    if (kills.getKillsNeeded() != 0) {
                        PacketHandlerV2.sendToPlayer(new SlayerDataSyncPacket(kills.getCurrentKills(), kills.getKillsNeeded()), (ServerPlayer) player);
                    }

                    if (kills.getKillsNeeded() != 0 && kills.killsReached()) {
                        kills.resetKills();
                        PacketHandlerV2.sendToPlayer(new SlayerDataSyncPacket(0, 0), (ServerPlayer) player);


                        EntityType.ZOMBIE.spawn( (ServerLevel) event.getEntity().level, null, Component.literal("Test boss").withStyle(ChatFormatting.DARK_GREEN), null, event.getEntity().blockPosition(), MobSpawnType.EVENT, true, false);

                    }
                });

                if (zombie.getDisplayName().getString().equals("Test boss")) {
                    // drops
                    ItemStack item = new ItemStack(Items.ENCHANTED_BOOK);
                    EnchantedBookItem.addEnchantment(item, new EnchantmentInstance(Enchantments.SMITE, 6));
                    zombie.spawnAtLocation(item);
                }
            }

        }
    }

}
