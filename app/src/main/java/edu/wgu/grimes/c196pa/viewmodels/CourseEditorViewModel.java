//*********************************************************************************
//  File:             CourseEditorViewModel.java
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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.utilities.validation.DeleteCourseValidator;
import edu.wgu.grimes.c196pa.utilities.validation.ValidationCallback;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;

/**
 * View model for the course editor activity
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class CourseEditorViewModel extends BaseViewModel {

    /**
     * Observable course that notifies on update
     */
    public MutableLiveData<CourseEntity> mLiveCourse = new MutableLiveData<>();

    /**
     * Observable list of assessments for the course being edited
     */
    private LiveData<List<AssessmentEntity>> mAssessments;

    /**
     * Constructor
     *
     * @param application
     */
    public CourseEditorViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Sets the course with the given id as the observable for this editor
     *
     * @param courseId
     */
    public void loadCourse(int courseId) {
        executor.execute(() -> {
            CourseEntity course = mRepository.getCourseById(courseId);
            mLiveCourse.postValue(course);
        });
    }

    /**
     * Sets the assessments for the given course id as the observable list for the recycler view
     *
     * @param courseId
     */
    public void loadCourseAssessments(int courseId) {
        mAssessments = mRepository.getAssessmentsForCourse(courseId);
    }

    /**
     * Passes the data from the screen to the repo for persisting
     *
     * @param title
     * @param code
     * @param termId
     * @param competencyUnits
     * @param status
     * @param startDate
     * @param startDateAlarm
     * @param endDate
     * @param endDateAlarm
     */
    public void saveCourse(String title, String code, String termId, String competencyUnits, String status, String startDate, Date startDateAlarm, String endDate, Date endDateAlarm) {
        if (TextUtils.isEmpty(title)) {
            return; // no saving empty titles
        }
        CourseEntity course = mLiveCourse.getValue();
        int cus = TextUtils.isEmpty(competencyUnits) ? 0 : Integer.parseInt(competencyUnits);
        if (course == null) {
            course = new CourseEntity(Integer.parseInt(termId), cus, code, title, getDate(startDate), startDateAlarm, getDate(endDate), endDateAlarm, status);
        } else {
            course.setTermId(Integer.parseInt(termId));
            course.setCompetencyUnits(cus);
            course.setTitle(title);
            course.setCode(code);
            course.setStatus(status);
            course.setStartDate(getDate(startDate));
            course.setStartDateAlarm(startDateAlarm);
            course.setEndDate(getDate(endDate));
            course.setEndDateAlarm(endDateAlarm);
        }
        mRepository.saveCourse(course);
    }

    /**
     * Forwards the request to delete the currently observable course to the repo
     */
    public void deleteCourse() {
        mRepository.deleteCourse(mLiveCourse.getValue());
    }

    /**
     * Returns the observable list of assessments for this course
     *
     * @return
     */
    public LiveData<List<AssessmentEntity>> getCourseAssessments() {
        return mAssessments;
    }

    /**
     * Calls into the delete course validator.
     *
     * @param course
     * @param onSuccess
     * @param onFailure
     */
    public void validateDeleteCourse(CourseEntity course, ValidationCallback onSuccess, ValidationCallback onFailure) {
        DeleteCourseValidator.validateDeleteCourse(getApplication(), course, onSuccess, onFailure);
    }

    /**
     * Forwards the request to delete an assessment to the repo
     *
     * @param assessment
     */
    public void deleteAssessment(AssessmentEntity assessment) {
        mRepository.deleteAssessment(assessment);
    }
}
