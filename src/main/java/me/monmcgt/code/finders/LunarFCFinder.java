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

public class LunarFCFinder {
    public static void find(String className, byte[] classFileBuffer) {
        /*if (className.startsWith("lunar/fc/lIlIllIIIIIIllIIIIllIIIII")) {
            System.out.println("[FC Finder] this is a FC class");
        }*/

        ClassReader classReader = new ClassReader(classFileBuffer);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        boolean match = false;
        boolean match2 = false;

        for (MethodNode methodNode : classNode.methods) {
            if (match) {
                break;
            }

            Set<String> stringToMatch = new HashSet<>();
            stringToMatch.add("replaymod.title");

            match = Arrays.stream(methodNode.instructions.toArray())
                    .filter(LdcInsnNode.class::isInstance)
                    .map(LdcInsnNode.class::cast)
                    .map (ldc -> ldc.cst)
                    .filter(stringToMatch::remove)
                    .anyMatch(__ -> stringToMatch.isEmpty());
        }

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.contains("sendMessage")) {
                match2 = true;
                break;
            }
        }

        if (match && match2) {
            Debug.println("Found lunar.fc class: " + className);
            String thatMethod = findThatMethod(classNode);

            ValueSetter.INSTANCE.set("LUNAR_FC_CLASS", className);
            ValueSetter.INSTANCE.set("PRE_PRINT_CHAT_METHOD_IN_LUNAR_FC", thatMethod);
        } /*else {
            if (className.startsWith("lunar/fc/lIlIllIIIIIIllIIIIllIIIII")) {
                System.out.println("[FC Finder] Not found idk why");
                System.out.println("match: " + match);
                System.out.println("match2: " + match2);
                *//*for (String l : stringToMatch) {
                    System.out.println("[FC Finder] Not found: " + l);
                }*//*
                List<MethodNode> collect = Arrays.stream(classNode.methods.toArray())
                        .map((m) -> (MethodNode) m)
                        .collect(Collectors.toList());

                for (MethodNode methodNode1 : collect) {
                    System.out.println("Method: " + methodNode1.name);
                }
            }
        }*/
    }

    private static String findThatMethod(ClassNode classNode) {
        String targetReturnType = ThatInterfaceFinder.name;

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.desc.equals("()L" + targetReturnType + ";")) {
                Debug.println("[FC Finder] Found method: " + methodNode.name);
                return methodNode.name;
            }
        }

        return null;
    }
}
