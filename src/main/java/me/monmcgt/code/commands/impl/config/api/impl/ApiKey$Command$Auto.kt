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
import me.monmcgt.code.tasks.hypixel.HypixelBedwarsTask
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["auto", "a"],
)
class `ApiKey$Command$Auto` : CommandAbstract() {
    override fun execute() {
        Thread {
            HypixelBedwarsTask.api {
                val success = ApiMainWrapper.validateApiKey(it)

                if (success) {
                    val message = "${yellow}Your API key has been set."
                    ConfigManager.config.hypixelApiKey = it
                    ConfigManager.save()
//                    Debug.println("Key -> " + ConfigManager.config.hypixelApiKey)
                    `BedwarsOverlay$Command`.isApiKeyValid = true
                    message.toChatMessage().addPrefix().printChat()
                } else {
                    val message = "${red}An error occurred while getting your API key."
                    message.toChatMessage().addPrefix().printChat()
                }
            }
        }.start()
    }
}