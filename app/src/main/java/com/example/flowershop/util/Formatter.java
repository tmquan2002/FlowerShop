package com.example.flowershop.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Formatter {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_PATTERN);
    private static final SimpleDateFormat millisFormat = new SimpleDateFormat(Constant.TIME_PATTERN);

    public static String datetime(Date date) {
        return dateFormat.format(date);
    }

    public static String time(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return millisFormat.format(calendar.getTime());
    }
}
