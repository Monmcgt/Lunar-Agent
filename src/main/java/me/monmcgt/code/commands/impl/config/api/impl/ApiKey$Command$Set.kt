package me.monmcgt.code.commands.impl.config.api.impl

import me.monmcgt.code.bedwarsstatslunarinject.apis.ApiMainWrapper
import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.impl.overlay.`BedwarsOverlay$Command`
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.config.ConfigManager
import me.monmcgt.code.helpers.red
import me.monmcgt.code.helpers.yellow
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["set", "s"],
)
class `ApiKey$Command$Set` : CommandAbstract() {
    override fun execute() {
        if (args.isEmpty()) {
            val message = "${red}Please specify an API key."
            message.toChatMessage().addPrefix().printChat()
            return
        }

        val apiKey = args[0]
        val success = ApiMainWrapper.validateApiKey(apiKey)

        if (success) {
            val message = "${yellow}Your API key has been set."
            ConfigManager.config.hypixelApiKey = apiKey
            ConfigManager.save()
            `BedwarsOverlay$Command`.isApiKeyValid = true
            message.toChatMessage().addPrefix().printChat()
        } else {
            val message = "${red}Your API key is invalid."
            message.toChatMessage().addPrefix().printChat()
        }
    }
}