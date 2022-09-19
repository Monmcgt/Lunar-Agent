package me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.mode

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.`ESP$Command$Settings$Colour`
import me.monmcgt.code.commands.impl.modules.util.ColourMode
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.*
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["set", "s"],
)
object `ESP$Command$Settings$Mode$Set` : CommandAbstract() {
    override fun execute() {
        if (args.isEmpty()) {
            "${red}Please specify a value.".toChatMessage().addPrefix().printChat()
            printAvailableModes()
            return
        }

        val mode = ColourMode.getByAlias(args[0])
        if (mode == null) {
            printAvailableModes()
        } else {
            `ESP$Command$Settings$Colour`.mode = mode
            "${yellow}Colour mode set to $orange${mode.displayName}$reset".toChatMessage().addPrefix().printChat()
        }
    }

    private fun printAvailableModes() {
        val modes = ColourMode.values().map { it.displayName }
        "$yellow${bold}Available modes: $reset$orange${modes.joinToString("$yellow, $orange")}$reset".toChatMessage().addPrefix().printChat()
    }
}