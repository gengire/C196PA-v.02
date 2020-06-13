package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class MainViewModel extends BaseViewModel {

    public LiveData<Integer> mCoursesCompleted;
    public LiveData<Integer> mCoursesInProgress;
    public LiveData<Integer> mCoursesDropped;
    public LiveData<Integer> mCoursesFailed;
    public LiveData<Integer> mAssessmentsPending;
    public LiveData<Integer> mAssessmentsPassed;
    public LiveData<Integer> mAssessmentsFailed;
    public LiveData<Integer> mTotalCourses;
    public LiveData<Integer> mTotalAssessments;

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

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAll() {
        mRepository.deleteAllData();
    }
}
