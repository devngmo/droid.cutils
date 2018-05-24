package com.tml.libs.cutils;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by TML on 17/02/2017.
 */

public class StringUtils {

    public static Calendar fromString(String text, String format, Locale locale) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        cal.setTime(sdf.parse(text));// all done
        return cal;
    }

    public static String getString(Calendar c, String format, Locale locale) {
        DateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        return df.format(c.getTime());
    }

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

    public static String encryptDESBase64(String password, String text) {
        try {
            DESKeySpec keySpec = new DESKeySpec(password.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] cleartext = text.getBytes("UTF8");

            Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread safe
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encodeToString(cipher.doFinal(cleartext),Base64.DEFAULT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return text;
    }

    public static String decryptDESBase64(String password, String text) {
        try {
            DESKeySpec keySpec = new DESKeySpec(password.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] encryptedBytes = Base64.decode(text, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decryptedData = cipher.doFinal(encryptedBytes);
            return new String(decryptedData);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return text;
    }

    public static String removeSpaceMoreThan(String src, int spaceLength) {
        String space = "";
        String result = src;
        for (int i = 0; i < spaceLength; i++) {
            space += " ";
        }
        while(result.indexOf(space + " ") > 0) {
            result = result.replaceAll(space + " ", space);
        }
        return result;
    }
    public static String getString(JSONObject obj, String key, String defaultValue) throws JSONException {
        if (obj.has(key))
            return obj.getString(key);
        return defaultValue;
    }

    public static int getInt(JSONObject obj, String key, int value) throws JSONException {
        if (obj.has(key))
            return obj.getInt(key);
        return value;
    }

    public static Date stringToDate(String str, String format) {
        if(str==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        Date stringDate = simpledateformat.parse(str, pos);
        return stringDate;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTime(date);
        return cal;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return Math.abs(diff);
    }

    public static float safeParseFloat(String text) {
        try {
            return Float.parseFloat(text);
        }
        catch (Exception ex){
            //StaticLogger.E("can not parse float " + text, ex);
            return 0;
        }
    }
    public static int safeParseInt(String text) {
        try {
            return Integer.parseInt(text);
        }
        catch (Exception ex){
            //StaticLogger.E("can not parse INT " + text, ex);
            return 0;
        }
    }
    public static Date getTimeFromString(String format, String timeStr) throws ParseException {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        return simpledateformat.parse(timeStr);
    }

    /**
     * Default First day of week is SUNDAY
     * @param yyyymmdd
     * @param weekFirstDayIsMonday
     * @return
     */
    public static int getWeekOfYYYYMMDD(String yyyymmdd, boolean weekFirstDayIsMonday) {
        Locale locale = Locale.US; // Sunday first
        if (weekFirstDayIsMonday)
            locale = Locale.GERMANY; // Monday First

        Calendar c = Calendar.getInstance(locale);
        int yyyy = Integer.parseInt( yyyymmdd.substring(0, 4) );
        int mm = Integer.parseInt( yyyymmdd.substring(4, 6) );
        int dd = Integer.parseInt( yyyymmdd.substring(6, 8) );
        c.set(yyyy,mm-1,dd);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getWeekOfDay(Calendar c, boolean weekFirstDayIsMonday) {
        Locale locale = Locale.US; // Sunday first
        if (weekFirstDayIsMonday)
            locale = Locale.GERMANY; // Monday First

        Calendar b = Calendar.getInstance(locale);
        b.setTime(c.getTime());
        return b.get(Calendar.WEEK_OF_YEAR);
    }
}
