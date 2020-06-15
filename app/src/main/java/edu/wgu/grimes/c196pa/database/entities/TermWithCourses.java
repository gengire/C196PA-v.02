//*********************************************************************************
//  File:             TermWithCourses.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TermWithCourses {
    @Embedded
    public TermEntity term;
    @Relation(parentColumn = "term_id",
            entityColumn = "term_id")
    public List<CourseEntity> courses;
}
