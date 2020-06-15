//*********************************************************************************
//  File:             DateUtils.java
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

public class DateUtils {

    public static boolean sameDate(final Date d1, final Date d2) {
        boolean same = d1 == null && d2 == null;
        if ((d1 != null && d2 == null) || (d1 == null && d2 != null)) {
            same = false;
        } else if (!same) {
            same = d1.getTime() == d2.getTime();
        }
        return same;
    }
}
