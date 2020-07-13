//*********************************************************************************
//  File:             AssessmentEditorViewModel.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;

import edu.wgu.grimes.c868pa.database.entities.AssessmentEntity;

import static edu.wgu.grimes.c868pa.utilities.StringUtils.getDate;

/**
 * View model for the assessment editor activity
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class AssessmentEditorViewModel extends BaseViewModel {

    /**
     * Observable assessment that notifies on update
     */
    public final MutableLiveData<AssessmentEntity> mLiveAssessment = new MutableLiveData<>();

    /**
     * Constructor
     *
     * @param application The context
     */
    public AssessmentEditorViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Sets the assessment with the given id as the observable for this editor
     *
     * @param assessmentId The id of the assessment
     */
    public void loadAssessment(int assessmentId) {
        executor.execute(() -> {
            AssessmentEntity assessment = mRepository.getAssessmentById(assessmentId);
            mLiveAssessment.postValue(assessment);
        });
    }

    /**
     * Passes the data from the screen into the repo for persisting
     *
     * @param courseId The id of the course this assessment is associated to
     * @param assessmentType The assessment type
     * @param title The assessment title
     * @param status The assessment status
     * @param completionDate The assessment completion date
     * @param completionDateAlarm The assessment completion date alarm
     */
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
            assessment.setAssessmentTitle(title);
            assessment.setAssessmentStatus(status);
            assessment.setCompletionDate(getDate(completionDate));
            assessment.setCompletionDateAlarm(completionDateAlarm);
        }
        mRepository.saveAssessment(assessment);
    }

    /**
     * Forwards the request to delete the currently observable assessment to the repo
     */
    public void deleteAssessment() {
        mRepository.deleteAssessment(mLiveAssessment.getValue());
    }
}
