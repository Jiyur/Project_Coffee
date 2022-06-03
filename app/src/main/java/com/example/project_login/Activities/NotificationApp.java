package com.example.project_login.Activities;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationApp extends Application {
    public static final  String CHANNEL_ID = "channel1";
    @Override
    public void onCreate() {
        super.onCreate();

        this.createNotificationChannels();
    }
    private void createNotificationChannels()  {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("This is channel 1");




            NotificationManager manager = this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
