package me.monmcgt.code.finders;

import me.monmcgt.code.Debug;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LunarAFinder {
    public static void find(String className, byte[] classFileBuffer) {
        ClassReader classReader = new ClassReader(classFileBuffer);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (MethodNode methodNode : classNode.methods) {
            Set<String> stringToMatch = new HashSet<>();
            stringToMatch.add("Can't reset Bridge Implementation!");

            boolean match = Arrays.stream(methodNode.instructions.toArray())
                    .filter(LdcInsnNode.class::isInstance)
                    .map(LdcInsnNode.class::cast)
                    .map (ldc -> ldc.cst)
                    .filter(stringToMatch::remove)
                    .anyMatch(__ -> stringToMatch.isEmpty());

            if (match) {
                Debug.println("Found LunarA class: " + className);
                String param = getParam(methodNode);
                getMethodReturnThatParam(param, classNode);
            }
        }
    }

    private static String getParam(MethodNode methodNode) {
        Debug.println("Getting param for method: " + methodNode.name);
        List<ParameterNode> parameters = methodNode.parameters;
        Debug.println("Found " + parameters.size() + " params");
        String paramName = parameters.get(0).name;
        Debug.println("LunarA Param name: " + paramName);

        return paramName;
    }

    private static void getMethodReturnThatParam(String paramClassName, ClassNode classNode) {
        Debug.println("Getting method that returns " + paramClassName);
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.desc.contains(paramClassName)) {
                Debug.println("Found LunarA Method: " + classNode.name);
            }
        }
    }
}
