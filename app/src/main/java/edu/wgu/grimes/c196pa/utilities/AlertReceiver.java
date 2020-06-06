package edu.wgu.grimes.c196pa.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import edu.wgu.grimes.c196pa.utilities.NotificationHelper;

import static edu.wgu.grimes.c196pa.utilities.Constants.CHANNEL_1_ID;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper helper = new NotificationHelper(context);
        NotificationCompat.Builder nb = helper.getChannel1Notification("title", "message", CHANNEL_1_ID);
        helper.getManager().notify(1, nb.build());
    }
}
