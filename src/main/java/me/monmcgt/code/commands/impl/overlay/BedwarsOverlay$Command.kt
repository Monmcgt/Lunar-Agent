package me.monmcgt.code.commands.impl.overlay

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.`BedwarsOverlay$Command$AutoQueue`
import me.monmcgt.code.commands.impl.overlay.impl.party.`BedwarsOverlay$Command$Party`
import me.monmcgt.code.commands.impl.overlay.impl.player.`BedwarsOverlay$Command$Player`
import me.monmcgt.code.commands.impl.overlay.impl.who.`BedwarsOverlay$Command$Who`
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.red
import me.monmcgt.code.util.checkModuleCommandArgs
import me.monmcgt.code.util.isHypixel
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["bedwars", "bw"],
)
class `BedwarsOverlay$Command` : CommandAbstract() {
    companion object {
        @JvmStatic
        var isApiKeyValid = false
            /*get() {
                println("Getting isApikeyValid = $field")
                return field
            }
            set(value) {
                println("Setting isApikeyValid = $value")
                field = value
            }*/
    }

    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `BedwarsOverlay$Command$Player`(),
            `BedwarsOverlay$Command$Who`(),
            `BedwarsOverlay$Command$Party`(),

            `BedwarsOverlay$Command$AutoQueue`,
        )

    override fun execute() {
        if (!isApiKeyValid) {
            val message = "${red}Your API key is invalid."
            printChatPrefix(message)
            return
        }

        if (checkModuleCommandArgs(this)) {
            runSubCommand = true
            runPreSubCommand = true
        }
    }

    override fun preRunSubCommand(subCommands: MutableList<CommandAbstract>) {
        if (isHypixel()) {
            return
        }
        val allowedClasses = arrayListOf(
            `BedwarsOverlay$Command$Player`::class.java,
        )
        subCommands.forEach {
            val contains = !allowedClasses.contains(it::class.java)
            if (contains) {
                "${red}You must be on Hypixel to use this command.".toChatMessage().addPrefix().printChat()
                subCommands.remove(it)
            }
        }
    }
}