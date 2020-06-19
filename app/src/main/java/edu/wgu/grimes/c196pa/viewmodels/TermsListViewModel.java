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

/**
 * View model for the terms list activity
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class TermsListViewModel extends BaseViewModel {

    /**
     * Observable list of terms
     */
    private LiveData<List<TermEntity>> allTerms;

    /**
     * Constructor
     *
     * @param application
     */
    public TermsListViewModel(@NonNull Application application) {
        super(application);
        allTerms = mRepository.getAllTerms();
    }

    /**
     * Forwards the request to delete the given term to the repo
     *
     * @param term
     */
    public void deleteTerm(TermEntity term) {
        mRepository.deleteTerm(term);
    }

    /**
     * Forwards the request to delete all data to the repo
     */
    public void deleteAll() {
        mRepository.deleteAllData();
    }

    /**
     * Returns the observable list of terms
     *
     * @return
     */
    public LiveData<List<TermEntity>> getAllTerms() {
        return allTerms;
    }

    /**
     * Forwards the request to add sample data to the repo
     */
    public void addSampleData() {
        mRepository.addSampleData();
    }

    /**
     * Calls into the delete term validator
     *
     * @param term
     * @param onSuccess
     * @param onFailure
     */
    public void validateDeleteTerm(TermEntity term, ValidationCallback onSuccess, ValidationCallback onFailure) {
        DeleteTermValidator.validateDeleteTerm(getApplication(), term, onSuccess, onFailure);
    }

    /**
     * Returns the observable list of Term / Competency Units Tuple
     *
     * @return
     */
    public LiveData<List<TermCusTuple>> getAllTermCus() {
        return mRepository.getAllTermCus();
    }
}
