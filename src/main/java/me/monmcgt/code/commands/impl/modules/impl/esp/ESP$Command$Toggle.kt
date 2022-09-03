package me.monmcgt.code.commands.impl.modules.impl.esp

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.printModuleEnableOrDisableMessage

@CommandInfo(
    ["toggle", "t"],
)
class `ESP$Command$Toggle` : CommandAbstract() {
    override fun execute() {
        `ESP$Command`.enabled = !`ESP$Command`.enabled
//        val message = "${dark_aqua}ESP ${yellow}is now ${if (`ESP$Command`.enabled) "${lime}enabled" else "${red}disabled"}."
//        printChatPrefix(message)
        printModuleEnableOrDisableMessage("ESP", `ESP$Command`.enabled)
    }
}