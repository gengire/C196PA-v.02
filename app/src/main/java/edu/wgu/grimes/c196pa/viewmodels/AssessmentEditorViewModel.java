package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;

public class AssessmentEditorViewModel extends BaseViewModel {

    public MutableLiveData<AssessmentEntity> mLiveAssessment = new MutableLiveData<>();

    public AssessmentEditorViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadAssessment(int assessmentId) {
        executor.execute(() -> {
            AssessmentEntity assessment = mRepository.getAssessmentById(assessmentId);
            mLiveAssessment.postValue(assessment);
        });
    }

    public void saveAssessment(Integer courseId, String assessmentType, String title, String status, String completionDate) {
        if (TextUtils.isEmpty(title)) {
            return; // no saving empty titles
        }
        AssessmentEntity assessment = mLiveAssessment.getValue();
        if (assessment == null) {
            assessment = new AssessmentEntity(courseId, assessmentType, title, status, getDate(completionDate));
        } else {
            assessment.setCourseId(Integer.valueOf(courseId));
            assessment.setType(assessmentType);
            assessment.setTitle(title);
            assessment.setStatus(status);
            assessment.setCompletionDate(getDate(completionDate));
        }
        mRepository.saveAssessment(assessment);
    }

    public void deleteAssessment() {
        mRepository.deleteAssessment(mLiveAssessment.getValue());
    }
}
