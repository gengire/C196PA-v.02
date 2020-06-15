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

public class NoteEditorViewModel extends BaseViewModel {

    public MutableLiveData<NoteEntity> mLiveNote = new MutableLiveData<>();

    public NoteEditorViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadNote(int noteId) {
        executor.execute(() -> {
            NoteEntity note = mRepository.getNoteById(noteId);
            mLiveNote.postValue(note);
        });
    }

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

    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
