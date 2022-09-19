package me.monmcgt.code.commands.impl.modules.impl.tracers

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.printModuleEnableOrDisableMessage

@CommandInfo(
    ["toggle", "t"],
)
class `Tracers$Command$Toggle` : CommandAbstract() {
    override fun execute() {
        `Tracers$Command`.enabled = !`Tracers$Command`.enabled
//        val message = "${dark_aqua}Tracers ${yellow}is now ${if (`Tracers$Command`.enabled) "${lime}enabled" else "${red}disabled"}."
//        printChatPrefix(message)
        printModuleEnableOrDisableMessage("Tracers", `Tracers$Command`.enabled)
    }
}