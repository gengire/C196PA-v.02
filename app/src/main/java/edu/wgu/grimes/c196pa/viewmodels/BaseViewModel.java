package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.AppRepository;

public class BaseViewModel extends AndroidViewModel {

    protected AppRepository mRepository;
    protected Executor executor = Executors.newSingleThreadExecutor();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AppRepository(application.getApplicationContext());
    }
}