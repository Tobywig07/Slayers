package net.tobywig.slayers.client;

public class ClientKillTrackerData {

    private static int playerKills;
    private static int playerMaxKills;
    private static int bossID;

    public static void set(int kills, int maxKills, int bossID) {
        ClientKillTrackerData.playerKills = kills;
        ClientKillTrackerData.playerMaxKills = maxKills;
        ClientKillTrackerData.bossID = bossID;
    }

    public static int getPlayerKills() {
        return playerKills;
    }

    public static int getPlayerMaxKills() {
        return playerMaxKills;
    }

    public static int getBossID() {
        return bossID;
    }

    public static float getPlayerScaledKills() {
        if (playerMaxKills != 0) {
            return 81 * ((float) playerKills / playerMaxKills);
        }
        else return 0;
    }
}
