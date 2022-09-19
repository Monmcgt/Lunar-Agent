package me.monmcgt.code.finders

import me.monmcgt.code.Debug
import me.monmcgt.code.transformers.`Transformer$1`
import me.monmcgt.code.util.isThisMethodByReturnType
import me.monmcgt.code.wrappers.net.minecraft.client.renderer.tessellator.`Tessellator$Data`
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode

/**
 * Helper for RenderUtils class
 */
object RenderUtilsHelperFinder {
    val TessellatorMCPMapping = "net/minecraft/client/renderer/Tessellator"
    val WorldRendererMCPMapping = "net/minecraft/client/renderer/WorldRenderer"

    @JvmStatic
    fun find(className: String, classFileBuffer: ByteArray?): Unit {
        if (className.replace(".", "/") == (`Transformer$1`.classes[TessellatorMCPMapping] ?: return).name.replace(".", "/")) {
            val classNode = ClassNode()
            val classReader = ClassReader(classFileBuffer)
            classReader.accept(classNode, 0)

            for (method in classNode.methods) {
                if (method.name.contains("bridge$")) {
                    continue
                }
                // if static, continue
                if (method.access and 8 != 0) {
                    /*val returnType = methodDescReturnType(method.desc)
                    if ("L$className" == returnType) {
                        RenderUtils.`Tessellator$getInstanceString` = method.name
                    }*/
                    if (isThisMethodByReturnType(method, TessellatorMCPMapping)) {
                        `Tessellator$Data`.`Tessellator$getInstanceString` = method.name
                    }
                } else {
                    if (isThisMethodByReturnType(method, "V", true)) {
                        `Tessellator$Data`.`Tessellator$drawString` = method.name
                    } else if (isThisMethodByReturnType(method, WorldRendererMCPMapping)) {
                        `Tessellator$Data`.`Tessellator$getWorldRendererString` = method.name
                    } else {
                        Debug.println("Unknown method in Tessellator: ${method.name} ${method.desc}")
                    }
                }
            }
        }
    }
}