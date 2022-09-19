package me.monmcgt.code.config

import com.google.gson.GsonBuilder
import me.monmcgt.code.LUNAR_1_8_FOLDER
import me.monmcgt.code.LUNAR_AGENT_FOLDER
import me.monmcgt.code.bedwarsstatslunarinject.apis.ApiMainWrapper
import me.monmcgt.code.commands.impl.overlay.`BedwarsOverlay$Command`
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.BedwarsMode
import java.io.File
import javax.swing.JOptionPane

private val defaultTemplate = ConfigJsonTemplate(hypixelApiKey = "", ConfigJsonTemplate.Modules(espSettings = ConfigJsonTemplate.Modules.ESPSettings(enabled = false, antiBot = true, throughWall = true, colour = ConfigJsonTemplate.Modules.ESPSettings.ESPColours(mode = "static", red = 255, green = 255, blue = 255)), tracersSettings = ConfigJsonTemplate.Modules.TracersSettings(enabled = false, antiBot = true, colour = ConfigJsonTemplate.Modules.TracersSettings.TracersColours(mode = "dynamic", red = 0, green = 0, blue = 0), yOffset = 1.62F)), ConfigJsonTemplate.BedwarsOverlay(
    ConfigJsonTemplate.BedwarsOverlay.AutoQueue(delay = 5, mode = BedwarsMode.MODE_4v4.name, leaveIfNicked = true, fkdr = 0.8f, star = 200)))

object ConfigManager {
    var config = defaultTemplate

    private val gson = GsonBuilder().setPrettyPrinting().create()

    private var configFile = LUNAR_1_8_FOLDER

    fun load(repeat: Boolean = false) {
        try {
            init()
            val json = configFile.readText()
            config = gson.fromJson(json, ConfigJsonTemplate::class.java)
        } catch (e: Exception) {
            e.printStackTrace()

            if (repeat) {
                JOptionPane.showMessageDialog(null, "Failed to load config.json.\nPlease check your config.json file and try again.", "Error", JOptionPane.ERROR_MESSAGE)
            }

            writeFileTemplate(configFile)

            load(true)
        }

        try {
            Thread {
                `BedwarsOverlay$Command`.isApiKeyValid = ApiMainWrapper.validateApiKey(config.hypixelApiKey, false)
            }.start()
        } catch (_: Exception) {
        }
    }

    fun save() {
        configFile.writeText(gson.toJson(config))
    }

    private fun init() {
        configFile = with(LUNAR_AGENT_FOLDER) {
            val file = File(this, "config.json")
            if (!file.exists()) {
                file.createNewFile()
                writeFileTemplate(file)
            }
            return@with file
        }
    }

    private fun writeFileTemplate(file: File) {
        file.bufferedWriter().use {
            it.write(gson.toJson(defaultTemplate))
        }
    }
}