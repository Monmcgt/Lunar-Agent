package me.monmcgt.code.bedwarsstatslunarinject.consumers;

import me.monmcgt.code.bedwarsstatslunarinject.configs.Config;
import me.monmcgt.code.bedwarsstatslunarinject.configs.ConfigManager_legacy;
import me.monmcgt.code.notifications.legacy.NotificationManager_Legacy;

import javax.swing.*;
import java.util.function.Consumer;

public class ApiKeyConsumer implements Consumer<String> {
    @Override
    public void accept(String apiKey) {
        if (false) {
            int api_key_found = JOptionPane.showConfirmDialog(null, "Found API key: " + apiKey + "\nDo you want to save it?", "API Key Found", JOptionPane.YES_NO_OPTION);

            if (api_key_found == JOptionPane.YES_OPTION) {
                NotificationManager_Legacy.INSTANCE.createNotification("API Key Found", "Your new API key is: " + apiKey);

                Config.apiKey = apiKey;
                ConfigManager_legacy.saveConfig();
            }
        }
    }
}
