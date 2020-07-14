//*********************************************************************************
//  File:             AccountEditorViewModel.java
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

import edu.wgu.grimes.c868pa.database.entities.AccountEntity;
import edu.wgu.grimes.c868pa.utilities.validation.DeleteAccountValidator;
import edu.wgu.grimes.c868pa.utilities.validation.ValidationCallback;

public class AccountEditorViewModel extends BaseViewModel {

    /**
     * Observable term that notifies on update
     */
    public final MutableLiveData<AccountEntity> mLiveData = new MutableLiveData<>();

    public AccountEditorViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Sets the account with the given id as the observable for this editor
     *
     * @param accountId The id of the account to load
     */
    public void loadAccount(int accountId) {
        executor.execute(() -> {
            AccountEntity account = mRepository.getAccountById(accountId);
            mLiveData.postValue(account);
        });
    }

    /**
     * Passes the data from the screen to the repo for persisting
     *
     * @param username The username of the account
     * @param password The password of the account
     */
    public void saveAccount(String username, String password) {
        if (TextUtils.isEmpty(username.trim())) {
            return; // no saving blank titles
        }
        AccountEntity account = mLiveData.getValue();
        if (account == null) {
            account = new AccountEntity(username, password, null);
        } else {
            account.setUsername(username.trim());
            account.setPassword(password);
        }
        mRepository.saveAccount(account);
    }

    /**
     * Forwards the request to delete the currently observable account to the repo
     */
    public void deleteAccount() {
        mRepository.deleteAccount(mLiveData.getValue());
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
