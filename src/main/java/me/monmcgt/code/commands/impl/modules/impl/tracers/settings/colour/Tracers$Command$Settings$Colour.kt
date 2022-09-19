package me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.blue.`Tracers$Command$Settings$Colour$Blue`
import me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.green.`Tracers$Command$Settings$Colour$Green`
import me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.mode.`Tracers$Command$Settings$Mode`
import me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.red.`Tracers$Command$Settings$Colour$Red`
import me.monmcgt.code.commands.impl.modules.util.ColourMode
import me.monmcgt.code.config.ConfigManager
import me.monmcgt.code.util.checkModuleCommandArgsAndSetRunSubCommand

@CommandInfo(
    ["colours", "colour", "colors", "color"],
)
object `Tracers$Command$Settings$Colour` : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `Tracers$Command$Settings$Colour$Red`,
            `Tracers$Command$Settings$Colour$Green`,
            `Tracers$Command$Settings$Colour$Blue`,
            `Tracers$Command$Settings$Mode`,
        )

    var mode = ColourMode.getByAlias(ConfigManager.config.modules.tracersSettings.colour.mode) ?: ColourMode.DYNAMIC
    var red = ConfigManager.config.modules.tracersSettings.colour.red
    var green = ConfigManager.config.modules.tracersSettings.colour.green
    var blue = ConfigManager.config.modules.tracersSettings.colour.blue

    override fun execute() {
        if (checkModuleCommandArgsAndSetRunSubCommand(this)) {
            runPostExecute = true
        }
    }

    override fun postExecute() {
        saveSettings()
    }

    fun saveSettings() {
        ConfigManager.config.modules.tracersSettings.colour.mode = mode.displayName
        ConfigManager.config.modules.tracersSettings.colour.red = red
        ConfigManager.config.modules.tracersSettings.colour.green = green
        ConfigManager.config.modules.tracersSettings.colour.blue = blue
        ConfigManager.save()
    }
}