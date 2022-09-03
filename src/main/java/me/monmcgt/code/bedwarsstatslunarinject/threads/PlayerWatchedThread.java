package me.monmcgt.code.bedwarsstatslunarinject.threads;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.monmcgt.code.bedwarsstatslunarinject.apis.inject.InjectMain;
import me.monmcgt.code.bedwarsstatslunarinject.configs.Config;
import me.monmcgt.code.helpers.HttpHelper;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static me.monmcgt.code.helpers.FormatHelperJava.*;

public class PlayerWatchedThread extends Thread {
    public static List<String> playersWhiteList = new ArrayList<>();

    public Consumer<String> chatConsumer;

    private static final Gson gson = new Gson();
    private static List<PlayerInfo> previousPlayerInfoList_1 = null;

    private static float getWhoDelay = 7.5f;

    private static long whoTime = System.currentTimeMillis();
    private static boolean getWho = false;

    public PlayerWatchedThread() {
        this.chatConsumer = (s) -> {
            if (!Config.playerWatcherWhitelist) {
                return;
            }

            String splitMessage = "[Client thread/INFO]: [CHAT]";

            String whoMessage = "ONLINE: ";

            if (s.contains(splitMessage)) {
                String chatMessage = null;
                try {
                    chatMessage = s.split(Pattern.quote(splitMessage), 2)[1].trim();
                } catch (ArrayIndexOutOfBoundsException e) {
                    return;
                }

                if (chatMessage.startsWith("Can't find a player by the name of '-pwt.w")) {
                    InjectMain.sendChat("/who");
                    whoTime = System.currentTimeMillis();
                    getWho = true;
                }

                if (chatMessage.startsWith("Can't find a player by the name of '-pwt.pi")){
                    getAllPlayerInRange();
                }

                if (getWho && System.currentTimeMillis() - whoTime < getWhoDelay * 1000L) {
                    if (chatMessage.startsWith(whoMessage)) {
                        getWho = false;

                        String raw = chatMessage.split(whoMessage, 2)[1].trim();
                        String[] split = raw.split(",");

                        playersWhiteList.clear();

                        for (String s1 : split) {
                            String s2 = s1.replaceAll("\\[.*\\]", "").trim();

                            if (s2.equalsIgnoreCase(Config.selfName) && !Config.includeSelf) {
                                continue;
                            }

                            playersWhiteList.add(s2);
                        }

//                        playersWhiteList.forEach(System.out::println);
                    }
                }
            }
        };
    }

