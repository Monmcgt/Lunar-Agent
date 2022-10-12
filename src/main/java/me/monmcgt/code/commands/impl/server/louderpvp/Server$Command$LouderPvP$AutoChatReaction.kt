package me.monmcgt.code.commands.impl.server.louderpvp

import me.monmcgt.code.classes.MessageHook
import me.monmcgt.code.classes.S2C_Message
import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.sendChat
import me.monmcgt.code.util.printModuleEnableOrDisableMessage
import me.monmcgt.code.util.toChatMessage
import java.util.function.Consumer

@CommandInfo(
    ["autochatreaction", "chatreaction", "acr"],
)
object `Server$Command$LouderPvP$AutoChatReaction` : CommandAbstract() {
    var enabled = false
        set(value) {
            field = value
            run()
        }

    var consumer: Consumer<S2C_Message> = Consumer {
        // §r§5[Reaction] §r§7(Diamond Boots)§r
        val message = it.message
        val regex = Regex("§r§5\\[Reaction] §r§7\\((.*)\\)§r")
        val matchResult = regex.find(message)
        if (matchResult != null) {
            val group = matchResult.groupValues[1]
            group.toChatMessage().sendChat()
        }
    }

    override fun execute() {
        enabled = !enabled
        printModuleEnableOrDisableMessage("LouderPvP AutoChatReaction", enabled)
    }

    fun run() {
        if (enabled) {
            MessageHook.addS2C(consumer)
        } else {
            MessageHook.removeS2C(consumer)
        }
    }
}