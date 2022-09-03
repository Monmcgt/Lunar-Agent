package me.monmcgt.code.commands.impl.test

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["test3"],
)
class Test3 : CommandAbstract() {
    override fun execute() {
        val screenName = LauncherMain.INSTANCE.currentScreen ?: "null"
        screenName.toChatMessage().printChat()
    }
}