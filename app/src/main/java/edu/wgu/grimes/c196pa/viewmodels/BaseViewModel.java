//*********************************************************************************
//  File:             BaseViewModel.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.AppRepository;

/**
 * Abstracted repo and executor construction
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class BaseViewModel extends AndroidViewModel {

    protected AppRepository mRepository;
    protected Executor executor = Executors.newSingleThreadExecutor();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
    }
}
