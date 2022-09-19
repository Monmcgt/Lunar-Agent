package me.monmcgt.code.commands.impl.modules.util

enum class ColourMode(val displayName: String) {
    STATIC("static"),
    DYNAMIC("dynamic");

    companion object {
        @JvmStatic
        fun getByName(name: String): ColourMode? {
            for (mode in values()) {
                if (mode.name == name) {
                    return mode
                }
            }
            return null
        }

        @JvmStatic
        fun getByAlias(alias: String): ColourMode? {
            for (mode in values()) {
                if (mode.displayName == alias) {
                    return mode
                }
            }
            return null
        }
    }
}