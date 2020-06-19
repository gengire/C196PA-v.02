//*********************************************************************************
//  File:             MentorsListViewModel.java
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

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.MentorEntity;

/**
 * View model for the mentors list activity
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class MentorsListViewModel extends BaseViewModel {

    /**
     * Observable list of mentors
     */
    private LiveData<List<MentorEntity>> allMentors;

    /**
     * Constructor
     *
     * @param application
     */
    public MentorsListViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Sets the mentors with the given course id as the observables for this list
     * @param courseId
     */
    public void loadCourseMentors(int courseId) {
        allMentors = mRepository.getMentorsForCourse(courseId);
    }

    /**
     * Forwards the request to delete the given mentor to the repo
     * @param mentor
     */
    public void deleteMentor(MentorEntity mentor) {
        mRepository.deleteMentor(mentor);
    }

    /**
     * Returns the observable list of mentors
     * @return
     */
    public LiveData<List<MentorEntity>> getCourseMentors() {
        return allMentors;
    }
}
