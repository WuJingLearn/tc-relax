package org.javaboy.tcrelax.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author:majin.wj
 */
public class DateUtils {


    public static Date formatDate(String dateStr) {
        try {
            return new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean between(Date now, Date start, Date end) {
        return now.after(start) && now.before(end);
    }

    public static void main(String[] args) {
        Date date = formatDate("2023-10-01 00:00:00");
        System.out.println(date);
    }

    public static String nowStr() {
        Calendar calendar = Calendar.getInstance();
        return  StringUtils.join(calendar.get(Calendar.YEAR),(calendar.get(Calendar.MONTH) + 1),calendar.get(Calendar.DATE),"");
    }

    public static String getFistDayOfWeak() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return StringUtils.join(calendar.get(Calendar.YEAR),(calendar.get(Calendar.MONTH) + 1),calendar.get(Calendar.DATE),"");
    }


}
