//*********************************************************************************
//  File:             DeleteCourseValidator.java
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
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;

/**
 * Validation for deleting a course
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class DeleteCourseValidator {

    /**
     * Handles validation for deleting courses
     *
     * @param context THe context
     * @param course The course to validate
     * @param onSuccess The on success strategy
     * @param onFailure The on failure strategy
     */
    public static void validateDeleteCourse(Context context, CourseEntity course, ValidationCallback onSuccess, ValidationCallback onFailure) {
        AppRepository mRepository = AppRepository.getInstance(context);
        AsyncTask<Void, Void, Boolean> async = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                // decided to not use this since it isn't a requirement  The DAO now simply cascade deletes courses
//                if (course == null)  {
//                    return true;
//                }
//                return mRepository.getAssessmentCountForCourse(course) == 0;
                return true;
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
