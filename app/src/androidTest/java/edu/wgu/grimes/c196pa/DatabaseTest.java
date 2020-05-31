package edu.wgu.grimes.c196pa;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.wgu.grimes.c196pa.database.AppDatabase;
import edu.wgu.grimes.c196pa.database.daos.CourseDao;
import edu.wgu.grimes.c196pa.database.daos.TermDao;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.database.entities.TermWithCourses;
import edu.wgu.grimes.c196pa.utilities.SampleData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private TermDao mTermDao;
    private CourseDao mCourseDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mTermDao = mDb.termDao();
        mCourseDao = mDb.courseDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndCountTerms() {
        mTermDao.saveAll(SampleData.getSampleTerms());
        int count = mTermDao.getCount();
        assertThat(count, is(8));
    }

    @Test
    public void createAndCountCourses() {
        mTermDao.saveAll(SampleData.getSampleTerms());
        mCourseDao.saveAll(SampleData.getSampleCourses());
        int count = mCourseDao.getCount();
        assertThat(count, is(25));
    }

    @Test
    public void compareTerms() {
        List<TermEntity> terms = SampleData.getSampleTerms();
        mTermDao.saveAll(terms);
        TermEntity original = terms.get(0);
        TermEntity fromDb = mTermDao.getTermById(1);
        assertThat(fromDb, notNullValue());
        assertThat(fromDb.getTitle(), is(original.getTitle()));
        assertThat(fromDb.getId(), is(original.getId()));
        assertThat(fromDb.getStartDate(), is(original.getStartDate()));
        assertThat(fromDb.getEndDate(), is(original.getEndDate()));
    }

    @Test
    public void compareCourses() {
        mTermDao.saveAll(SampleData.getSampleTerms());
        mCourseDao.saveAll(SampleData.getSampleCourses());
        CourseEntity original = SampleData.getSampleCourses().get(0);
        CourseEntity fromDb = mCourseDao.getCourseById(1);
        assertThat(fromDb, notNullValue());
        assertThat(fromDb.getId(), is(original.getId()));
        assertThat(fromDb.getTermId(), is(original.getTermId()));
        assertThat(fromDb.getCode(), is(original.getCode()));
        assertThat(fromDb.getTitle(), is(original.getTitle()));
    }

    @Test
    public void termsWithCoursesTest() {
        mTermDao.saveAll(SampleData.getSampleTerms());
        mCourseDao.saveAll(SampleData.getSampleCourses());
        TermWithCourses term = mTermDao.getTermWithCourses(1);
        assertThat(term, notNullValue());
        assertThat(term.courses, notNullValue());
        assertThat(term.courses.isEmpty(), is(Boolean.FALSE));
        assertThat(term.courses.size(), is(Integer.valueOf(10)));
    }
}
