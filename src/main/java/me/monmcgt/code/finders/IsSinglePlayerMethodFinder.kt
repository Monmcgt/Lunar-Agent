package me.monmcgt.code.finders

import me.monmcgt.code.Debug
import me.monmcgt.code.Fields
import me.monmcgt.code.lunarclassfinder.LunarClassFinderMain
import me.monmcgt.code.util.isThisMethodByReturnType
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode

private var minecraftClassName: String? = null

object IsSinglePlayerMethodFinder {
    @JvmStatic
    fun find(className: String, classFileBuffer: ByteArray?) {
        if (minecraftClassName == null) {
            minecraftClassName =
                LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/Minecraft")
        }

        val className0 = className.replace('.', '/')

        if (className0 != minecraftClassName) {
            return
        }

        val classReader = ClassReader(classFileBuffer)
        val classNode = ClassNode()
        classReader.accept(classNode, 0)

        var count = 0

        for (methodNode in classNode.methods) {
            // boolean primitive descriptor
            val thisMethodReturnType = isThisMethodByReturnType(methodNode, "Z", true)

            if (thisMethodReturnType && ++count == 12) {
                Debug.println("Found isSinglePlayer method: ${methodNode.name}")
                Fields.IS_SINGLE_PLAYER_METHOD_NAME = methodNode.name
            }
        }
    }
}