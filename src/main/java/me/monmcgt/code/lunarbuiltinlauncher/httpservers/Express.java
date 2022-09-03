package me.monmcgt.code.lunarbuiltinlauncher.httpservers;

import com.google.gson.Gson;
import express.http.request.Request;
import me.monmcgt.code.Debug;
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain;
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.chat.Print;
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.chat.Send;
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.chat.Send2;
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.debug.fps.FPS;
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.debug.display.title.Set_SetTitle;
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.debug.screen.ScreenName;
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.world.player.AllPlayer;
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.world.player.all.PlayerInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Express {
    private static final Gson gson = new Gson();

    private static final int port = 59741;

    public void start() {
        express.Express app = new express.Express();

        Print.INSTANCE.add(app);
        Send2.INSTANCE.add(app);
        Send.INSTANCE.add(app);
        FPS.INSTANCE.add(app);
        Set_SetTitle.INSTANCE.add(app);
        AllPlayer.INSTANCE.add(app);
        PlayerInfo.INSTANCE.add(app);
        ScreenName.INSTANCE.add(app);

        app.listen(port);

        Debug.println("Express server started on port " + port);
    }

    private void checkInit() {
        if (!LauncherMain.INSTANCE.alreadyInited) {
            LauncherMain.INSTANCE.init();
        }
    }

    private String getJson(Request request) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getBody()))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
