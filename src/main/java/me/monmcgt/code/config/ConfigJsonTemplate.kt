package me.monmcgt.code.config

data class ConfigJsonTemplate(
    var hypixelApiKey: String,
    var modules: Modules,
    var bedwarsOverlay: BedwarsOverlay,
) {
    data class Modules(
        var espSettings: ESPSettings,
        var tracersSettings: TracersSettings,
    ) {
        data class ESPSettings(
            var enabled: Boolean,
            var antiBot: Boolean,
            var throughWall: Boolean,
            var colour: ESPColours,
        ) {
            data class ESPColours(
                var mode: String,
                var red: Int,
                var green: Int,
                var blue: Int,
            )
        }

        data class TracersSettings(
            var enabled: Boolean,
            var antiBot: Boolean,
            var colour: TracersColours,
            var yOffset: Float,
        ) {
            data class TracersColours(
                var mode: String,
                var red: Int,
                var green: Int,
                var blue: Int,
            )
        }
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
