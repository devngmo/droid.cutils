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

    public void E(String msg, Exception ex) {
        StaticLogger.E(this, msg, ex);
    }

    public void W(String msg) {
        StaticLogger.W(this, msg);
    }

    public void I(String msg) {
        StaticLogger.I(this, msg);
    }
}
