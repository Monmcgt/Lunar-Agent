package me.monmcgt.code.finders;

import me.monmcgt.code.Debug;
import me.monmcgt.code.Fields;
import me.monmcgt.code.classes.C2S_Message;
import me.monmcgt.code.classes.MessageHook;
import me.monmcgt.code.classes.S2C_Message;
import me.monmcgt.code.listeners.JoinLeaveServerEventListener;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MessageEventClassFinder {
    public static boolean PRINT_NEW_EVENT = false;

    public static List<String> foundClasses = new ArrayList<>();

    public static volatile ClassLoader classLoader;

    private static short count = 0;

    public static byte[] format(String className, byte[] classFileBuffer) {
//        System.out.println("MESSAGE EVENT CLASS FINDERRRRRRRR");

        ClassReader classReader = new ClassReader(classFileBuffer);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        /*ClassWriter classWriter = new ClassWriter(classReader, 0);
        classNode.accept(classWriter);*/

        boolean thisClass = false;

        /*if (className.startsWith("lunar/aH/IIllIIIIIllIIIIlIIIlIIIll")) {
            Debug.println("[MessageEventClassFinder] Found class NAME: " + className);
            thisClass = true;
        }*/

        for (MethodNode methodNode : classNode.methods) {
            /*Set<String> stringToMatch = new HashSet<>();
//            stringToMatch.add("EventBus [");
            stringToMatch.add("SentryEventBus");
            stringToMatch.add("BetaSentryEventBus");
            stringToMatch.add("beta");

            *//*if (methodNode.name.equals("IllIlIIllIIlllIIllIlIlIII") && thisClass) {
                Debug.println("[MessageEventClassFinder] Found method NAME: " + methodNode.name);
                Arrays.stream(methodNode.instructions.toArray())
                        .filter(LdcInsnNode.class::isInstance)
                        .map(LdcInsnNode.class::cast)
                        .map (ldc -> ldc.cst)
                        .forEach(System.out::println);
            }
*//*
            boolean match = Arrays.stream(methodNode.instructions.toArray())
                    .filter(LdcInsnNode.class::isInstance)
                    .map(LdcInsnNode.class::cast)
                    .map (ldc -> ldc.cst)
                    .filter(stringToMatch::remove)
                    .anyMatch(__ -> stringToMatch.isEmpty());*/

            /*if (className.equals("lunar/aH/lIIIIIllIllIIlIIlllIIIlII") && methodNode.name.equals("IlIIIIllllllIlIIllIIllIll")) {
                Debug.println("---------------------------------in");
                System.out.println("Method desc: " + methodNode.desc);
                *//*Arrays.stream(methodNode.instructions.toArray())
                        .filter(LdcInsnNode.class::isInstance)
                        .map(LdcInsnNode.class::cast)
                        .map(ldc -> ldc.cst)
                        .map (Object::toString)
                        .map((e) -> e = e.replace("\u0001", "&&&"))
                        .forEach((e) -> {
                            Debug.println("[MessageEventClassFinder123] Found LDC: " + e);
                        });*//*

                List<Object[]> collect = Arrays.stream(methodNode.instructions.toArray())
                        .filter(InvokeDynamicInsnNode.class::isInstance)
                        .map(InvokeDynamicInsnNode.class::cast)
                        .map((a) -> a.bsmArgs).collect(Collectors.toList());

                Debug.println("Collect size: " + collect.size());
                Debug.println("Collect: " + collect);
                for (Object[] objects : collect) {
                    System.out.println("Objects: " + Arrays.toString(objects).replace("\u0001", "&&&"));
                }
                collect.forEach((e) -> {
                    System.out.println("Collect123: " + Arrays.toString(e));
                });
                Debug.println("---------------------------------out");
            }*/


            Set<String> stringToMatch = new HashSet<>();
            stringToMatch.add("EventBus [\u0001]: \u0001");
            Arrays.stream(methodNode.instructions.toArray())
                    .filter(InvokeDynamicInsnNode.class::isInstance)
                    .map(InvokeDynamicInsnNode.class::cast)
                    .map((a) -> a.bsmArgs).forEach((e) -> {
                        for (Object object : e) {
                            if (object instanceof String) {
                                String string = (String) object;
                                stringToMatch.remove(string);
                            }
                        }
                    });

            Set<String> ldcNodeStringNotToMatch = new HashSet<>();
            ldcNodeStringNotToMatch.add("EventBus");
            Arrays.stream(methodNode.instructions.toArray())
                    .filter(LdcInsnNode.class::isInstance)
                    .map(LdcInsnNode.class::cast)
                    .map(ldc -> ldc.cst)
                    .map (Object::toString)
                    .forEach(ldcNodeStringNotToMatch::remove);

            boolean match = stringToMatch.isEmpty() && !ldcNodeStringNotToMatch.isEmpty();

            if (match) {
//                classNode.accept(classWriter);

                Debug.println("[EventBus] Found Message Event Class: " + className);

                // insert before return
//                methodNode.instructions.insertBefore(methodNode.instructions.getLast(), new LdcInsnNode(className));

//                MethodVisitor methodVisitor = new MethodVisitor(Opcodes.ASM9) {};


                /*Label end = new Label();
                insert(methodNode, new VarInsnNode(Opcodes.ALOAD, 1));
                methodNode.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
                methodNode.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
                // access field of `me.monmcgt.code.Fields` named `EVENT_BUS_MESSAGE_EVENT_CLASSES_NAME` (string)
                methodNode.visitFieldInsn(Opcodes.GETSTATIC, "me/monmcgt/code/Fields", "EVENT_BUS_MESSAGE_EVENT_CLASSES_NAME", "Ljava/lang/String;");
                // compare the two strings
                methodNode.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
                // if not equal, jump to end
                methodNode.visitJumpInsn(Opcodes.IFEQ, end);
                // call command handler
                methodNode.visitMethodInsn(Opcodes.INVOKESTATIC, "me/monmcgt/code/finders/MessageEventClassFinder", "commandHandler", "(Ljava/lang/Object;)V", false);
                // jump to end
                methodNode.visitJumpInsn(Opcodes.GOTO, end);*/


                /*methodNode.instructions.insert(new VarInsnNode(Opcodes.ALOAD, 1));
                methodNode.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
                methodNode.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
                methodNode.visitFieldInsn(Opcodes.GETSTATIC, "me/monmcgt/code/Fields", "EVENT_BUS_MESSAGE_EVENT_CLASSES_NAME", "Ljava/lang/String;");
                methodNode.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
                *//*Label l1 = new Label();
                methodNode.visitJumpInsn(Opcodes.IFEQ, l1);
                methodNode.visitMethodInsn(Opcodes.INVOKESTATIC, "me/monmcgt/code/finders/MessageEventClassFinder", "commandHandler", "(Ljava/lang/Object;)V", false);
                methodNode.visitJumpInsn(Opcodes.GOTO, l1);*/

                // insert before try catch
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), new MethodInsnNode(Opcodes.INVOKESTATIC, "me/monmcgt/code/finders/MessageEventClassFinder", "analyseObject", "(Ljava/lang/Object;)V", false));
                // load first argument
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 1));

                // insert
                // invoke method me/monmcgt/code/finders/MessageEventClassFinder.processClassLoader(Thread thread)
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), new MethodInsnNode(Opcodes.INVOKESTATIC, "me/monmcgt/code/finders/MessageEventClassFinder", "processClassLoader", "(Ljava/lang/Thread;)V", false));
                // load Thread.currentThread()
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Thread", "currentThread", "()Ljava/lang/Thread;", false));

                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                classNode.accept(classWriter);

                writeFile("IIllIIIIIllIIIIlIIIlIIIll.class", classWriter.toByteArray());

                return classWriter.toByteArray();



                /*
                run the following code when the method is exiting
                */
