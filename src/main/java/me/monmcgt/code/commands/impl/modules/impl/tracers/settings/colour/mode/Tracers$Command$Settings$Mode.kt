package me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.mode

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.checkModuleCommandArgsAndSetRunSubCommand

@CommandInfo(
    ["mode", "m"],
)
object `Tracers$Command$Settings$Mode` : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `Tracers$Command$Settings$Mode$Get`,
            `Tracers$Command$Settings$Mode$Set`,
        )

    override fun execute() {
        checkModuleCommandArgsAndSetRunSubCommand(this)
    }
}