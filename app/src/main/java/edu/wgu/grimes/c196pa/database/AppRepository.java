package edu.wgu.grimes.c196pa.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.daos.CourseDao;
import edu.wgu.grimes.c196pa.database.daos.TermDao;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;

public class AppRepository {

    private static AppRepository instance;

    private TermDao termDao;
    private CourseDao courseDao;

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
        mTerms = termDao.getAllTerms();
    }

    public void saveTerm(TermEntity term) {
        executor.execute(() -> termDao.save(term));
    }

    public void deleteTerm(TermEntity term) {
        executor.execute(() -> termDao.delete(term));
    }

    public void deleteAllTerms() {
        executor.execute(() -> termDao.deleteAll());
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return mTerms;
    }

    public TermEntity getTermById(int termId) {
        return mDb.termDao().selectTermById(termId);
    }

    public void addSampleData() {
        executor.execute(() -> {
            termDao.save(new TermEntity("Term 1", getDate("October 1, 2018"), getDate("March 31, 2019")));
            courseDao.save(new CourseEntity(1, 1, 4, "C182",
                    "Introduction to IT",
                    getDate("October 10, 2018"),
                    getDate("October 8, 2018"), "Complete"));
            courseDao.save(new CourseEntity(2, 1, 3, "C173",
                    "Scripting and Programming - Foundations",
                    getDate("October 8, 2018"),
                    getDate("October 10, 2018"), "Complete"));
            courseDao.save(new CourseEntity(3, 1, 0, "ORA1",
                    "Orientation",
                    getDate("March 30, 2019"),
                    getDate("April 1, 2019"), "Complete"));
            courseDao.save(new CourseEntity(4, 1, 3, "C779",
                    "Web Development Foundations",
                    getDate("November 11, 2018"),
                    getDate("November 13, 2018"), "Complete"));
            courseDao.save(new CourseEntity(5, 1, 3, "C100",
                    "Introduction to Humanities",
                    getDate("October 30, 2018"),
                    getDate("December 30, 2018"), "Complete"));
            courseDao.save(new CourseEntity(6, 1, 4, "C993",
                    "Structured Query Language",
                    getDate("January 14, 2019"),
                    getDate("January 30, 2019"), "Complete"));
            courseDao.save(new CourseEntity(7, 1, 6, "C482",
                    "Software I",
                    getDate("February 11, 2019"),
                    getDate("February 13, 2019"), "Complete"));
            courseDao.save(new CourseEntity(8, 1, 6, "C195",
                    "Software II - Advanced Java Concepts",
                    getDate("February 25, 2019"),
                    getDate("February 27, 2019"), "Complete"));
            courseDao.save(new CourseEntity(9, 1, 3, "C175",
                    "Data Management - Foundations",
                    getDate("March 11, 2019"),
                    getDate("March 12, 2019"), "Complete"));
            courseDao.save(new CourseEntity(10, 1, 4, "C170",
                    "Data Management - Applications",
                    getDate("March 16, 2019"),
                    getDate("March 18, 2019"), "Complete"));

            termDao.save(new TermEntity("Term 2", getDate("April 1, 2019"), getDate("September 30, 2019")));
            termDao.save(new TermEntity("Term 3", getDate("October 1, 2019"), getDate("March 31, 2020")));
            termDao.save(new TermEntity("Term 4", getDate("April 1, 2020"), getDate("September 30, 2020")));
            termDao.save(new TermEntity("Term 5", getDate("October 1, 2020"), getDate("March 31, 2021")));
            termDao.save(new TermEntity("Term 6", getDate("April 1, 2021"), getDate("September 30, 2021")));
            termDao.save(new TermEntity("Term 7", getDate("October 1, 2021"), getDate("March 31, 2022")));
            termDao.save(new TermEntity("Term 8", getDate("April 1, 2022"), getDate("September 30, 2022")));
        });
    }
}
