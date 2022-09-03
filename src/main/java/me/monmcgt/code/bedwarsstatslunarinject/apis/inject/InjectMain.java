package me.monmcgt.code.bedwarsstatslunarinject.apis.inject;

import com.google.gson.Gson;
import me.monmcgt.code.bedwarsstatslunarinject.apis.inject.json.Json$1;
import me.monmcgt.code.bedwarsstatslunarinject.apis.inject.json.Response$1;
import me.monmcgt.code.commands.ChatMessage;
import me.monmcgt.code.commands.ChatMessageUtilityKt;
import me.monmcgt.code.helpers.HttpHelper;

import javax.swing.*;
import java.net.HttpURLConnection;

public class InjectMain {
    private static Gson gson = new Gson();

    public static boolean useJOptionPane = false;

    public static void addChat(String message) {
        ChatMessageUtilityKt.printChat(new ChatMessage(message), "\n");
    }

    public static void addChatWithPrefix(String message) {
        /*String url = "http://localhost:59741/lunar/chat/print";

        sendReq_1(message, url, true);*/

        ChatMessageUtilityKt.printChat(ChatMessageUtilityKt.addPrefix(new ChatMessage(message)), "\n");
    }

    public static void sendChat(String message) {
        /*String url = "http://localhost:59741/lunar/chat/send";

        sendReq_1(message, url, true);*/

        ChatMessageUtilityKt.sendChat(new ChatMessage(message), "\n");
    }

    private static void sendReq_1(String message, String url, boolean isJson) {
        /*System.out.println("Sending message: " + message);
        System.out.println("URL: " + url);*/
        try {
            HttpURLConnection httpURLConnection = HttpHelper.getPost(url);
            if (isJson) {
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
            }
            String data = gson.toJson(new Json$1(message));
            String response = HttpHelper.sendPostString(httpURLConnection, data);
            Response$1 response$1 = gson.fromJson(response, Response$1.class);
            if (!response$1.isSuccess()) {
                System.err.println("Error: " + response$1.getMessage());
                if (useJOptionPane) {
                    JOptionPane.showMessageDialog(null, response$1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (useJOptionPane) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
