package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;

public class AssessmentEditorViewModel extends AndroidViewModel {

    public MutableLiveData<AssessmentEntity> mLiveAssessment = new MutableLiveData<>();

    private AppRepository mRepository;

    private Executor executor = Executors.newSingleThreadExecutor();

    public AssessmentEditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
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