package me.monmcgt.code.commands.impl.overlay.impl.who

import me.monmcgt.code.bedwarsstatslunarinject.apis.ApiMainWrapper
import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.tasks.hypixel.HypixelBedwarsTask
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@CommandInfo(
    ["who", "w"],
)
class `BedwarsOverlay$Command$Who` : CommandAbstract() {
    override fun execute() {
        Thread {
            HypixelBedwarsTask.who {
                val executorService = Executors.newFixedThreadPool(30)
                it.forEach { it2 ->
//                    Debug.println("Checking $it2 #FOREACH")
                    executorService.submit {
//                        Debug.println("Checking $it2 #SUBMIT")
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