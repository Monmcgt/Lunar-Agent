package me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.red

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.`ESP$Command$Settings$Colour`
import me.monmcgt.code.util.printModuleSettingsCurrentValueMessage

@CommandInfo(
    ["get", "g"],
)
object `ESP$Command$Settings$Colour$Red$Get` : CommandAbstract() {
    override fun execute() {
        printModuleSettingsCurrentValueMessage("ESP", "Colour (R)", `ESP$Command$Settings$Colour`.red.toString())
    }
}