package me.monmcgt.code.bedwarsstatslunarinject.apis.responses;

import com.google.gson.annotations.SerializedName;

public class HypixelStats$Response {
    public boolean success;
    public Player player;

    public static class Player {
        @SerializedName("_id")
        public String id;
        public String uuid;
        public String displayname;
        public Stats stats;
        public Achievements achievements;

        public static class Stats {
            @SerializedName("Bedwars")
            public Bedwars bedwars;

            public static class Bedwars {
                public String final_kills_bedwars;
                public String final_deaths_bedwars;
                public String wins_bedwars;
                public String losses_bedwars;
                public String winstreak;
            }
        }

        public static class Achievements {
            public String bedwars_level;
        }
    }
}
