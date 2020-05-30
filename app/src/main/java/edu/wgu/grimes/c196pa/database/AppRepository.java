package edu.wgu.grimes.c196pa.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.daos.TermDao;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;

public class AppRepository {

    private static AppRepository instance;

    private TermDao termDao;
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
