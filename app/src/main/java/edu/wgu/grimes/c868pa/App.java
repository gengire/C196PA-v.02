//*********************************************************************************
//  File:             App.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa;

import android.app.Application;

import edu.wgu.grimes.c868pa.database.AppRepository;

/**
 * Main application hook
 */
public class App extends Application {

    private int loggedInAccountId;

    @Override
    public void onCreate() { // app on create hook
        super.onCreate();
        AppRepository.getInstance(this); // kicking off the instance for this session
        // Decided not to put this here
//        new NotificationHelper(getBaseContext());
    }

    public int getLoggedInAccountId() {
        return loggedInAccountId;
    }

    public void setLoggedInAccountId(int accountId) {

    }

}
