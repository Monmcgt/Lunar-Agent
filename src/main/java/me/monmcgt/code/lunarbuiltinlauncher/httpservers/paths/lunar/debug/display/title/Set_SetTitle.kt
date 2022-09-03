package me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.debug.display.title

import express.Express
import express.http.request.Request
import express.http.response.Response
import express.utils.Status
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.lunarbuiltinlauncher.exceptions.NotReadyException
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.debug.display.title.`JSON$C$SetDisplayTitle`
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.debug.display.title.`JSON$S$SetDisplayTitle`
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.Template

object Set_SetTitle : Template() {
    override fun add(app: Express) {
        app.all("/lunar/debug/display/title/set") { req: Request?, res: Response ->
            checkInit()
            val `json$C$SetDisplayTitle`: `JSON$C$SetDisplayTitle` =
                gson.fromJson<`JSON$C$SetDisplayTitle`>(
                    getJson(
                        req!!
                    ),
                    `JSON$C$SetDisplayTitle`::class.java
                )
            var success = false
            var failedReason: String? = ""
            try {
                success = if (`json$C$SetDisplayTitle`.getTitle() == null) {
                    res.setStatus(Status._400)
                    val `json$S$SetDisplayTitle` =
                        `JSON$S$SetDisplayTitle`(false, "No title provided\nPlease provide a title")
                    res.send(
                        gson.toJson(
                            `json$S$SetDisplayTitle`
                        )
                    )
                    return@all
                } else {
                    LauncherMain.INSTANCE.setDisplayTitle(`json$C$SetDisplayTitle`.getTitle())
                    true
                }
            } catch (e: NotReadyException) {
                failedReason = e.message
            }
            if (success) {
                val `json$S$SetDisplayTitle` = `JSON$S$SetDisplayTitle`(true, "Success")
                res.send(gson.toJson(`json$S$SetDisplayTitle`))
            } else {
                res.setStatus(Status._503)
                val `json$S$SetDisplayTitle` = `JSON$S$SetDisplayTitle`(false, failedReason)
                res.send(gson.toJson(`json$S$SetDisplayTitle`))
            }
        }
    }
}