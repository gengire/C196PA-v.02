//*********************************************************************************
//  File:             NoteDao.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.NoteEntity;

/**
 * DAO for the notes table
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
@Dao
public interface NoteDao {

    /**
     * Inserts / Updates the given entity
     *
     * @param note The note to save
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(NoteEntity note);

    /**
     * Inserts / Updates the given list of entities
     *
     * @param notes The notes to save
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<NoteEntity> notes);

    /**
     * Deletes the given entity
     *
     * @param note The note to delete
     */
    @Delete
    void delete(NoteEntity note);

    /**
     * Deletes all rows from the table
     */
    @Query("delete from notes")
    void deleteAll();

    /**
     * Deletes all the notes associated to a given course id
     *
     * @param courseId The id of the course to delete the notes from
     */
    @Query("delete from notes where course_id = :courseId")
    void deleteNotesForCourse(int courseId);

    /**
     * Gets a note by the given id
     *
     * @param noteId The id of the note
     * @return The note with the given id
     */
    @Query("select * from notes where note_id = :noteId")
    NoteEntity getNoteById(int noteId);

    /**
     * Gets the notes (observable) associated to the given course id
     *
     * @param courseId The id of the course
     * @return An observable list of notes associated to the given course id
     */
    @Query("select * from notes where course_id = :courseId")
    LiveData<List<NoteEntity>> getNotesForCourse(int courseId);

    /**
     * Gets the count of all notes
     *
     * @return A count of all notes
     */
    @Query("select count(*) from notes")
    int getCount();

}
