//*********************************************************************************
//  File:             HasDate.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.utilities;

import java.util.Date;

/**
 * Used as a base type for entities that have dates in the date picker
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public interface HasDate {

    Date getDate();
    void setDate(Date date);

}
