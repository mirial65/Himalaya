package com.example.himalaya.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Description :
 * Time : 2020/7/26 16:50
 * Author : zengkun
 */
public class DateFormatUtil {
    public static String formatMin(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return sdf.format(time);
    }

    public static String formatHour(long time) {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return sdf.format(new Date(time));
    }

}