package me.monmcgt.code.bedwarsstatslunarinject.consumers;

import me.monmcgt.code.bedwarsstatslunarinject.apis.inject.InjectMain;
import me.monmcgt.code.bedwarsstatslunarinject.apis.responses.HypixelStats$Response;
import me.monmcgt.code.bedwarsstatslunarinject.stats.BedwarsStatsData;
import me.monmcgt.code.helpers.FormatHelperJava;

import java.util.function.Consumer;

public class HypixelStatsConsumer/* implements Consumer<HypixelStats$Response> */ {
    //    @Override
    public void accept(HypixelStats$Response hypixelStats$Response, Consumer<BedwarsStatsData> consumer) {
//        Debug.println("[HypixelStatsConsumer] Accepting HypixelStats$Response...");

        String displayname = hypixelStats$Response.player.displayname;

        if (hypixelStats$Response.success) {
//            System.out.println("Successfully retrieved Hypixel stats! (username: " + displayname + ")");
//            Debug.println("[HypixelStatsConsumer] Successfully retrieved Hypixel stats! (username: " + displayname + ")");

            if (consumer != null) {
//                Debug.println("[HypixelStatsConsumer] Calling consumer...");
                if (/*hypixelStats$Response.player != null && */hypixelStats$Response.player.stats != null) {
                    HypixelStats$Response.Player.Stats.Bedwars bedwars = hypixelStats$Response.player.stats.bedwars;

//                    Debug.println("Not nicked: " + displayname);
//                    Debug.println("[HypixelStatsConsumer] Not nicked: " + displayname);

                    /*Debug.println("[HypixelStatsConsumer] Casting to KtHypixelStatsResponse...");
                    KtHypixelStatsResponse hypixelStats$Response1 = (KtHypixelStatsResponse) hypixelStats$Response;
                    Debug.println("[HypixelStatsConsumer] Casting to KtHypixelStatsResponse...DONE");*/

                    consumer.accept(new BedwarsStatsData(
                            Integer.parseInt(hypixelStats$Response.player.achievements.bedwars_level),
                            Float.parseFloat(bedwars.final_deaths_bedwars) / Float.parseFloat(bedwars.final_kills_bedwars),
                            Float.parseFloat(bedwars.wins_bedwars) / Float.parseFloat(bedwars.losses_bedwars),
                            Integer.parseInt(bedwars.winstreak),
//                            KtHypixelStatsResponse.Companion.newInstance(hypixelStats$Response)
                            hypixelStats$Response

                    ));
                } else {
//                    Debug.println("Nicked: " + displayname);
//                    Debug.println("[HypixelStatsConsumer] Nicked: " + displayname);

                    consumer.accept(new BedwarsStatsData(
                            -1,
                            -1,
                            -1,
                            -1,
//                            KtHypixelStatsResponse.Companion.newInstance(hypixelStats$Response)
                            hypixelStats$Response
                    ));
                }
//                Debug.println("[HypixelStatsConsumer - Consumer] Successfully retrieved Hypixel stats! (username: " + displayname + ")");
            } else {
                String message;

                if (hypixelStats$Response.player != null && hypixelStats$Response.player.stats != null) {
                    HypixelStats$Response.Player.Stats.Bedwars bedwars = hypixelStats$Response.player.stats.bedwars;

                    String bedwarsLevel = hypixelStats$Response.player.achievements.bedwars_level;
                    String final_kills_bedwars = bedwars.final_kills_bedwars;
                    String final_deaths_bedwars = bedwars.final_deaths_bedwars;
                    String wins = bedwars.wins_bedwars;
                    String losses = bedwars.losses_bedwars;
                    String fkdr = String.format("%.2f", Double.parseDouble(final_kills_bedwars) / Double.parseDouble(final_deaths_bedwars));
                    String winrate = String.format("%.2f", Double.parseDouble(wins) / Double.parseDouble(losses));
                    String winstreak = bedwars.winstreak;

                    if (winstreak == null) {
                        winstreak = "?";
                    }

//            System.out.println("Bedwars Level: " + bedwarsLevel + " | Final Kills: " + final_kills_bedwars + " | Final Deaths: " + final_deaths_bedwars + " | FKDR: " + fkdr);


//            Main.gui.addDisplayName(bedwarsLevel, displayname, fkdr);

                    message = FormatHelperJava.formatStats(bedwarsLevel, displayname, fkdr, winrate, winstreak);
                } else {
                    message = FormatHelperJava.formatStats(null, displayname, "1", "1", "1");
                }

                InjectMain.addChat(message);
            }
        } else {
            System.err.println("Failed to retrieve Hypixel stats! (username: " + displayname + ")");
        }
    }
}
