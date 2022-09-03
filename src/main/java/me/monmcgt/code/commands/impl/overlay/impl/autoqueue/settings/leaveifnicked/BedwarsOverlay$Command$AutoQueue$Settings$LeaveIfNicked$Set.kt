package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.leaveifnicked

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.`BedwarsOverlay$Command$AutoQueue$Settings`
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.red
import me.monmcgt.code.util.printModuleSettingEnableOrDisableMessage
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["set", "s"],
)
object `BedwarsOverlay$Command$AutoQueue$Settings$LeaveIfNicked$Set` : CommandAbstract() {
    override fun execute() {
        if (args.isEmpty()) {
            "${red}Please specify a value.".toChatMessage().addPrefix().printChat()
            return
        }

        val value = args[0].toBooleanStrictOrNull()
        if (value == null) {
            // true or false
            "${red}Please specify a valid value (true or false).".toChatMessage().addPrefix().printChat()
            return
        }

        `BedwarsOverlay$Command$AutoQueue$Settings`.leaveIfNicked = value
        printModuleSettingEnableOrDisableMessage("LeaveIfNicked", "AutoQueue", value)
    }
}