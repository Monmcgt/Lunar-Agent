package me.monmcgt.code.commands.impl.test

import me.monmcgt.code.Debug
import me.monmcgt.code.Fields
import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.modules.ESP
import me.monmcgt.code.util.getMinecraftObj

@CommandInfo(
    ["test1"],
)
class Test1 : CommandAbstract() {
    override fun execute() {
        printChatPrefix("TEST 123")
        Debug.println("HMMMMM")
        /*MessageEventClassFinder.foundClasses.forEach {
            println("FOUND EVENT CLASS: $it")
        }*/
        val mc = getMinecraftObj()
        Debug.println("WHATT")
        if (mc != null) {
            val method = ESP.minecraftClass.getMethod(Fields.IS_SINGLE_PLAYER_METHOD_NAME)
            Debug.println("method = $method")
            val isSinglePlayer = method.invoke(mc) as Boolean
            Debug.println("IS SINGLE PLAYER: $isSinglePlayer")
            printChat("IS SINGLE PLAYER: $isSinglePlayer")
        } else {
            Debug.println("MC IS NULL")
        }
    }
}