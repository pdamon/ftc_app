/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public abstract class LightSensor
implements HardwareDevice {
    public abstract double getLightDetected();

    public abstract int getLightDetectedRaw();

    public abstract void enableLed(boolean var1);

    public abstract String status();

    public String toString() {
        return String.format("Light Level: %1.2f", this.getLightDetected());
    }
}

