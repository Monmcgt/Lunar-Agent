package me.monmcgt.code.finders

import me.monmcgt.code.Debug
import me.monmcgt.code.Fields
import me.monmcgt.code.lunarclassfinder.LunarClassFinderMain
import me.monmcgt.code.util.isThisMethodByReturnType
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode

private var minecraftClassName: String? = null

object MinecraftDotGetMinecraftMethodFinder {
    @JvmStatic
    fun find(className: String, classFileBuffer: ByteArray?) {
        if (minecraftClassName == null) {
            minecraftClassName = LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/Minecraft")
        }

        val className0 = className.replace('.', '/')

        if (className0 != minecraftClassName) {
            return
        }

        val classReader = ClassReader(classFileBuffer)
        val classNode = ClassNode()
        classReader.accept(classNode, 0)

        for (methodNode in classNode.methods) {
            /*val cleanMethodDesc = methodDescReturnType(methodNode.desc)
            if (cleanMethodDesc == "L$minecraftClassName;") {
                println("[MinecraftDotGetRenderManagerFinder] Found method getMinecraft(): $className0.${methodNode.name}")

                Fields.MINECRAFT_GET_RENDER_MANAGER = methodNode.name
            }*/
            val thisMethodReturnType = isThisMethodByReturnType(methodNode, "net/minecraft/client/Minecraft")

            if (thisMethodReturnType) {
                Debug.println("[MinecraftDotGetMinecraftFinder] Found method getMinecraft(): $className0.${methodNode.name}")

                Fields.MINECRAFT_GET_MINECRAFT_METHOD_NAME = methodNode.name
            }
        }
    }
}