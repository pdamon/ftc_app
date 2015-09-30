/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.qualcomm.robotcore.sensor;

import android.util.Log;
import com.qualcomm.robotcore.sensor.SensorBase;
import com.qualcomm.robotcore.sensor.SensorListener;
import com.qualcomm.robotcore.sensor.TargetInfo;
import com.qualcomm.robotcore.sensor.TargetSize;
import com.qualcomm.robotcore.sensor.TrackedTargetInfo;
import com.qualcomm.robotcore.util.MatrixD;
import com.qualcomm.robotcore.util.Pose;
import com.qualcomm.robotcore.util.PoseUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorImageLocalizer
extends SensorBase<Pose>
implements SensorListener<List<TrackedTargetInfo>> {
    private final boolean a = false;
    private final String b = "SensorImageLocalizer";
    private final Map<String, TargetInfo> c = new HashMap<String, TargetInfo>();
    private Pose d;
    private final HashMap<String, a> e = new HashMap();
    private a f;

    public SensorImageLocalizer(List<SensorListener<Pose>> l) {
        super(l);
    }

    @Override
    public boolean initialize() {
        return true;
    }

    @Override
    public boolean shutdown() {
        return true;
    }

    @Override
    public boolean resume() {
        return true;
    }

    @Override
    public boolean pause() {
        return true;
    }

    public void AddListener(SensorListener<Pose> l) {
        List list = this.mListeners;
        synchronized (list) {
            if (!this.mListeners.contains(l)) {
                this.mListeners.add(l);
            }
        }
    }

    public void RemoveListener(SensorListener<Pose> l) {
        List list = this.mListeners;
        synchronized (list) {
            if (this.mListeners.contains(l)) {
                this.mListeners.remove(l);
            }
        }
    }

    public boolean addTargetReference(String targetName, double xTrans, double yTrans, double zTrans, double angle, double longSideTransFromCenterToVertex, double shortSideTransFromCenterToVertex) {
        if (targetName == null) {
            throw new IllegalArgumentException("Null targetInfoWorldRef");
        }
        if (this.c.containsKey(targetName)) {
            return false;
        }
        MatrixD matrixD = Pose.makeRotationY(Math.toRadians(angle));
        MatrixD matrixD2 = new MatrixD(3, 4);
        matrixD2.setSubmatrix(matrixD, 3, 3, 0, 0);
        matrixD2.data()[0][3] = yTrans;
        matrixD2.data()[1][3] = zTrans;
        matrixD2.data()[2][3] = xTrans;
        Pose pose = new Pose(matrixD2);
        Log.d((String)"SensorImageLocalizer", (String)("Target Pose \n" + matrixD2));
        TargetSize targetSize = new TargetSize(targetName, longSideTransFromCenterToVertex, shortSideTransFromCenterToVertex);
        TargetInfo targetInfo = new TargetInfo(targetName, pose, targetSize);
        this.c.put(targetName, targetInfo);
        return true;
    }

    public boolean addRobotToCameraRef(double length, double width, double height, double angle) {
        MatrixD matrixD = new MatrixD(3, 3);
        matrixD = Pose.makeRotationY(- angle);
        MatrixD matrixD2 = new MatrixD(3, 4);
        matrixD2.setSubmatrix(matrixD, 3, 3, 0, 0);
        matrixD2.data()[0][3] = width;
        matrixD2.data()[1][3] = - height;
        matrixD2.data()[2][3] = length;
        this.d = new Pose(matrixD2);
        return true;
    }

    public boolean removeTargetReference(String targetName) {
        if (targetName == null) {
            throw new IllegalArgumentException("Null targetName");
        }
        if (this.c.containsKey(targetName)) {
            this.c.remove(targetName);
            return true;
        }
        return false;
    }

    private boolean a(TrackedTargetInfo trackedTargetInfo) {
        long l = System.currentTimeMillis() / 1000;
        a a = null;
        if (this.e.containsKey(trackedTargetInfo.mTargetInfo.mTargetName)) {
            a = this.e.get(trackedTargetInfo.mTargetInfo.mTargetName);
            a.b = trackedTargetInfo.mTimeTracked;
            a.e = trackedTargetInfo.mConfidence;
            a.c = l - a.b > 120 ? 1 : ++a.c;
        } else {
            a = new a();
            a.e = trackedTargetInfo.mConfidence;
            a.d = trackedTargetInfo.mTargetInfo.mTargetName;
            a.b = trackedTargetInfo.mTimeTracked;
            a.c = 1;
            this.e.put(trackedTargetInfo.mTargetInfo.mTargetName, a);
        }
        if (this.f != null && this.f.d != a.d && l - this.f.a < 10) {
            Log.d((String)"SensorImageLocalizer", (String)("Ignoring target " + trackedTargetInfo.mTargetInfo.mTargetName + " Time diff " + (l - this.f.a)));
            return false;
        }
        return true;
    }

    @Override
    public void onUpdate(List<TrackedTargetInfo> targetPoses) {
        void var10_11;
        Log.d((String)"SensorImageLocalizer", (String)"SensorImageLocalizer onUpdate");
        if (targetPoses == null || targetPoses.size() < 1) {
            Log.d((String)"SensorImageLocalizer", (String)"SensorImageLocalizer onUpdate NULL");
            this.update(null);
            return;
        }
        boolean bl = false;
        double d = 4.9E-324;
        long l = System.currentTimeMillis() / 1000;
        TrackedTargetInfo trackedTargetInfo = null;
        a a = null;
        for (TrackedTargetInfo object2 : targetPoses) {
            if (!this.c.containsKey(object2.mTargetInfo.mTargetName)) continue;
            boolean bl2 = this.a(object2);
            if (bl2 && object2.mConfidence > d) {
                a = this.e.get(object2.mTargetInfo.mTargetName);
                trackedTargetInfo = object2;
                d = object2.mConfidence;
                bl = true;
                Log.d((String)"SensorImageLocalizer", (String)("Potential target " + object2.mTargetInfo.mTargetName + " Confidence " + object2.mConfidence));
                continue;
            }
            Log.d((String)"SensorImageLocalizer", (String)("Ignoring target " + object2.mTargetInfo.mTargetName + " Confidence " + object2.mConfidence));
        }
        if (!bl) {
            this.update(null);
            return;
        }
        Object object3 = this.c.get(trackedTargetInfo.mTargetInfo.mTargetName);
        a.a = l;
        this.f = a;
        Log.d((String)"SensorImageLocalizer", (String)("Selected target " + trackedTargetInfo.mTargetInfo.mTargetName + " time " + l));
        Object var10_9 = null;
        if (this.d != null) {
            MatrixD matrixD = this.d.poseMatrix.submatrix(3, 3, 0, 0);
        }
        MatrixD matrixD = trackedTargetInfo.mTargetInfo.mTargetPose.poseMatrix.submatrix(3, 3, 0, 0);
        MatrixD matrixD2 = matrixD.transpose();
        MatrixD matrixD3 = object3.mTargetPose.poseMatrix.submatrix(3, 3, 0, 0);
        MatrixD matrixD4 = Pose.makeRotationX(Math.toRadians(90.0));
        matrixD4 = matrixD4.times(Pose.makeRotationY(Math.toRadians(90.0)));
        MatrixD matrixD5 = matrixD4.times(matrixD3).times(matrixD2);
        if (var10_11 != null) {
            matrixD5 = matrixD5.times((MatrixD)var10_11);
        }
        MatrixD matrixD6 = new MatrixD(3, 1);
        matrixD6.data()[0][0] = object3.mTargetSize.mLongSide;
        matrixD6.data()[1][0] = object3.mTargetSize.mShortSide;
        matrixD6.data()[2][0] = 0.0;
        MatrixD matrixD7 = trackedTargetInfo.mTargetInfo.mTargetPose.getTranslationMatrix();
        MatrixD matrixD8 = matrixD2.times(matrixD7);
        MatrixD matrixD9 = new MatrixD(3, 1);
        if (this.d != null) {
            matrixD9 = this.d.getTranslationMatrix();
        }
        MatrixD matrixD10 = matrixD2.times(matrixD9);
        matrixD8 = matrixD8.add(matrixD10);
        matrixD8 = matrixD8.add(matrixD6);
        matrixD8 = matrixD3.times(matrixD8);
        MatrixD matrixD11 = object3.mTargetPose.getTranslationMatrix();
        matrixD8 = matrixD11.subtract(matrixD8);
        matrixD8 = matrixD4.times(matrixD8);
        MatrixD matrixD12 = new MatrixD(3, 4);
        matrixD12.setSubmatrix(matrixD5, 3, 3, 0, 0);
        matrixD12.setSubmatrix(matrixD8, 3, 1, 0, 3);
        Pose pose = new Pose(matrixD12);
        double[] arrd = PoseUtils.getAnglesAroundZ(pose);
        Log.d((String)"SensorImageLocalizer", (String)String.format("POSE_HEADING: x %8.4f z %8.4f up %8.4f", arrd[0], arrd[1], arrd[2]));
        matrixD8 = pose.getTranslationMatrix();
        Log.d((String)"SensorImageLocalizer", (String)String.format("POSE_TRANS: x %8.4f y %8.4f z %8.4f", matrixD8.data()[0][0], matrixD8.data()[1][0], matrixD8.data()[2][0]));
        this.update(pose);
    }

    private class a {
        public long a;
        public long b;
        public int c;
        public String d;
        public double e;

        private a() {
        }
    }

}

