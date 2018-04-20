package com.tml.libs.cutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TML on 4/26/2017.
 */

public class SysUtils {
    public static void forceShowSoftKeyboard(Context c)
    {
        InputMethodManager imm = (InputMethodManager)c.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    public static void hideSoftKeyboard(Activity a) {
        if(a.getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(a.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static Point getScreenSize(Activity c) {
        Display display = c.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return size;
    }

    public void showSoftKeyboard(Context c, View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        v.requestFocus();
        inputMethodManager.showSoftInput(v, 0);
    }

    public static void hideSoftKeyboard(Context c, IBinder binder)
    {
        InputMethodManager imm = (InputMethodManager)c.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binder, 0);
    }

    public static void openCameraTakePicture(Activity a, int requestCode) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        a.startActivityForResult(cameraIntent, requestCode);
    }

    public static String getPhoneNumber(Context c) {
        TelephonyManager telephony = (TelephonyManager) c.getSystemService(
                Context.TELEPHONY_SERVICE);
        try {
            String phoneNo = telephony.getLine1Number();
            if (phoneNo == null || phoneNo.length() < 1)
                phoneNo = "0";

            if (phoneNo.startsWith("+84"))
                phoneNo = "0" + phoneNo.substring(3);
            if (phoneNo.startsWith("+1"))
                phoneNo = "0" + phoneNo.substring(1);
            return phoneNo;
        }
        catch(Exception ex)
        {
            Log.e("SysUtils", "can not get Line 1 Number on this device!");
            ex.printStackTrace();
        }
        return "0";
    }

    public static String getIMEINumber(Context c) {
        TelephonyManager telephony = (TelephonyManager) c.getSystemService(
                Context.TELEPHONY_SERVICE);
        try {
            String imei = telephony.getDeviceId();
            if (imei == null || imei.length() < 1)
                imei = "0";

            return imei;
        }
        catch(Exception ex)
        {
            Log.e("SysUtils", "can not get IMEI on this device!");
            ex.printStackTrace();
        }
        return "0";
    }


    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }
    public static void safeSleep(long time)
    {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getPhoneSubscriberNumber(Context c) {
        TelephonyManager telephony = (TelephonyManager) c.getSystemService(
                Context.TELEPHONY_SERVICE);
        try {
            return telephony.getSubscriberId();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return "0";
    }

    public static boolean canWriteToExternalSD(Context context) {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        return mExternalStorageAvailable && mExternalStorageWriteable;
    }

    public static boolean canReadFileOnSD(Context context) {
        boolean mExternalStorageAvailable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = false;
        }
        return mExternalStorageAvailable;
    }


    static long lastPlayTimeStick = 0;
    static Ringtone ringtone = null;
    public static void playNotificationSound(Context context) {
        try {
            long d = System.currentTimeMillis() - lastPlayTimeStick;
            if (d < 500)
                return;

            lastPlayTimeStick = System.currentTimeMillis();
            boolean needCreateNewRingtone = ringtone == null;

            if (needCreateNewRingtone) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                ringtone = RingtoneManager.getRingtone(context, notification);
            }
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
