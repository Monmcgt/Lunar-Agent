package me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.world.player

import express.Express
import express.http.request.Request
import express.http.response.Response
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.lunarbuiltinlauncher.entities.Player
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.world.player.all.`JSON$S$GetAllPlayers`
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.Template

object AllPlayer : Template() {
    override fun add(app: Express) {
        app.all("/lunar/world/player/all") { req: Request?, res: Response ->
            checkInit()
            checkInit()
            try {
                val players =
                    LauncherMain.INSTANCE.playerEntitiesList
                val `json$S$GetAllPlayers` =
                    `JSON$S$GetAllPlayers`(true, "Success", players.toTypedArray())
                res.send(gson.toJson(`json$S$GetAllPlayers`))
            } catch (e: NullPointerException) {
                val `json$S$GetAllPlayers` =
                    `JSON$S$GetAllPlayers`(true, "No players found", arrayOfNulls<Player>(0))
                res.send(gson.toJson(`json$S$GetAllPlayers`))
            }
        }
    }
}