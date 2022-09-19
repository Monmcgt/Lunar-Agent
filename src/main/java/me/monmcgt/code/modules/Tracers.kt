package me.monmcgt.code.modules

import me.monmcgt.code.commands.impl.modules.impl.esp.settings.`ESP$Command$Settings$AntiBot`
import me.monmcgt.code.commands.impl.modules.impl.tracers.`Tracers$Command`
import me.monmcgt.code.commands.impl.modules.impl.tracers.settings.`Tracers$Command$Settings`
import me.monmcgt.code.commands.impl.modules.impl.tracers.settings.colour.`Tracers$Command$Settings$Colour`
import me.monmcgt.code.commands.impl.modules.util.ColourMode
import me.monmcgt.code.listeners.JoinLeaveServerEventListener
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.lunarbuiltinlauncher.entities.PlayerInfo
import me.monmcgt.code.util.RenderUtils

object Tracers {
    @JvmStatic
    fun onRender() {
        try {
            if (!`Tracers$Command`.enabled) {
                return
            }

            val mode = `Tracers$Command$Settings$Colour`.mode
            var red = `Tracers$Command$Settings$Colour`.red
            var green = `Tracers$Command$Settings$Colour`.green
            var blue = `Tracers$Command$Settings$Colour`.blue

            var mySelf: PlayerInfo? = null
            for ((count, player) in LauncherMain.INSTANCE.playerEntitiesListInfo.withIndex()) {
                if (count == 0) {
                    mySelf = player
                    continue
                }

                var isBot = false

                if (JoinLeaveServerEventListener.isHypixel) {
                    if (`Tracers$Command$Settings`.antiBotHypixel) {
                        if (`ESP$Command$Settings$AntiBot`.isBot(player.name)) {
                            isBot = true
                        }
                    }
                } else {
                    if (`Tracers$Command$Settings`.antiBot) {
                        if (`ESP$Command$Settings$AntiBot`.isBot(player.name)) {
//                        continue
                            isBot = true
                        }
                    }
                }

                if (isBot) {
                    continue
                }

                val renderPosX = ESP.declaredFieldX.get(ESP.renderManager) as Double
                val renderPosY = ESP.declaredFieldY.get(ESP.renderManager) as Double
                val renderPosZ = ESP.declaredFieldZ.get(ESP.renderManager) as Double

                // POSITION
                val posX = player.posX - renderPosX
                val posY = player.posY - renderPosY
                val posZ = player.posZ - renderPosZ

                /*// colour (dynamic)
                val (rid, gid, bid) = ESP.getColour(
                    Triple(mySelf!!.posX, mySelf.posY, mySelf.posZ),
                    Triple(player.posX, player.posY, player.posZ)
                )*/
                if (mode == ColourMode.DYNAMIC) {
                    val colour = ESP.getColour(
                        Triple(mySelf!!.posX, mySelf.posY, mySelf.posZ),
                        Triple(player.posX, player.posY, player.posZ)
                    )
                    red = colour.first
                    green = colour.second
                    blue = colour.third
                }

                RenderUtils.drawTracerLineWithHelpFromESPModule(posX, posY, posZ, red, green, blue, 0.45f, 1.0f)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}