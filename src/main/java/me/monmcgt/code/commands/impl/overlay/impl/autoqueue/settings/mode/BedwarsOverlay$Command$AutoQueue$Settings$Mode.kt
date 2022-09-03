package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.mode

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.checkModuleCommandArgsAndSetRunSubCommand

@CommandInfo(
    ["mode"],
)
class `BedwarsOverlay$Command$AutoQueue$Settings$Mode` : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `BedwarsOverlay$Command$AutoQueue$Settings$Mode$Get`,
            `BedwarsOverlay$Command$AutoQueue$Settings$Mode$Set`,
        )

    override fun execute() {
        /*if (args.isEmpty()) {
            printAvailableModes()
        } else {
            val arg = args[0]
            val mode = BedwarsMode.getByAlias(arg)

            if (mode == null) {
                printAvailableModes()
            } else {
                `BedwarsOverlay$Command$AutoQueue$Settings`.mode = mode
                "${yellow}Mode set to $orange${mode.name}${reset}".toChatMessage().addPrefix().printChat()
            }
        }*/

        checkModuleCommandArgsAndSetRunSubCommand(this)
    }

    /*private fun printAvailableModes() {
        val modes = BedwarsMode.values().map { it.alias }
        "${yellow}${bold}Available modes: ${reset}${orange}${modes.joinToString("$yellow, $orange")}$reset".toChatMessage().addPrefix().printChat()
    }*/
}