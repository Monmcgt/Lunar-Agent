package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.fkdr

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
object `BedwarsOverlay$Command$AutoQueue$Settings$FKDR$Set` : CommandAbstract() {
    override fun execute() {
        if (args.isEmpty()) {
            "${red}Please specify a value.".toChatMessage().addPrefix().printChat()
            return
        }

        val value = args[0].toFloatOrNull() ?: run {
            "${red}Please specify a valid value.".toChatMessage().addPrefix().printChat()
            return
        }

        `BedwarsOverlay$Command$AutoQueue$Settings`.maxFKDR = value
        printModuleSettingsValueMessage("AutoQueue", "Maximum FKDR", value.toString())
    }
}