package me.monmcgt.code.commands.impl.modules.impl.esp.settings

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.printModuleSettingEnableOrDisableMessage

@CommandInfo(
    ["throughwall"],
)
class `ESP$Command$Settings$ThroughWall` : CommandAbstract() {
    override fun execute() {
//        println("EXECUTE THROUGHWALLS")
        `ESP$Command$Settings`.throughWalls = !`ESP$Command$Settings`.throughWalls
//        val message = "${yellow}Through walls: ${if (`ESP$Command$Settings`.throughWalls) "${lime}enabled" else "${red}disabled"}."
//        printChatPrefix(message)
        printModuleSettingEnableOrDisableMessage("ESP", "Through-walls", `ESP$Command$Settings`.throughWalls)
    }
}