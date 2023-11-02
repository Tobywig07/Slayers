package net.tobywig.slayers.capability.killTracker;

import net.minecraft.nbt.CompoundTag;

public class PlayerKillTracker {
    private int kills;
    private int killsNeeded;
    private int bossID;


    public int getCurrentKills() {
        return kills;
    }

    public void resetKills() {
        this.kills = 0;
        this.killsNeeded = 0;
    }

    public int getKillsNeeded() {
        return killsNeeded;
    }


    public void setKillsNeeded(int amount) {
        this.killsNeeded = amount;
    }

    public Boolean killsReached() {
        return kills == killsNeeded;
    }


    public void addKill(int add) {
        this.kills = Math.min(kills + add, killsNeeded);
    }


    public void copyFrom(PlayerKillTracker source) {
        this.kills = source.kills;
        this.killsNeeded = source.killsNeeded;
    }


    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("kills", kills);
        nbt.putInt("killsNeeded", killsNeeded);
    }

    public void loadNBTData(CompoundTag nbt) {
        kills = nbt.getInt("kills");
        killsNeeded = nbt.getInt("killsNeeded");
    }
}
