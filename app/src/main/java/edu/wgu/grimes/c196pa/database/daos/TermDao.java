//*********************************************************************************
//  File:             TermDao.java
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
import androidx.room.Transaction;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.TermCompetencyUnitsTuple;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.database.entities.TermWithCourses;

/**
 * DAO for the terms table
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
@Dao
public interface TermDao {

    /**
     * Inserts / Updates the given entity
     *
     * @param term The term to save
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(TermEntity term);

    /**
     * Inserts / Updates the given list of entities
     *
     * @param term The terms to save
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<TermEntity> term);

    /**
     * Deletes the given entity
     *
     * @param term The term to delete
     */
    @Delete
    void delete(TermEntity term);

    /**
     * Deletes all rows from the table
     */
    @Query("delete from terms")
    void deleteAll();

    /**
     * Gets all terms (observable)
     *
     * @return An observable list of all terms
     */
    @Query("select * from terms")
    LiveData<List<TermEntity>> getAllTerms();

    /**
     * Gets a term by id
     *
     * @param termId The id of the term
     * @return The therm with the given id
     */
    @Query("select * from terms where term_id = :termId")
    TermEntity getTermById(int termId);

    /**
     * Gets the count of all terms
     *
     * @return A count of all terms
     */
    @Query("select count(*) from terms")
    Integer getCount();

    /**
     * Gets a term pojo with a list of ossociated courses
     *
     * @param termId The id of the term
     * @return TermWithCourses for the given term
     */
    @Transaction
    @Query("select * from terms where term_id = :termId")
    TermWithCourses getTermWithCourses(int termId);

    /**
     * Gets a list of (observable) Terms with the sum of competency units for each term
     *
     * @return An observable list of TermCusTuples
     */
    @Query("select term_id, sum(competencyUnits) \"cus\" from terms " +
            "join courses using (term_id) " +
            "group by term_id")
    LiveData<List<TermCompetencyUnitsTuple>> getTermCompetencyUnits();
}
