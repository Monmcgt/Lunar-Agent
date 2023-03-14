package me.monmcgt.code.commands.impl.modules.impl.tracers.settings

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.listeners.JoinLeaveServerEventListener
import me.monmcgt.code.util.printModuleSettingEnableOrDisableMessage

@CommandInfo(
    ["antibot"],
)
class `Tracers$Command$Settings$AntiBot` : CommandAbstract() {
    override fun execute() {
        if (JoinLeaveServerEventListener.isHypixel) {
            `Tracers$Command$Settings`.antiBotHypixel = !`Tracers$Command$Settings`.antiBotHypixel
            printModuleSettingEnableOrDisableMessage("Tracers", "Anti-Bot", `Tracers$Command$Settings`.antiBotHypixel)
        } else {
            `Tracers$Command$Settings`.antiBot = !`Tracers$Command$Settings`.antiBot
            printModuleSettingEnableOrDisableMessage("Tracers", "Anti-Bot", `Tracers$Command$Settings`.antiBot)
        }
    }
}