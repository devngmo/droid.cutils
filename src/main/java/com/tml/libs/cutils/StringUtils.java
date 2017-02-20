package com.tml.libs.cutils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by TML on 17/02/2017.
 */

public class StringUtils {
    public static String createDateTimeStr_yyyy_MM_dd_HH_mm_ss() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        return df.format(c.getTime());
    }
    public static String createDateTime(String format, Locale locale) {
        DateFormat df = new SimpleDateFormat(format, locale);
        Calendar c = Calendar.getInstance();
        return df.format(c.getTime());
    }
}
