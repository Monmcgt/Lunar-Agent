package me.monmcgt.code.bedwarsstatslunarinject.consumers;

import com.google.gson.Gson;
import me.monmcgt.code.bedwarsstatslunarinject.apis.ApiMain;
import me.monmcgt.code.bedwarsstatslunarinject.apis.inject.InjectMain;
import me.monmcgt.code.bedwarsstatslunarinject.apis.responses.HypixelStats$Response;
import me.monmcgt.code.bedwarsstatslunarinject.apis.responses.PlayerDB$Response;
import me.monmcgt.code.bedwarsstatslunarinject.stats.BedwarsStatsData;
import me.monmcgt.code.config.ConfigManager;
import me.monmcgt.code.helpers.FormatHelperKt;
import me.monmcgt.code.helpers.HttpHelper;

import java.net.HttpURLConnection;
import java.util.function.Consumer;

import static me.monmcgt.code.helpers.FormatHelperJava.red;

public class PlayerDBConsumer /*implements Consumer<PlayerDB$Response>*/ {
    private static final Gson gson = new Gson();

    private static HypixelStatsConsumer hypixelStatsConsumer = new HypixelStatsConsumer();

//    @Override
    public void accept(PlayerDB$Response playerDB$Response, Consumer<BedwarsStatsData> consumer) {
//        Debug.println("[PlayerDBConsumer] Accepting PlayerDB$Response...");
        /*if (Objects.equals(playerDB$Response.code, "500")) {
            hypixelStatsConsumer.accept();
        }*/

        if (playerDB$Response.success) {
//            System.out.println("Successfully retrieved player data from PlayerDB (username: " + playerDB$Response.data.player.username + ")");

            String uuid = playerDB$Response.data.player.id;

            String format = "%splayer?key=%s&uuid=%s";
//            System.out.println("format = " + format);
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = HttpHelper.getGet(String.format(format, ApiMain.HYPIXEL_API_LINK, ConfigManager.INSTANCE.getConfig().getHypixelApiKey(), uuid));
            } catch (RuntimeException e) {
                String errMessage = e.getMessage();
                if (errMessage.startsWith("r_code:")) {
                    String errorCode = errMessage.substring(errMessage.indexOf(":") + 1);
                    errorCode = errorCode.substring(0, 3);
                    /*if (errorCode.equals("403")) {
                        String message = red + "Error occurred. Please check your game's logs for more information.";
                        InjectMain.addChat(FormatHelperKt.addPrefix(message));
                        return;
                    }*/
                    String message = red + "Error occurred. Please check your game's logs for more information.";
                    InjectMain.addChat(FormatHelperKt.addPrefix(message));
                    return;
                }
            }

            HypixelStats$Response hypixelStats$Response = gson.fromJson(HttpHelper.sendGetString(httpURLConnection), HypixelStats$Response.class);

            if (hypixelStatsConsumer != null && hypixelStats$Response != null) {
                hypixelStatsConsumer.accept(hypixelStats$Response, consumer);
            }
        } else {
            System.err.println("Failed to retrieve player data from PlayerDB (username: " + playerDB$Response.data.player.username + ")");
        }
    }
}
