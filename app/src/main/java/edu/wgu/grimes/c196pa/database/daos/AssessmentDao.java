//*********************************************************************************
//  File:             AssessmentDao.java
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

import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;

/**
 * DAO for the assessments table
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
@Dao
public interface AssessmentDao {

    /**
     * Inserts / Updates the given entity
     *
     * @param course
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(AssessmentEntity course);

    /**
     * Inserts / Updates the given list of entities
     *
     * @param sampleAssessments
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<AssessmentEntity> sampleAssessments);

    /**
     * Deletes the given entity
     *
      * @param course
     */
    @Delete
    void delete(AssessmentEntity course);

    /**
     * Deletes all rows from the table
     */
    @Query("delete from assessments")
    void deleteAll();

    /**
     * Gets the assessments (observable) associated to the given course id
     *
     * @param courseId The identifier of the course we want the assessments for
     * @return Live Data list of assessments associated to the given course id
     */
    @Query("select * from assessments where course_id = :courseId")
    LiveData<List<AssessmentEntity>> getAllAssessmentsForCourse(int courseId);

    /**
     * Gets an assessment by assessment id
     *
     * @param assessmentId The identifier of the assessment we want
     * @return The assessment with the given assessment id
     */
    @Query("select * from assessments where assessment_id = :assessmentId")
    AssessmentEntity getAssessmentById(int assessmentId);

    /**
     * Gets a count (observable) of the assessments that have the given status
     *
     * @param status The status of the assessments we want
     * @return Live Data count of the assessments with the given status
     */
    @Query("select count(*) from assessments where status = :status")
    LiveData<Integer> getAssessmentsByStatus(String status);

    /**
     * Deletes the assessments associated to the given course id
     *
     * @param courseId
     */
    @Query("delete from assessments where course_id = :courseId")
    void deleteAssessmentsForCourse(int courseId);

    /**
     * Gets a count (observable) of all the assessments
     * @return
     */
    @Query("select count(*) from assessments")
    LiveData<Integer> getLiveCount();

}
