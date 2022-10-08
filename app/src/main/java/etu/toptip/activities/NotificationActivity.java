package etu.toptip.activities;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.util.Objects;

//Appli de démarrage android:name=".ApplicationDemo" dans manifest
public class NotificationActivity extends Application {
    static String CHANNEL_ID = "channelID";
    static NotificationManager notificationManager;


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel("channel","le channel de la démo des notifs", NotificationManager.IMPORTANCE_DEFAULT);
    }

    private void createNotificationChannel(String nomChannel, String descriptionChannel, int importance) {
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,nomChannel,importance); //création channel
            channel.setDescription(descriptionChannel); //je lui ajoute une description
            notificationManager = getSystemService(NotificationManager.class); //maj du notifmanager, on peut le faire qu'une seule fois dans l'appli
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel); //on s'assure que le getSystem n'a pas retourné null sinon on peut pas créer le channel puis on le créer pour qu'ils communiquent
        }
    }

    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }
}

