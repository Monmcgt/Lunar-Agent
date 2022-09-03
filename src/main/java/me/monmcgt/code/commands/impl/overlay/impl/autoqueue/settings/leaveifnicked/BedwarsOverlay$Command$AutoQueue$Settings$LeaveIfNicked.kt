package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.leaveifnicked

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.checkModuleCommandArgs

@CommandInfo(
    ["leaveifnicked"],
)
object `BedwarsOverlay$Command$AutoQueue$Settings$LeaveIfNicked` : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `BedwarsOverlay$Command$AutoQueue$Settings$LeaveIfNicked$Get`,
            `BedwarsOverlay$Command$AutoQueue$Settings$LeaveIfNicked$Set`,
        )

    override fun execute() {
        if (checkModuleCommandArgs(this)) {
            runSubCommand = true
        }
    }
}