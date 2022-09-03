package me.monmcgt.code.bedwarsstatslunarinject.stats

import me.monmcgt.code.bedwarsstatslunarinject.apis.responses.`HypixelStats$Response`

data class KtHypixelStatsResponse(
    val hypixelStatsResponse: `HypixelStats$Response`
) {
    companion object {
        @JvmStatic
        fun newInstance(hypixelStatsResponse: `HypixelStats$Response`): KtHypixelStatsResponse {
            return KtHypixelStatsResponse(hypixelStatsResponse)
        }
    }
}

//class KtHypixelStatsResponse : `HypixelStats$Response`()