//*********************************************************************************
//  File:             AssessmentEntity.java
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
 * Entity for the assessments table
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
@Entity(tableName = "assessments")
public class AssessmentEntity implements HasId {
    /**
     * Assessment primary key
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessment_id")
    private int id;
    /**
     * Course foreign key
     */
    @ColumnInfo(name = "course_id")
    private int courseId;
    /**
     * Assessment type e.g. Performance, Objective
     */
    private String type;
    /**
     * Assessment title
     */
    private String assessmentTitle;
    /**
     * Assessment Status e.g. Passed, Failed, Pending
     */
    private String assessmentStatus;
    /**
     * Assessment completion / goal date.
     */
    private Date completionDate;
    /**
     * Assessment completion / goal date notification alarm
     */
    private Date completionDateAlarm;

    /**
     * Constructor for new assessments lacking primary key
     *
     * @param courseId The course id this assessment is associated with
     * @param type  The assessment type (performance or objective)
     * @param assessmentTitle The assessment assessmentTitle
     * @param assessmentStatus The assessment status
     * @param completionDate The assessment completion date
     * @param completionDateAlarm The assessment completion date alarm
     */
    @Ignore
    public AssessmentEntity(int courseId, String type, String assessmentTitle, String assessmentStatus, Date completionDate, Date completionDateAlarm) {
        this.courseId = courseId;
        this.type = type;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStatus = assessmentStatus;
        this.completionDate = completionDate;
        this.completionDateAlarm = completionDateAlarm;
    }

    /**
     * Constructor for updated assessments (or new with non generated primary key)
     * @param id The id of the assessment
     * @param courseId The course id this assessment is associated with
     * @param type  The assessment type (performance or objective)
     * @param assessmentTitle The assessment title
     * @param assessmentStatus The assessment status
     * @param completionDate The assessment completion date
     * @param completionDateAlarm The assessment completion date alarm
     */
    public AssessmentEntity(int id, int courseId, String type, String assessmentTitle, String assessmentStatus, Date completionDate, Date completionDateAlarm) {
        this.id = id;
        this.courseId = courseId;
        this.type = type;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStatus = assessmentStatus;
        this.completionDate = completionDate;
        this.completionDateAlarm = completionDateAlarm;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(String assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Date getCompletionDateAlarm() {
        return completionDateAlarm;
    }

    public void setCompletionDateAlarm(Date completionDateAlarm) {
        this.completionDateAlarm = completionDateAlarm;
    }

    @NonNull
    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", type='" + type + '\'' +
                ", title='" + assessmentTitle + '\'' +
                ", status='" + assessmentStatus + '\'' +
                ", completionDate=" + completionDate +
                ", completionDateAlarm=" + completionDateAlarm +
                '}';
    }
}
