//*********************************************************************************
//  File:             DeleteAccountValidator.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.utilities.validation;

import android.content.Context;
import android.os.AsyncTask;

import edu.wgu.grimes.c868pa.database.AppRepository;
import edu.wgu.grimes.c868pa.database.entities.AccountEntity;

/**
 * Handles validation for deleting accounts
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class DeleteAccountValidator {

    /**
     * Requires currently logged in accounts cannot be deleted
     *
     * @param context The context
     * @param account The account to validate
     * @param onSuccess The on success strategy
     * @param onFailure The on failure strategy
     */
    public static void validateDeleteAccount(Context context, AccountEntity account, ValidationCallback onSuccess, ValidationCallback onFailure) {
        AppRepository mRepository = AppRepository.getInstance(context);
        AsyncTask<Void, Void, Boolean> async = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                if (account == null) {
                    return true;
                }
                return mRepository.getLoggedInAccountId() != account.getId();
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    onSuccess.callback();
                } else {
                    onFailure.callback();
                }
            }
        };
        async.execute();
    }

}
