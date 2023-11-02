package net.tobywig.slayers.client;

public class ClientKillTrackerData {

    private static int playerKills;
    private static int playerMaxKills;

    public static void set(int kills, int maxKills) {
        ClientKillTrackerData.playerKills = kills;
        ClientKillTrackerData.playerMaxKills = maxKills;
    }

    public static int getPlayerKills() {
        return playerKills;
    }

    public static int getPlayerMaxKills() {
        return playerMaxKills;
    }

    public static float getPlayerScaledKills() {
        if (playerMaxKills != 0) {
            return 81 * ((float) playerKills / playerMaxKills);
        }
        else return 0;
    }
}
