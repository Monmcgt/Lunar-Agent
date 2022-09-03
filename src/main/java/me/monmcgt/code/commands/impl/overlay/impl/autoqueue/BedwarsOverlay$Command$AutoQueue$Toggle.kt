package me.monmcgt.code.commands.impl.overlay.impl.autoqueue

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.red
import me.monmcgt.code.util.isHypixel
import me.monmcgt.code.util.printModuleEnableOrDisableMessage
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["toggle", "t"],
)
object `BedwarsOverlay$Command$AutoQueue$Toggle` : CommandAbstract() {
    override fun execute() {
        if (!isHypixel()) {
            "${red}You must be on Hypixel to use this command.".toChatMessage().addPrefix().printChat()
        }

        `BedwarsOverlay$Command$AutoQueue`.enabled = !`BedwarsOverlay$Command$AutoQueue`.enabled
        printModuleEnableOrDisableMessage("AutoQueue", `BedwarsOverlay$Command$AutoQueue`.enabled)
    }
}