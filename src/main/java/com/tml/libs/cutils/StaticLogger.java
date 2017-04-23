package com.tml.libs.cutils;

import android.util.Log;

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
        enableLogClasses.add( obj.getClass().getName() );
    }

    public static void enableLog(String className) {
        enableLogClasses.add( className );
    }

    public static void D(Object sender, String msg) {
        String clsName = sender.getClass().getName();
        if (enableLogClasses.contains(clsName)) {
            Log.d(curAppTag, clsName + "::" + msg);
        }
    }

    public static void D(String className, String msg) {
        if (enableLogClasses.contains(className)) {
            Log.d(curAppTag, className + "::" + msg);
        }
    }

    public static void E(Object sender, String msg) {
        String clsName = sender.getClass().getName();
        if (logErrorOnAnyClasses) {
            Log.e(curAppTag, clsName + "::" + msg);
        }
    }
}
