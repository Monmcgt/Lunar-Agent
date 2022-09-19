package me.monmcgt.code.transformers;

import me.monmcgt.code.Agentmain;
import me.monmcgt.code.Debug;
import me.monmcgt.code.Fields;
import me.monmcgt.code.KotlinAgentMainKt;
import me.monmcgt.code.finders.*;
import me.monmcgt.code.lunarclassfinder.LunarClassFinderMain;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Transformer$1 implements ClassFileTransformer {
    // net/minecraft/client/Minecraft | and its Class
    public static final Map<String, Class<?>> classes = new HashMap<>();
    public static final ScheduledExecutorService loadClassExecutor = Executors.newScheduledThreadPool(5);

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        Agentmain.addClassCount();

        if (!KotlinAgentMainKt.getHasInitClasses()) {
            /*
             val renderGlobal = Class.forName(renderGlobalPatchName)
    val axisAlignedBB = Class.forName(axisAlignedBBPatchName)

    Fields.RENDER_GLOBAL_CLASS = renderGlobal
    Fields.AXIS_ALIGNED_BB_CLASS = axisAlignedBB
    * */

            try {
                Class<?> renderGlobal = loader.loadClass(KotlinAgentMainKt.renderGlobalPatchName);
                Class<?> axisAlignedBB = loader.loadClass(KotlinAgentMainKt.axisAlignedBBPatchName);

//                Class<?> renderGlobal = LauncherMain.INSTANCE.classLoader.loadClass(KotlinAgentMainKt.renderGlobalPatchName);
//                Class<?> axisAlignedBB = LauncherMain.INSTANCE.classLoader.loadClass(KotlinAgentMainKt.axisAlignedBBPatchName);

                Debug.println("renderGlobal = " + renderGlobal);
                Debug.println("axisAlignedBB = " + axisAlignedBB);

                Fields.RENDER_GLOBAL_CLASS = renderGlobal;
                Fields.AXIS_ALIGNED_BB_CLASS = axisAlignedBB;

//                System.out.println("[KotlinAgent] Loaded classes: " + KotlinAgentMainKt.renderGlobalPatchName + ", " + KotlinAgentMainKt.axisAlignedBBPatchName);

                /*if (!KotlinAgentMainKt.getHasInitClasses()) {
                    LauncherMain.start();
                }*/

                List<String> allMinecraftClassesPatchName = LunarClassFinderMain.LunarClassFinderMAIN.getAllMinecraftClassesPatchName();
                short max = 10;
                String[] part = new String[max];
                short i = 0;
                for (String s : allMinecraftClassesPatchName) {
                    /*String patchName = LunarClassFinderMain.LunarClassFinderMAIN.getPatchName(s).replace('/', '.');
                    Class<?> aClass = loader.loadClass(patchName);
                    classes.put(s, aClass);
                    Debug.println("Loaded class: " + s + " -> " + aClass);*/
                    if (i == max) {
                        i = 0;
                        final String[] finalPart = part;
                        new Thread(() -> {
                            for (String s1 : finalPart) {
                                String patchName = LunarClassFinderMain.LunarClassFinderMAIN.getPatchName(s1).replace('/', '.');
                                if (patchName.equals("NULL_NULL_NULL_NULL")) {
                                    continue;
                                }
                                try {
                                    Class<?> aClass = loader.loadClass(patchName);
//                                    classes.put(s1, aClass);
                                    addClass(s1, aClass);
//                                    Debug.println("Loaded class: " + s1 + " -> " + aClass);
                                } catch (ClassNotFoundException e) {
//                                    e.printStackTrace();
                                    loadClassExecutor.scheduleWithFixedDelay(() -> {
                                        try {
                                            Class<?> aClass = loader.loadClass(patchName);
                                            addClass(s1, aClass);
                                            // terminate the task
                                            Thread.currentThread().interrupt();
                                        } catch (ClassNotFoundException classNotFoundException) {
//                                            classNotFoundException.printStackTrace();
                                        }
                                    }, 0, 10, TimeUnit.MILLISECONDS);
                                }
                            }
                            Debug.println("Classes size: " + classes.size());
                        }).start();
                        part = new String[max];
                    }
                    part[i] = s;
                    i++;
                }

                KotlinAgentMainKt.setHasInitClasses(true);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        /*if (className.toLowerCase().contains("gl11")) {
            Debug.println("-----------------");
            Debug.println("[KotlinAgent] Found GL11 class: " + className);
            Debug.println("-----------------");
        }*/

        if (!className.startsWith("lunar/")) {
            // Minecraft classes
            TextComponentFinder.find(className, classfileBuffer);
            EntityOtherPlayerMPFinder.find(className, classfileBuffer);
            IsSinglePlayerMethodFinder.find(className, classfileBuffer);
            classfileBuffer = EntityRendererFinder.find(className, classfileBuffer);
            classfileBuffer = DrawSelectionMethodFinder.find(className, classfileBuffer);
            MinecraftDotGetRenderManagerFinder.find(className, classfileBuffer);
            MinecraftDotGetMinecraftMethodFinder.find(className, classfileBuffer);
            RenderManagerFieldChangerFinder.find(className, classfileBuffer);
            RenderUtilsHelperFinder.find(className, classfileBuffer);

            return classfileBuffer;
        }

        // Lunar classes
        ThatInterfaceFinder.find(className, classfileBuffer);
        TextEventChatFinder.find(className, classfileBuffer);
        FormattedTextEventChatFinder.find(className, classfileBuffer);
        JoinServerEventClassFinder.find(className, classfileBuffer);
        LeaveServerEventClassFinder.find(className, classfileBuffer);
        LunarAFinder.find(className, classfileBuffer);
        LunarFCFinder.find(className, classfileBuffer);
        classfileBuffer = MessageEventClassFinder.format(className, classfileBuffer);

        return classfileBuffer;
    }

    public static synchronized void addClass(String className, Class<?> aClass) {
        classes.put(className, aClass);
    }
}
