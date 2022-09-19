package me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.blue

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.checkModuleCommandArgsAndSetRunSubCommand

@CommandInfo(
    ["blue", "b"],
)
object `Tracers$Command$Settings$Colour$Blue`  : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `Tracers$Command$Settings$Colour$Blue$Get`,
            `Tracers$Command$Settings$Colour$Blue$Set`,
        )

    override fun execute() {
        checkModuleCommandArgsAndSetRunSubCommand(this)
    }
}