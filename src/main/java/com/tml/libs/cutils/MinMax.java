package com.tml.libs.cutils;

/**
 * Created by TML on 10/02/2017.
 */

public class MinMax {
    public float min;
    public float max;
    public MinMax(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public void set(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public float getRangeSize() {
        return max - min;
    }

    public float getRandomValueInRange() {
        return MathUtils.clamp(min + MathUtils.getRand().nextFloat() * getRangeSize(), min, max);
    }

    /**
     *
     * @param text = min-max
     *             <br/>example: 6-7
     */
    public void set(String text) {
        String[] parts = text.split("-");
        min = Float.parseFloat( parts[0]);
        max = Float.parseFloat( parts[1]);
    }
}
