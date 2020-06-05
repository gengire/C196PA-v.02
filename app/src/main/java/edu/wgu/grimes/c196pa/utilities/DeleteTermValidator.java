package edu.wgu.grimes.c196pa.utilities;

import android.content.Context;
import android.os.AsyncTask;

import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.database.entities.TermWithCourses;

public class DeleteTermValidator {

    public static void validateDeleteTerm(Context context, TermEntity term, ValidationCallback onSuccess, ValidationCallback onFailure) {
        AppRepository mRepository = AppRepository.getInstance(context);
        AsyncTask<Void, Void, Boolean> async = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
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
