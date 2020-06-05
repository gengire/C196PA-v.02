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
