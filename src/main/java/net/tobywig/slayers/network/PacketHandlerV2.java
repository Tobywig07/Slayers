package net.tobywig.slayers.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.tobywig.slayers.Slayers;
import net.tobywig.slayers.network.packet.c_to_s.FluidSyncPacket;
import net.tobywig.slayers.network.packet.s_to_c.SlayerDataSyncPacket;

public class PacketHandlerV2 {

    private static SimpleChannel INSTANCE;

    private static int packedID = 0;
    private static int id() {
        return packedID++;
    }

    private PacketHandlerV2() {
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Slayers.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions((s -> true))
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(FluidSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(FluidSyncPacket::toBytes)
                .decoder(FluidSyncPacket::new)
                .consumerMainThread(FluidSyncPacket::handle)
                .add();

        net.messageBuilder(SlayerDataSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SlayerDataSyncPacket::new)
                .encoder(SlayerDataSyncPacket::toBytes)
                .consumerMainThread(SlayerDataSyncPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
