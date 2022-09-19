package me.monmcgt.code.commands.impl.modules.impl.tracers

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.modules.impl.tracers.settings.`Tracers$Command$Settings`
import me.monmcgt.code.config.ConfigManager
import me.monmcgt.code.util.checkModuleCommandArgs

@CommandInfo(
    ["tracers", "tracer"],
)
class `Tracers$Command` : CommandAbstract() {
    companion object {
        var enabled = /*true*/ ConfigManager.config.modules.tracersSettings.enabled
            set(value) {
                field = value
                ConfigManager.config.modules.tracersSettings.enabled = value
                ConfigManager.save()
            }
    }

    override val subCommands: MutableList<CommandAbstract>
        get() = mutableListOf(
            `Tracers$Command$Toggle`(),
            `Tracers$Command$Settings`()
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