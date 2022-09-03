package me.monmcgt.code.finders;

import me.monmcgt.code.Debug;
import me.monmcgt.code.helpers.ValueSetter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TextComponentFinder {
    public static void find(String className, byte[] classFileBuffer) {
        if (!className.startsWith("net/minecraft")) {
            return;
        }

        ClassReader classReader = new ClassReader(classFileBuffer);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (MethodNode methodNode : classNode.methods) {
            Set<String> stringToMatch = new HashSet<>();
            stringToMatch.add("TextComponent{text='");

            boolean match = Arrays.stream(methodNode.instructions.toArray())
                    .filter(LdcInsnNode.class::isInstance)
                    .map(LdcInsnNode.class::cast)
                    .map (ldc -> ldc.cst)
                    .filter(stringToMatch::remove)
                    .anyMatch(__ -> stringToMatch.isEmpty());

            if (match) {
                Debug.println("Found TextComponent Class: " + className);

                ValueSetter.INSTANCE.set("TEXT_COMPONENT_CLASS", className);
            }
        }
    }
}
