package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.utilities.DeleteCourseValidator;
import edu.wgu.grimes.c196pa.utilities.ValidationCallback;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;

public class CourseEditorViewModel extends AndroidViewModel {

    public MutableLiveData<CourseEntity> mLiveCourse = new MutableLiveData<>();

    private AppRepository mRepository;

    private Executor executor = Executors.newSingleThreadExecutor();

    public CourseEditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadCourse(int courseId) {
        executor.execute(() -> {
            CourseEntity course = mRepository.getCourseById(courseId);
            mLiveCourse.postValue(course);
        });
    }

    public void saveCourse(String title, String code, String termId, String competencyUnits, String status, String startDate, String endDate) {
        if (TextUtils.isEmpty(title)) {
            return; // no saving empty titles
        }
        CourseEntity course = mLiveCourse.getValue();
        if (course == null) {
            course = new CourseEntity(Integer.valueOf(termId), Integer.valueOf(competencyUnits), code, title, getDate(startDate), getDate(endDate), status);
        } else {
            course.setTermId(Integer.valueOf(termId));
            course.setCompetencyUnits(Integer.valueOf(competencyUnits));
            course.setTitle(title);
            course.setCode(code);
            course.setStatus(status);
            course.setStartDate(getDate(startDate));
            course.setEndDate(getDate(endDate));
        }
        mRepository.saveCourse(course);
    }

    public void deleteCourse() {
        mRepository.deleteCourse(mLiveCourse.getValue());
    }

    public void validateDeleteCourse(CourseEntity course, ValidationCallback onSuccess, ValidationCallback onFailure) {
        DeleteCourseValidator.validateDeleteCourse(getApplication(), course, onSuccess, onFailure);
    }
}
