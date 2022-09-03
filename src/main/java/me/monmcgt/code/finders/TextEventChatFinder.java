package me.monmcgt.code.finders;

import me.monmcgt.code.Debug;
import me.monmcgt.code.Fields;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TextEventChatFinder {
    public static void find(String className, byte[] classFileBuffer) {
        ClassReader classReader = new ClassReader(classFileBuffer);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        // if super class is object, return
        if (classNode.superName.equals("java/lang/Object")) {
            return;
        }

        Set<String> methodsName = new HashSet<>();
        addSet(methodsName);

        boolean match = Arrays.stream(classNode.methods.toArray())
                .map((m) -> (MethodNode) m)
                .map(m -> m.name)
                .filter(methodsName::remove)
                .anyMatch(__ -> methodsName.isEmpty());

        if (match) {
            Debug.println("[TextEventChatFinder] Found: " + className);

            Fields.EVENT_BUS_MESSAGE_EVENT_CLASSES_NAME = className;
        }
    }

    private static void addSet(Set<String> set) {
        set.add("get");
        set.add("setMessage");
        set.add("getMessage");
    }
}
