package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import edu.wgu.grimes.c196pa.database.AppRepository;

public class MainViewModel extends AndroidViewModel {

    private AppRepository mRepository;
    private LiveData<Integer> pendingCourses;
    private LiveData<Integer> completedCourses;
    private LiveData<Integer> droppedCourses;
    private LiveData<Integer> failedCourses;
    private LiveData<Integer> pendingAssessments;
    private LiveData<Integer> passedAssessments;
    private LiveData<Integer> failedAssessments;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
