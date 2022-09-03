package me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.debug.screen

import express.Express
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.debug.screen.`JSON$S$ScreenName`
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.Template

object ScreenName : Template() {
    override fun add(app: Express) {
        app.all("/lunar/debug/screen/name") { req, res ->
            val screenName = LauncherMain.INSTANCE.currentScreen
            if (screenName == null) {
                val `JSON$S$ScreenName` = `JSON$S$ScreenName`(false, "")
                res.send(gson.toJson(`JSON$S$ScreenName`))
            } else {
                val `JSON$S$ScreenName` = `JSON$S$ScreenName`(true, screenName)
                res.send(gson.toJson(`JSON$S$ScreenName`))
            }
        }
    }
}