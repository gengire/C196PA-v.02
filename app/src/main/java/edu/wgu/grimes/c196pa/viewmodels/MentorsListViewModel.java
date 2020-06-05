package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.MentorEntity;

public class MentorsListViewModel extends AndroidViewModel {

    AppRepository mRepository;
    private LiveData<List<MentorEntity>> allMentors;
    Executor executor = Executors.newSingleThreadExecutor();

    public MentorsListViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadCourseMentors(int courseId) {
        allMentors = mRepository.getMentorsForCourse(courseId);
    }

    public void deleteMentor(MentorEntity mentor) {
        mRepository.deleteMentor(mentor);
    }

    public LiveData<List<MentorEntity>> getCourseMentors() {
        return allMentors;
    }
}
