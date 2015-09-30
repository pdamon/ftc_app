/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.sensor;

public class TargetSize {
    public String mTargetName;
    public double mLongSide;
    public double mShortSide;

    public TargetSize() {
        this("", 0.0, 0.0);
    }

    public TargetSize(String targetName, double longSide, double shortSide) {
        this.mTargetName = targetName;
        this.mLongSide = longSide;
        this.mShortSide = shortSide;
    }
}

