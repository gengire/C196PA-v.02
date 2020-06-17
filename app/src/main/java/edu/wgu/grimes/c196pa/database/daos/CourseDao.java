//*********************************************************************************
//  File:             CourseDao.java
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

import edu.wgu.grimes.c196pa.database.entities.CourseEntity;

/**
 * DAO for the courses table
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
@Dao
public interface CourseDao {

    /**
     * Inserts / Updates the given entity
     *
     * @param course
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(CourseEntity course);

    /**
     * Inserts / Updates the given list of entities
     *
     * @param courses
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<CourseEntity> courses);

    /**
     * Deletes the given entity
     *
     * @param course
     */
    @Delete
    void delete(CourseEntity course);

    /**
     * Deletes all rows from the table
     */
    @Query("delete from courses")
    void deleteAll();

    /**
     * Gets the courses (observable) associated with the given term id
     *
     * @param termId The identifier
     * @return
     */
    @Query("select * from courses where term_id = :termId")
    LiveData<List<CourseEntity>> getAllCoursesForTerm(int termId);

    /**
     * Gets a course by course id
     *
     * @param courseId
     * @return
     */
    @Query("select * from courses where course_id = :courseId")
    CourseEntity getCourseById(int courseId);

    /**
     * Gets the courses (observable) of courses with the given status
     *
     * @param status
     * @return
     */
    @Query("select count(*) from courses where status = :status")
    LiveData<Integer> getCoursesByStatus(String status);

    /**
     * Gets a count of all courses
     *
     * @return
     */
    @Query("select count(*) from courses")
    Integer getCount();

    /**
     * Gets a count (observable) of all course
     *
     * @return
     */
    @Query("select count(*) from courses")
    LiveData<Integer> getLiveCount();

}
