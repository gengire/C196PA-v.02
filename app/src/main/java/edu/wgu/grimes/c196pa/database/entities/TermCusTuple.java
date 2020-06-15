//*********************************************************************************
//  File:             TermCusTuple.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.database.entities;

import androidx.room.ColumnInfo;

public class TermCusTuple {
    @ColumnInfo(name = "term_id")
    public int termId;

    @ColumnInfo(name = "cus")
    public int cus;
}
