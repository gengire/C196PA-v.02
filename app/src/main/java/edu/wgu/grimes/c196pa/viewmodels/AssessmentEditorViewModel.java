//*********************************************************************************
//  File:             AssessmentEditorViewModel.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;

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

    public void saveAssessment(Integer courseId, String assessmentType, String title, String status, String completionDate, Date completionDateAlarm) {
        if (TextUtils.isEmpty(title)) {
            return; // no saving empty titles
        }
        AssessmentEntity assessment = mLiveAssessment.getValue();
        if (assessment == null) {
            assessment = new AssessmentEntity(courseId, assessmentType, title, status, getDate(completionDate), completionDateAlarm);
        } else {
            assessment.setCourseId(courseId);
            assessment.setType(assessmentType);
            assessment.setTitle(title);
            assessment.setStatus(status);
            assessment.setCompletionDate(getDate(completionDate));
            assessment.setCompletionDateAlarm(completionDateAlarm);
        }
        mRepository.saveAssessment(assessment);
    }

    public void deleteAssessment() {
        mRepository.deleteAssessment(mLiveAssessment.getValue());
    }
}
