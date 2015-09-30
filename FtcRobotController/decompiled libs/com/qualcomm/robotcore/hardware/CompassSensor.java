/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public abstract class CompassSensor
implements HardwareDevice {
    public abstract double getDirection();

    public abstract String status();

    public abstract void setMode(CompassMode var1);

    public abstract boolean calibrationFailed();

    public String toString() {
        return String.format("Compass: %3.1f", this.getDirection());
    }

    public static enum CompassMode {
        MEASUREMENT_MODE,
        CALIBRATION_MODE;
        

        private CompassMode() {
        }
    }

}

