package me.monmcgt.code.transformers;

import me.monmcgt.code.Agentmain;
import me.monmcgt.code.Debug;
import me.monmcgt.code.Fields;
import me.monmcgt.code.KotlinAgentMainKt;
import me.monmcgt.code.finders.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class Transformer$1 implements ClassFileTransformer {
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
            TextComponentFinder.find(className, classfileBuffer);
            EntityOtherPlayerMPFinder.find(className, classfileBuffer);
            IsSinglePlayerMethodFinder.find(className, classfileBuffer);
            classfileBuffer = EntityRendererFinder.find(className, classfileBuffer);
            classfileBuffer = DrawSelectionMethodFinder.find(className, classfileBuffer);
            MinecraftDotGetRenderManagerFinder.find(className, classfileBuffer);
            MinecraftDotGetMinecraftMethodFinder.find(className, classfileBuffer);
            RenderManagerFieldChangerFinder.find(className, classfileBuffer);

            return classfileBuffer;
        }

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
}
