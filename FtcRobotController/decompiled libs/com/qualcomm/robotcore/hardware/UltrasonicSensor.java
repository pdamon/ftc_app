/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public abstract class UltrasonicSensor
implements HardwareDevice {
    public abstract double getUltrasonicLevel();

    public abstract String status();

    public String toString() {
        return String.format("Ultrasonic: %6.1f", this.getUltrasonicLevel());
    }
}

