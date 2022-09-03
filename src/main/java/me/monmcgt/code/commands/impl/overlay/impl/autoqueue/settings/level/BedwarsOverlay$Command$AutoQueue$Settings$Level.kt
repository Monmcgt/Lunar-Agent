package me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.level

import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.CommandInfo
import me.monmcgt.code.util.checkModuleCommandArgsAndSetRunSubCommand

@CommandInfo(
    ["maximumstars", "maximumstar", "maxstars", "maxstar", "maximumlevels", "maximumlevel", "maximumlvls", "maximumlvl", "maxlevels", "maxlevel", "maxlvls", "maxlvl", "stars", "star", "levels", "level", "lvls", "lvl"],
)
object `BedwarsOverlay$Command$AutoQueue$Settings$Level` : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = arrayListOf(
            `BedwarsOverlay$Command$AutoQueue$Settings$Level$Get`,
            `BedwarsOverlay$Command$AutoQueue$Settings$Level$Set`,
        )

    override fun execute() {
        checkModuleCommandArgsAndSetRunSubCommand(this)
    }
}