//*********************************************************************************
//  File:             AssessmentEntity.java
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

@Entity(tableName = "assessments")
public class AssessmentEntity implements HasId {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessment_id")
    private int id;
    @ColumnInfo(name = "course_id")
    private int courseId;
    private String type;
    private String title;
    private String status;
    private Date completionDate;
    private Date completionDateAlarm;

    @Ignore
    public AssessmentEntity(int courseId, String type, String title, String status, Date completionDate, Date completionDateAlarm) {
        this.courseId = courseId;
        this.type = type;
        this.title = title;
        this.status = status;
        this.completionDate = completionDate;
        this.completionDateAlarm = completionDateAlarm;
    }

    public AssessmentEntity(int id, int courseId, String type, String title, String status, Date completionDate, Date completionDateAlarm) {
        this.id = id;
        this.courseId = courseId;
        this.type = type;
        this.title = title;
        this.status = status;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", completionDate=" + completionDate +
                ", completionDateAlarm=" + completionDateAlarm +
                '}';
    }
}
