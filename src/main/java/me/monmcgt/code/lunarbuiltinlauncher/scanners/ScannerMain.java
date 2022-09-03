package me.monmcgt.code.lunarbuiltinlauncher.scanners;

import me.monmcgt.code.Debug;
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain;
import me.monmcgt.code.lunarbuiltinlauncher.entities.PlayerInfo;

import java.util.List;
import java.util.Scanner;

public class ScannerMain {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        String line;
        boolean continueLoop = true;
        while (scanner.hasNextLine() && continueLoop) {
            line = scanner.nextLine();
            String[] split = line.split(" ", 2);
            String command = split[0];
            String args = split.length > 1 ? split[1] : "";

            LauncherMain instance = LauncherMain.INSTANCE;
            if (!instance.alreadyInited) {
                instance.init();
            }

            switch (command) {
                case "allen":
//                    instance.getPlayerEntities();
//                    List<PlayerInfo> collect = instance.getPlayerEntitiesToFormattedList().stream().map((e) -> (PlayerInfo) e).collect(Collectors.toList());
                    List<PlayerInfo> collect = instance.getPlayerEntitiesListInfo();
                    collect.forEach((e) -> {
                        Debug.println("Player: " + e.toString());
                    });
                    break;
                case "exit":
                    continueLoop = false;
                    break;
                default:
                    Debug.println("Unknown command: " + command);
                    break;
            }
        }
    }
}
