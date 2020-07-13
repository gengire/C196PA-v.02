//*********************************************************************************
//  File:             StringUtils.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static edu.wgu.grimes.c868pa.utilities.Constants.DATE_PATTERN;

/**
 * String utility methods
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class StringUtils {

    /**
     * Helper to convert a given month, day, and year into a formatted date string
     *
     * @param month The month
     * @param dayOfMonth The day
     * @param year The year
     * @return The string formatted version of the given monday/day/year
     */
    public static String getFormattedDate(int month, int dayOfMonth, int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        return getFormattedDate(c.getTime());
    }

    /**
     * Helper to convert a Date object into a formatted date string
     *
     * @param date The date
     * @return The string formatted version of the given date
     */
    public static String getFormattedDate(Date date) {
        return getFormattedDate(DATE_PATTERN, date);
    }

    /**
     * Helper to convert a Date object into a specific formatted date string
     *
     * @param pattern The pattern
     * @param date The date
     * @return The string formatted version of the given date
     */
    public static String getFormattedDate(String pattern, Date date) {
        DateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        return formatter.format(date);
    }

    /**
     * Helper to convert a given string into a date object
     *
     * @param format The format
     * @param dateText The date text
     * @return The date set to the given date text value
     */
    public static Date getDate(String format, String dateText) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format, Locale.getDefault()).parse(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Helper to convert a given string into a date object
     *
     * @param dateText The date text
     * @return The ate set to the given date text value
     */
    public static Date getDate(String dateText) {
        return getDate(DATE_PATTERN, dateText);
    }

}
