package me.monmcgt.code.commands.impl.debug

import me.monmcgt.code.Debug
import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.lime
import me.monmcgt.code.helpers.red
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["debug"],
)
class `Debug$Command` : CommandAbstract() {
    override fun execute() {
        Debug.DEBUG = !Debug.DEBUG

        ("Debug mode is now " + (if (Debug.DEBUG) "${lime}enabled" else "${red}disabled") + ".")
            .toChatMessage().addPrefix().printChat()
    }
}