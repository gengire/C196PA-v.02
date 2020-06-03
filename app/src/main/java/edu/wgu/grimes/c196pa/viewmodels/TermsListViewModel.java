package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.utilities.DeleteTermValidator;
import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.TermCusTuple;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.utilities.ValidationCallback;

public class TermsListViewModel extends AndroidViewModel {

    AppRepository mRepository;
    private LiveData<List<TermEntity>> allTerms;
    Executor executor = Executors.newSingleThreadExecutor();

    public TermsListViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AppRepository(application.getApplicationContext());
        allTerms = mRepository.getAllTerms();
    }

    public void deleteTerm(TermEntity term) {
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

    public void validateDeleteTerm(TermEntity term, ValidationCallback onSuccess, ValidationCallback onFailure) {
//        AsyncTask<Void, Void, Boolean> async = new AsyncTask<Void, Void, Boolean>() {
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                TermWithCourses termWithCourses = mRepository.getTermWithCourses(term.getId());
//                return !(termWithCourses != null && // we have a term with courses
//                        termWithCourses.courses != null && // there are courses
//                        !termWithCourses.courses.isEmpty()); // the courses are not empty
//            }
//
//            @Override
//            protected void onPostExecute(Boolean success) {
//                if (success) {
//                    onSuccess.callback();
//                } else {
//                    onFailure.callback();
//                }
//            }
//        };
//        async.execute();
        DeleteTermValidator.validateDeleteTerm(getApplication(), term, onSuccess, onFailure);
    }

    public LiveData<List<TermCusTuple>> getAllTermCus() {
        return mRepository.getAllTermCus();
    }
}
