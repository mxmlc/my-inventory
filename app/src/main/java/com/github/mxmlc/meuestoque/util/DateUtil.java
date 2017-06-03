package com.github.mxmlc.meuestoque.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fabiano on 02/06/17.
 */
public class DateUtil {

    public static String formatDate(Date date) {
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
    }

    public static String formatDate(String dateStr) {
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(parseDate(dateStr));
    }

    public static Date parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

}
