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

public class TermEditorViewModel extends BaseViewModel {

    public MutableLiveData<TermEntity> mLiveData = new MutableLiveData<>();

    private LiveData<List<CourseEntity>> mCourses;

    public TermEditorViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadTerm(int termId) {
        executor.execute(() -> {
            TermEntity term = mRepository.getTermById(termId);
            mLiveData.postValue(term);
        });
    }

    public void loadTermCourses(int termId) {
        mCourses = mRepository.getCoursesByTermId(termId);
    }

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

    public void deleteTerm() {
        mRepository.deleteTerm(mLiveData.getValue());
    }

    public LiveData<List<CourseEntity>> getTermCourses() {
        return mCourses;
    }

    public void deleteCourse(CourseEntity course) {
        mRepository.deleteCourse(course);
    }

    public void validateDeleteCourse(CourseEntity course, ValidationCallback onSuccess, ValidationCallback onFailure) {
        DeleteCourseValidator.validateDeleteCourse(getApplication().getApplicationContext(), course, onSuccess, onFailure);
    }

    public void validateDeleteTerm(TermEntity term, ValidationCallback onSuccess, ValidationCallback onFailure) {
        DeleteTermValidator.validateDeleteTerm(getApplication(), term, onSuccess, onFailure);
    }
}