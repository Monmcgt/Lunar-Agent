package me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.chat

import express.utils.Status
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.lunarbuiltinlauncher.exceptions.NotReadyException
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.chat.print.`JSON$C$PrintChat`
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.chat.print.`JSON$S$PrintChat`
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.Template
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/*
print chat but not sending to the server
*/
object Print : Template() {
    override fun add(app: express.Express): Unit {
        app.post("/lunar/chat/print") { req, res ->
            try {
                BufferedReader(InputStreamReader(req.body)).use { bufferedReader ->
                    val stringBuilder = StringBuilder()
                    var line: String?
                    while (bufferedReader.readLine().also { line = it } != null) {
                        stringBuilder.append(line)
                    }
                    var message = stringBuilder.toString()
                    if (message.isEmpty()) {
                        message = "{}"
                    }
                    val `json$C$PrintChat`: `JSON$C$PrintChat` = gson.fromJson(message, `JSON$C$PrintChat`::class.java)
                    //                System.out.println("Chat: " + json$C$PrintChat.getMessage());
                    var success = false
                    var failedReason: String? = ""
                    try {
                        this.checkInit()
                        LauncherMain.INSTANCE.printChatMessage(`json$C$PrintChat`.message)
                        success = true
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
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
    }
}