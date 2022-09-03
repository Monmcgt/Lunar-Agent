package me.monmcgt.code.listeners

import me.monmcgt.code.Debug
import me.monmcgt.code.bedwarsstatslunarinject.apis.ApiMainWrapper
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.impl.modules.impl.esp.`ESP$Command`
import me.monmcgt.code.commands.impl.modules.impl.esp.settings.`ESP$Command$Settings`
import me.monmcgt.code.commands.impl.overlay.`BedwarsOverlay$Command`
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.`BedwarsOverlay$Command$AutoQueue`
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.config.ConfigManager
import me.monmcgt.code.helpers.dark_aqua
import me.monmcgt.code.helpers.lime
import me.monmcgt.code.helpers.pink
import me.monmcgt.code.helpers.yellow
import me.monmcgt.code.util.isHypixelIp
import me.monmcgt.code.util.toChatMessage

object JoinLeaveServerEventListener {
    var isHypixel = false

    private val apiValidatingThreads = ArrayList<ValidateThread>()
        @Synchronized get

    fun onJoinServer(ip: String, port: Int) {
        if (/*ip.endsWith(".hypixel.net", true)*/isHypixelIp(ip) /*disable*/ /*&& false*/) {
            // detect joining Hypixel server
            Debug.println("[JoinServerEventListener] Joining Hypixel server...")
            isHypixel = true
//            `ESP$Command$Settings`.antiBotBackup = `ESP$Command$Settings`.antiBot
            `ESP$Command$Settings`.antiBotHypixel = true
            val message = "${yellow}Automatically ${lime}enabled ${pink}Anti-Bot ${yellow}in ${dark_aqua}ESP${yellow} (Hypixel Profile)."
            Thread {
                Thread.sleep(5000)
                if (`ESP$Command`.enabled) {
                    message.toChatMessage().addPrefix().printChat()
                }
            }.start()
            ValidateThread().start()
        } else {
            isHypixel = false

//            println("ip = ${ip}")
//            println("port = ${port}")

//            `ESP$Command$Settings`.antiBot = `ESP$Command$Settings`.antiBotBackup

            try {
                apiValidatingThreads.toMutableList().forEach {
                    if (it.isAlive) {
                        /*try {
                            it.interrupt()
                        } catch (_: Exception) {
                        }*/
                        it.cancel = true
                    }
                }
                apiValidatingThreads.clear()
            } catch (_: Exception) {
            }

//            `BedwarsOverlay$Command`.isApiKeyValid = false
        }
    }

    fun onLeaveServer() {
        `BedwarsOverlay$Command$AutoQueue`.enabled = false
    }

    private class ValidateThread : Thread() {
        var cancel = false

        override fun run() {
//            println("ValidateThread: Api -> " + ConfigManager.config.hypixelApiKey)
            val validateApiKey = ApiMainWrapper.validateApiKey(ConfigManager.config.hypixelApiKey)
            if (!cancel) {
                `BedwarsOverlay$Command`.isApiKeyValid = validateApiKey
//                println("validateApiKey = $validateApiKey")
            } /*else {
               println("validateApiKey = :cancelled")
            }*/
        }
    }
}