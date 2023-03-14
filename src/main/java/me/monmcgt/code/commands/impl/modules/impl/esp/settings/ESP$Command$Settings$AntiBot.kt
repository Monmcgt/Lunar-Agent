package me.monmcgt.code.commands.impl.modules.impl.esp.settings

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.listeners.JoinLeaveServerEventListener
import me.monmcgt.code.util.printModuleSettingEnableOrDisableMessage

@CommandInfo(
    ["antibot"],
)
class `ESP$Command$Settings$AntiBot` : CommandAbstract() {
    override fun execute() {
        if (JoinLeaveServerEventListener.isHypixel) {
            `ESP$Command$Settings`.antiBotHypixel = !`ESP$Command$Settings`.antiBotHypixel
            printModuleSettingEnableOrDisableMessage("ESP", "Anti-Bot", `ESP$Command$Settings`.antiBotHypixel)
        } else {
            `ESP$Command$Settings`.antiBot = !`ESP$Command$Settings`.antiBot
            printModuleSettingEnableOrDisableMessage("ESP", "Anti-Bot", `ESP$Command$Settings`.antiBot)
        }
    }
}