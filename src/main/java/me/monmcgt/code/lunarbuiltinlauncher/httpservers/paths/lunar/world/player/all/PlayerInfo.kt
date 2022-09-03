package me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.world.player.all

import express.Express
import express.http.request.Request
import express.http.response.Response
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.lunarbuiltinlauncher.entities.PlayerInfo
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.world.player.info.`JSON$S$GetAllPlayersInfo`
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.Template

object PlayerInfo : Template() {
    override fun add(app: Express) {
        app.all("/lunar/world/player/all/info") { req: Request?, res: Response ->
            checkInit()
            try {
//                List<PlayerInfo> players = Main.INSTANCE.getPlayerEntitiesToFormattedList().stream().map((e) -> (PlayerInfo) e).collect(Collectors.toList());
                val players =
                    LauncherMain.INSTANCE.playerEntitiesListInfo
                val `json$S$GetAllPlayersInfo` = `JSON$S$GetAllPlayersInfo`(
                    true,
                    "Success",
                    players.toTypedArray()
                )
                res.send(gson.toJson(`json$S$GetAllPlayersInfo`))
            } catch (e: NullPointerException) {
                val `json$S$GetAllPlayersInfo` = `JSON$S$GetAllPlayersInfo`(
                    true,
                    "No players found",
                    arrayOfNulls<PlayerInfo>(0)
                )
                res.send(gson.toJson(`json$S$GetAllPlayersInfo`))
            }
        }
    }
}