//*********************************************************************************
//  File:             App.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa;

import android.app.Application;

import edu.wgu.grimes.c196pa.utilities.NotificationHelper;

/**
 * Main application hoook
 */
public class App extends Application {

    @Override
    public void onCreate() { // app on create hook
        super.onCreate();
        // Decided not to put this here
//        new NotificationHelper(getBaseContext());
    }

}
