package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.leaveifnicked

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.`BedwarsOverlay$Command$AutoQueue$Settings`
import me.monmcgt.code.util.printModuleSettingsCurrentlyEnableOrDisableMessage

@CommandInfo(
    ["get", "g"],
)
object `BedwarsOverlay$Command$AutoQueue$Settings$LeaveIfNicked$Get` : CommandAbstract() {
    override fun execute() {
        printModuleSettingsCurrentlyEnableOrDisableMessage("AutoQueue","LeaveIfNicked",
            `BedwarsOverlay$Command$AutoQueue$Settings`.leaveIfNicked
        )
    }
}