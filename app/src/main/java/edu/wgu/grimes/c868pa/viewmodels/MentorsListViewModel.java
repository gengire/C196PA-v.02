//*********************************************************************************
//  File:             MentorsListViewModel.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.wgu.grimes.c868pa.database.entities.MentorEntity;

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
     * @param application The context
     */
    public MentorsListViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Sets the mentors with the given course id as the observables for this list
     * @param courseId The id of the course to load the mentors for
     */
    public void loadCourseMentors(int courseId) {
        allMentors = mRepository.getMentorsForCourse(courseId);
    }

    /**
     * Forwards the request to delete the given mentor to the repo
     * @param mentor The mentor to delete
     */
    public void deleteMentor(MentorEntity mentor) {
        mRepository.deleteMentor(mentor);
    }

    /**
     * Returns the observable list of mentors
     * @return An observable list of mentors for the currently selected course
     */
    public LiveData<List<MentorEntity>> getCourseMentors() {
        return allMentors;
    }
}
