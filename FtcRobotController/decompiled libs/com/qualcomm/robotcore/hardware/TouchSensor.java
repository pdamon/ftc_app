/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public abstract class TouchSensor
implements HardwareDevice {
    public abstract double getValue();

    public abstract boolean isPressed();

    public String toString() {
        return String.format("Touch Sensor: %1.2f", this.getValue());
    }
}

