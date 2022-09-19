package me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.mode.`ESP$Command$Settings$Mode`
import me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.red.`ESP$Command$Settings$Colour$Blue`
import me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.red.`ESP$Command$Settings$Colour$Green`
import me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.red.`ESP$Command$Settings$Colour$Red`
import me.monmcgt.code.commands.impl.modules.util.ColourMode
import me.monmcgt.code.config.ConfigManager
import me.monmcgt.code.util.checkModuleCommandArgsAndSetRunSubCommand

@CommandInfo(
    ["colours", "colour", "colors", "color"],
)
object `ESP$Command$Settings$Colour` : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `ESP$Command$Settings$Colour$Red`,
            `ESP$Command$Settings$Colour$Green`,
            `ESP$Command$Settings$Colour$Blue`,
            `ESP$Command$Settings$Mode`,
        )

    var mode = ColourMode.getByAlias(ConfigManager.config.modules.espSettings.colour.mode) ?: ColourMode.DYNAMIC
    var red = ConfigManager.config.modules.espSettings.colour.red
    var green = ConfigManager.config.modules.espSettings.colour.green
    var blue = ConfigManager.config.modules.espSettings.colour.blue

    override fun execute() {
        if (checkModuleCommandArgsAndSetRunSubCommand(this)) {
            runPostExecute = true
        }
    }

    override fun postExecute() {
        saveSettings()
    }

    fun saveSettings() {
        ConfigManager.config.modules.espSettings.colour.mode = mode.displayName
        ConfigManager.config.modules.espSettings.colour.red = red
        ConfigManager.config.modules.espSettings.colour.green = green
        ConfigManager.config.modules.espSettings.colour.blue = blue
        ConfigManager.save()
    }
}