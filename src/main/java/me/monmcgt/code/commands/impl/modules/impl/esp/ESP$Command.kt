package me.monmcgt.code.commands.impl.modules.impl.esp

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.modules.impl.esp.settings.`ESP$Command$Settings`
import me.monmcgt.code.config.ConfigManager
import me.monmcgt.code.util.checkModuleCommandArgs

@CommandInfo(
    ["esp"],
)
class `ESP$Command` : CommandAbstract() {
    companion object {
        var enabled = /*true*/ ConfigManager.config.modules.espSettings.enabled
            set(value) {
                field = value
                ConfigManager.config.modules.espSettings.enabled = value
                ConfigManager.save()
            }
    }

    override val subCommands: MutableList<CommandAbstract>
        get() = mutableListOf(
            `ESP$Command$Toggle`(),
            `ESP$Command$Settings`()
        )

    override fun execute() {
        if (checkModuleCommandArgs(this)) {
//            println("RUN SUB COMMAND")
            runSubCommand = true
        } /*else {
            println("NOT RUN SUB COMMAND")
        }*/
    }
}