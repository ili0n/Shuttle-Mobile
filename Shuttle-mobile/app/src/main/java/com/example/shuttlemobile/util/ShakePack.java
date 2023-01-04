package com.example.shuttlemobile.util;

public class ShakePack {
    private float lastAcc = 9f;
    private float currAcc = 9f;
    private float acc = 10f;
    private float threshold;

    public ShakePack(float threshold) {
        this.threshold = threshold;
    }

    public void update(float[] acceleration) {
        final float x = acceleration[0];
        final float y = acceleration[1];
        final float z = acceleration[2];
        lastAcc = currAcc;
        currAcc = (float)Math.sqrt(x*x + y*y + z*z);
        final float delta = currAcc - lastAcc;
        acc = acc * 0.9f + delta;
    }

    public boolean isShaking() {
        return Math.abs(acc) > threshold;
    }
}
