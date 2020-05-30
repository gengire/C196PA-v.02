package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.database.entities.TermWithCourses;

public class TermsListViewModel extends AndroidViewModel {

    AppRepository mRepository;
    private LiveData<List<TermEntity>> allTerms;

    public TermsListViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AppRepository(application.getApplicationContext());
        allTerms = mRepository.getAllTerms();
    }

    public void delete(TermEntity term) {
        mRepository.deleteTerm(term);
    }

    public void deleteAll() {
        mRepository.deleteAllData();
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return allTerms;
    }


    public void addSampleData() {
        mRepository.addSampleData();
    }

    public boolean getTermHasCourses(TermEntity term) {
        TermWithCourses termWithCourses = mRepository.getTermWithCourses(term.getId());
        //TODO: null terms coming back for some weird reason.
        return !termWithCourses.courses.isEmpty();
    }
}
