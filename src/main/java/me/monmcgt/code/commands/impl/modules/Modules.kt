package me.monmcgt.code.commands.impl.modules

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.modules.impl.esp.`ESP$Command`
import me.monmcgt.code.util.checkModuleCommandArgs

@CommandInfo(
    ["modules", "module", "mods", "mod"],
)
class Modules : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = mutableListOf(
            `ESP$Command`()
        )

    override fun execute() {
        /*if (args.isEmpty()) {
            val usage = "${commandUsageMessage(previousCommandName)} <module> <args>"
            ChatMessage(usage).addPrefix().printChat()
            return
        } else {
            runSubCommand = true
        }*/
        if (checkModuleCommandArgs(this)) {
            runSubCommand = true
        }
    }
}