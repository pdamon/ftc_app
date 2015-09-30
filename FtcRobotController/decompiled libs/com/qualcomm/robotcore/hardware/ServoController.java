/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public interface ServoController
extends HardwareDevice {
    public void pwmEnable();

    public void pwmDisable();

    public PwmStatus getPwmStatus();

    public void setServoPosition(int var1, double var2);

    public double getServoPosition(int var1);

    public static enum PwmStatus {
        ENABLED,
        DISABLED;
        

        private PwmStatus() {
        }
    }

}

