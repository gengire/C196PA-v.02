//*********************************************************************************
//  File:             NoteEntity.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Entity for the notes table
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
@Entity(tableName = "notes")
public class NoteEntity implements HasId {

    /**
     * Note primary key
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private int id;
    /**
     * Course foreign key
     */
    @ColumnInfo(name = "course_id")
    private int courseId;
    /**
     * Note title
     */
    private String title;
    /**
     * Note description
     */
    private String description;

    /**
     * Constructor for new note lacking primary key
     *
     * @param courseId The id of the course the note is associated with
     * @param title The title of the note
     * @param description The description of the note
     */
    @Ignore
    public NoteEntity(int courseId, String title, String description) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
    }

    /**
     * Constructor for updating notes (or new with non generated primary key)
     *
     * @param id The id of the note
     * @param courseId The id of the course the note is associated with
     * @param title The title of the note
     * @param description The description of the note
     */
    public NoteEntity(int id, int courseId, String title, String description) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", course_id=" + courseId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