//                AdviceAdapter adviceAdapter = new AdviceAdapter(Opcodes.ASM9, )



//                return classWriter.toByteArray();

//                ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM9)
            }
        }

//        return classWriter.toByteArray();
        return classFileBuffer;
    }

    public static void commandHandler(Object eventMessageClassObject) {
        Debug.println("EventBus Message Event Class: " + eventMessageClassObject.getClass().getName());
    }

    public static void analyseObject(Object object) {
//        System.out.println("Object: " + object.getClass().getName());
        String eventBusMessageEventClassesName = Fields.EVENT_BUS_MESSAGE_EVENT_CLASSES_NAME;
        if (eventBusMessageEventClassesName == null) {
            return;
        } else {
//            System.out.println("EventBus Message Event Class: " + eventBusMessageEventClassesName);
        }

        String className = object.getClass().getName().replace(".", "/");

        if (!foundClasses.contains(className)) {
            foundClasses.add(className);
            if (PRINT_NEW_EVENT) {
                Debug.println("-----------------------------------------------------");
                Debug.println("New Event Class: " + className);
                Method[] methods = object.getClass().getMethods();
                for (Method method : methods) {
                    Debug.println("Method: " + method.getName() + " | " + method.getReturnType().getName());
                }
                Field[] fields = object.getClass().getFields();
                for (Field field : fields) {
                    Debug.println("Field: " + field.getName() + " | " + field.getType().getName());
                }
                Debug.println("-----------------------------------------------------");
            }
        }

        /*if (className.equals("lunar/aK/IIllIlIlllIIIIIIlllllIlII")) {
            try {

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }*/

        /*if (!className.equals(eventBusMessageEventClassesName)) {
            return;
        }*/

        try {
            Class<?> objectClass = object.getClass();
            if (className.equals(Fields.EVENT_BUS_MESSAGE_EVENT_CLASSES_NAME)) {
                // C2S
                Method getMessage = objectClass.getMethod("getMessage");
                String message = (String) getMessage.invoke(object);

                Method setCancelled = objectClass.getMethod("setCancelled", boolean.class);

//            System.out.println("Message: " + message);

            /*if (message.contains("cancel")) {
                setCancelled.invoke(object, true);
                System.out.println("Cancelled");
            }*/
                /*if (CommandManager.INSTANCE.onMessage(message)) {
//                UtilityKt.printBlankLineInChat(false);
                    setCancelled.invoke(object, true);
                }*/
                if (MessageHook.INSTANCE.c2s(new C2S_Message(message, false)).getCancel()) {
                    setCancelled.invoke(object, true);
                }
            } else if (className.equals(Fields.EVENT_BUS_FORMATTED_MESSAGE_EVENT_CLASSES_NAME)) {
                // S2C
                Field field = object.getClass().getDeclaredField("formattedMessage");
                String chatIn = (String) field.get(object);
                Method setCancelled = objectClass.getMethod("setCancelled", boolean.class);
                Debug.println("formattedMessage: " + chatIn);

                if (MessageHook.INSTANCE.s2c(new S2C_Message(chatIn, false)).getCancel()) {
                    setCancelled.invoke(object, true);
                }
            } else if (/*objectClass == Fields.EVENT_JOIN_SERVER_CLASS*/ /*className.equals(Fields.EVENT_JOIN_SERVER_CLASS.getName())*/ className.equals(Fields.EVENT_JOIN_SERVER_CLASS_NAME)) {
//                System.out.println("EVENT_JOIN_SERVER_CLASS");
                Field ip = objectClass.getDeclaredField("ip");
                Field port = objectClass.getDeclaredField("port");
//                System.out.println("A");
                String ipStr = (String) ip.get(object);
//                System.out.println("B");
                int portInt = (int) port.get(object);
//                System.out.println("C");
                // do something
//                System.out.println("ipStr = " + ipStr);
//                System.out.println("portInt = " + portInt);
                JoinLeaveServerEventListener.INSTANCE.onJoinServer(ipStr, portInt);
            } else if (className.equals(Fields.EVENT_LEAVE_SERVER_CLASS_NAME)) {
                JoinLeaveServerEventListener.INSTANCE.onLeaveServer();
            } else if (className.equals("lunar/aL/IIllIIIIIllIIIIlIIIlIIIll")) {
                /*Method getText = objectClass.getMethod("getText");
                List<?> text = (List<?>) getText.invoke(object);
                for (Object o : text) {
                    if (o instanceof String) {
                        String s = (String) o;
                        Debug.println("lunar/aL/IIllIIIIIllIIIIlIIIlIIIll getText1: " + s);
                    } else {
                        Debug.println("lunar/aL/IIllIIIIIllIIIIlIIIlIIIll getText2: " + text);
                    }
                }*/

            } else if (className.equals("lunar/aN/IllIlIIllIIlllIIllIlIlIII")) {
                /*Debug.println("lunar/aN/IllIlIIllIIlllIIllIlIlIII.........");
                Field f1 = objectClass.getField("lllIIlIIlIlIlIlllIllIIIIl");
                Field f2 = objectClass.getField("IIlllIIIlllIlIlIlllllIIIl");
                Field f3 = objectClass.getField("IIllIIIllllllIlIlllIlIlIl");
                boolean fv1 = (boolean) f1.get(object);
                Object fv2 = f2.get(object);
                Object fv3 = f3.get(object);
//                Enum<?> fv2Enum = (Enum<?>) fv2;
//                Enum<?> fv3Enum = (Enum<?>) fv3;
//                Debug.println("lunar/aN/IllIlIIllIIlllIIllIlIlIII fv1: " + fv1);
                Enum<?>[] enumConstants2 = fv2Enum.getDeclaringClass().getEnumConstants();
                Enum<?>[] enumConstants3 = fv3Enum.getDeclaringClass().getEnumConstants();
                for (Enum<?> e : enumConstants2) {
                    Debug.println("lunar/aN/IllIlIIllIIlllIIllIlIlIII enumConstants 2: " + e.name());
                }
                for (Enum<?> e : enumConstants3) {
                    Debug.println("lunar/aN/IllIlIIllIIlllIIllIlIlIII enumConstants 3: " + e.name());
                }
                Field fv3f = fv3Enum.getDeclaringClass().getField("IllIllllIIllIlIIIllIIIlll");
                Object[] fv3fv = (Object[]) fv3f.get(fv3Enum);
                for (Object o : fv3fv) {
                    Debug.println("lunar/aN/IllIlIIllIIlllIIllIlIlIII fv3fv: " + o);
                }
                Method[] fv3m = fv3.getClass().getMethods();
                for (Method m : fv3m) {
                    Debug.println("lunar/aN/IllIlIIllIIlllIIllIlIlIII fv3m: " + m.getName() + " | " + m.getReturnType());
                }
                String fv2v = (String) fv2.getClass().getMethod("getName").invoke(fv2);
                String fv3v = (String) fv3.getClass().getMethod("name").invoke(fv3);*/
//                Debug.println("lunar/aN/IllIlIIllIIlllIIllIlIlIII fv2v: " + fv2v);
//                Debug.println("lunar/aN/IllIlIIllIIlllIIllIlIlIII fv3v: " + fv3v);

            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | ClassCastException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insert(MethodNode methodNode, AbstractInsnNode insnNode) {
        methodNode.instructions.insertBefore(new VarInsnNode(Opcodes.ARETURN, 0), insnNode);
    }

    public static void writeFile(String fileName, byte[] bytes) {
        if (false) {
            Debug.println("Agent.writeFile()");
            File file = new File(fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            // write bytes to file
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void processClassLoader(Thread thread) {
//        System.out.println("Agent.processClassLoader()");
        ClassLoader contextClassLoader = thread.getContextClassLoader();
        if (contextClassLoader == null) {
            return;
        }

        if (classLoader == null) {
            classLoader = contextClassLoader;
        } else {
            if (++count == 20000) {
                count = 0;
            }

            if (count == 0) {
                classLoader = contextClassLoader;
            }
        }
    }
}
