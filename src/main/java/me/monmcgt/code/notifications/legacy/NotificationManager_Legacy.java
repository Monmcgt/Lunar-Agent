package me.monmcgt.code.notifications.legacy;

import java.awt.*;

public class NotificationManager_Legacy {
    public static NotificationManager_Legacy INSTANCE;

    private SystemTray systemTray;
    private TrayIcon trayIcon;

    static {
        INSTANCE = new NotificationManager_Legacy();
    }

    public NotificationManager_Legacy() {
        try {
            this.init();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void init() throws AWTException {
        this.systemTray = SystemTray.getSystemTray();
        this.trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(""));
        this.trayIcon.setImageAutoSize(true);
        this.trayIcon.setToolTip("");
        this.systemTray.add(this.trayIcon);
    }

    public NotificationManager_Legacy createNotification(String message) {
        return this.createNotification("", message);
    }

    public NotificationManager_Legacy createNotification(String title, String message) {
        this.trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);

        return this;
    }
}