    public static void startScheduledThread() {
//        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new PlayerWatchedThread(), 0, 1, TimeUnit.SECONDS);
//        Executors.newScheduledThreadPool(5).schedule(new PlayerWatchedThread(), 0, TimeUnit.SECONDS);
        Executors.newFixedThreadPool(15).execute(new PlayerWatchedThread());
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (Config.playerWatcherEnabled) {
//                    System.out.println("[PlayerWatcher] Checking...");
                    JSON$S$GetAllPlayersInfo playerInfo = this.getPlayerInfo();
                    PlayerInfo[] players = playerInfo.players;
                    List<PlayerInfo> var0 = Arrays.asList(players);
                    List<PlayerInfo> var1 = new ArrayList<>();
                    /*var0.stream().filter((player) -> playersWhiteList.contains((player).getName())).forEach((player) -> {
                        if (playersWhiteList.contains(player.getName())) {
                            var1.add(player);
                        }
                    });*/

                    // print all elements in list
                    /*System.out.println("Player White List:");
                    System.out.println(playersWhiteList);
                    System.out.println();*/
                    for (PlayerInfo player : var0) {
                        if (playersWhiteList.contains(player.name)) {
//                            System.out.println("Adding player: " + player.getName());
                            var1.add(player);
                        } /*else {
                            System.out.println("Not adding player: " + player.getName());
                        }*/
                    }
                    if (Config.playerWatcherWhitelist) {
                        players = var1.toArray(new PlayerInfo[0]);
                    }
                    if (players.length != 0) {
                        PlayerInfo mySelf = players[0];
                        PlayerInfo[] others = new PlayerInfo[players.length - 1];
                        System.arraycopy(players, 1, others, 0, players.length - 1);
                        List<PlayerInfo> otherPlayerInfoList = Arrays.stream(others).filter((p) -> this.calculateDistance(mySelf.posX, mySelf.posY, mySelf.posZ, p.posX, p.posY, p.posZ) <= Config.playerWatcherRadius).collect(Collectors.toList());
                        this.checkEnterLeave(otherPlayerInfoList);
                        this.checkInvisible(otherPlayerInfoList);

                        previousPlayerInfoList_1 = otherPlayerInfoList;
                    }

                    /*try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }*/
                } /*else {
//                    System.out.println("[PlayerWatcher] Disabled.");
                }*/

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkInvisible(List<PlayerInfo> otherPlayerInfoList) {
        for (PlayerInfo playerInfo : otherPlayerInfoList) {
            if (playerInfo.invisibleToMyself) {
                for (PlayerInfo playerInfo1 : previousPlayerInfoList_1) {
                    if (playerInfo1.name.equals(playerInfo.name)) {
                        if (playerInfo1.invisibleToMyself) {
                            if (Config.playerWatcherInvisible) {
                                // make distance 0 decimal
                                String distance = String.format("%.0f", this.calculateDistance(playerInfo1.posX, playerInfo1.posY, playerInfo1.posZ, playerInfo.posX, playerInfo.posY, playerInfo.posZ));
                                String message = addPrefix(String.format("%s%s%s is invisible. [%s]", dark_aqua, playerInfo.name, yellow, distance));
                                InjectMain.addChat(message);
                            }
                        } else {
                            if (Config.playerWatcherInvisible) {
//                                String message = addPrefix(String.format("%s%s%s has drank invisibility potion. [%s]", dark_aqua, playerInfo.getName(), yellow, this.calculateDistance(playerInfo.posX, playerInfo.posY, playerInfo.posZ, playerInfo1.posX, playerInfo1.posY, playerInfo1.posZ)));
                                String distance = String.format("%.0f", this.calculateDistance(playerInfo1.posX, playerInfo1.posY, playerInfo1.posZ, playerInfo.posX, playerInfo.posY, playerInfo.posZ));
                                String message = addPrefix(String.format("%s%s%s has gone invisible. [%s]", dark_aqua, playerInfo.name, yellow, distance));
                                InjectMain.addChat(message);
                            }
                        }
                    }
                }
            }
        }
    }

    private void getAllPlayerInRange() {
        PlayerInfo[] playerInfo = this.getPlayerInfo().players;
        if (playerInfo.length == 0) {
            InjectMain.addChat(addPrefix(yellow + "No player in range."));
            return;
        }
        if (playerInfo.length == 1) {
            InjectMain.addChat(addPrefix(yellow + "There are only yourself in range."));
            return;
        }
        PlayerInfo mySelf = playerInfo[0];
        PlayerInfo[] others = new PlayerInfo[playerInfo.length - 1];
        System.arraycopy(playerInfo, 1, others, 0, playerInfo.length - 1);
        for (PlayerInfo playerInfo1 : playerInfo) {
            if (this.calculateDistance(mySelf.posX, mySelf.posY, mySelf.posZ, playerInfo1.posX, playerInfo1.posY, playerInfo1.posZ) <= Config.playerWatcherRadius) {
//                String posX = String.format("%.2f", playerInfo1.posX);
//                String posY = String.format("%.2f", playerInfo1.posY);
//                String posZ = String.format("%.2f", playerInfo1.posZ);
                // no decimal
                String posX = String.format("%.0f", playerInfo1.posX);
                String posY = String.format("%.0f", playerInfo1.posY);
                String posZ = String.format("%.0f", playerInfo1.posZ);
                if (Config.playerWatcherWhitelist) {
                    boolean isWhitelist = false;
                    for (String name : playersWhiteList) {
                        if (name.equals(playerInfo1.name)) {
                            isWhitelist = true;
                            break;
                        }
                    }

                    if (isWhitelist) {
                        String distance = String.format("%.0f", this.calculateDistance(mySelf.posX, mySelf.posY, mySelf.posZ, playerInfo1.posX, playerInfo1.posY, playerInfo1.posZ));
                        String message = addPrefix(String.format("%s%s%s is in range. [%s] (%s, %s, %s)", dark_aqua, playerInfo1.name, yellow, distance, posX, posY, posZ));
                        if (playerInfo1.invisibleToMyself) {
                            message += red + " (invisible)";
                        }
                        InjectMain.addChat(message);
                    }
                } else {
                    String distance = String.format("%.0f", this.calculateDistance(mySelf.posX, mySelf.posY, mySelf.posZ, playerInfo1.posX, playerInfo1.posY, playerInfo1.posZ));
                    String message = addPrefix(String.format("%s%s%s is in range. [%s] (%s, %s, %s)", dark_aqua, playerInfo1.name, yellow, distance, posX, posY, posZ));
                    if (playerInfo1.invisibleToMyself) {
                        message += red + " (invisible)";
                    }
                    InjectMain.addChat(message);
                }
            }
        }
    }

    private void checkEnterLeave(List<PlayerInfo> otherPlayerInfoList) {
        if (previousPlayerInfoList_1 == null) {
            previousPlayerInfoList_1 = new ArrayList<>(otherPlayerInfoList);
        } else if (previousPlayerInfoList_1.size() != otherPlayerInfoList.size()) {
            List<PlayerInfo> left = this.getLeft(previousPlayerInfoList_1, otherPlayerInfoList);
            List<PlayerInfo> entered = this.getEntered(previousPlayerInfoList_1, otherPlayerInfoList);
            left.forEach((p) -> {
                String message = addPrefix(String.format("%s%s%s has%s left%s your visible area.", dark_aqua, p.name, yellow, red, yellow));
                InjectMain.addChat(message);
            });
            entered.forEach((p) -> {
                String message = addPrefix(String.format("%s%s%s has%s entered%s your visible area.", dark_aqua, p.name, yellow, lime, yellow));
                InjectMain.addChat(message);
            });
        }
    }

    private JSON$S$GetAllPlayersInfo getPlayerInfo() {
//        HttpURLConnection httpURLConnection = HttpHelper.getGet("http://localhost:59741/lunar/world/player/all/info");
        String sendGetString = null;
        try {
            HttpURLConnection httpURLConnection = HttpHelper.getGet(HttpHelper.getPath() + "lunar/world/player/all/info");
            sendGetString = HttpHelper.sendGetString(httpURLConnection);
        } catch (RuntimeException e) {
            // if it's connectException
            if (e.getCause() instanceof ConnectException) {
                // cannot connect to game's local server, your game might not fully start
                System.out.println("Cannot connect to server. Your game might not fully start.");
            }
            e.printStackTrace();
        }
        return gson.fromJson(sendGetString, JSON$S$GetAllPlayersInfo.class);
    }

    private List<PlayerInfo> getLeft(List<PlayerInfo> previousPlayerInfoList, List<PlayerInfo> currentPlayerInfoList) {
        List<PlayerInfo> left = new ArrayList<>();
        for (PlayerInfo previousPlayerInfo : previousPlayerInfoList) {
            boolean found = false;
            for (PlayerInfo currentPlayerInfo : currentPlayerInfoList) {
                if (previousPlayerInfo.equals(currentPlayerInfo)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                left.add(previousPlayerInfo);
            }
        }
        return left;
    }

    private List<PlayerInfo> getEntered(List<PlayerInfo> previousPlayerInfoList, List<PlayerInfo> currentPlayerInfoList) {
        List<PlayerInfo> entered = new ArrayList<>();
        for (PlayerInfo currentPlayerInfo : currentPlayerInfoList) {
            boolean found = false;
            for (PlayerInfo previousPlayerInfo : previousPlayerInfoList) {
                if (previousPlayerInfo.equals(currentPlayerInfo)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                entered.add(currentPlayerInfo);
            }
        }
        return entered;
    }

    public static double calculateDistance(double posX_1, double posY_1, double posZ_1, double posX_2, double posY_2, double posZ_2) {
        return Math.sqrt(Math.pow(posX_1 - posX_2, 2) + Math.pow(posY_1 - posY_2, 2) + Math.pow(posZ_1 - posZ_2, 2));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    private class JSON$S$GetAllPlayersInfo {
        private boolean success;
        private String message;
        private PlayerInfo[] players;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    private class PlayerInfo {
        private String name;
        private double posX;
        private double posY;
        private double posZ;
        private double yaw;
        private double pitch;
        private boolean invisible;
        private boolean invisibleToMyself;

    }
}
