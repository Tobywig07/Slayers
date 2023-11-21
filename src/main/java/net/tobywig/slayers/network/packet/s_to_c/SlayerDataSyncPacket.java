package net.tobywig.slayers.network.packet.s_to_c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.tobywig.slayers.client.ClientKillTrackerData;

import java.util.function.Supplier;

public class SlayerDataSyncPacket {
    private final int kills;
    private final int maxKills;
    private final int bossID;

    public SlayerDataSyncPacket(int kills, int maxKills, int bossID) {
        this.kills = kills;
        this.maxKills = maxKills;
        this.bossID = bossID;
    }

    public SlayerDataSyncPacket(FriendlyByteBuf buf) {
        this.kills = buf.readInt();
        this.maxKills = buf.readInt();
        this.bossID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(kills);
        buf.writeInt(maxKills);
        buf.writeInt(bossID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientKillTrackerData.set(kills, maxKills, bossID);

        });
        return true;
    }
}
