//*********************************************************************************
//  File:             HasId.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.database.entities;

/**
 * Used as a base type for handling entities that are clicked in a recycler view
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public interface HasId {
    /**
     * Gets the primary key of the entity
     *
     * @return The id field value
     */
    int getId();
}
