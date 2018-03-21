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
        if(text.contains("-")) {
            String[] parts = text.split("-");
            min = StringUtils.safeParseFloat(parts[0]);
            max = StringUtils.safeParseFloat(parts[1]);
        }
        else {
            min = max = StringUtils.safeParseFloat(text);
        }
    }

    @Override
    public String toString() {
        return String.format("%.1f-%.1f", min, max);
    }

    public boolean contains(int val) {
        return min <= val && val <= max;
    }

    public void minRoundTo(int d) {
        int k = (int)(min / d);
        min = k * d;
    }

    public void setMax(float max) {
        this.max = max;
    }
}
