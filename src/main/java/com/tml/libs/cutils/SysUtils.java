package com.tml.libs.cutils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
//import androidx.core.content.ContextCompat;

/**
 * Created by TML on 4/26/2017.
 */

@SuppressWarnings("unused")
public class SysUtils {
    public static int getBatteryPercentage(Context context) {

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;

        return (int) (batteryPct * 100);
    }

    public static void forceShowSoftKeyboard(Context c)
    {
        InputMethodManager imm = (InputMethodManager)c.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    public static void hideSoftKeyboard(Activity a) {
        if(a.getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null)
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
        if (v != null)
            v.requestFocus();

        if (inputMethodManager != null)
            inputMethodManager.showSoftInput(v, 0);
    }

    public static void hideSoftKeyboard(Context c, IBinder binder)
    {
        InputMethodManager imm = (InputMethodManager)c.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(binder, 0);
    }

    public static void openCameraTakePicture(Activity a, int requestCode) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        a.startActivityForResult(cameraIntent, requestCode);
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getPhoneNumber(Context c) {
        if (ContextCompat.checkSelfPermission(c,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            StaticLogger.D("Permission : READ_PHONE_STATE NOT GRANTED!!!");
            return "0";
        }

        TelephonyManager telephony = (TelephonyManager) c.getSystemService(
                Context.TELEPHONY_SERVICE);
        try {
            String phoneNo = null;
            if (telephony != null) {
                phoneNo = telephony.getLine1Number();
            }
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

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getIMEINumber(Context c) {
        try {
            if (ContextCompat.checkSelfPermission(c,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                StaticLogger.D("Permission : READ_PHONE_STATE NOT GRANTED!!!");
                return "0";
            }
            TelephonyManager telephony = (TelephonyManager) c.getSystemService(
                    Context.TELEPHONY_SERVICE);
            String imei = null;
            if (telephony != null) {
                imei = telephony.getDeviceId();
            }
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

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getPhoneSubscriberNumber(Context c) {
        TelephonyManager telephony = (TelephonyManager) c.getSystemService(
                Context.TELEPHONY_SERVICE);
        try {
            if (telephony != null) {
                return telephony.getSubscriberId();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return "0";
    }

    public static boolean canWriteToExternalSD(Context context) {
        boolean mExternalStorageAvailable;
        boolean mExternalStorageWriteable;
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
        boolean mExternalStorageAvailable;
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
