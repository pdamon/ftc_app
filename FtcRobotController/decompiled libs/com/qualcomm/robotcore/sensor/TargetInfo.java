/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.sensor;

import com.qualcomm.robotcore.sensor.TargetSize;
import com.qualcomm.robotcore.util.Pose;

public class TargetInfo {
    public String mTargetName;
    public Pose mTargetPose;
    public TargetSize mTargetSize;

    public TargetInfo() {
    }

    public TargetInfo(String targetName, Pose targetPose, TargetSize targetSize) {
        this.mTargetName = targetName;
        this.mTargetPose = targetPose;
        this.mTargetSize = targetSize;
    }
}

