package me.monmcgt.code;

import me.monmcgt.code.commands.CommandManager;
import me.monmcgt.code.config.ConfigManager;
import me.monmcgt.code.helpers.ValueSetter;
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain;
import me.monmcgt.code.lunarclassfinder.LunarClassFinderMain;
import me.monmcgt.code.transformers.Transformer$1;

import java.lang.instrument.Instrumentation;

public class Agentmain {
    private static int classCount = 0;

    public static void premain(String agentArgs, Instrumentation inst) {
        processArgs(agentArgs, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
       processArgs(agentArgs, inst);
    }

    private static void processArgs(String agentArgs, Instrumentation inst) {
        if (agentArgs != null && agentArgs.equals("--debug")) {
            Debug.DEBUG = true;
        }

        Init.init();
        ConfigManager.INSTANCE.load(false);
        LunarClassFinderMain.main(new String[0]);
        KotlinAgentMainKt.run();
        CommandManager.INSTANCE.init();

        inst.addTransformer(new Transformer$1());
//        Debug.println("Agentmain.processArgs()");
        System.out.println("------------------------------------------------------");
        System.out.println("[LunarAgent] LunarAgent has been loaded.");

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);

//                    Debug.println("Agentmain.processArgs() - " + getClassCount());
                    System.out.println("[LunarAgent] Classes loaded: " + getClassCount());

                    if (classCount > 1500) {
                        System.out.println("------------------------------------------------------");

                        LauncherMain.start();

                        break;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new ValueSetter();
    }

    public static synchronized void addClassCount() {
        classCount++;
    }

    public static synchronized int getClassCount() {
        return classCount;
    }
}
