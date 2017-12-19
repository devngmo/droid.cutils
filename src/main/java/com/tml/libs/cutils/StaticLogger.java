package com.tml.libs.cutils;

import android.util.Log;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManhLinh on 4/23/2017.
 */

public class StaticLogger {
    static List<String> enableLogClasses = new ArrayList<>();
    static String curAppTag;
    public static boolean logErrorOnAnyClasses = true;
    public static void setAppTag(String tag) {
        curAppTag = tag;
    }
    public static void enableLog(Object obj) {
        enableLogClasses.add( obj.getClass().getSimpleName() );
    }

    public static void enableLog(String className) {
        if (enableLogClasses.contains(className)) return;
        enableLogClasses.add( className );
    }

    public static void D(Object sender, String msg) {
        String clsName = sender.getClass().getSimpleName();
        if (enableLogClasses.contains(clsName)) {
            Log.d(curAppTag, clsName + "::" + msg);
            //System.out.println(clsName + "::" + msg);
        }
    }

    public static void W(Object sender, String msg) {
        String clsName = sender.getClass().getSimpleName();
        if (enableLogClasses.contains(clsName)) {
            Log.w(curAppTag, clsName + "::" + msg);
        }
    }
    public static void W(String tag, String msg) {
        Log.w(curAppTag, tag + "::" + msg);
    }
    public static void W(String msg) {
        Log.w(curAppTag, msg);
    }

    public static void I(Object sender, String msg) {
        String clsName = sender.getClass().getSimpleName();
        if (enableLogClasses.contains(clsName)) {
            Log.i(curAppTag, clsName + "::" + msg);
        }
    }

    public static void D(String className, String msg) {
        if (enableLogClasses.contains(className)) {
            Log.d(curAppTag, className + "::" + msg);
            //System.out.println(className + "::" + msg);
        }
    }

    public static void E(Object sender, String msg) {
        String clsName = sender.getClass().getSimpleName();
        if (logErrorOnAnyClasses) {
            Log.e(curAppTag, clsName + "::" + msg);
        }
    }

    public static void E(String msg) {
        if (logErrorOnAnyClasses) {
            Log.e(curAppTag, msg);
        }
    }

    public static void E(Exception ex) {
        if (logErrorOnAnyClasses) {
            Log.e(curAppTag, ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void E(String msg, Exception ex) {
        if (logErrorOnAnyClasses) {
            Log.e(curAppTag, msg + " EXCEPTION MSG:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void E(Object sender, String msg, Exception ex) {
        String clsName = sender.getClass().getSimpleName();
        if (logErrorOnAnyClasses) {
            Log.e(curAppTag, clsName + "::" + msg);
            ex.printStackTrace();
        }
    }

    public static void E(String clsName, String msg) {
        if (logErrorOnAnyClasses) {
            Log.e(curAppTag, clsName + "::" + msg);
        }
        else if (enableLogClasses.contains(clsName))
            Log.e(curAppTag, clsName + "::" + msg);
    }

    public static void D(String msg) {
        if (curAppTag != null)
            Log.d(curAppTag, msg);
    }
}
