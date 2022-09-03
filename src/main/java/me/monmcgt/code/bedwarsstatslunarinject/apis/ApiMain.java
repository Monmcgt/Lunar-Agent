package me.monmcgt.code.bedwarsstatslunarinject.apis;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import me.monmcgt.code.Debug;
import me.monmcgt.code.bedwarsstatslunarinject.apis.inject.InjectMain;
import me.monmcgt.code.bedwarsstatslunarinject.apis.responses.HypixelKey$Response;
import me.monmcgt.code.bedwarsstatslunarinject.apis.responses.HypixelStats$Response;
import me.monmcgt.code.bedwarsstatslunarinject.apis.responses.PlayerDB$Response;
import me.monmcgt.code.bedwarsstatslunarinject.configs.Config;
import me.monmcgt.code.bedwarsstatslunarinject.consumers.PlayerDBConsumer;
import me.monmcgt.code.bedwarsstatslunarinject.stats.BedwarsStatsData;
import me.monmcgt.code.helpers.FormatHelperJava;
import me.monmcgt.code.helpers.HttpHelper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

import static me.monmcgt.code.helpers.FormatHelperJava.red;

public class ApiMain {
    private static final Gson gson = new Gson();

    public static final String HYPIXEL_API_LINK = "https://api.hypixel.net/";
    public static final String PLAYER_DB_LINK = "https://playerdb.co/api/player/minecraft/";

    public static volatile PlayerDBConsumer playerDBConsumer = new PlayerDBConsumer();

    public ApiMain() {
//        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this::checkSelf, 0, 5, TimeUnit.MINUTES);
    }

