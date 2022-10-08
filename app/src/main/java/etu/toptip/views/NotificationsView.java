package etu.toptip.views;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import etu.toptip.R;
import etu.toptip.activities.AddBonPlanActivity;
import etu.toptip.activities.MainActivity;
import etu.toptip.controller.NotificationsController;
import etu.toptip.model.NotificationsModel;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class NotificationsView implements Observer {
    private NotificationsController notificationsController;
    private View root;
    private int notificationId = 0;

    public NotificationsView(View root) {
        this.root = root;
        this.setListeners();
    }

    private void setListeners() {
        final Button showNotificationButton = root.findViewById(R.id.show_notification_add_place);
        showNotificationButton.setOnClickListener(click -> notificationsController.newNotification());
    }

    public void setController(NotificationsController notificationsController) {
        this.notificationsController = notificationsController;
    }

    /**
     * Show the notification with a given text, and given image
     *
     * @param img  Image in bitmap format
     * @param text Text (String) to display on the notification
     */
    private void showNotificationWithImage(final String text, final Bitmap img) {
        final String title = "Nouveau lieu sur TopTip !";
        notificationId++;
        final String channelId = "notification.channel";
        final int flags = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
        Context context = root.getContext();

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        Intent intent = new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, flags);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);

        builder.setSmallIcon(R.drawable.logodetoure);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setContentIntent(pendingIntent);
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(img));
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(channelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId, "notification.channel2", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("Notif channel to notify user");
                notificationChannel.enableVibration(true);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(0xFFFF0000);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = builder.build();
        Objects.requireNonNull(notificationManager).notify(notificationId, notification);
    }

    /**
     * Create a thread which display a notification
     */
    public void newNotification(final String text, final Bitmap url) {
        //final Bitmap img = notificationsController.fetchImage(url);
        new Thread(() -> showNotificationWithImage(text, url)).start();
    }


    @Override
    public void update(Observable observable, Object o) {
        final String text = NotificationsModel.getInstance().getNotificationText();
        final Bitmap url = NotificationsModel.getInstance().getNotificationImage();
        this.newNotification(text, url);

        if (observable.hasChanged()) {
            this.newNotification("t", url);
        }
    }
}