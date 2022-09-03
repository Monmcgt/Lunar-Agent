package me.monmcgt.code.commands.impl.test

import me.monmcgt.code.commands.*
import me.monmcgt.code.helpers.lime
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.tasks.hypixel.HypixelBedwarsTask
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["test2"],
)
class Test2 : CommandAbstract() {
    override fun execute() {
        HypixelBedwarsTask.who {
            it.forEach { it2 ->
                ChatMessage("Player: $lime$it2").addPrefix().printChat()
            }
        }
        val ip: String = LauncherMain.INSTANCE.currentServerIp ?: "null"
        ip.toChatMessage().addPrefix().printChat()
    }
}