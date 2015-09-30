/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public abstract class GyroSensor
implements HardwareDevice {
    public abstract double getRotation();

    public abstract String status();

    public String toString() {
        return String.format("Gyro: %3.1f", this.getRotation());
    }
}

