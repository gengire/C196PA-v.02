//*********************************************************************************
//  File:             OnItemClickListener.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.adapters;

import edu.wgu.grimes.c196pa.database.entities.HasId;

public interface OnItemClickListener<T extends HasId> {
    void onItemClick(T entity);
}
