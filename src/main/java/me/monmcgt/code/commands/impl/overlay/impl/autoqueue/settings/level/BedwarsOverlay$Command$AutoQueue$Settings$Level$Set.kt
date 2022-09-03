package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.level

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.`BedwarsOverlay$Command$AutoQueue$Settings`
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.red
import me.monmcgt.code.util.printModuleSettingsValueMessage
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["set", "s"],
)
object `BedwarsOverlay$Command$AutoQueue$Settings$Level$Set` : CommandAbstract() {
    override fun execute() {
        if (args.isEmpty()) {
            "${red}Please specify a value.".toChatMessage().addPrefix().printChat()
            return
        }

        val value = args[0].toIntOrNull() ?: run {
            "${red}Please specify a valid value.".toChatMessage().addPrefix().printChat()
            return
        }

        `BedwarsOverlay$Command$AutoQueue$Settings`.maxLevel = value
        printModuleSettingsValueMessage("AutoQueue", "Maximum Stars", value.toString())
    }
}