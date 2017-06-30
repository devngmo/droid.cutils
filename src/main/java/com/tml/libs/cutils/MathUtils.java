package com.tml.libs.cutils;

import java.util.Random;

/**
 * Created by TML on 10/02/2017.
 */

public class MathUtils {
    static Random rand = new Random();

    public static Random getRand() {
        return rand;
    }

    private static final float CLAMP_EPSILON = 0.00001f;

    public static int clamp(int value, int min, int max) {
        if (value < min) return  min;
        if (value > max) return  max;
        return value;
    }

    public static float clamp(float value, float min, float max, float epsilon) {
        if (value < min-epsilon) return  min;
        if (value > max+epsilon) return  max;
        return value;
    }

    public static float clamp(float value, float min, float max) {
        return clamp(value, min, max, CLAMP_EPSILON);
    }

    public static float getHours(int millisecond) {
        return getMinutes(millisecond) / 60;
    }

    public static float getMinutes(int millisecond) {
        return getSeconds(millisecond) / 60;
    }

    public static float getSeconds(int millisecond) {
        return millisecond / 1000.0f;
    }

    public static int getSecondsOfHours(int hours) {
        return hours * 60 * 60;
    }
}
