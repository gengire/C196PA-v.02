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

/**
 * View model for the mentor editor activity
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class MentorEditorViewModel extends BaseViewModel {

    /**
     * Observable mentor that notifies on update
     */
    public final MutableLiveData<MentorEntity> mLiveMentor = new MutableLiveData<>();

    /**
     * Constructor
     *
     * @param application The context
     */
    public MentorEditorViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Sets the mentor with the given id as the observable for this editor
     *
     * @param mentorId The mentor to load
     */
    public void loadMentor(int mentorId) {
        executor.execute(() -> {
            MentorEntity mentor = mRepository.getMentorById(mentorId);
            mLiveMentor.postValue(mentor);
        });
    }

    /**
     * Passes the data from the screen to the repo for persisting
     *
     * @param courseId The id of the course associated to this mentor
     * @param firstName The first name of the mentor
     * @param lastName The last name of the mentor
     * @param phone The phone number of the mentor
     * @param email The email address of the mentor
     */
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

    /**
     * Forwards the request to delete the currently observable mentor to the repo
     */
    public void deleteMentor() {
        mRepository.deleteMentor(mLiveMentor.getValue());
    }


}
