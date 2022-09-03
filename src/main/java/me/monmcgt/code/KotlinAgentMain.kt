package me.monmcgt.code

import me.monmcgt.code.lunarclassfinder.LunarClassFinderMain

var hasInitClasses = false

lateinit var renderGlobalPatchName: String
lateinit var axisAlignedBBPatchName: String

fun run(): Unit {
    renderGlobalPatchName = (LunarClassFinderMain.LunarClassFinderMAIN.getNewMapping().firstOrNull {
        val split = it.split(" ".toRegex(), 3)
        // find RenderGlobal
        split[2] == "net/minecraft/client/renderer/RenderGlobal".replace("/", ".")
    } ?: throw RuntimeException("Cannot find RenderGlobal class")).split(" ".toRegex(), 3)[1].replace("/", ".")
    axisAlignedBBPatchName = (LunarClassFinderMain.LunarClassFinderMAIN.getNewMapping().firstOrNull {
        val split = it.split(" ".toRegex(), 3)
        // find AxisAlignedBB
        split[2] == "net/minecraft/util/AxisAlignedBB".replace("/", ".")
    } ?: throw RuntimeException("Cannot find AxisAlignedBB class")).split(" ".toRegex(), 3)[1].replace("/", ".")
}