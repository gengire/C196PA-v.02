package edu.wgu.grimes.c196pa.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.daos.TermDao;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;

public class AppRepository {

    private static AppRepository instance;

    private TermDao termDao;
    private LiveData<List<TermEntity>> allTerms;
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
        allTerms = termDao.getAllTerms();
    }

    public void saveTerm(TermEntity term) {
        executor.execute(() -> termDao.insert(term));
    }

    public void deleteTerm(TermEntity term) {
        executor.execute(() -> termDao.delete(term));
    }

    public void deleteAllTerms() {
        executor.execute(() -> termDao.deleteAll());
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return allTerms;
    }

    public TermEntity getTermById(int termId) {
        return mDb.termDao().selectTermById(termId);
    }
}
