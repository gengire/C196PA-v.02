//*********************************************************************************
//  File:             MainViewModel.java
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
import androidx.lifecycle.LiveData;

/**
 * View model for the main statistics and tracking activity
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class MainViewModel extends BaseViewModel {

    /**
     * Observable count of completed courses
     */
    public LiveData<Integer> mCoursesCompleted;
    /**
     * Observable count of courses in progress
     */
    public LiveData<Integer> mCoursesInProgress;
    /**
     * Observable count of dropped courses
     */
    public LiveData<Integer> mCoursesDropped;
    /**
     * Observable count of failed courses
     */
    public LiveData<Integer> mCoursesFailed;
    /**
     * Observable count of pending assessments
     */
    public LiveData<Integer> mAssessmentsPending;
    /**
     * Observable count of passed assessments
     */
    public LiveData<Integer> mAssessmentsPassed;
    /**
     * Observable count of failed assessments
     */
    public LiveData<Integer> mAssessmentsFailed;
    /**
     * Observable count of total courses
     */
    public LiveData<Integer> mTotalCourses;
    /**
     * Observable count of total assessments
     */
    public LiveData<Integer> mTotalAssessments;

    /**
     * Loads the data from the repo
     *
     * @param application
     */
    public MainViewModel(@NonNull Application application) {
        super(application);
        mCoursesCompleted = mRepository.getCoursesByStatus("Complete");
        mCoursesInProgress = mRepository.getCoursesByStatus("In Progress");
        mCoursesDropped = mRepository.getCoursesByStatus("Dropped");
        mCoursesFailed = mRepository.getCoursesByStatus("Failed");
        mAssessmentsPassed = mRepository.getAssessmentsByStatus("Pass");
        mAssessmentsPending = mRepository.getAssessmentsByStatus("Pending");
        mAssessmentsFailed = mRepository.getAssessmentsByStatus("Fail");
        mTotalCourses = mRepository.getTotalCourseCount();
        mTotalAssessments = mRepository.getTotalAssessmentCount();
    }

    /**
     * Directs the repo to load sample data
     */
    public void addSampleData() {
        mRepository.addSampleData();
    }

    /**
     * Directs the repo to delete all data
     */
    public void deleteAll() {
        mRepository.deleteAllData();
    }
}
