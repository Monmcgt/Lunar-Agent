package me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.red

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.`ESP$Command$Settings$Colour`
import me.monmcgt.code.util.printModuleSettingsCurrentValueMessage

@CommandInfo(
    ["get", "g"],
)
object `ESP$Command$Settings$Colour$Green$Get` : CommandAbstract() {
    override fun execute() {
        printModuleSettingsCurrentValueMessage("ESP", "Colour (G)", `ESP$Command$Settings$Colour`.green.toString())
    }
}