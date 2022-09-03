package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.mode

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.`BedwarsOverlay$Command$AutoQueue$Settings`
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.dark_aqua
import me.monmcgt.code.helpers.orange
import me.monmcgt.code.helpers.pink
import me.monmcgt.code.helpers.yellow
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["get", "g"],
)
object `BedwarsOverlay$Command$AutoQueue$Settings$Mode$Get` : CommandAbstract() {
    override fun execute() {
        "${dark_aqua}AutoQueue ${pink}Mode ${yellow}is currently set to ${orange}${`BedwarsOverlay$Command$AutoQueue$Settings`.mode.alias}$yellow.".toChatMessage().addPrefix().printChat()
    }
}