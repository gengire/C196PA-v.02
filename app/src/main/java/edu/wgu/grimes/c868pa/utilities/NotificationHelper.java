//*********************************************************************************
//  File:             NotificationHelper.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import edu.wgu.grimes.c868pa.R;

import static edu.wgu.grimes.c868pa.utilities.Constants.COURSE_END_ALERT_CHANNEL;
import static edu.wgu.grimes.c868pa.utilities.Constants.COURSE_START_ALERT_CHANNEL;

/**
 * Attempt to abstract the notification code.
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class NotificationHelper extends ContextWrapper {

    NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        createNotificationChannels();
    }

    public void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    COURSE_START_ALERT_CHANNEL,
                    "Course Start Date Alert Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Channel used to notify about an upcoming course start date");
            NotificationChannel channel2 = new NotificationChannel(
                    COURSE_END_ALERT_CHANNEL,
                    "Course End Date Alert Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("Channel used to notify about an upcoming course end date");

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

    public NotificationCompat.Builder getChannelNotification(String title, String message, String channelId) {
        return new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_announcement)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setStyle(new NotificationCompat.BigTextStyle())
                ;
    }
}
