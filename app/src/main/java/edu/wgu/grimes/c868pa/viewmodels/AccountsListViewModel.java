//*********************************************************************************
//  File:             AccountsListViewModel.java
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

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.wgu.grimes.c868pa.database.entities.AccountEntity;
import edu.wgu.grimes.c868pa.database.entities.TermEntity;
import edu.wgu.grimes.c868pa.utilities.validation.DeleteAccountValidator;
import edu.wgu.grimes.c868pa.utilities.validation.DeleteTermValidator;
import edu.wgu.grimes.c868pa.utilities.validation.ValidationCallback;

/**
 * View model for the accounts list activity
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class AccountsListViewModel extends BaseViewModel {

    /**
     * Observable list of accounts
     */
    private final LiveData<List<AccountEntity>> allAccounts;

    public AccountsListViewModel(@NonNull Application application) {
        super(application);
        allAccounts = mRepository.getAllAccounts();
    }

    /**
     * Forwards the request to delete the given account to the repo
     *
     * @param account The account to be deleted
     */
    public void deleteAccount(AccountEntity account) {
        mRepository.deleteAccount(account);
    }

    public LiveData<List<AccountEntity>> getAllAccounts() {
        return mRepository.getAllAccounts();
    }

    /**
     * Calls into the delete account validator
     *
     * @param account The account to be validated / deleted
     * @param onSuccess The on validation success strategy
     * @param onFailure The on validation failure strategy
     */
    public void validateDeleteAccount(AccountEntity account, ValidationCallback onSuccess, ValidationCallback onFailure) {
        DeleteAccountValidator.validateDeleteAccount(getApplication(), account, onSuccess, onFailure);
    }
}
