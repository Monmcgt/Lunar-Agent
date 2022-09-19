package me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.red

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.checkModuleCommandArgsAndSetRunSubCommand

@CommandInfo(
    ["red", "r"],
)
object `Tracers$Command$Settings$Colour$Red`  : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `Tracers$Command$Settings$Colour$Red$Get`,
            `Tracers$Command$Settings$Colour$Red$Set`,
        )

    override fun execute() {
        checkModuleCommandArgsAndSetRunSubCommand(this)
    }
}