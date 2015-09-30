/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.qualcomm.robotcore.util;

import android.util.Log;
import com.qualcomm.robotcore.util.MatrixD;
import com.qualcomm.robotcore.util.Pose;

public class PoseUtils {
    public static double[] getAnglesAroundZ(Pose inputPose) {
        Object object = null;
        if (inputPose != null && inputPose.poseMatrix != null) {
            MatrixD matrixD = inputPose.poseMatrix.submatrix(3, 3, 0, 0);
            object = PoseUtils.getAnglesAroundZ(matrixD);
        } else {
            Log.e((String)"PoseUtils", (String)"null input");
        }
        return object;
    }

    public static double[] getAnglesAroundZ(MatrixD rotMat) {
        if (rotMat.numRows() != 3 || rotMat.numCols() != 3) {
            throw new IllegalArgumentException("Invalid Matrix Dimension: Expected (3,3) got (" + rotMat.numRows() + "," + rotMat.numCols() + ")");
        }
        double[][] arrarrd = new double[][]{{0.0}, {0.0}, {1.0}};
        MatrixD matrixD = new MatrixD(arrarrd);
        matrixD = rotMat.times(matrixD);
        double d = Math.atan2(matrixD.data()[1][0], matrixD.data()[0][0]);
        d = Math.toDegrees(d);
        double d2 = Math.atan2(matrixD.data()[0][0], matrixD.data()[1][0]);
        d2 = Math.toDegrees(d2);
        double d3 = matrixD.length();
        double d4 = Math.asin(matrixD.data()[2][0] / d3);
        d4 = Math.toDegrees(d4);
        double[] arrd = new double[]{d, d2, d4};
        return arrd;
    }

    public static double smallestAngularDifferenceDegrees(double firstAngleDeg, double secondAngleDeg) {
        double d = firstAngleDeg - secondAngleDeg;
        double d2 = d * 3.141592653589793 / 180.0;
        double d3 = Math.atan2(Math.sin(d2), Math.cos(d2));
        double d4 = d3 * 180.0 / 3.141592653589793;
        return d4;
    }
}

