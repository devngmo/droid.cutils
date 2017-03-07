package com.tml.libs.cutils;

import android.util.Base64;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

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

}
