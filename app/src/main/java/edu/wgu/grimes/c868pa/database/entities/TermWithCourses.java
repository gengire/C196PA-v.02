//*********************************************************************************
//  File:             TermWithCourses.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * Class to relate courses to terms. Another experiment with Room that I think now I would do
 * differently. It works but I think next time I would just make a join query to get this data.
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class TermWithCourses {
    @Embedded
    public TermEntity term;
    @Relation(parentColumn = "term_id",
            entityColumn = "term_id")
    public List<CourseEntity> courses;
}
