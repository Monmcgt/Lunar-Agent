package me.monmcgt.code.util

import me.monmcgt.code.lunarbuiltinlauncher.entities.PlayerInfo
import test.a

fun isBotNew(playerInfo: PlayerInfo): Boolean {
    return !playerInfo.isOnGround && playerInfo.invisible /*&& !entity.isPotionActive(14)*/ && playerInfo.ticksExisted < 40 && a.check(playerInfo.name)
}

fun PlayerInfo.isBot(): Boolean {
    return isBotNew(this)
}