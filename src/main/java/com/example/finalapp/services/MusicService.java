package com.example.finalapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.finalapp.R;
import com.example.finalapp.menuitems.ChangeNames;

public class MusicService extends Service {
    MediaPlayer myPlayer;
    public static final String CHANNEL_ID = "Foreground Service";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (myPlayer == null) {
            myPlayer = MediaPlayer.create(this, R.raw.mozartsong);
            myPlayer.start();
            createNotificationChannel();

            Intent notificationIntent = new Intent(this, ChangeNames.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    10, notificationIntent, PendingIntent.FLAG_MUTABLE);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Foreground Service")
                    .setContentText("Music is playing")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();

            startForeground(1, notification);

            return START_NOT_STICKY;
        }
        else
        {
            return START_NOT_STICKY;
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager manager = getSystemService(NotificationManager.class);
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop and release MediaPlayer resources when the service is destroyed
        myPlayer.stop();
        myPlayer.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
