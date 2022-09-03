package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings

enum class BedwarsMode(val alias: String, val command: String) {
    MODE_SOLO("solo", "bedwars_eight_one"),
    MODE_DOUBLE("double", "bedwars_eight_two"),
    MODE_TRIPLE("3s", "bedwars_four_three"),
    MODE_QUADRUPLE("4s", "bedwars_four_four"),

    MODE_4v4("4v4", "bedwars_two_four");

    companion object {
        @JvmStatic
        fun getByName(name: String): BedwarsMode? {
            for (mode in values()) {
                if (mode.name == name) {
                    return mode
                }
            }
            return null
        }

        @JvmStatic
        fun getByAlias(alias: String): BedwarsMode? {
            for (mode in values()) {
                if (mode.alias == alias) {
                    return mode
                }
            }
            return null
        }
    }
}