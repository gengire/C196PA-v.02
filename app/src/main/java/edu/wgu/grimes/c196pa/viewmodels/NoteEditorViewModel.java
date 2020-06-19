//*********************************************************************************
//  File:             NoteEditorViewModel.java
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

import edu.wgu.grimes.c196pa.database.entities.NoteEntity;

/**
 * View model for the note editor activity
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class NoteEditorViewModel extends BaseViewModel {

    /**
     * Observable note that notifies on updates
     */
    public MutableLiveData<NoteEntity> mLiveNote = new MutableLiveData<>();

    /**
     * Constructor
     *
     * @param application
     */
    public NoteEditorViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Sets the note with the given id as the observable for this editor
     *
     * @param noteId
     */
    public void loadNote(int noteId) {
        executor.execute(() -> {
            NoteEntity note = mRepository.getNoteById(noteId);
            mLiveNote.postValue(note);
        });
    }

    /**
     * Passes the data from the screen to the repo for persisting
     *
     * @param courseId
     * @param title
     * @param description
     */
    public void saveNote(int courseId, String title, String description) {
        if (TextUtils.isEmpty(title.trim())) {
            return; // no saving blank titles
        }
        NoteEntity note = mLiveNote.getValue();
        if (note == null) {
            note = new NoteEntity(courseId, title, description);
        } else {
            note.setCourseId(courseId);
            note.setTitle(title);
            note.setDescription(description);
        }
        mRepository.saveNote(note);
    }

    /**
     * Forwards the request to delete the currently observable note to the repo
     */
    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
