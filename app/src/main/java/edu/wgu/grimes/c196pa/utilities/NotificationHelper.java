package edu.wgu.grimes.c196pa.utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import edu.wgu.grimes.c196pa.R;

import static edu.wgu.grimes.c196pa.utilities.Constants.CHANNEL_1_ID;
import static edu.wgu.grimes.c196pa.utilities.Constants.CHANNEL_2_ID;

public class NotificationHelper extends ContextWrapper {

    NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        createNotificationChannels();
    }

    public void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is Channel 2");

            getManager().createNotificationChannel(channel1);
            getManager().createNotificationChannel(channel2);
        }
    }

    public NotificationManager getManager() {
        if (manager == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager = getSystemService(NotificationManager.class);
            }
        }
        return manager;
    }

    public NotificationCompat.Builder getChannel1Notification(String title, String message, String channelId) {
        return new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_announcement)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                ;
    }
}
