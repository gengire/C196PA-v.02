package edu.wgu.grimes.c196pa.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static edu.wgu.grimes.c196pa.utilities.Constants.DATE_PATTERN;

public class StringUtils {

    public static String getFormattedDate(int month, int dayOfMonth, int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        return getFormattedDate(c.getTime());
    }

    public static String getFormattedDate(Date date) {
        return getFormattedDate(DATE_PATTERN, date);
    }

    public static String getFormattedDate(String pattern, Date date) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        String formatted = formatter.format(date);
        return formatted;
    }

    public static Date getDate(String format, String dateText) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDate(String dateText) {
        return getDate(DATE_PATTERN, dateText);
    }

}
