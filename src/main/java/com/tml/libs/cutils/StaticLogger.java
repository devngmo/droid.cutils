package com.tml.libs.cutils;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManhLinh on 4/23/2017.
 */

public class StaticLogger {
    public interface LogStreamer {
        void D(String appTag, String className, String msg);
        void E(String appTag, String className, String msg);
        void E(String appTag, String className, String msg, Exception ex);
        void W(String appTag, String className, String msg);
        void I(String appTag, String className, String msg);
    }

    static LogStreamer logStreamer;

    public static void setLogStreamer(LogStreamer streamer) {
        logStreamer = streamer;
    }

    public static LogStreamer getLogStreamer() {
        return logStreamer;
    }

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
            logStreamer.D(curAppTag, clsName, msg);
        }
    }

    public static void W(Object sender, String msg) {
        String clsName = sender.getClass().getSimpleName();
        if (enableLogClasses.contains(clsName)) {
            logStreamer.W(curAppTag, clsName, msg);
        }
    }
    public static void W(String tag, String msg) {
        logStreamer.W(curAppTag, tag, msg);
    }
    public static void W(String msg) {
        logStreamer.W(curAppTag, "", msg);
    }

    public static void I(Object sender, String msg) {
        String clsName = sender.getClass().getSimpleName();
        if (enableLogClasses.contains(clsName)) {
            logStreamer.I(curAppTag, clsName, msg);
        }
    }

    public static void D(String className, String msg) {
        if (enableLogClasses.contains(className)) {
            logStreamer.D(curAppTag, className, msg);
            //System.out.println(className + "::" + msg);
        }
    }

    public static void E(Object sender, String msg) {
        String clsName = sender.getClass().getSimpleName();
        if (logErrorOnAnyClasses) {
            logStreamer.E(curAppTag, clsName, msg);
        }
    }

    public static void E(String msg) {
        if (logErrorOnAnyClasses) {
            logStreamer.E(curAppTag, "", msg);
        }
    }

    public static void E(Exception ex) {
        if (logErrorOnAnyClasses) {
            logStreamer.E(curAppTag, "", "", ex);
        }
    }

    public static void E(String msg, Exception ex) {
        if (logErrorOnAnyClasses) {
            logStreamer.E(curAppTag, "", msg, ex);
        }
    }

    public static void E(Object sender, String msg, Exception ex) {
        String clsName = sender.getClass().getSimpleName();
        if (logErrorOnAnyClasses) {
            logStreamer.E(curAppTag, clsName, msg, ex);
        }
    }

    public static void E(String clsName, String msg) {
        if (logErrorOnAnyClasses) {
            logStreamer.E(curAppTag, clsName, msg);
        }
        else if (enableLogClasses.contains(clsName))
            logStreamer.E(curAppTag, clsName, msg);


    }

    public static void D(String msg) {
        if (curAppTag != null)
            logStreamer.D(curAppTag, "", msg);
    }
}
