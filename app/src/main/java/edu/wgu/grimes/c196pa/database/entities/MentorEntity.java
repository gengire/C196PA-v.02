//*********************************************************************************
//  File:             MentorEntity.java
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
 * Entity for the mentors table
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
@Entity(tableName = "mentors")
public class MentorEntity implements HasId {
    /**
     * Mentor primary key
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mentor_id")
    private int id;
    /**
     * Course foreign key
     */
    @ColumnInfo(name = "course_id")
    private int courseId;
    /**
     * First name of the mentor
     */
    private String firstName;
    /**
     * Last name of the mentor
     */
    private String lastName;
    /**
     * Phone number of the mentor
     */
    private String phoneNumber;
    /**
     * Email of the mentor
     */
    private String email;

    /**
     * Constructor for new mentor lacking primary key
     *
     * @param courseId
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param email
     */
    @Ignore
    public MentorEntity(int courseId, String firstName, String lastName, String phoneNumber, String email) {
        this.courseId = courseId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Constructor for updating mentors (or new with non generated primary key)
     *
     * @param id
     * @param courseId
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param email
     */
    public MentorEntity(int id, int courseId, String firstName, String lastName, String phoneNumber, String email) {
        this.id = id;
        this.courseId = courseId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return "MentorEntity{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
