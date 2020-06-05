package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.MentorEntity;

public class MentorsListViewModel extends BaseViewModel {

    private LiveData<List<MentorEntity>> allMentors;

    public MentorsListViewModel(@NonNull Application application) {
        super(application);
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
