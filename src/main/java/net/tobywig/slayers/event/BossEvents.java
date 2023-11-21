package net.tobywig.slayers.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tobywig.slayers.capability.killTracker.PlayerKillTrackerProvider;
import net.tobywig.slayers.item.ModItems;
import net.tobywig.slayers.network.PacketHandlerV2;
import net.tobywig.slayers.network.packet.s_to_c.SlayerDataSyncPacket;

import java.util.Random;

@Mod.EventBusSubscriber
public class BossEvents {

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (!event.getEntity().level.isClientSide()) {
            if (event.getEntity() instanceof Zombie zombie && event.getSource().getEntity() instanceof Player player) {
                player.getCapability(PlayerKillTrackerProvider.PLAYER_KILLS).ifPresent(kills -> {
                    kills.addKill(1);

                    if (kills.getKillsNeeded() != 0) {
                        PacketHandlerV2.sendToPlayer(new SlayerDataSyncPacket(kills.getCurrentKills(), kills.getKillsNeeded(), kills.getBossId()), (ServerPlayer) player);
                    }

                    if (kills.getKillsNeeded() != 0 && kills.killsReached()) {
                        // spawn boss
                        if (kills.getBossId() == 1) {
                            EntityType.ZOMBIE.spawn( (ServerLevel) event.getEntity().level, null, Component.literal("Test boss").withStyle(ChatFormatting.DARK_GREEN), null, event.getEntity().blockPosition(), MobSpawnType.EVENT, true, false);
                        }


                        kills.resetKills();
                        PacketHandlerV2.sendToPlayer(new SlayerDataSyncPacket(0, 0, 0), (ServerPlayer) player);

                    }
                });

                if (zombie.getDisplayName().getString().equals("Test boss")) {
                    // drops
                    ItemStack item = new ItemStack(Items.ENCHANTED_BOOK);
                    EnchantedBookItem.addEnchantment(item, new EnchantmentInstance(Enchantments.SMITE, 6));
                    zombie.spawnAtLocation(item);

                    Random random = new Random();
                    for (int i = 0; i < random.nextInt(2); i++) {
                        player.sendSystemMessage(Component.literal(String.valueOf(i)));
                        zombie.spawnAtLocation(new ItemStack(ModItems.DREADED_FLESH.get()));
                    }
                }
            }


            // resets kill counter on death
            if (event.getEntity() instanceof Player player) {
                player.getCapability(PlayerKillTrackerProvider.PLAYER_KILLS).ifPresent(kills -> {
                    if (kills.getKillsNeeded() != 0) {
                        kills.resetKills();
                    }
                });
            }
        }
    }
}
