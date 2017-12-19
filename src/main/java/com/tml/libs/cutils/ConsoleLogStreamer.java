package com.tml.libs.cutils;

/**
 * Created by TML on 19/12/2017.
 */

public class ConsoleLogStreamer implements StaticLogger.LogStreamer {

    @Override
    public void D(String appTag, String className, String msg) {
        System.out.println("D/ [" + appTag + "] - " + className + "::" + msg);
    }

    @Override
    public void E(String appTag, String className, String msg) {
        System.out.println("E/ [" + appTag + "] - " + className + "::" + msg);
    }

    @Override
    public void E(String appTag, String className, String msg, Exception ex) {
        System.out.println("E/ [" + appTag + "] - " + className + "::" + msg + " EXCEPTION: " + ex.getMessage());
        ex.printStackTrace();
    }

    @Override
    public void W(String appTag, String className, String msg) {
        System.out.println("W/ [" + appTag + "] - " + className + "::" + msg);
    }

    @Override
    public void I(String appTag, String className, String msg) {
        System.out.println("I/ [" + appTag + "] - " + className + "::" + msg);
    }
}
