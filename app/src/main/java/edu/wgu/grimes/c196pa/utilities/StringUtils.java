package edu.wgu.grimes.c196pa.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtils {

    public static String getFormattedDate(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String formattedDate =
                (cal.get(Calendar.MONTH) + 1) + "/" +
                        cal.get(Calendar.DATE) + "/" +
                        cal.get(Calendar.YEAR);
        return formattedDate;
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
        return getDate("MM/dd/yyyy", dateText);
    }


}
