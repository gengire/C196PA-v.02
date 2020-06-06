package edu.wgu.grimes.c196pa;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import static edu.wgu.grimes.c196pa.utilities.Constants.CHANNEL_1_ID;
import static edu.wgu.grimes.c196pa.utilities.Constants.CHANNEL_2_ID;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new NotificationHelper(getBaseContext());
    }


}
