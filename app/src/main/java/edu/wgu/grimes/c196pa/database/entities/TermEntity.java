//*********************************************************************************
//  File:             TermEntity.java
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

import java.util.Date;

/**
 * Entity for the terms table
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
@Entity(tableName = "terms")
public class TermEntity implements HasId {

    /**
     * Term primary key
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "term_id")
    private int id;
    /**
     * Term title
     */
    private String title;
    /**
     * Term start date
     */
    private Date startDate;
    /**
     * Term end date
     */
    private Date endDate;

    /**
     * Constructor for new term lacking primary key
     *
     * @param title The title of the term
     * @param startDate The start date of the term
     * @param endDate The end date of the term
     */
    @Ignore
    public TermEntity(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructor for updating terms (or new terms with non generated primary key)
     *
     * @param id The id of the term
     * @param title The title of the term
     * @param startDate The start date of the term
     * @param endDate The end date of the term     */
    public TermEntity(int id, String title, Date startDate, Date endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "TermEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
