package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.MentorEntity;

public class MentorEditorViewModel extends AndroidViewModel {

    public MutableLiveData<MentorEntity> mLiveMentor = new MutableLiveData<>();

    private AppRepository mRepository;

    private Executor executor = Executors.newSingleThreadExecutor();

    public MentorEditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadMentor(int mentorId) {
        executor.execute(() -> {
            MentorEntity mentor = mRepository.getMentorById(mentorId);
            mLiveMentor.postValue(mentor);
        });
    }

    public void saveMentor(int courseId, String firstName, String lastName, String phone, String email) {
        if (TextUtils.isEmpty(firstName.trim())) {
            return; // no saving blank first names
        }
        MentorEntity mentor = mLiveMentor.getValue();
        if (mentor == null) {
            mentor = new MentorEntity(courseId, firstName, lastName, phone, email);
        } else {
            mentor.setCourseId(courseId);
            mentor.setFirstName(firstName);
            mentor.setLastName(lastName);
            mentor.setPhoneNumber(phone);
            mentor.setEmail(email);
        }
        mRepository.saveMentor(mentor);
    }

    public void deleteMentor() {
        mRepository.deleteMentor(mLiveMentor.getValue());
    }


}
