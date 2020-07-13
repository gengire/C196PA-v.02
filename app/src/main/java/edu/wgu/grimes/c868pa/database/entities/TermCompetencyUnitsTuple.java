//*********************************************************************************
//  File:             TermCusTuple.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.database.entities;

import androidx.room.ColumnInfo;

/**
 * This was me experimenting with what Room has to offer. I think now that this tuple was not
 * necessary but it was worth doing it this way for the educational benefit.
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class TermCompetencyUnitsTuple {
    @ColumnInfo(name = "term_id")
    public int termId;

    @ColumnInfo(name = "cus")
    public int cus;
}
