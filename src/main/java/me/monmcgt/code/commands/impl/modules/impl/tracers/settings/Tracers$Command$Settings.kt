package me.monmcgt.code.commands.impl.modules.impl.tracers.settings

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.`Tracers$Command$Settings$Colour`
import me.monmcgt.code.config.ConfigManager
import me.monmcgt.code.util.checkModuleCommandArgs

@CommandInfo(
    ["settings", "setting", "st"],
)
class `Tracers$Command$Settings` : CommandAbstract() {
    companion object {
        var antiBot = ConfigManager.config.modules.tracersSettings.antiBot
        var antiBotHypixel = true
    }

    override val subCommands: MutableList<CommandAbstract>
        get() = mutableListOf(
            `Tracers$Command$Settings$AntiBot`(),
            `Tracers$Command$Settings$Colour`,
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
        ConfigManager.config.modules.tracersSettings.antiBot = antiBot
        ConfigManager.save()
    }
}