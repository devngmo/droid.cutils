package com.tml.libs.cutils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TML on 17/02/2017.
 */

public class AppUtils {
    private static final String TAG = "AppUtils";
    public static final int REQUEST_CODE_REQUEST_PERMISSION = 5000;

    /**
     *
     * @param a
     * @param permissions
     * @return True if no permission is required
     */
    public static boolean askForPermissionIfNeed(Activity a, String[] permissions) {
        List<String> requirePermissions = new ArrayList<>();
        for (String p : permissions
             ) {
            if (ContextCompat.checkSelfPermission(a,
                    p) != PackageManager.PERMISSION_GRANTED) {
                Log.w(TAG, "Permission : " + p + " NOT GRANTED!!!");
                requirePermissions.add(p);
            }
        }
        if (requirePermissions.size() == 0)
            return  true;

        if (ActivityCompat.shouldShowRequestPermissionRationale(a,
                requirePermissions.get(0))) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.

            ActivityCompat.requestPermissions(a,
                    permissions,
                    REQUEST_CODE_REQUEST_PERMISSION);
//
        } else {

            // No explanation needed, we can request the permission.

            Log.d(TAG, "ask for require permissions...");
            ActivityCompat.requestPermissions(a,
                    permissions,
                    REQUEST_CODE_REQUEST_PERMISSION);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
        return false;
    }

    public static boolean isAllPermissionGranted(int[] grantResults) {
        for (int result : grantResults
                ) {
            if (result != PackageManager.PERMISSION_GRANTED)
                return  false;
        }
        return true;
    }

    public static boolean isAllPermissionGranted(Context ctx, String[] permissions) {
        for (String permission : permissions
                ) {
            if (ContextCompat.checkSelfPermission(ctx, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    public static boolean isExternalStoragePermissionGranted(Activity a) {
        if (ContextCompat.checkSelfPermission(a, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)  return false;
        if (ContextCompat.checkSelfPermission(a, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)  return false;
        return true;
    }
}
