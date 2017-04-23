package com.tml.libs.cutils;

/**
 * Created by ManhLinh on 4/23/2017.
 */

public class LoggableClass {
    public void D(String msg) {
        StaticLogger.D(this, msg);
    }

    public void E(String msg) {
        StaticLogger.E(this, msg);
    }
}
