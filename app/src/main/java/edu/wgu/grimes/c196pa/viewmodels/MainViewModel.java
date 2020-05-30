package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;

public class MainViewModel extends AndroidViewModel {


    public LiveData<List<TermEntity>> mTerms;

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

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mTerms = mRepository.mTerms;
    }

    public String getCoursesCompleted() {
        return String.valueOf(23);
    }

    public String getCoursesInProgress() {
        return String.valueOf(1);
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