    public boolean checkSelf(String key, boolean printChatIfInvalid) {
        TimeoutThread timeoutThread = new TimeoutThread(5000, printChatIfInvalid);
        timeoutThread.start();

//        String link = HYPIXEL_API_LINK + "key?key=" + Config.apiKey;
        final String[] link = {HYPIXEL_API_LINK + "key?key=" + key};
//        System.out.println("link = " + link[0]);
//        System.out.println("link = " + link);
//        HttpURLConnection get = HttpHelper.getGet(link);
//        String json = HttpHelper.sendGetString(get);
//        System.out.println("json = " + json);

        try {
            new Thread(() -> {
                try {
                    HttpURLConnection get;
                    try {
                        get = (HttpURLConnection) new URL(link[0]).openConnection();
                        get.setRequestMethod("GET");
                        get.setRequestProperty("Accept-Charset", "UTF-8");
                        get.setRequestProperty("Accept", "application/json");
                        get.setConnectTimeout(5000);
                        get.setReadTimeout(5000);
        //            get.setDoOutput(true);
                    } catch (IOException e) {
                        if (printChatIfInvalid) {
                            e.printStackTrace();
                        }
                        return;
                    }

            /*try {
                InputStream test = get.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/

            /*try {
                System.out.println("get.getResponseCode() = " + get.getResponseCode());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/

                    InputStream inputStream = null;
                    try {
                        inputStream = get.getInputStream();
                    } catch (IOException e) {
                        if (printChatIfInvalid) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line);
                        }

                        String json = stringBuilder.toString();

                        HypixelKey$Response hypixelKey$Response = gson.fromJson(json, HypixelKey$Response.class);

                        if (hypixelKey$Response.success) {
                            String uuid = hypixelKey$Response.record.owner;

                            link[0] = PLAYER_DB_LINK + uuid;

                            get = HttpHelper.getGet(link[0]);
                            json = HttpHelper.sendGetString(get);

                            PlayerDB$Response playerDB$Response = gson.fromJson(json, PlayerDB$Response.class);

                            Config.selfName = playerDB$Response.data.player.username;

                            timeoutThread.setFinished(true);
                            timeoutThread.setSuccess(true);
        //                System.out.println("[BedWars] Successfully checked self! (username: " + Config.selfName + ")");

                            timeoutThread.setInterrupted(true);
                        } else {
                            System.err.println("[BedWarsOverlay] Error: Cannot verify API key.");
                            String s = "Error: Cannot verify API key.";
                            if (hypixelKey$Response.cause != null) {
                                s += "\nCause: " + hypixelKey$Response.cause;
                            }
                            JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
                            timeoutThread.setFinished(true);
                            timeoutThread.setSuccess(false);
        //                System.exit(1);
                        }

                        // StackTrace
                        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
                        for (StackTraceElement stackTraceElement : stackTraceElements) {
        //            System.out.println("[stacktrace] " + stackTraceElement.toString());
                        }
                    } catch (IOException e) {
        //                throw new RuntimeException(e);
                        String errMessage = e.getMessage();
    //                    String errorCode = errMessage.substring(errMessage.indexOf(":") + 1);
    //                    errorCode = errorCode.substring(0, 3);
                        String errorCode = String.valueOf(get.getResponseCode());
                        if (errMessage.startsWith("r_code:") && errorCode.equals("403")) {
                            /*if (errorCode.equals("403")) {
                                String message = red + "Error occurred. Please check your game's logs for more information.";
                                InjectMain.addChat(FormatHelperKt.addPrefix(message));
                                return;
                            }*/
                            String message = red + "Error occurred. Please check your game's logs for more information.";
        //                    InjectMain.addChat(FormatHelperKt.addPrefix(message));
                            return;
                        }
        //                throw new RuntimeException(e);
                        if (printChatIfInvalid) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        if (printChatIfInvalid) {
                            e.printStackTrace();
                        }
                    }
                } catch (/*Runtime*/Exception/* | IOException*/ e) {
                    if (printChatIfInvalid) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            if (printChatIfInvalid) {
                e.printStackTrace();
            }
        }

        while (!timeoutThread.isFinished()) {
//            System.out.println("Check timeout thread...");
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return timeoutThread.success;
    }

    public void checkPlayer(String username) {
        checkPlayer(username, null);
    }

    public void checkPlayer(String username, Consumer<BedwarsStatsData> consumer) {
        Debug.println("Checking player: " + username);
        String link = PLAYER_DB_LINK + username;

        HttpURLConnection get = HttpHelper.getGet(link);
        String json = null;
        PlayerDB$Response playerDB$Response = null;
        try {
            json = HttpHelper.sendGetString(get);
            playerDB$Response = gson.fromJson(json, PlayerDB$Response.class);
        } catch (RuntimeException e) {
            if (e.getMessage().startsWith("r_code:")) {
//                String responseCode = e.getMessage().split(":", 2)[1];
                String responseCode = null;
                try {
                    responseCode = String.valueOf(get.getResponseCode());
                } catch (IOException ex) {
                    e.printStackTrace();
                    return;
                }
                if (responseCode.equals("500")) {
                    /*playerDB$Response = new PlayerDB$Response();
                    playerDB$Response.code = "500";*/
                    if (consumer != null) {
                        HypixelStats$Response hypixelStats$Response = new HypixelStats$Response();
                        hypixelStats$Response.player.displayname = username;
                        consumer.accept(new BedwarsStatsData(
                                -1,
                                -1,
                                -1,
                                -1,
                                hypixelStats$Response
                        ));
                    } else {
                        String message = FormatHelperJava.formatStats(null, username, "1", "1", "1");
                        InjectMain.addChat(message);
                    }
                } else {
                    System.err.println("[BedWarsOverlay] Error: Cannot verify username.");
                    System.err.println("responseCode = " + responseCode);
//                    JOptionPane.showMessageDialog(null, "Error: Cannot verify username.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                e.printStackTrace();
                return;
            }
        }

        playerDBConsumer.accept(playerDB$Response, consumer);
    }

    private class TimeoutThread extends Thread {
        @Getter
//        @Setter
        private boolean interrupted = false;

        @Getter
        @Setter
        private boolean success = false;

//        @Getter
        @Setter
        private boolean finished = false;

        private long time = 0;

        private boolean printChatIfInvalid;

        public TimeoutThread(long time, boolean printChatIfInvalid) {
            this.time = time;
            this.printChatIfInvalid = printChatIfInvalid;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (this.isInterrupted()) {
                return;
            }

//            System.err.println("Cannot verify api key.");
//            System.err.println("Please check your api key.");
//            System.err.println("Make sure you use a valid api key.");
//            String message = "Cannot verify api key.\nPlease check your api key.\nMake sure you use a valid api key.";
//            throw new RuntimeException(message);
            /*System.err.println(message);
            System.exit(1);*/
            this.setFinished(true);
            this.setSuccess(false);

//            System.out.println("Set success to true");

            if (this.printChatIfInvalid) {
                InjectMain.addChat(FormatHelperJava.addPrefix(FormatHelperJava.red + "Invalid API key."));
            }
        }

        public void setInterrupted(boolean interrupted) {
//            System.out.println("Setting interrupted to " + interrupted);
            this.interrupted = interrupted;
        }

        public boolean isFinished() {
            return finished;
        }
    }
}
