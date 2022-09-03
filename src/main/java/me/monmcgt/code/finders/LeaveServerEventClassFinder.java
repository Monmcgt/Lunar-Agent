package me.monmcgt.code.finders;

import me.monmcgt.code.Debug;
import me.monmcgt.code.Fields;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LeaveServerEventClassFinder {
    public static void find(String className, byte[] classFileBuffer) {
        ClassReader classReader = new ClassReader(classFileBuffer);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        if (!classNode.superName.equals("org/java_websocket/client/WebSocketClient")) {
            return;
        }

        Debug.println("--------------------------------");
        Debug.println("[LeaveServerEventClassFinder] Found class: " + className);

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("<init>")) {
                List<String> stringList = Arrays.stream(methodNode.instructions.toArray())
                        .filter(LdcInsnNode.class::isInstance)
                        .map(LdcInsnNode.class::cast)
                        .map((i) -> i.cst)
                        .map((s) -> {
                            Debug.println("[LeaveServerEventClassFinder] Found LDC: " + s);
                            return s.toString();
                        })
                        .collect(Collectors.toList());
                String leaveServerEvent = null;
                for (String s : stringList) {
                    if (s.startsWith("Llunar/")) {
                        leaveServerEvent = s;
                        break;
                    }
                }
                if (leaveServerEvent != null) {
                    Debug.println("[LeaveServerEventClassFinder] Found leave server event: " + leaveServerEvent);
                    Fields.EVENT_LEAVE_SERVER_CLASS_NAME = leaveServerEvent.substring(1, leaveServerEvent.length() - 1);
                }
            }
        }

        Debug.println("--------------------------------");
    }
}
