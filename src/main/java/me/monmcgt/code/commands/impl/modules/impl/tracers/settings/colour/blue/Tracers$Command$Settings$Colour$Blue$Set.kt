package me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.blue

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.`Tracers$Command$Settings$Colour`
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.red
import me.monmcgt.code.util.printModuleSettingsValueMessage
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["set", "s"],
)
object `Tracers$Command$Settings$Colour$Blue$Set` : CommandAbstract() {
    override fun execute() {
        if (args.isEmpty()) {
            "${red}Please specify a value.".toChatMessage().addPrefix().printChat()
            return
        }

        val value = args[0].toIntOrNull() ?: run {
            "${red}Please specify a valid value (0-255).".toChatMessage().addPrefix().printChat()
            return
        }

        `Tracers$Command$Settings$Colour`.blue = value
        printModuleSettingsValueMessage("Tracers", "Colour (B)", value.toString())
    }
}