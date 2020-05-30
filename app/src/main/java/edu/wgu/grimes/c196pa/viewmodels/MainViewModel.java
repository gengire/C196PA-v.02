package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import edu.wgu.grimes.c196pa.database.AppRepository;

public class MainViewModel extends AndroidViewModel {

    private AppRepository mRepository;
    public LiveData<Integer> mCoursesCompleted;
    public LiveData<Integer> mCoursesInProgress;
    public LiveData<Integer> mCoursesDropped;
    public LiveData<Integer> mCoursesFailed;
    public LiveData<Integer> mAssessmentsPending;
    public LiveData<Integer> mAssessmentsPassed;
    public LiveData<Integer> mAssessmentsFailed;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mCoursesCompleted = mRepository.getCoursesByStatus("Complete");
        mCoursesInProgress = mRepository.getCoursesByStatus("In Progress");
    }

    public String getCoursesDropped() {
        return String.valueOf(0);
    }

    public String getCoursesFailed() {
        return String.valueOf(0);
    }

    public String getAssessmentsPending() {
        return String.valueOf(2);
    }

    public String getAssessmentsPassed() {
        return String.valueOf(25);
    }

    public String getAssessmentsFailed() {
        return String.valueOf(0);
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAll() {
        mRepository.deleteAllTerms();
    }
}
