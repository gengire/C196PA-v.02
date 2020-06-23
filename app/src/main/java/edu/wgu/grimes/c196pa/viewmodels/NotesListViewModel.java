//*********************************************************************************
//  File:             NotesListViewModel.java
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

import edu.wgu.grimes.c196pa.database.entities.NoteEntity;

/**
 * View model for the notes list activity
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class NotesListViewModel extends BaseViewModel {

    /**
     * Observable list of notes
     */
    private LiveData<List<NoteEntity>> allNotes;

    /**
     * Constructor
     *
     * @param application The context
     */
    public NotesListViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Sets the notes with the given course id as the observables for this list
     *
     * @param courseId The id of the course
     */
    public void loadCoursesNotes(int courseId) {
        allNotes = mRepository.getNotesForCourse(courseId);
    }

    /**
     * Returns the observable list of notes
     *
     * @return An observable list of note for the currently selected course
     */
    public LiveData<List<NoteEntity>> getCourseNotes() {
        return allNotes;
    }

    /**
     * Forward the request to delete the currently observable note to the repo
     *
     * @param note The note to delete
     */
    public void deleteNote(NoteEntity note) {
        mRepository.deleteNote(note);
    }

}
