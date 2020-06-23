//*********************************************************************************
//  File:             MentorDao.java
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

import edu.wgu.grimes.c196pa.database.entities.MentorEntity;

/**
 * DAO for the mentors table
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
@Dao
public interface MentorDao {

    /**
     * Inserts / Updates the given entity
     *
     * @param mentor The mentor to save
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(MentorEntity mentor);

    /**
     * Inserts / Updates the given list of entities
     *
     * @param mentors The mentors to save
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<MentorEntity> mentors);

    /**
     * Deletes the given entity
     *
     * @param mentor The mentor to delete
     */
    @Delete
    void delete(MentorEntity mentor);

    /**
     * Deletes all rows from the table
     */
    @Query("delete from mentors")
    void deleteAll();

    /**
     * Gets the mentors (observable) associated with the given course id
     *
     * @param courseId The id of the course
     * @return An observable list of mentors for the given course
     */
    @Query("select * from mentors where course_id = :courseId")
    LiveData<List<MentorEntity>> getAllMentorsForCourse(int courseId);

    /**
     * Gets a mentor by mentor id
     *
     * @param mentorId The id of the mentor
     * @return The mentor with the given id
     */
    @Query("select * from mentors where mentor_id = :mentorId")
    MentorEntity getMentorById(int mentorId);

    /**
     * Gets the total count of mentors
     *
     * @return A count of all mentors
     */
    @Query("select count(*) from mentors")
    Integer getCount();

    /**
     * Deletes all mentors associated to a given course id
     *
     * @param courseId The id of the course to delete all the mentors from
     */
    @Query("delete from mentors where course_id = :courseId")
    void deleteMentorsForCourse(int courseId);
}
