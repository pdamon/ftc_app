/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.LightSensor;

public abstract class OpticalDistanceSensor
extends LightSensor {
    @Override
    public String toString() {
        return String.format("OpticalDistanceSensor: %d", this.getLightDetected());
    }
}

