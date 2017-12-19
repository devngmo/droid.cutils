package com.tml.libs.cutils;

import android.util.Log;

/**
 * Created by TML on 19/12/2017.
 */

public class LogcatStreamer implements StaticLogger.LogStreamer {
    @Override
    public void D(String appTag, String className, String msg) {
        Log.d(appTag, className + "::" + msg);
    }

    @Override
    public void E(String appTag, String className, String msg) {
        Log.e(appTag, className + "::" + msg);
    }

    @Override
    public void E(String appTag, String className, String msg, Exception ex) {
        Log.e(appTag, className + "::" + msg + " EXCEPTION: " + ex.getMessage());
        ex.printStackTrace();
    }

    @Override
    public void W(String appTag, String className, String msg) {
        Log.w(appTag, className + "::" + msg);
    }

    @Override
    public void I(String appTag, String className, String msg) {
        Log.i(appTag, className + "::" + msg);
    }
}
