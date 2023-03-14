package me.monmcgt.code.commands.impl.test

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.util.toChatMessage

@CommandInfo(
    ["test4"],
)
object Test4 : CommandAbstract() {
    override fun execute() {
        val playerEntitiesListInfo = LauncherMain.INSTANCE.playerEntitiesListInfo
        val myself = playerEntitiesListInfo[0]
        val others = playerEntitiesListInfo.subList(1, playerEntitiesListInfo.size)

        myself.lookingAt(others.toTypedArray()).forEach {
            "Looking at ${it.name} (${it.posX}, ${it.posY}, ${it.posZ})".toChatMessage().printChat()
        }
    }
}