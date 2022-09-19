package me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.mode

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.checkModuleCommandArgsAndSetRunSubCommand

@CommandInfo(
    ["mode", "m"],
)
object `ESP$Command$Settings$Mode` : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `ESP$Command$Settings$Mode$Get`,
            `ESP$Command$Settings$Mode$Set`,
        )

    override fun execute() {
        checkModuleCommandArgsAndSetRunSubCommand(this)
    }
}