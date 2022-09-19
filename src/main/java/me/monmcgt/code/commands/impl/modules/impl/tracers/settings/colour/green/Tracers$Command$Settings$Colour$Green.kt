package me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.green

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.checkModuleCommandArgsAndSetRunSubCommand

@CommandInfo(
    ["green", "g"],
)
object `Tracers$Command$Settings$Colour$Green`  : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `Tracers$Command$Settings$Colour$Green$Get`,
            `Tracers$Command$Settings$Colour$Green$Set`,
        )

    override fun execute() {
        checkModuleCommandArgsAndSetRunSubCommand(this)
    }
}