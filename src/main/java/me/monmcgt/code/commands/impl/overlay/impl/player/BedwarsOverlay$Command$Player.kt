package me.monmcgt.code.commands.impl.overlay.impl.player

import me.monmcgt.code.bedwarsstatslunarinject.apis.ApiMainWrapper
import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.dark_aqua
import me.monmcgt.code.helpers.red
import me.monmcgt.code.util.checkPlayerName
import me.monmcgt.code.util.toChatMessage
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@CommandInfo(
//    ["player", "p"],
    ["specify", "player", "s"],
)
class `BedwarsOverlay$Command$Player` : CommandAbstract() {
    override fun execute() {
        if (args.isEmpty()) {
            // you gotta specify player name (can multiple)
            "${red}Please specify a player name.".toChatMessage().addPrefix().printChat()
        }

        args.forEach {
            val valid = checkPlayerName(it)
            if (!valid) {
                "${red}Invalid player name: $dark_aqua$it".toChatMessage().addPrefix().printChat()
                return
            }
        }

        Thread {
            val executorService = Executors.newFixedThreadPool(30)

            args.forEach {
                executorService.submit {
                    ApiMainWrapper.checkBedwarsStats(it)
                }
            }

            executorService.shutdown()
            try {
                val b = executorService.awaitTermination(20, TimeUnit.SECONDS)
                if (!b) {
                    executorService.shutdownNow()
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }
}