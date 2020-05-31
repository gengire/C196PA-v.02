package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;
import android.os.AsyncTask;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.database.entities.TermWithCourses;
import edu.wgu.grimes.c196pa.utilities.ValidationCallback;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;

public class TermEditorViewModel extends AndroidViewModel {

    public MutableLiveData<TermEntity> mLiveTerm = new MutableLiveData<>();

    private LiveData<List<CourseEntity>> mCourses;

    private AppRepository mRepository;

    private Executor executor = Executors.newSingleThreadExecutor();

    public TermEditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadTerm(int termId) {
        executor.execute(() -> {
            TermEntity term = mRepository.getTermById(termId);
            mLiveTerm.postValue(term);
        });
    }

    public void loadTermCourses(int termId) {
        mCourses = mRepository.getCoursesByTermId(termId);
    }

    public void saveTerm(String title, String sDate, String eDate) {
        if (TextUtils.isEmpty(title.trim())) {
            return; // no saving blank titles
        }
        TermEntity term = mLiveTerm.getValue();
        if (term == null) {
            term = new TermEntity(title, getDate(sDate), getDate(eDate));
        } else {
            term.setTitle(title.trim());
            term.setStartDate(getDate(sDate));
            term.setEndDate(getDate(eDate));
        }
        mRepository.saveTerm(term);
    }

    public void deleteTerm() {
        mRepository.deleteTerm(mLiveTerm.getValue());
    }

    public LiveData<List<CourseEntity>> getTermCourses() {
        return mCourses;
    }

    public void deleteCourse(CourseEntity course) {
        mRepository.deleteCourse(course);
    }

    public void validateDeleteCourse(CourseEntity course, ValidationCallback onSuccess, ValidationCallback onFailure) {
        AsyncTask<Void, Void, Boolean> async = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
//                TermWithCourses termWithCourses = mRepository.getTermWithCourses(term.getId());
//                return !(termWithCourses != null && // we have a term with courses
//                        termWithCourses.courses != null && // there are courses
//                        !termWithCourses.courses.isEmpty()); // the courses are not empty
                // TODO: implement validation for deleting courses
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    onSuccess.callback();
                } else {
                    onFailure.callback();
                }
            }
        };
        async.execute();
    }
}