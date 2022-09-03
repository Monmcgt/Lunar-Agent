package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.mode

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.BedwarsMode
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.`BedwarsOverlay$Command$AutoQueue$Settings`
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.*
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["set", "s"],
)
object `BedwarsOverlay$Command$AutoQueue$Settings$Mode$Set` : CommandAbstract() {
    override fun execute() {
        if (args.isEmpty()) {
            "${red}Please specify a value.".toChatMessage().addPrefix().printChat()
            printAvailableModes()
            return
        }

        val mode = BedwarsMode.getByAlias(args[0])
        if (mode == null) {
            printAvailableModes()
        } else {
            `BedwarsOverlay$Command$AutoQueue$Settings`.mode = mode
            "${yellow}Mode set to $orange${mode.name}${reset}".toChatMessage().addPrefix().printChat()
        }
    }

    private fun printAvailableModes() {
        val modes = BedwarsMode.values().map { it.alias }
        "$yellow${bold}Available modes: $reset$orange${modes.joinToString("$yellow, $orange")}$reset".toChatMessage().addPrefix().printChat()
    }
}