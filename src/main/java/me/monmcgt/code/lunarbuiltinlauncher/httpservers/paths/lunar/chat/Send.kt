package me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.chat

import express.Express
import express.http.request.Request
import express.http.response.Response
import express.utils.Status
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.lunarbuiltinlauncher.exceptions.NotReadyException
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.chat.print.`JSON$C$PrintChat`
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.chat.print.`JSON$S$PrintChat`
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.Template

/*
print chat and also send to the server
*/
object Send : Template() {
    override fun add(app: Express) {
        app.post("/lunar/chat/send") { req: Request?, res: Response ->
            checkInit()

            // lazy to create new class so use JSON$C$PrintChat and JSON$S$PrintChat
            val `json$C$PrintChat`: `JSON$C$PrintChat` =
                gson.fromJson<`JSON$C$PrintChat`>(
                    this.getJson(req!!),
                    `JSON$C$PrintChat`::class.java
                )
            var success = false
            var failedReason: String? = ""
            try {
                success = if (`json$C$PrintChat`.getMessage() == null) {
                    res.setStatus(Status._400)
                    val `json$S$PrintChat` =
                        `JSON$S$PrintChat`(false, "No message provided\nPlease provide a message")
                    res.send(gson.toJson(`json$S$PrintChat`))
                    return@post
                } else if (`json$C$PrintChat`.getMessage().isEmpty()) {
                    res.setStatus(Status._400)
                    val `json$S$PrintChat` =
                        `JSON$S$PrintChat`(false, "Message is empty\nPlease provide a message")
                    res.send(gson.toJson(`json$S$PrintChat`))
                    return@post
                } else {
                    LauncherMain.INSTANCE.sendChatMessage(`json$C$PrintChat`.getMessage())
                    true
                }
            } catch (e: NotReadyException) {
                failedReason = e.message
            }
            if (success) {
                val `json$S$PrintChat` = `JSON$S$PrintChat`(true, "Success")
                res.send(gson.toJson(`json$S$PrintChat`))
            } else {
                res.setStatus(Status._503)
                val `json$S$PrintChat` = `JSON$S$PrintChat`(false, failedReason)
                res.send(gson.toJson(`json$S$PrintChat`))
            }
        }
    }
}