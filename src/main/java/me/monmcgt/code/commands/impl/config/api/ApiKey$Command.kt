package me.monmcgt.code.commands.impl.config.api

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.config.api.impl.`ApiKey$Command$Auto`
import me.monmcgt.code.commands.impl.config.api.impl.`ApiKey$Command$Set`
import me.monmcgt.code.util.checkModuleCommandArgs

@CommandInfo(
    ["apikey", "api"],
)
class `ApiKey$Command` : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `ApiKey$Command$Auto`(),
            `ApiKey$Command$Set`(),
        )

    override fun execute() {
        if (checkModuleCommandArgs(this)) {
            runSubCommand = true
        }
    }
}