package me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.green

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.`Tracers$Command$Settings$Colour`
import me.monmcgt.code.util.printModuleSettingsCurrentValueMessage

@CommandInfo(
    ["get", "g"],
)
object `Tracers$Command$Settings$Colour$Green$Get` : CommandAbstract() {
    override fun execute() {
        printModuleSettingsCurrentValueMessage("Tracers", "Colour (G)", `Tracers$Command$Settings$Colour`.green.toString())
    }
}