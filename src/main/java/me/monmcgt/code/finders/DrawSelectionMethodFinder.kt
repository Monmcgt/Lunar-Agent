package me.monmcgt.code.finders

import me.monmcgt.code.Debug
import me.monmcgt.code.Fields
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode

object DrawSelectionMethodFinder {
    @JvmStatic
    fun find(className: String, classFileBuffer: ByteArray?): ByteArray? {
        if (className.replace(".", "/") != Fields.RENDER_GLOBAL_CLASS.name.replace(".", "/")) {
            return classFileBuffer
        }

        val axisAlignedBBClassName = Fields.AXIS_ALIGNED_BB_CLASS.name.replace(".", "/")

        val classReader = ClassReader(classFileBuffer)
        val classNode = ClassNode()
        classReader.accept(classNode, 0)

        for (methodNode in classNode.methods) {
            val desc = methodNode.desc
            val param = desc.replace("(", "").replace(")V", "")
            val paramSplit = param.split(";")
            if (paramSplit.size > 1 && paramSplit[1].isNotEmpty()) {
//                println("NOT MATCHED: $param")

                if (paramSplit[0] == "L$axisAlignedBBClassName" && paramSplit[1] == "IIII") {
                    Fields.func_181563_a_name = methodNode.name
                    Debug.println("Found drawOutlineBoundingBox method: ${methodNode.name}")
                }

                continue
            }
            if (paramSplit[0] == "L$axisAlignedBBClassName") {
                Debug.println("Found drawSelectionBox method: ${methodNode.name}")
                Debug.println("Fields.RENDER_GLOBAL_CLASS.name = ${Fields.RENDER_GLOBAL_CLASS.name}")

                /*println("Fields.RENDER_GLOBAL_CLASS = ${Fields.RENDER_GLOBAL_CLASS}")
                *//*println("Fields.RENDER_GLOBAL_CLASS.methods = ${Fields.RENDER_GLOBAL_CLASS.methods}")
                val method = Fields.RENDER_GLOBAL_CLASS.methods.find { it.name == methodNode.name } ?: throw RuntimeException("Method not found: ${methodNode.name}")
                Debug.println("Found drawSelectionBox method 2: $method")
                Fields.func_181561_a = method*//*
                try {
                    val method: Any? = Fields.RENDER_GLOBAL_CLASS.getMethod(methodNode.name, Fields.AXIS_ALIGNED_BB_CLASS)
                    println("method = ${method}")
                } catch (e: Exception) {
                    e.printStackTrace()
                }*/
//                Fields.func_181561_a = method
                Fields.func_181561_a_name = methodNode.name
            }
        }

        /*println("----------------------------------------")
        classNode.methods.forEach { println(" >> $it") }*/

        return classFileBuffer
    }
}