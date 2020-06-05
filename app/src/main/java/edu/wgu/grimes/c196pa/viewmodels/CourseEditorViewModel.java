package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.utilities.DeleteCourseValidator;
import edu.wgu.grimes.c196pa.utilities.ValidationCallback;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;

public class CourseEditorViewModel extends BaseViewModel {

    public MutableLiveData<CourseEntity> mLiveCourse = new MutableLiveData<>();

    private LiveData<List<AssessmentEntity>> mAssessments;

    public CourseEditorViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadCourse(int courseId) {
        executor.execute(() -> {
            CourseEntity course = mRepository.getCourseById(courseId);
            mLiveCourse.postValue(course);
        });
    }

    public void loadCourseAssessments(int courseId) {
        mAssessments = mRepository.getAssessmentsForCourse(courseId);
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

    public LiveData<List<AssessmentEntity>> getCourseAssessments() {
        return mAssessments;
    }

    public void validateDeleteCourse(CourseEntity course, ValidationCallback onSuccess, ValidationCallback onFailure) {
        DeleteCourseValidator.validateDeleteCourse(getApplication(), course, onSuccess, onFailure);
    }

    public void deleteAssessment(AssessmentEntity assessment) {
        mRepository.deleteAssessment(assessment);
    }
}
