package me.monmcgt.code.finders

import me.monmcgt.code.Fields
import me.monmcgt.code.lunarclassfinder.LunarClassFinderMain
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode

private val patchName = LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/renderer/entity/RenderManager")

object RenderManagerFieldChangerFinder {
    @JvmStatic
    fun find(className: String, classFileBuffer: ByteArray?): ByteArray? {
        if (className != patchName) {
            return classFileBuffer
        }

        val classReader = ClassReader(classFileBuffer)
        val classNode = ClassNode()
        classReader.accept(classNode, 0)

        val fields = classNode.fields

        val fieldNodes = arrayOf<FieldNode?>(null, null, null)

        var count = 0

        for (field in fields) {
            if (count > 2) {
                break
            } else {
//                println("count_ABC = $count")
            }

            val desc = field.desc
//            println("Field: ${field.name} ${field.desc}")

            if (desc == "D") {
//                println("HMMM 12")
                fieldNodes[count] = field
//                println("HM -> @@#$(")
                count++
//                println("WAAAA 53")

//                println("fieldNodes = $fieldNodes")

                // change private to public
                field.access = field.access and 0xFFFFFFF7.toInt()

//                println("count = $count")
            }

//            println("WOWOWOWO RAINBOWWWWW")
        }

        Fields.RENDER_MANAGER_POS_X_Y_Z_FIELDS = fieldNodes

        val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
        classNode.accept(classWriter)

        val toByteArray = classWriter.toByteArray()

        val cleanClassName = className.subSequence(className.lastIndexOf("/") + 1, className.length)
        MessageEventClassFinder.writeFile("$cleanClassName.class", toByteArray)

        return toByteArray
    }
}