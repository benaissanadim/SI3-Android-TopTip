package etu.toptip.model;

import android.graphics.Bitmap;

public class NotificationsModel {
    private static NotificationsModel instance;
    private String notificationText = "TopTip";
    private Bitmap notificationImage = null;

    private NotificationsModel() {
    }

    public static NotificationsModel getInstance() {
        if (instance == null) {
            instance = new NotificationsModel();
        }
        return instance;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public Bitmap getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(Bitmap notificationImage) {
        this.notificationImage = notificationImage;
    }

}