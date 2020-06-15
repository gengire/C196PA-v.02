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

public class NotesListViewModel extends BaseViewModel {

    private LiveData<List<NoteEntity>> allNotes;

    public NotesListViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadCoursesNotes(int courseId) {
        allNotes = mRepository.getNotesForCourse(courseId);
    }

    public LiveData<List<NoteEntity>> getCourseNotes() {
        return allNotes;
    }

    public void deleteNote(NoteEntity note) {
        mRepository.deleteNote(note);
    }

}
