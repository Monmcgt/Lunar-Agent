package me.monmcgt.code.commands.impl.modules.impl.esp.settings

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.config.ConfigManager
import me.monmcgt.code.util.checkModuleCommandArgs

@CommandInfo(
    ["settings", "setting", "st"],
)
class `ESP$Command$Settings` : CommandAbstract() {
    companion object {
        var throughWalls = ConfigManager.config.modules.espSettings.throughWall
        var antiBot = ConfigManager.config.modules.espSettings.antiBot
        var antiBotHypixel = true
    }

    override val subCommands: MutableList<CommandAbstract>
        get() = mutableListOf(
            `ESP$Command$Settings$ThroughWall`(),
            `ESP$Command$Settings$AntiBot`(),
        )

    override fun execute() {
        // create function to check args
        if (checkModuleCommandArgs(this)) {
            runSubCommand = true
            runPostExecute = true
        }
    }

    override fun postExecute() {
        saveSettings()
    }

    fun saveSettings() {
        ConfigManager.config.modules.espSettings.throughWall = throughWalls
        ConfigManager.config.modules.espSettings.antiBot = antiBot
        ConfigManager.save()
    }
}