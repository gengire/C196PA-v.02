//*********************************************************************************
//  File:             DateUtils.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.utilities;

import java.util.Date;

/**
 * Date Utility functions
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class DateUtils {

    /**
     * Determines if the given dates are the same or not
     *
     * @param date1 The first date to compare
     * @param date2 The second date to compare
     * @return True if the dates are the same
     */
    public static boolean sameDate(final Date date1, final Date date2) {
        boolean same = date1 == null && date2 == null;
//        if ((date1 != null && date2 == null) || (date1 == null && date2 != null)) {
//            same = false;
//        } else if (!same) {
//            same = date1.getTime() == date2.getTime();
//        }
        if (!same) {
            same = date1.getTime() == date2.getTime();
        }
        return same;
    }
}
