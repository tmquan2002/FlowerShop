package com.example.flowershop.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_PATTERN);

    public static String datetime(Date date) {
        return dateFormat.format(date);
    }
}
