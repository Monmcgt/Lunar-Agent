package me.monmcgt.code.bedwarsstatslunarinject;

import me.monmcgt.code.bedwarsstatslunarinject.configs.ConfigManager_legacy;
import me.monmcgt.code.bedwarsstatslunarinject.consumers.ChatConsumer;
import me.monmcgt.code.bedwarsstatslunarinject.threads.PlayerWatchedThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OverlayMain {
    public static final File homePath = new File(System.getProperty("user.home"));

    public static void main(String[] args) {
        ConfigManager_legacy.initConfig();
        garbageCollect();
        logWatcher();
        PlayerWatchedThread.startScheduledThread();

//        Runtime.getRuntime().addShutdownHook(new Thread(ConfigManager::saveConfig));
    }

    public static void logWatcher() {
        ChatConsumer chatConsumer = new ChatConsumer();
        PlayerWatchedThread playerWatchedThread = new PlayerWatchedThread();

        new Thread(() -> {
            File logFileDirectory = new File(homePath, String.format(".lunarclient%soffline%s1.8%slogs%s", File.separator, File.separator, File.separator, File.separator));
            File logFile = new File(logFileDirectory, "latest.log");
//            System.out.println("logFileDirectory = " + logFileDirectory);
//            System.out.println("logFile = " + logFile);

            List<String> oldText = null;

            while (true) {
                try {
                    if (!logFile.exists()) {
//                        System.out.println("logFile does not exist");
                        throw new IOException("logFile does not exist");
                    }

                    /*List<String> newText = Files.readAllLines(Paths.get(logFile.getAbsolutePath()), StandardCharsets.UTF_8);*/
                    List<String> newText;
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(logFile))) {
                        newText = bufferedReader.lines().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
                    }

                    if (oldText == null) {
                        oldText = newText;
                    } else {
                        if (oldText.size() != newText.size()) {
                            if (newText.size() > oldText.size()) {
                                List<String> diff = newText.subList(oldText.size(), newText.size());

                                for (String line : diff) {
//                                    System.out.println(line);

                                    playerWatchedThread.chatConsumer.accept(line);
                                    chatConsumer.accept(line);
                                }

                                oldText = newText;
                            } else {
                                oldText = null;
                            }
                        }
                    }

                    Thread.sleep(1000);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public static void garbageCollect() {
        Executors.newScheduledThreadPool(3).scheduleAtFixedRate(System::gc, 0, 5, TimeUnit.SECONDS);
    }
}