package me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.mode

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.`ESP$Command$Settings$Colour`
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.dark_aqua
import me.monmcgt.code.helpers.orange
import me.monmcgt.code.helpers.pink
import me.monmcgt.code.helpers.yellow
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["get", "g"],
)
object `ESP$Command$Settings$Mode$Get` : CommandAbstract() {
    override fun execute() {
        // "${dark_aqua}AutoQueue ${pink}Mode ${yellow}is currently set to ${orange}${`BedwarsOverlay$Command$AutoQueue$Settings`.mode.alias}$yellow.".toChatMessage().addPrefix().printChat()
        "${dark_aqua}ESP ${pink} colour mode ${yellow}is currently set to ${orange}${`ESP$Command$Settings$Colour`.mode.displayName}$yellow.".toChatMessage().addPrefix().printChat()
    }
}