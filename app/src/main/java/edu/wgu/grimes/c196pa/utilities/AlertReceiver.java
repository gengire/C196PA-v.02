//*********************************************************************************
//  File:             AlertReceiver.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import static edu.wgu.grimes.c196pa.utilities.Constants.CHANNEL_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ALARM_KEY_ID;
import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ALARM_MESSAGE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ALARM_TITLE_ID_KEY;

/**
 * Alert Receiver
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class AlertReceiver extends BroadcastReceiver {

    /**
     * Gets the receiver alert and fires off the notification helper
     * @param context The context
     * @param intent The intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String title = bundle.getString(COURSE_ALARM_TITLE_ID_KEY);
        String message = bundle.getString(COURSE_ALARM_MESSAGE_ID_KEY);
        int key = bundle.getInt(COURSE_ALARM_KEY_ID);
        String channelId = bundle.getString(CHANNEL_ID_KEY);
        NotificationHelper helper = new NotificationHelper(context);
        NotificationCompat.Builder nb = helper.getChannelNotification(title, message, channelId);
        helper.getManager().notify(key, nb.build());
    }
}
