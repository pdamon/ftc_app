/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.util;

import com.qualcomm.robotcore.util.RobotLog;

public class CurvedWheelMotion {
    public static int velocityForRotationMmPerSec(int rotateAroundXInMM, int rotateAroundYInMM, double rotationalVelocityInDegPerSec, int wheelOffsetXInMm, int wheelOffsetYInMm) {
        int n = (int)Math.sqrt(Math.pow(wheelOffsetXInMm - rotateAroundXInMM, 2.0) + Math.pow(wheelOffsetYInMm - rotateAroundYInMM, 2.0));
        int n2 = (int)(rotationalVelocityInDegPerSec * (6.283185307179586 * (double)n / 360.0));
        RobotLog.d("CurvedWheelMotion rX " + rotateAroundXInMM + ", theta " + rotationalVelocityInDegPerSec + ", velocity " + n2);
        return n2;
    }

    public static int getDiffDriveRobotWheelVelocity(int linearVelocityInMmPerSec, double rotationalVelocityInDegPerSec, int wheelRadiusInMm, int axleLengthInMm, boolean leftWheel) {
        double d = Math.toRadians(rotationalVelocityInDegPerSec);
        double d2 = leftWheel ? ((double)(2 * linearVelocityInMmPerSec) - d * (double)axleLengthInMm) / (double)(2 * wheelRadiusInMm) : ((double)(2 * linearVelocityInMmPerSec) + d * (double)axleLengthInMm) / (double)(2 * wheelRadiusInMm);
        int n = (int)(d2 * (double)wheelRadiusInMm);
        return n;
    }

    public static int getDiffDriveRobotTransVelocity(int leftVelocityInMmPerSec, int rightVelocityInMmPerSec) {
        int n = (leftVelocityInMmPerSec + rightVelocityInMmPerSec) / 2;
        return n;
    }

    public static double getDiffDriveRobotRotVelocity(int leftVelocityInMmPerSec, int rightVelocityInMmPerSec, int axleLengthInMm) {
        double d = (rightVelocityInMmPerSec - leftVelocityInMmPerSec) / axleLengthInMm;
        double d2 = Math.toDegrees(d);
        return d2;
    }
}

