/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.view.InputDevice
 */
package com.qualcomm.robotcore.util;

import android.os.Build;
import android.view.InputDevice;
import com.qualcomm.robotcore.util.RobotLog;
import java.util.HashSet;
import java.util.Set;

public class Hardware {
    private static boolean a = Hardware.CheckIfIFC();

    public static Set<Integer> getGameControllerIds() {
        int[] arrn;
        HashSet<Integer> hashSet = new HashSet<Integer>();
        for (int n : arrn = InputDevice.getDeviceIds()) {
            int n2 = InputDevice.getDevice((int)n).getSources();
            if ((n2 & 1025) != 1025 && (n2 & 16777232) != 16777232) continue;
            hashSet.add(n);
        }
        return hashSet;
    }

    public static boolean IsIFC() {
        return a;
    }

    public static boolean CheckIfIFC() {
        boolean bl = false;
        String string = Build.BOARD;
        String string2 = Build.BRAND;
        String string3 = Build.DEVICE;
        String string4 = Build.HARDWARE;
        String string5 = Build.MANUFACTURER;
        String string6 = Build.MODEL;
        String string7 = Build.PRODUCT;
        RobotLog.d("Platform information: board = " + string + " brand = " + string2 + " device = " + string3 + " hardware = " + string4 + " manufacturer = " + string5 + " model = " + string6 + " product = " + string7);
        if (string.equals("MSM8960") && string2.equals("qcom") && string3.equals("msm8960") && string4.equals("qcom") && string5.equals("unknown") && string6.equals("msm8960") && string7.equals("msm8960")) {
            RobotLog.d("Detected IFC6410 Device!");
            bl = true;
        } else {
            RobotLog.d("Detected regular SmartPhone Device!");
        }
        return bl;
    }
}

