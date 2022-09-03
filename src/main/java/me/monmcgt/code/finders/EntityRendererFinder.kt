package me.monmcgt.code.finders

import me.monmcgt.code.Debug
import me.monmcgt.code.helpers.BufferedHelper
import me.monmcgt.code.lunarclassfinder.LunarClassFinderMain
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import java.util.*

private val minecraftClassPatchName = LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/Minecraft")
private val renderManagerClassPatchName = LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/renderer/entity/RenderManager")

object EntityRendererFinder {
    @JvmStatic
    fun find(className: String, classFileBuffer: ByteArray?): ByteArray? {
        if (!className.startsWith("net/minecraft")) {
            return classFileBuffer
        }

        val classReader = ClassReader(classFileBuffer)
        val classNode = ClassNode()
        classReader.accept(classNode, 0)

        for (method in classNode.methods) {
            val stringToMatch = HashSet<String>()
            stringToMatch.add("aboveClouds")

            val match = Arrays.stream<AbstractInsnNode>(method.instructions.toArray())
                .filter { o: AbstractInsnNode? ->
                    LdcInsnNode::class.java.isInstance(
                        o
                    )
                }
                .map { o: AbstractInsnNode? ->
                    LdcInsnNode::class.java.cast(
                        o
                    )
                }
                .map { ldc: LdcInsnNode -> ldc.cst }
                .filter { o: Any? -> stringToMatch.remove(o) }
                .anyMatch { _: Any? -> stringToMatch.isEmpty() }

            if (match) {
                Debug.println("Found EntityRenderer in $className")

//                method.instructions.insertBefore(LdcInsnNode("hand"), MethodInsnNode(Opcodes.INVOKESTATIC, "me/monmcgt/code/finders/EntityRenderedHelper", "onRender", "()V", false))

                /*var instruction0: AbstractInsnNode? = null
                var instruction1: AbstractInsnNode? = null
                var instruction2: AbstractInsnNode? = null
                var instruction3: AbstractInsnNode? = null
                var instruction4: AbstractInsnNode? = null
                var instruction5: AbstractInsnNode? = null

                for (instruction in method.instructions.toArray()) {
                    instruction0 = instruction1
                    instruction1 = instruction2
                    instruction2 = instruction3
                    instruction3 = instruction4
                    instruction4 = instruction5
                    instruction5 = instruction

                    if (instruction0 != null && instruction1 != null && instruction2 != null && instruction3 != null && instruction4 != null && instruction5 != null) {
                        if (instruction4.opcode == Opcodes.LDC) {
                            val ldc = LdcInsnNode::class.java.cast(instruction4)
                            if (ldc.cst == "hand") {
//                                method.instructions.insertBefore(instruction0, MethodInsnNode(Opcodes.INVOKESTATIC, "me/monmcgt/code/finders/EntityRendererFinder", "onRender", "()V", false))
                                method.instructions.insertBefore(instruction0, MethodInsnNode(Opcodes.INVOKESTATIC, "me/monmcgt/code/modules/ESP", "onRender", "()V", false))
                            }
                        }
                    }
                }*/

                val bufferedHelper = BufferedHelper<AbstractInsnNode>(13)

                for (instruction in method.instructions.toArray()) {
                    bufferedHelper.swap(instruction)

                    if (bufferedHelper.allNotNull()) {
                        if (bufferedHelper.last.opcode == Opcodes.LDC) {
                            val ldc = LdcInsnNode::class.java.cast(bufferedHelper.last)
                            if (ldc.cst == "hand") {
                                method.instructions.insertBefore(bufferedHelper.first, MethodInsnNode(Opcodes.INVOKESTATIC, "me/monmcgt/code/modules/ESP", "onRender", "()V", false))
                            }
                        }
                    }
                }

//                method.instructions.insertBefore(method.instructions.first, MethodInsnNode(Opcodes.INVOKESTATIC, "me/monmcgt/code/modules/ESP", "onRender", "()V", false))

                val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
                classNode.accept(classWriter)

//                writeFile("EntityRenderer.class", classWriter.toByteArray())

                return classWriter.toByteArray()
            }
        }

        return classFileBuffer
    }
}