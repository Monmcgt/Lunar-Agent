package me.monmcgt.code.bedwarsstatslunarinject.apis

import me.monmcgt.code.bedwarsstatslunarinject.stats.BedwarsStatsData
import java.util.function.Consumer

object ApiMainWrapper {
    @JvmStatic
    val apiMainInstance = ApiMain()

    fun checkBedwarsStats(playerName: String, statsConsumer: Consumer<BedwarsStatsData>? = null) {
        apiMainInstance.checkPlayer(playerName, statsConsumer)
    }

    fun validateApiKey(key: String, printChatIfInvalid: Boolean = true): Boolean {
        return apiMainInstance.checkSelf(key, printChatIfInvalid)
    }
}