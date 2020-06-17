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
     * @param note
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(NoteEntity note);

    /**
     * Inserts / Updates the given list of entities
     *
     * @param notes
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<NoteEntity> notes);

    /**
     * Deletes the given entity
     *
     * @param note
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
     * @param courseId
     */
    @Query("delete from notes where course_id = :courseId")
    void deleteNotesForCourse(int courseId);

    /**
     * Gets a note by the given id
     *
     * @param noteId
     * @return
     */
    @Query("select * from notes where note_id = :noteId")
    NoteEntity getNoteById(int noteId);

    /**
     * Gets the notes (observable) associated to the given course id
     *
     * @param courseId
     * @return
     */
    @Query("select * from notes where course_id = :courseId")
    LiveData<List<NoteEntity>> getNotesForCourse(int courseId);

    /**
     * Gets the count of all notes
     *
     * @return
     */
    @Query("select count(*) from notes")
    int getCount();

}
