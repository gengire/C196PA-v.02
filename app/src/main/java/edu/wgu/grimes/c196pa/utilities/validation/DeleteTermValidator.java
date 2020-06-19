//*********************************************************************************
//  File:             DeleteTermValidator.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.utilities.validation;

import android.content.Context;
import android.os.AsyncTask;

import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.database.entities.TermWithCourses;

/**
 * Handles validation for deleting terms
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class DeleteTermValidator {

    /**
     * Requires that before a term can be deleted, that it has no courses associated to it.
     *
     * @param context
     * @param term
     * @param onSuccess
     * @param onFailure
     */
    public static void validateDeleteTerm(Context context, TermEntity term, ValidationCallback onSuccess, ValidationCallback onFailure) {
        AppRepository mRepository = AppRepository.getInstance(context);
        AsyncTask<Void, Void, Boolean> async = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                if (term == null) {
                    return true;
                }
                TermWithCourses termWithCourses = mRepository.getTermWithCourses(term.getId());
                return !(termWithCourses != null && // we have a term with courses
                        termWithCourses.courses != null && // there are courses
                        !termWithCourses.courses.isEmpty()); // the courses are not empty
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
