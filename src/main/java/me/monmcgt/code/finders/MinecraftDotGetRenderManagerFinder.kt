package me.monmcgt.code.finders

import me.monmcgt.code.Debug
import me.monmcgt.code.Fields
import me.monmcgt.code.lunarclassfinder.LunarClassFinderMain
import me.monmcgt.code.util.isThisMethodByReturnType
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode

private val minecraftClassName = LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net.minecraft.client.Minecraft").replace('.', '/')
private val patchName = LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net.minecraft.client.renderer.entity.RenderManager").replace('.', '/')

object MinecraftDotGetRenderManagerFinder {
    @JvmStatic
    fun find(className: String, classFileBuffer: ByteArray?) {
        val className0 = className.replace('.', '/')

//        println("Test = " + LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net.minecraft.client.renderer.entity.RenderManager"))
//        println("className0 = $className0")
//        println("minecraftClassName = $minecraftClassName")

        if (className0 != minecraftClassName) {
            /*println("className0 = $className0")
            println("minecraftClassName = $minecraftClassName")*/

            return
        } else {
            Debug.println("Found class Minecraft $className0")
        }

//        val realName = "net.minecraft.client.renderer.RenderManager"

        val classReader = ClassReader(classFileBuffer)
        val classNode = ClassNode()
        classReader.accept(classNode, 0)

        Debug.println("patchName = $patchName")

        for (methodNode in classNode.methods) {
//            println("methodNode.desc = ${methodNode.desc}")
            /*val cleanMethodDesc = methodDescReturnType(methodNode.desc).replace('.', '/')
            val patchNameTemp = "L$patchName"
            if (cleanMethodDesc == patchNameTemp) {
                println("[MinecraftDotGetRenderManagerFinder] Found method getRenderManager(): $className0.${methodNode.name}")

                Fields.MINECRAFT_GET_RENDER_MANAGER = methodNode.name
            } else {
                println("cleanMethodDesc = $cleanMethodDesc")
                println("lPatchTemp = $patchNameTemp")
                println("Equal? ${cleanMethodDesc == patchNameTemp}")
            }*/
            val thisMethodReturnType =
                isThisMethodByReturnType(methodNode, "net/minecraft/client/renderer/entity/RenderManager")

            if (thisMethodReturnType) {
                Debug.println("[MinecraftDotGetRenderManagerFinder] Found method getRenderManager(): $className0.${methodNode.name}")

                Fields.MINECRAFT_GET_RENDER_MANAGER_METHOD_NAME = methodNode.name
            }
        }
    }
}