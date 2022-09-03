package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.fkdr

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.checkModuleCommandArgsAndSetRunSubCommand

@CommandInfo(
    ["maxfkdr", "fkdr"],
)
object `BedwarsOverlay$Command$AutoQueue$Settings$FKDR` : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `BedwarsOverlay$Command$AutoQueue$Settings$FKDR$Get`,
            `BedwarsOverlay$Command$AutoQueue$Settings$FKDR$Set`,
        )

    override fun execute() {
        checkModuleCommandArgsAndSetRunSubCommand(this)
    }
}