//*********************************************************************************
//  File:             AssessmentDao.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.grimes.c868pa.database.entities.AssessmentEntity;

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
     * @param assessment The assessment to be saved
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(AssessmentEntity assessment);

    /**
     * Inserts / Updates the given list of entities
     *
     * @param assessments The assessments to be saved
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<AssessmentEntity> assessments);

    /**
     * Deletes the given entity
     *
     * @param assessment  The assessment to delete
     */
    @Delete
    void delete(AssessmentEntity assessment);

    /**
     * Deletes all rows from the table
     * @param accountId
     */
    @Query("delete from assessments " +
            "where course_id in (" +
                "select course_id from courses " +
                "where term_id in (" +
                    "select term_id from terms " +
                    "where account_id = :accountId" +
                ")" +
            ")")
    void deleteAll(int accountId);

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
     * @param accountId
     * @return Live Data count of the assessments with the given status
     */
    @Query("select count(*) from assessments a " +
            "join courses c using (course_id) " +
            "join terms t using (term_id) " +
            "where assessmentStatus = :status " +
            "and account_id = :accountId")
    LiveData<Integer> getAssessmentsByStatus(String status, int accountId);

    /**
     * Deletes the assessments associated to the given course id
     *
     * @param courseId The id of the course
     */
    @Query("delete from assessments where course_id = :courseId")
    void deleteAssessmentsForCourse(int courseId);

    /**
     * Gets a count (observable) of all the assessments
     *
     * @return An Observable count of all assessments
     * @param accountId
     */
    @Query("select count(*) from assessments a " +
            "join courses c using (course_id) " +
            "join terms t using (term_id) " +
            "where account_id = :accountId")
    LiveData<Integer> getLiveCount(int accountId);

}
