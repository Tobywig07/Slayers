package net.tobywig.slayers.network.packet.s_to_c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.tobywig.slayers.client.ClientKillTrackerData;

import java.util.function.Supplier;

public class SlayerDataSyncPacket {
    private final int kills;
    private final int maxKills;

    public SlayerDataSyncPacket(int kills, int maxKills) {
        this.kills = kills;
        this.maxKills = maxKills;
    }

    public SlayerDataSyncPacket(FriendlyByteBuf buf) {
        this.kills = buf.readInt();
        this.maxKills = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(kills);
        buf.writeInt(maxKills);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientKillTrackerData.set(kills, maxKills);

        });
        return true;
    }
}
