/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.util;

import com.qualcomm.robotcore.util.MatrixD;
import com.qualcomm.robotcore.util.PoseUtils;

public class Pose {
    public double transX;
    public double transY;
    public double transZ;
    public MatrixD poseMatrix;

    public Pose(MatrixD poseMatrix) {
        if (poseMatrix == null) {
            throw new IllegalArgumentException("Attempted to construct Pose from null matrix");
        }
        if (poseMatrix.numRows() != 3 || poseMatrix.numCols() != 4) {
            throw new IllegalArgumentException("Invalid matrix size ( " + poseMatrix.numRows() + ", " + poseMatrix.numCols() + " )");
        }
        this.poseMatrix = poseMatrix;
        this.transX = poseMatrix.data()[0][3];
        this.transY = poseMatrix.data()[1][3];
        this.transZ = poseMatrix.data()[2][3];
    }

    public Pose(double transX, double transY, double transZ) {
        this.transX = transX;
        this.transY = transY;
        this.transZ = transZ;
        this.poseMatrix = new MatrixD(3, 4);
        this.poseMatrix.data()[2][2] = 1.0;
        this.poseMatrix.data()[1][1] = 1.0;
        this.poseMatrix.data()[0][0] = 1.0;
        this.poseMatrix.data()[0][3] = transX;
        this.poseMatrix.data()[1][3] = transY;
        this.poseMatrix.data()[2][3] = transZ;
    }

    public Pose() {
        this.transX = 0.0;
        this.transY = 0.0;
        this.transZ = 0.0;
    }

    public MatrixD getTranslationMatrix() {
        double[][] arrarrd = new double[][]{{this.transX}, {this.transY}, {this.transZ}};
        MatrixD matrixD = new MatrixD(arrarrd);
        return matrixD;
    }

    public static MatrixD makeRotationX(double angle) {
        double[][] arrd = new double[3][3];
        double d = Math.cos(angle);
        double d2 = Math.sin(angle);
        arrd[0][0] = 1.0;
        arrd[2][0] = 0.0;
        arrd[1][0] = 0.0;
        arrd[0][2] = 0.0;
        arrd[0][1] = 0.0;
        double d3 = d;
        arrd[2][2] = d3;
        arrd[1][1] = d3;
        arrd[1][2] = - d2;
        arrd[2][1] = d2;
        return new MatrixD(arrd);
    }

    public static MatrixD makeRotationY(double angle) {
        double[][] arrd = new double[3][3];
        double d = Math.cos(angle);
        double d2 = Math.sin(angle);
        arrd[2][1] = 0.0;
        arrd[1][2] = 0.0;
        arrd[1][0] = 0.0;
        arrd[0][1] = 0.0;
        arrd[1][1] = 1.0;
        double d3 = d;
        arrd[2][2] = d3;
        arrd[0][0] = d3;
        arrd[0][2] = d2;
        arrd[2][0] = - d2;
        return new MatrixD(arrd);
    }

    public static MatrixD makeRotationZ(double angle) {
        double[][] arrd = new double[3][3];
        double d = Math.cos(angle);
        double d2 = Math.sin(angle);
        arrd[2][2] = 1.0;
        arrd[1][2] = 0.0;
        arrd[0][2] = 0.0;
        arrd[2][1] = 0.0;
        arrd[2][0] = 0.0;
        double d3 = d;
        arrd[1][1] = d3;
        arrd[0][0] = d3;
        arrd[0][1] = - d2;
        arrd[1][0] = d2;
        return new MatrixD(arrd);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        double[] arrd = PoseUtils.getAnglesAroundZ(this);
        stringBuilder.append(String.format("(XYZ %1$,.2f ", this.transX));
        stringBuilder.append(String.format(" %1$,.2f ", this.transY));
        stringBuilder.append(String.format(" %1$,.2f mm)", this.transZ));
        stringBuilder.append(String.format("(Angles %1$,.2f, ", arrd[0]));
        stringBuilder.append(String.format(" %1$,.2f, ", arrd[1]));
        stringBuilder.append(String.format(" %1$,.2f ", arrd[2]));
        stringBuilder.append('\u00b0');
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public double getDistanceInMm() {
        return Math.sqrt(Math.pow(this.transX, 2.0) + Math.pow(this.transY, 2.0) + Math.pow(this.transZ, 2.0));
    }
}

