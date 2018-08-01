package com.tml.libs.cutils;

import org.jetbrains.annotations.NotNull;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ManhLinh on 4/23/2017.
 */

public class StaticLogger {
    static HashMap<String, String> excludeMap = new HashMap<>();
    public static void addExclude(@NotNull String className, @NotNull String verboses) {
        if (excludeMap.containsKey(className)) {
            excludeMap.put(className, excludeMap.get(className) + verboses);
        }
    }

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

    public static boolean showInfo = true;
    public static boolean showDebug = true;
    public static boolean showAllClass = false;

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
        if (!isPrintable()) return;
        if (!showDebug) return;


        String clsName = "null";
        if (sender instanceof String)
            clsName = (String)sender;
        else if (sender != null)
            clsName = sender.getClass().getSimpleName();

        if (excludeMap.containsKey(clsName))
            if (excludeMap.get(clsName).contains("D")) return;

        if (showAllClass || enableLogClasses.contains(clsName)) {
                logStreamer.D(curAppTag, clsName, msg);
        }
    }

    public static void W(Object sender, String msg) {
        if (!isPrintable()) return;

        String clsName = "null";
        if (sender instanceof String)
            clsName = (String)sender;
        else if (sender != null)
            clsName = sender.getClass().getSimpleName();

        if (excludeMap.containsKey(clsName))
            if (excludeMap.get(clsName).contains("W")) return;

        if (showAllClass || enableLogClasses.contains(clsName)) {
            logStreamer.W(curAppTag, clsName, msg);
        }
    }
    public static void W(String tag, String msg) {
        if (!isPrintable()) return;

         logStreamer.W(curAppTag, tag, msg);
    }
    public static void W(String msg) {
        if (!isPrintable()) return;

         logStreamer.W(curAppTag, "", msg);
    }

    public static void I(Object sender, String msg) {
        if (!isPrintable()) return;
        if (!showInfo) return;

        String clsName = "null";
        if (sender instanceof String)
            clsName = (String)sender;
        else if (sender != null)
            clsName = sender.getClass().getSimpleName();

        if (excludeMap.containsKey(clsName))
            if (excludeMap.get(clsName).contains("I")) return;

        if (showAllClass || enableLogClasses.contains(clsName)) {
             logStreamer.I(curAppTag, clsName, msg);
        }
    }

    public static void D(String className, String msg) {
        if (!isPrintable()) return;
        if (!showDebug) return;

        if (excludeMap.containsKey(className))
            if (excludeMap.get(className).contains("D")) return;

        if (showAllClass || enableLogClasses.contains(className)) {
             logStreamer.D(curAppTag, className, msg);
            //System.out.println(className + "::" + msg);
        }
    }

    public static void E(Object sender, String msg) {
        if (!isPrintable()) return;

        String clsName = "null";
        if (sender instanceof String)
            clsName = (String)sender;
        else if (sender != null)
            clsName = sender.getClass().getSimpleName();

        if (excludeMap.containsKey(clsName))
            if (excludeMap.get(clsName).contains("E")) return;

        if (logErrorOnAnyClasses) {
             logStreamer.E(curAppTag, clsName, msg);
        }
    }

    public static void E(String msg) {
        if (!isPrintable()) return;

        if (logErrorOnAnyClasses) {
            logStreamer.E(curAppTag, "", msg);
        }
    }

    public static void E(Exception ex) {
        if (!isPrintable()) return;


        if (logErrorOnAnyClasses) {
            logStreamer.E(curAppTag, "", "", ex);
        }
    }

    public static void E(String msg, Exception ex) {
        if (!isPrintable()) return;

        if (logErrorOnAnyClasses) {
            logStreamer.E(curAppTag, "", msg, ex);
        }
    }

    public static void E(Object sender, String msg, Exception ex) {
        if (!isPrintable()) return;

        String clsName = "null";
        if (sender != null)
            clsName = sender.getClass().getSimpleName();

        if (excludeMap.containsKey(clsName))
            if (excludeMap.get(clsName).contains("E")) return;

        if (logErrorOnAnyClasses) {
            logStreamer.E(curAppTag, clsName, msg, ex);
        }
    }

    public static void E(String clsName, String msg) {
        if (!isPrintable()) return;

        if (logErrorOnAnyClasses) {
            logStreamer.E(curAppTag, clsName, msg);
        }
        else if (enableLogClasses.contains(clsName))
            logStreamer.E(curAppTag, clsName, msg);
    }

    public static boolean isPrintable() {
        return (curAppTag != null) && (logStreamer != null);
    }
    public static void D(String msg) {
        if (!isPrintable()) return;
        if (!showDebug) return;


        logStreamer.D(curAppTag, "", msg);
    }
}
