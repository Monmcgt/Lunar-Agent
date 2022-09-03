package me.monmcgt.code.finders;

import me.monmcgt.code.Debug;
import me.monmcgt.code.helpers.ValueSetter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;

public class EntityOtherPlayerMPFinder {
    public static void find(String className, byte[] classFileBuffer) {
        if (!className.startsWith("net/minecraft")) {
            return;
        }

        ClassReader classReader = new ClassReader(classFileBuffer);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        MethodNode constructor = null;
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("<init>")) {
                constructor = methodNode;
            }
        }

        if (constructor == null) {
            return;
        }

        boolean found = false;

        if (constructor.desc.contains("Lcom/mojang/authlib/GameProfile;")) {
            String[] split = constructor.desc.split(";");
            int constructorParamLength = split.length;
            if (split[0].contains("Lnet/minecraft/") && split.length == 3) {
//                System.out.println("Found EntityOtherPlayerMP constructor: " + className + "." + constructor.name + constructor.desc);

                for (MethodNode methodNode : classNode.methods) {
                    String methodDesc = methodNode.desc;
                    /*String[] methodDescSplit = methodDesc.split(",");
                    if (methodDescSplit.length >= 7) {
                        if (methodDescSplit[0].contains("D") && methodDescSplit[1].contains("D") && methodDescSplit[2].contains("D") && methodDescSplit[3].contains("F") && methodDescSplit[4].contains("F") && methodDescSplit[5].contains("I") && methodDescSplit[6].contains("Z")) {
                            found = true;
                            System.out.println("Found EntityOtherPlayerMP updatePositionAndRotation method: " + className + "." + methodNode.name + methodNode.desc);
                        } else if (className.startsWith("net/minecraft/v1_8/shesshspppsepeheahppsahse")) {
                            System.out.println("EntityOtherPlayerMP NOT FOUND IDK WHY 2");
                            for (String s : methodDescSplit) {
                                System.out.print(s + " | ");
                            }
                            System.out.println();
                        }
                    } else if (className.startsWith("net/minecraft/v1_8/shesshspppsepeheahppsahse")) {
                        System.out.println("EntityOtherPlayerMP NOT FOUND IDK WHY 1");
                        System.out.println("Class: " + className);
                        System.out.println(methodDesc);
                    }*/
                    String methodDescClean = methodDesc.substring(1, methodDesc.length() - 2);
                    if (methodDescClean.equals("DDDFFIZ")) {
                        found = true;
                        Debug.println("Found EntityOtherPlayerMP updatePositionAndRotation method: " + className + "." + methodNode.name + methodNode.desc);
                    } /*else if (className.startsWith("net/minecraft/v1_8/shesshspppsepeheahppsahse")) {
                        System.out.println("EntityOtherPlayerMP NOT FOUND IDK WHY 2");
                        System.out.println("Class: " + className);
                        System.out.println(methodDescClean);
                    }*/
                }
            } /*else if (className.startsWith("net/minecraft/v1_8/shesshspppsepeheahppsahse")) {
                System.out.println("EntityOtherPlayerMP NOT FOUND IDK WHY 3");
                System.out.println(constructor.desc);
            }*/
        } /*else if (className.startsWith("net/minecraft/v1_8/shesshspppsepeheahppsahse")) {
            System.out.println("EntityOtherPlayerMP NOT FOUND IDK WHY 4");
            System.out.println(constructor.desc);
        }*/

        /*if (!found) {
            if (className.startsWith("net/minecraft/v1_8/shesshspppsepeheahppsahse")) {
                System.out.println("EntityOtherPlayerMP NOT FOUND IDK WHY 0");
            }
        }*/

        if (found) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldNode> fields = classNode.fields;
            FieldNode[] fieldsArray = fields.toArray(new FieldNode[0]);
            fields.stream()
                    .map((field) -> field.desc)
                    .forEach(stringBuilder::append);
            if (stringBuilder.toString().equals("ZIDDDDD")) {
                String[] pos = new String[5];
                // copy fieldsArray index 2-6 to pos
                for (int i = 0; i < 5; i++) {
                    pos[i] = fieldsArray[i + 2].name;
                }

                ValueSetter instance = ValueSetter.INSTANCE;
                instance.set("ENTITY_OTHER_PLAYER_MP_CLASS", className);
                instance.set("ENTITY_OTHER_PLAYER_MP_POS_X_FIELD", pos[0]);
                instance.set("ENTITY_OTHER_PLAYER_MP_POS_Y_FIELD", pos[1]);
                instance.set("ENTITY_OTHER_PLAYER_MP_POS_Z_FIELD", pos[2]);
                instance.set("ENTITY_OTHER_PLAYER_MP_YAW_FIELD", pos[3]);
                instance.set("ENTITY_OTHER_PLAYER_MP_PITCH_FIELD", pos[4]);

                Debug.println("Found EntityOtherPlayerMP class: " + className);
            }
        }
    }
}
