package me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.chat

import express.Express
import express.http.request.Request
import express.http.response.Response
import express.utils.Status
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.lunarbuiltinlauncher.exceptions.NotReadyException
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.chat.send2.`JSON$C$SendChat2`
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.chat.send2.`JSON$S$SendChat2`
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar.Template
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Boolean
import kotlin.Exception
import kotlin.RuntimeException
import kotlin.String
import kotlin.also

object Send2 : Template() {
    override fun add(app: Express) {
        app.post("/lunar/chat/send2") { req: Request, res: Response ->
            try {
                BufferedReader(InputStreamReader(req.body)).use { bufferedReader ->
                    val stringBuilder = StringBuilder()
                    var line: String?
                    while (bufferedReader.readLine().also { line = it } != null) {
                        stringBuilder.append(line)
                    }
                    val message = stringBuilder.toString()
                    if (message.isEmpty()) {
                        res.setStatus(Status._400)
                        val `json$S$SendChat2` = `JSON$S$SendChat2`(
                            false,
                            "No message provided\nPlease provide a message and a boolean (this one is optional)"
                        )
                        res.send(gson.toJson(`json$S$SendChat2`))
                    } else {
                        val `json$C$SendChat2`: `JSON$C$SendChat2` =
                            gson.fromJson<`JSON$C$SendChat2`>(
                                message,
                                `JSON$C$SendChat2`::class.java
                            )
                        var success = false
                        var failedReason: String? = ""
                        try {
                            checkInit()
                            var b = true
                            if (`json$C$SendChat2`.getB() != null) {
                                b = try {
                                    Boolean.parseBoolean(`json$C$SendChat2`.getB())
                                } catch (e: Exception) {
                                    res.setStatus(Status._400)
                                    val `json$S$SendChat21` = `JSON$S$SendChat2`(
                                        false,
                                        "Boolean value is not valid\nPlease provide a valid boolean (this one is optional)"
                                    )
                                    res.send(
                                        gson.toJson(
                                            `json$S$SendChat21`
                                        )
                                    )
                                    return@post
                                }
                                LauncherMain.INSTANCE.sendMessage(
                                    `json$C$SendChat2`.getMessage(),
                                    b
                                )
                            }
                            success = true
                        } catch (e: NotReadyException) {
                            failedReason = e.message
                        }
                        if (success) {
                            val `json$S$SendChat2` = `JSON$S$SendChat2`(true, "Success")
                            res.send(
                                gson.toJson(
                                    `json$S$SendChat2`
                                )
                            )
                        } else {
                            res.setStatus(Status._503)
                            val `json$S$SendChat2` = `JSON$S$SendChat2`(false, failedReason)
                            res.send(
                                gson.toJson(
                                    `json$S$SendChat2`
                                )
                            )
                        }
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
    }
}