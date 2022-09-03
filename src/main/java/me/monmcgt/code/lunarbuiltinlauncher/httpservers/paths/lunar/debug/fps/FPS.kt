package me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.debug.fps

import express.Express
import express.http.request.Request
import express.http.response.Response
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.debug.fps.`JSON$S$FPS`
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.Template

object FPS : Template() {
    override fun add(app: Express) {
        app.all("/lunar/debug/fps") { req: Request?, res: Response ->
            checkInit()
            val fps = LauncherMain.INSTANCE.debugFPS
            val `json$S$FPS` = `JSON$S$FPS`(true, fps.toString())
            res.send(gson.toJson(`json$S$FPS`))
        }
    }
}