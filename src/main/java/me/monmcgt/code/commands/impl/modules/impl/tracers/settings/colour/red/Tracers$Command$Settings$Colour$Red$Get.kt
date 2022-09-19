package me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.red

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.`Tracers$Command$Settings$Colour`
import me.monmcgt.code.util.printModuleSettingsCurrentValueMessage

@CommandInfo(
    ["get", "g"],
)
object `Tracers$Command$Settings$Colour$Red$Get` : CommandAbstract() {
    override fun execute() {
        printModuleSettingsCurrentValueMessage("Tracers", "Colour (R)", `Tracers$Command$Settings$Colour`.red.toString())
    }
}