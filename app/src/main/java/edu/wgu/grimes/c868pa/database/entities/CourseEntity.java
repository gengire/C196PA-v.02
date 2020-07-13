//*********************************************************************************
//  File:             CourseEntity.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Entity for the courses table
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
@Entity(tableName = "courses")
public class CourseEntity implements HasId {

    /**
     * Course primary key
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    private int id;
    /**
     * Term foreign key
     */
    @ColumnInfo(name = "term_id")
    private int termId;
    /**
     * Course competency units
     */
    private int competencyUnits;
    /**
     * Course code
     */
    private String code;
    /**
     * Course title
     */
    private String courseTitle;
    /**
     * Course start date
     */
    private Date startDate;
    /**
     * Course start date notification alarm
     */
    private Date startDateAlarm;
    /**
     * Anticipated course end date
     */
    private Date endDate; // anticipated
    /**
     * Course end date notification alarm
     */
    private Date endDateAlarm;
    /**
     * Course status e.g. Complete, Dropped
     */
    private String courseStatus;

    /**
     * Constructor for new course lacking primary key
     *
     * @param termId The term id this course is associated with
     * @param competencyUnits   The term competency units
     * @param code The term code
     * @param courseTitle The term title
     * @param startDate The term start date
     * @param startDateAlarm The term start date alarm
     * @param endDate The term end date
     * @param endDateAlarm The term end date alarm
     * @param courseStatus The term status
     */
    @Ignore
    public CourseEntity(int termId, int competencyUnits, String code, String courseTitle, Date startDate, Date startDateAlarm, Date endDate, Date endDateAlarm, String courseStatus) {
        this.termId = termId;
        this.competencyUnits = competencyUnits;
        this.code = code;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.startDateAlarm = startDateAlarm;
        this.endDate = endDate;
        this.endDateAlarm = endDateAlarm;
        this.courseStatus = courseStatus;
    }

    /**
     * Constructor for updated courses (or new with non generated primary key)
     *
     * @param id The id of the term
     * @param termId The term id this course is associated with
     * @param competencyUnits   The term competency units
     * @param code The term code
     * @param courseTitle The term title
     * @param startDate The term start date
     * @param startDateAlarm The term start date alarm
     * @param endDate The term end date
     * @param endDateAlarm The term end date alarm
     * @param courseStatus The term status
     */
    public CourseEntity(int id, int termId, int competencyUnits, String code, String courseTitle, Date startDate, Date startDateAlarm, Date endDate, Date endDateAlarm, String courseStatus) {
        this.id = id;
        this.termId = termId;
        this.competencyUnits = competencyUnits;
        this.code = code;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.startDateAlarm = startDateAlarm;
        this.endDate = endDate;
        this.endDateAlarm = endDateAlarm;
        this.courseStatus = courseStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getCompetencyUnits() {
        return competencyUnits;
    }

    public void setCompetencyUnits(int competencyUnits) {
        this.competencyUnits = competencyUnits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDateAlarm() {
        return startDateAlarm;
    }

    public void setStartDateAlarm(Date startDateAlarm) {
        this.startDateAlarm = startDateAlarm;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDateAlarm() {
        return endDateAlarm;
    }

    public void setEndDateAlarm(Date endDateAlarm) {
        this.endDateAlarm = endDateAlarm;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    @NonNull
    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", termId=" + termId +
                ", competencyUnits=" + competencyUnits +
                ", code='" + code + '\'' +
                ", title='" + courseTitle + '\'' +
                ", startDate=" + startDate +
                ", startDateAlarm=" + startDateAlarm +
                ", endDate=" + endDate +
                ", endDateAlarm=" + endDateAlarm +
                ", status='" + courseStatus + '\'' +
                '}';
    }
}
