/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.sensor;

import com.qualcomm.robotcore.sensor.TargetInfo;

public class TrackedTargetInfo {
    public TargetInfo mTargetInfo;
    public double mConfidence;
    public long mTimeTracked;

    public TrackedTargetInfo(TargetInfo targetInfo, double reProjectionError, long timeTracked) {
        this.mTargetInfo = targetInfo;
        this.mConfidence = reProjectionError;
        this.mTimeTracked = timeTracked;
    }
}

