package me.monmcgt.code.bedwarsstatslunarinject.consumers;

import me.monmcgt.code.bedwarsstatslunarinject.apis.ApiMain;
import me.monmcgt.code.bedwarsstatslunarinject.apis.inject.InjectMain;
import me.monmcgt.code.bedwarsstatslunarinject.configs.Config;
import me.monmcgt.code.helpers.FormatHelperJava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class ChatConsumer implements Consumer<String> {
    private static ApiMain apiMain = new ApiMain();
    private static ApiKeyConsumer apiConsumer = new ApiKeyConsumer();

    private static long whoTime = System.currentTimeMillis();
    private static long partyTime = System.currentTimeMillis();
    private static long listTime = System.currentTimeMillis();
    private static boolean getWho = false;
    private static float getWhoDelay = 7.5f;
    private static boolean getParty = false;
    private static float getPartyDelay = 7.5f;
    private static boolean getList = false;
    private static float getListDelay = 7.5f;

    private static boolean pState_1 = false;
    private static boolean pState_2 = false;
    private static boolean pState_3 = false;


    private static List<Character> validChars = new ArrayList<>();

    static {
        for (char c = 'a'; c <= 'z'; c++) {
            validChars.add(c);
        }
        for (int i = 0; i < 10; i++) {
            validChars.add((char) ('0' + i));
        }
        validChars.add('_');
    }

    @Override
    public void accept(String s) {
        String splitMessage = "[Client thread/INFO]: [CHAT]";

        String apiKeyMessage = "Your new API key is";
        String whoMessage = "ONLINE: ";

        if (s.contains(splitMessage)) {
            String chatMessage = null;
            try {
                chatMessage = s.split(Pattern.quote(splitMessage), 2)[1].trim();
            } catch (ArrayIndexOutOfBoundsException e) {
                /*System.out.println("ArrayIndexOutOfBoundsException: " + s);
                String[] sp = s.split(splitMessage, 2);
                for (String ssd: sp) {
                    System.out.println(ssd);
                }*/

                return;
            }

            List<String> players = new ArrayList<>();

//            System.out.println(chatMessage);

            if (chatMessage.startsWith("Can't find a player by the name of '-c")) {
                String var0 = chatMessage.trim().substring(0, chatMessage.length() - 1);
                String[] split = var0.split("-c", 2);
                if (split.length != 2) {
                    this.sendUsage();
                    return;
                }
                String playerName = split[1].trim();
                if (playerName.isEmpty()) {
                    this.sendUsage();
                    return;
                }
//                playerName = playerName.substring(0, playerName.length() - 1);
                if (this.checkValidName(playerName)) {
                    players.add(playerName);
                }
            }

            if (chatMessage.equals("Can't find a player by the name of '-a'")) {
                InjectMain.sendChat("/who");
                whoTime = System.currentTimeMillis();
                getWho = true;
            }

            if (chatMessage.equals("Can't find a player by the name of '-p'")) {
                InjectMain.sendChat("/pl");
                partyTime = System.currentTimeMillis();
                getParty = true;
            }

            if (chatMessage.equals("Can't find a player by the name of '-l'")) {
                InjectMain.sendChat("/list");
                listTime = System.currentTimeMillis();
                getList = true;
            }

            if (getParty && System.currentTimeMillis() - partyTime < getPartyDelay * 1000L) {
                this.checkParty(chatMessage, players);
            }

            if (getList && System.currentTimeMillis() - listTime < getListDelay * 1000L) {
                if (chatMessage.startsWith("Online Players (")) {
                    if (!chatMessage.contains(")")) {
                        return;
                    }
                    // format: Online Players (number): name, name, name, ...
                    String number = chatMessage.substring(chatMessage.indexOf("(") + 1, chatMessage.indexOf(")"));
                    try {
                        Integer.parseInt(number);
                    } catch (NumberFormatException e) {
                        return;
                    }
                    String[] split = chatMessage.split(":", 2);
                    String[] playersOnline = split[1].split(",");
                    for (String player : playersOnline) {
                        player = this.replaceRanksAndOnlineOffline(player.trim());
                        if (player.isEmpty()) {
                            continue;
                        }
                        if (player.equalsIgnoreCase(Config.selfName) && !Config.includeSelf) {
                            continue;
                        }
                        players.add(player);
                    }
                }
            }

            if (chatMessage.contains(apiKeyMessage)) {
                apiConsumer.accept(chatMessage.split(apiKeyMessage, 2)[1].trim());

                return;
            }

            if (chatMessage.startsWith(whoMessage) && getWho && System.currentTimeMillis() - whoTime < getWhoDelay * 1000L) {
                getWho = false;

                String raw = chatMessage.split(whoMessage, 2)[1].trim();
                String[] split = raw.split(",");

//                Main.gui.clear();

                for (String s1 : split) {
                    // remove [anything]
                    String s2 = s1.replaceAll("\\[.*\\]", "").trim();

                    if (s2.equalsIgnoreCase(Config.selfName) && !Config.includeSelf) {
                        continue;
                    }

                    players.add(s2);
                }
//                players.forEach(apiMain::checkPlayer);
            }

            if (players.size() > 0) {
                new Thread(() -> {
                    ExecutorService executorService = Executors.newFixedThreadPool(100);

                    players.forEach((p) -> {
                        executorService.submit(() -> {
                            apiMain.checkPlayer(p);
                        });
                    });

                    executorService.shutdown();
                    try {
                        boolean b = executorService.awaitTermination(20, TimeUnit.SECONDS);

                        if (!b) {
                            executorService.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        } /*else {
            System.out.println("Not a chat message: " + s);
        }*/
    }

    private void checkParty(String line, List<String> players) {
        if (line.trim().isEmpty()) {
            return;
        }
        if (line.contains("-----------------------------------------------------")) {
            pState_1 = true;
            return;
        }
        if (pState_1 && line.startsWith("You are not currently in a party.")) {
            pState_1 = false;
            getParty = false;
//            System.out.println("Not in a party bruh");
            String message = FormatHelperJava.addPrefix(FormatHelperJava.yellow + "Failed to get party members\n");
            message += FormatHelperJava.addPrefix(FormatHelperJava.yellow + "You are not currently in a party.");
            InjectMain.addChat(message);
            return;
        }
        if (pState_1 && line.startsWith("Party Members (")) {
            if (!line.contains(")")) {
                return;
            }
            String s = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
            try {
                Integer.parseInt(s);
            } catch (NumberFormatException e) {
                pState_1 = false;
                return;
            }
            pState_2 = true;
//            System.out.println("state2 = true");
        }
        if (pState_1 && pState_2 && line.startsWith("Party Leader: ")) {
            String s = line.substring(line.indexOf(":") + 1).trim();
            s = /*this.*/replaceRanksAndOnlineOffline(s);
            for (String s1 : s.split("\\s+")) {
                if (s1.equalsIgnoreCase(Config.selfName) && !Config.includeSelf) {
                    continue;
                }
                players.add(s1);
            }
            pState_3 = true;
//            System.out.println("state3 = true");
        }
        if (pState_1 && pState_2 && pState_3) {
            if (line.startsWith("Party Moderators: ")) {
                String s = line.substring(line.indexOf(":") + 1).trim();
                s = /*this.*/replaceRanksAndOnlineOffline(s);
                for (String s1 : s.split("\\s+")) {
                    if (s1.equalsIgnoreCase(Config.selfName) && !Config.includeSelf) {
                        continue;
                    }
                    players.add(s1);
                }
            }
            if (line.startsWith("Party Members: ")) {
//                System.out.println("Party Members: " + line);
                String s = line.substring(line.indexOf(":") + 1).trim();
                s = /*this.*/replaceRanksAndOnlineOffline(s);
                for (String s1 : s.split("\\s+")) {
//                    System.out.println("Party Member: " + s1);
                    if (s1.equalsIgnoreCase(Config.selfName) && !Config.includeSelf) {
                        continue;
                    }
                    players.add(s1);
                }

                pState_1 = false;
                pState_2 = false;
                pState_3 = false;
                getParty = false;
            }
            if (line.contains("-----------------------------------------------------")) {
                pState_1 = false;
                pState_2 = false;
                pState_3 = false;
                getParty = false;
            }
        }
    }

    public static String replaceRanksAndOnlineOffline(String line) {
        String[] ranks = {"[VIP]", "[VIP+]", "[MVP]", "[MVP+]", "[MVP++]", "[YOUTUBER]", "[HELPER]", "[MOD]", "[ADMIN]", "[OWNER]"};
        String emoji = "●";
        // start replacing
        for (String rank : ranks) {
            line = line.replace(rank, "").trim();
        }
        line = line.replace(emoji, "").trim();
        while (line.contains("  ")) {
            line = line.replace("  ", " ");
        }

        return line;
    }

    private void sendUsage() {
        String format1 = String.format("%s[%sLA%s] %s%s%s%s", FormatHelperJava.grey, FormatHelperJava.pink, FormatHelperJava.grey, FormatHelperJava.yellow, "Usage: ", FormatHelperJava.lime, "/w -c<username>");
        String format2 = String.format("%s[%sLA%s] %s%s%s%s%s", FormatHelperJava.grey, FormatHelperJava.pink, FormatHelperJava.grey, FormatHelperJava.yellow, "Example: ", FormatHelperJava.lime, "/w -c", Config.selfName);
        InjectMain.addChat(format1);
        InjectMain.addChat(format2);
    }

    private boolean checkValidName(String name) {
        if (name.length() > 16) {
            String message = FormatHelperJava.red + "Name too long! Max length is 16 characters.";
            InjectMain.addChat(message);
            return false;
        }
        for (char c : name.toCharArray()) {
            char c1 = Character.toLowerCase(c);
            boolean pass = false;
            for (char c2 : validChars) {
                if (c1 == c2) {
                    pass = true;
                    break;
                }
            }
            if (!pass) {
                String message = FormatHelperJava.red + "Invalid character in name! Valid characters are: a-z, 0-9, and _";
                InjectMain.addChat(message);
                return false;
            }
        }
        return true;
    }
}
