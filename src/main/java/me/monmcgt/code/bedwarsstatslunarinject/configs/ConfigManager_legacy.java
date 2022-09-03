package me.monmcgt.code.bedwarsstatslunarinject.configs;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class ConfigManager_legacy {
    public static File configFile = new File("config" + File.separator + "config.properties");
    public static Properties properties = new Properties();

    static {
        try {
            if (!configFile.getParentFile().exists()) {
                configFile.getParentFile().mkdirs();
            }
            if (!configFile.exists()) {
                configFile.createNewFile();
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFile))) {
                    bufferedWriter.write("#BedwarsStatsLunarInject Config\n");
//                    bufferedWriter.write("# Hypixel api key\n");
                    bufferedWriter.write("api-key=\n");
//                    bufferedWriter.write("# include your stats\n");
                    bufferedWriter.write("include-self=true\n");
                    bufferedWriter.write("player-watcher=true\n");
                    bufferedWriter.write("player-watcher-whitelist=true\n");
                    bufferedWriter.write("player-watcher-invisible=true\n");
                    bufferedWriter.write("player-watcher-radius=30\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initConfig() {
        try {
            loadConfig();
        } catch (RuntimeException e) {
            String message = e.getMessage();
            JOptionPane.showMessageDialog(null, message, "RuntimeException", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void loadConfig() throws RuntimeException {
        try {
            properties.clear();
            properties.load(new FileReader(configFile));
            String apiKey = properties.getProperty("api-key");
            if (apiKey == null || apiKey.isEmpty()) {
                throw new RuntimeException("API key is not set!");
            }
            Config.apiKey = apiKey;
            String includeSelfString = properties.getProperty("include-self");
            if (includeSelfString == null || includeSelfString.isEmpty()) {
                throw new RuntimeException("Include self is not set!");
            }
            boolean includeSelf;
            try {
                includeSelf = Boolean.parseBoolean(includeSelfString);
                Config.includeSelf = includeSelf;
            } catch (Exception e) {
                throw new RuntimeException("Include self is not a boolean!");
            }
            String playerWatcherEnabledString = properties.getProperty("player-watcher");
            if (playerWatcherEnabledString == null || playerWatcherEnabledString.isEmpty()) {
                throw new RuntimeException("Player watcher is not set!");
            }
            boolean playerWatcherEnabled;
            try {
                playerWatcherEnabled = Boolean.parseBoolean(playerWatcherEnabledString);
                Config.playerWatcherEnabled = playerWatcherEnabled;
            } catch (Exception e) {
                throw new RuntimeException("Player watcher is not a boolean!");
            }
            String playerWatcherWhitelistEnabledString = properties.getProperty("player-watcher-whitelist");
            if (playerWatcherWhitelistEnabledString == null || playerWatcherWhitelistEnabledString.isEmpty()) {
                throw new RuntimeException("Player watcher whitelist is not set!");
            }
            boolean playerWatcherWhitelistEnabled;
            try {
                playerWatcherWhitelistEnabled = Boolean.parseBoolean(playerWatcherWhitelistEnabledString);
                Config.playerWatcherWhitelist = playerWatcherWhitelistEnabled;
            } catch (Exception e) {
                throw new RuntimeException("Player watcher whitelist is not a boolean!");
            }
            String playerWatcherInvisibleEnabledString = properties.getProperty("player-watcher-invisible");
            if (playerWatcherInvisibleEnabledString == null || playerWatcherInvisibleEnabledString.isEmpty()) {
                throw new RuntimeException("Player watcher invisible is not set!");
            }
            boolean playerWatcherInvisibleEnabled;
            try {
                playerWatcherInvisibleEnabled = Boolean.parseBoolean(playerWatcherInvisibleEnabledString);
                Config.playerWatcherInvisible = playerWatcherInvisibleEnabled;
            } catch (Exception e) {
                throw new RuntimeException("Player watcher invisible is not a boolean!");
            }
            String playerWatcherRadiusString = properties.getProperty("player-watcher-radius");
            if (playerWatcherRadiusString == null || playerWatcherRadiusString.isEmpty()) {
                throw new RuntimeException("Player watcher radius is not set!");
            }
            int playerWatcherRadius;
            try {
                playerWatcherRadius = Integer.parseInt(playerWatcherRadiusString);
                Config.playerWatcherRadius = playerWatcherRadius;
            } catch (Exception e) {
                throw new RuntimeException("Player watcher radius is not an integer!");
            }
            if (playerWatcherRadius < 1) {
                throw new RuntimeException("Player watcher radius is less than 1!");
            }
            if (playerWatcherRadius > 300) {
                throw new RuntimeException("Player watcher radius is greater than 300!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveConfig() {
        try {
            properties.clear();
            properties.setProperty("api-key", Config.apiKey);
            properties.setProperty("include-self", Boolean.toString(Config.includeSelf));
            properties.setProperty("player-watcher", Boolean.toString(Config.playerWatcherEnabled));
            properties.setProperty("player-watcher-whitelist", Boolean.toString(Config.playerWatcherWhitelist));
            properties.setProperty("player-watcher-invisible", Boolean.toString(Config.playerWatcherInvisible));
            properties.setProperty("player-watcher-radius", Integer.toString(Config.playerWatcherRadius));
            properties.store(new FileWriter(configFile), "BedwarsStatsLunarInject config");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
