package me.monmcgt.code.config

data class ConfigJsonTemplate(
    var hypixelApiKey: String,
    var modules: Modules,
    var bedwarsOverlay: BedwarsOverlay,
) {
    data class Modules(
        var espSettings: ESPSettings,
    ) {
        data class ESPSettings(
            var enabled: Boolean,
            var antiBot: Boolean,
            var throughWall: Boolean,
        )
    }

    data class BedwarsOverlay(
        var autoQueue: AutoQueue,
    ) {
        data class AutoQueue(
            var delay: Int,
            var mode: String,
            var leaveIfNicked: Boolean,
            var fkdr: Float,
            var star: Int,
        )
    }
}
