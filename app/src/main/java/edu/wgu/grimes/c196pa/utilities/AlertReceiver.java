package edu.wgu.grimes.c196pa.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import edu.wgu.grimes.c196pa.utilities.NotificationHelper;

import static edu.wgu.grimes.c196pa.utilities.Constants.CHANNEL_1_ID;
import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ALARM_KEY_ID;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("title");
        String message = bundle.getString("message");
        int key = bundle.getInt(COURSE_ALARM_KEY_ID);
        NotificationHelper helper = new NotificationHelper(context);
        NotificationCompat.Builder nb = helper.getChannel1Notification(title, message, CHANNEL_1_ID);
        helper.getManager().notify(1, nb.build());
    }
}
