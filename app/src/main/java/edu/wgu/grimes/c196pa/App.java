package edu.wgu.grimes.c196pa;

import android.app.Application;

import edu.wgu.grimes.c196pa.utilities.NotificationHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new NotificationHelper(getBaseContext());
    }

}
