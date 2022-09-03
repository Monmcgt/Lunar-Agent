package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.fkdr

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.`BedwarsOverlay$Command$AutoQueue$Settings`
import me.monmcgt.code.util.printModuleSettingsCurrentValueMessage

@CommandInfo(
    ["get", "g"],
)
object `BedwarsOverlay$Command$AutoQueue$Settings$FKDR$Get` : CommandAbstract() {
    override fun execute() {
//        printModuleSettingsCurrentlyEnableOrDisableMessage("AutoQueue", "FKDR", `BedwarsOverlay$Command$AutoQueue$Settings`.maxFKDR)
        printModuleSettingsCurrentValueMessage("AutoQueue", "Maximum FKDR", `BedwarsOverlay$Command$AutoQueue$Settings`.maxFKDR.toString())
    }
}