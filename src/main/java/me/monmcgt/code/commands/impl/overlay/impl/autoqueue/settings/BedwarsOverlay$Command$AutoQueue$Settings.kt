package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.fkdr.`BedwarsOverlay$Command$AutoQueue$Settings$FKDR`
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.leaveifnicked.`BedwarsOverlay$Command$AutoQueue$Settings$LeaveIfNicked`
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.level.`BedwarsOverlay$Command$AutoQueue$Settings$Level`
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.mode.`BedwarsOverlay$Command$AutoQueue$Settings$Mode`
import me.monmcgt.code.config.ConfigManager
import me.monmcgt.code.util.checkModuleCommandArgsAndSetRunSubCommand

@CommandInfo(
    ["settings", "setting", "st"],
)
class `BedwarsOverlay$Command$AutoQueue$Settings` : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `BedwarsOverlay$Command$AutoQueue$Settings$Mode`(),
            `BedwarsOverlay$Command$AutoQueue$Settings$LeaveIfNicked`,
            `BedwarsOverlay$Command$AutoQueue$Settings$FKDR`,
            `BedwarsOverlay$Command$AutoQueue$Settings$Level`,
        )

    companion object {
        var mode = BedwarsMode.getByName(ConfigManager.config.bedwarsOverlay.autoQueue.mode) ?: BedwarsMode.MODE_4v4
        var leaveIfNicked = ConfigManager.config.bedwarsOverlay.autoQueue.leaveIfNicked
        var maxFKDR = ConfigManager.config.bedwarsOverlay.autoQueue.fkdr
        var maxLevel = ConfigManager.config.bedwarsOverlay.autoQueue.star
    }

    override fun execute() {
        if (checkModuleCommandArgsAndSetRunSubCommand(this)) {
            runPostExecute = true
        }
    }

    override fun postExecute() {
        saveSettings()
    }

    fun saveSettings() {
        ConfigManager.config.bedwarsOverlay.autoQueue.mode = mode.name
        ConfigManager.config.bedwarsOverlay.autoQueue.leaveIfNicked = leaveIfNicked
        ConfigManager.config.bedwarsOverlay.autoQueue.fkdr = maxFKDR
        ConfigManager.config.bedwarsOverlay.autoQueue.star = maxLevel
        ConfigManager.save()
    }
}