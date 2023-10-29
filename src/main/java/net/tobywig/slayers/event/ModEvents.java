package net.tobywig.slayers.event;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.ZombieEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.capability.killAmount.PlayerKills;
import net.tobywig.slayers.capability.killAmount.PlayerKillsProvider;

@Mod.EventBusSubscriber
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerKillsProvider.PLAYER_KILLS).isPresent()) {
                event.addCapability(new ResourceLocation(Slayers.MOD_ID, "properties"), new PlayerKillsProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerKillsProvider.PLAYER_KILLS).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerKillsProvider.PLAYER_KILLS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerKills.class);
    }

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (!event.getEntity().level.isClientSide()) {
            if (event.getEntity() instanceof Zombie && event.getSource().getEntity() instanceof Player player) {
                player.getCapability(PlayerKillsProvider.PLAYER_KILLS).ifPresent(kills -> {
                    kills.addKill(1);

                    if (kills.getKillsNeeded() != 0 && kills.killsReached()) {
                        kills.resetKills();

                        player.sendSystemMessage(Component.literal("spawn boss"));
                    }
                });
            }
        }
    }
}
