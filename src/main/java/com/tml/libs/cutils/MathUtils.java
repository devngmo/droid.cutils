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
}
