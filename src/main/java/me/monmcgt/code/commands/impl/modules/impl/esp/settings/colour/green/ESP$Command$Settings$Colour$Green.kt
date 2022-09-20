package me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.green

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.checkModuleCommandArgsAndSetRunSubCommand

@CommandInfo(
    ["green", "g"],
)
object `ESP$Command$Settings$Colour$Green`  : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `ESP$Command$Settings$Colour$Green$Get`,
            `ESP$Command$Settings$Colour$Green$Set`,
        )

    override fun execute() {
        checkModuleCommandArgsAndSetRunSubCommand(this)
    }
}