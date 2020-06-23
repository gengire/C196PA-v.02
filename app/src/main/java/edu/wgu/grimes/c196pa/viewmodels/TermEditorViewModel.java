//*********************************************************************************
//  File:             TermEditorViewModel.java
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

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.utilities.validation.DeleteCourseValidator;
import edu.wgu.grimes.c196pa.utilities.validation.DeleteTermValidator;
import edu.wgu.grimes.c196pa.utilities.validation.ValidationCallback;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;

/**
 * View model for the term editor activity
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class TermEditorViewModel extends BaseViewModel {

    /**
     * Observable term that notifies on update
     */
    public MutableLiveData<TermEntity> mLiveData = new MutableLiveData<>();

    /**
     * Observable list of courses for the term being edited
     */
    private LiveData<List<CourseEntity>> mCourses;

    /**
     * Constructor
     *
     * @param application The context
     */
    public TermEditorViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Sets the term with the given id as the observable for this editor
     *
     * @param termId The id of the term to load
     */
    public void loadTerm(int termId) {
        executor.execute(() -> {
            TermEntity term = mRepository.getTermById(termId);
            mLiveData.postValue(term);
        });
    }

    /**
     * Sets the courses for the given term id as the observable list for the recycler view
     *
     * @param termId The id of the term
     */
    public void loadTermCourses(int termId) {
        mCourses = mRepository.getCoursesByTermId(termId);
    }

    /**
     * Passes the data from the screen to the repo for persisting
     *
     * @param title The title of the term
     * @param sDate The start date of the term
     * @param eDate The end date of the term
     */
    public void saveTerm(String title, String sDate, String eDate) {
        if (TextUtils.isEmpty(title.trim())) {
            return; // no saving blank titles
        }
        TermEntity term = mLiveData.getValue();
        if (term == null) {
            term = new TermEntity(title, getDate(sDate), getDate(eDate));
        } else {
            term.setTitle(title.trim());
            term.setStartDate(getDate(sDate));
            term.setEndDate(getDate(eDate));
        }
        mRepository.saveTerm(term);
    }

    /**
     * Forwards the request to delete the currently observable term to the repo
     */
    public void deleteTerm() {
        mRepository.deleteTerm(mLiveData.getValue());
    }

    /**
     * Returns the observable list of courses for this term
     *
     * @return An observable list of courses for the currently selected term
     */
    public LiveData<List<CourseEntity>> getTermCourses() {
        return mCourses;
    }

    /**
     * Forwards the request to delete the given course to the repo
     *
     * @param course The course to be deleted
     */
    public void deleteCourse(CourseEntity course) {
        mRepository.deleteCourse(course);
    }

    /**
     * Calls into the delete course validator
     *
     * @param course The course to be validated / deleted
     * @param onSuccess The on validation success strategy
     * @param onFailure The on validation failure strategy
     */
    public void validateDeleteCourse(CourseEntity course, ValidationCallback onSuccess, ValidationCallback onFailure) {
        DeleteCourseValidator.validateDeleteCourse(getApplication().getApplicationContext(), course, onSuccess, onFailure);
    }

    /**
     * Calls into the delete term validator
     *
     * @param term The term to be validated / deleted
     * @param onSuccess The on validation success strategy
     * @param onFailure The on validation failure strategy
     */
    public void validateDeleteTerm(TermEntity term, ValidationCallback onSuccess, ValidationCallback onFailure) {
        DeleteTermValidator.validateDeleteTerm(getApplication(), term, onSuccess, onFailure);
    }
}