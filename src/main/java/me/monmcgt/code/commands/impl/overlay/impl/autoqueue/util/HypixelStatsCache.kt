package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.util

import me.monmcgt.code.bedwarsstatslunarinject.stats.BedwarsStatsData

object HypixelStatsCache {
    var CACHE_TIME = 300000 // IN MILLISECONDS

    val hypixelStats = mutableListOf<HypixelStats>()
        @Synchronized get

    fun getHypixelStatsByName(username: String): HypixelStats? {
        try {
            return hypixelStats.filter { System.currentTimeMillis() - it.time < CACHE_TIME }
                .firstOrNull { it.username == username }
        } finally {
            removeHypixelStatsExpired()
        }
    }

    fun addStats(stats: HypixelStats) {
        hypixelStats.removeIf { it.username == stats.username }
        hypixelStats.add(stats)
    }

    private fun removeHypixelStatsExpired() {
        hypixelStats.removeAll { System.currentTimeMillis() - it.time > CACHE_TIME }
    }

    data class HypixelStats(
        val username: String,
        val isNicked: Boolean,
        /*val FKDR: Float,
        val stars: Int,*/
        val bedwarsStatsData: BedwarsStatsData,
        val time: Long = System.currentTimeMillis(),
    )
}