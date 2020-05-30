package edu.wgu.grimes.c196pa.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.daos.AssessmentDao;
import edu.wgu.grimes.c196pa.database.daos.CourseDao;
import edu.wgu.grimes.c196pa.database.daos.TermDao;
import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.database.entities.TermWithCourses;
import edu.wgu.grimes.c196pa.utilities.SampleData;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;

public class AppRepository {

    private static AppRepository instance;

    private TermDao termDao;
    private CourseDao courseDao;
    private AssessmentDao assessmentDao;

    public LiveData<List<TermEntity>> mTerms;

    private AppDatabase mDb;
    Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository(context);
        }
        return instance;
    }

    public AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        termDao = mDb.termDao();
        courseDao = mDb.courseDao();
        assessmentDao = mDb.assessmentDao();
        mTerms = termDao.getAllTerms();
    }

    public void saveTerm(TermEntity term) {
        executor.execute(() -> termDao.save(term));
    }

    public void deleteTerm(TermEntity term) {
        executor.execute(() -> termDao.delete(term));
    }

    public void deleteAllData() {
        executor.execute(() -> assessmentDao.deleteAll());
        executor.execute(() -> courseDao.deleteAll());
        executor.execute(() -> termDao.deleteAll());
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return mTerms;
    }

    public TermEntity getTermById(int termId) {
        return mDb.termDao().selectTermById(termId);
    }

    public LiveData<Integer> getCoursesByStatus(String status) {
        return courseDao.getCoursesByStatus(status);
    }

    public LiveData<Integer> getAssessmentsByStatus(String status) {
        return assessmentDao.getAssessmentsByStatus(status);
    }

    public void addSampleData() {
        executor.execute(() -> {
            for (TermEntity sampleTerm : SampleData.getSampleTerms()) {
                termDao.save(sampleTerm);
            }
            for (CourseEntity sampleCourse : SampleData.getSampleCourses()) {
                courseDao.save(sampleCourse);
            }
            for (AssessmentEntity sampleAssessment : SampleData.getSampleAssessments()) {
                assessmentDao.save(sampleAssessment);
            }
        });
    }

    public TermWithCourses getTermWithCourses(int id) {
        final TermWithCourses[] termWithCourses = new TermWithCourses[1];
        executor.execute(() -> {
            termWithCourses[0] = termDao.getTermWithCourses(id);
        });
        return termWithCourses[0];
    }
}
