//*********************************************************************************
//  File:             MentorEditorViewModel.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import edu.wgu.grimes.c196pa.database.entities.MentorEntity;

public class MentorEditorViewModel extends BaseViewModel {

    public MutableLiveData<MentorEntity> mLiveMentor = new MutableLiveData<>();

    public MentorEditorViewModel(@NonNull Application application) {
        super(application);
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
