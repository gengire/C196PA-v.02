//*********************************************************************************
//  File:             TermsListViewModel.java
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

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.TermCusTuple;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.utilities.validation.DeleteTermValidator;
import edu.wgu.grimes.c196pa.utilities.validation.ValidationCallback;

public class TermsListViewModel extends BaseViewModel {

    private LiveData<List<TermEntity>> allTerms;

    public TermsListViewModel(@NonNull Application application) {
        super(application);
        allTerms = mRepository.getAllTerms();
    }

    public void deleteTerm(TermEntity term) {
        mRepository.deleteTerm(term);
    }

    public void deleteAll() {
        mRepository.deleteAllData();
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return allTerms;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void validateDeleteTerm(TermEntity term, ValidationCallback onSuccess, ValidationCallback onFailure) {
        DeleteTermValidator.validateDeleteTerm(getApplication(), term, onSuccess, onFailure);
    }

    public LiveData<List<TermCusTuple>> getAllTermCus() {
        return mRepository.getAllTermCus();
    }
}
