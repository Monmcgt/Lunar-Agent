package me.monmcgt.code.commands.impl.overlay.impl.party

import me.monmcgt.code.bedwarsstatslunarinject.apis.ApiMainWrapper
import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.red
import me.monmcgt.code.tasks.hypixel.HypixelBedwarsTask
import me.monmcgt.code.util.toChatMessage
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@CommandInfo(
    ["party", "p"],
)
class `BedwarsOverlay$Command$Party` : CommandAbstract() {
    override fun execute() {
        Thread {
            HypixelBedwarsTask.party {
                if (!it.isInParty) {
                    "${red}You are not in a party!".toChatMessage().addPrefix().printChat()
                    return@party
                }

                val executorService = Executors.newFixedThreadPool(30)
                it.players.forEach { it2 ->
                    executorService.submit {
                        ApiMainWrapper.checkBedwarsStats(it2)
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
            }
        }.start()
    }
}