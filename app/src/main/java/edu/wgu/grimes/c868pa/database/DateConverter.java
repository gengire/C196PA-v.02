//*********************************************************************************
//  File:             DateConverter.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.database;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * The database doesn't have a Date time so we need to Tell Room how to convert the Date
 * objects into Longs to store in the database and also back into dates when selecting.
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

