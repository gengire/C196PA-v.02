package edu.wgu.grimes.c196pa.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
        return courseDao.getCompletedCoursesByStatus(status);
    }

    public void addSampleData() {
        executor.execute(() -> {
            termDao.save(new TermEntity(1,"Term 1", getDate("October 1, 2018"), getDate("March 31, 2019")));
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

            termDao.save(new TermEntity(2,"Term 2", getDate("April 1, 2019"), getDate("September 30, 2019")));
            courseDao.save(new CourseEntity(11, 2, 6, "C777",
                    "Web Development Applications",
                    getDate("April 1, 2019"),
                    getDate("April 4, 2019"), "Complete"));
            courseDao.save(new CourseEntity(12, 2, 3, "C172",
                    "Network and Security - Foundations",
                    getDate("May 20, 2019"),
                    getDate("May 28, 2019"), "Complete"));
            courseDao.save(new CourseEntity(13, 2, 4, "C176",
                    "Business of IT - Project Management",
                    getDate("June 19, 2019"),
                    getDate("June 25, 2019"), "Complete"));
            courseDao.save(new CourseEntity(14, 2, 4, "C393",
                    "IT Foundations",
                    getDate("July 7, 2019"),
                    getDate("July 14, 2019"), "Complete"));
            courseDao.save(new CourseEntity(15, 2, 3, "C857",
                    "Software Quality Assurance",
                    getDate("August 20, 2019"),
                    getDate("August 27, 2019"), "Complete"));
            courseDao.save(new CourseEntity(16, 2, 4, "C188",
                    "Software Engineering",
                    getDate("September 19, 2019"),
                    getDate("September 26, 2019"), "Complete"));
            termDao.save(new TermEntity(3,"Term 3", getDate("October 1, 2019"), getDate("March 31, 2020")));
            courseDao.save(new CourseEntity(17, 3, 4, "C867",
                    "Scripting and Programming - Applications",
                    getDate("October 1, 2019"),
                    getDate("October 8, 2019"), "Complete"));
            courseDao.save(new CourseEntity(18, 3, 4, "C394",
                    "IT Applications",
                    getDate("October 15, 2019"),
                    getDate("October 22, 2019"), "Complete"));
            courseDao.save(new CourseEntity(19, 3, 4, "C846",
                    "Business of IT - Applications",
                    getDate("December 20, 2019"),
                    getDate("January 5, 2020"), "Complete"));
            courseDao.save(new CourseEntity(20, 3, 4, "C773",
                    "User Interface Design",
                    getDate("January 7, 2020"),
                    getDate("January 12, 2020"), "Complete"));
            courseDao.save(new CourseEntity(21, 3, 3, "C484",
                    "Organizational Behavior and Leadership",
                    getDate("January 26, 2020"),
                    getDate("February 4, 2020"), "Complete"));
            courseDao.save(new CourseEntity(22, 3, 3, "C856",
                    "User Experience Design",
                    getDate("February 1, 2020"),
                    getDate("February 6, 2020"), "Complete"));
            termDao.save(new TermEntity(4,"Term 4", getDate("April 1, 2020"), getDate("September 30, 2020")));
            courseDao.save(new CourseEntity(23, 4, 3, "C191",
                    "Operating Systems for Programmers",
                    getDate("February 7, 2020"),
                    getDate("May 18, 2020"), "Complete"));
            courseDao.save(new CourseEntity(24, 4, 3, "C196",
                    "Mobile Application Development",
                    getDate("May 19, 2020"),
                    null, "In Progress"));
            termDao.save(new TermEntity("Term 5", getDate("October 1, 2020"), getDate("March 31, 2021")));
            termDao.save(new TermEntity("Term 6", getDate("April 1, 2021"), getDate("September 30, 2021")));
            termDao.save(new TermEntity("Term 7", getDate("October 1, 2021"), getDate("March 31, 2022")));
            termDao.save(new TermEntity("Term 8", getDate("April 1, 2022"), getDate("September 30, 2022")));
        });
    }
}
