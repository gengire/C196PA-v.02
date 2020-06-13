package edu.wgu.grimes.c196pa.utilities.validation;

import android.content.Context;
import android.os.AsyncTask;

import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;

public class DeleteCourseValidator {

    public static void validateDeleteCourse(Context context, CourseEntity course, ValidationCallback onSuccess, ValidationCallback onFailure) {
        AppRepository mRepository = AppRepository.getInstance(context);
        AsyncTask<Void, Void, Boolean> async = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
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
