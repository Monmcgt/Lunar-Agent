package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.level

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.`BedwarsOverlay$Command$AutoQueue$Settings`
import me.monmcgt.code.util.printModuleSettingsCurrentValueMessage

@CommandInfo(
    ["get", "g"],
)
object `BedwarsOverlay$Command$AutoQueue$Settings$Level$Get` : CommandAbstract() {
    override fun execute() {
        printModuleSettingsCurrentValueMessage("AutoQueue", "Maximum Stars", `BedwarsOverlay$Command$AutoQueue$Settings`.maxLevel.toString())
    }
}