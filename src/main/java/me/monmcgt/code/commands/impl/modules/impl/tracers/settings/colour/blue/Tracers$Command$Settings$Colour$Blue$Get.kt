package me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.blue

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.`Tracers$Command$Settings$Colour`
import me.monmcgt.code.util.printModuleSettingsCurrentValueMessage

@CommandInfo(
    ["get", "g"],
)
object `Tracers$Command$Settings$Colour$Blue$Get` : CommandAbstract() {
    override fun execute() {
        printModuleSettingsCurrentValueMessage("Tracers", "Colour (B)", `Tracers$Command$Settings$Colour`.blue.toString())
    }
}