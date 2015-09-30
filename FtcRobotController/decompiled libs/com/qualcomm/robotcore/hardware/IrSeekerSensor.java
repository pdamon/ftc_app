/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public abstract class IrSeekerSensor
implements HardwareDevice {
    public static final int MAX_NEW_I2C_ADDRESS = 126;
    public static final int MIN_NEW_I2C_ADDRESS = 16;

    public abstract void setSignalDetectedThreshold(double var1);

    public abstract double getSignalDetectedThreshold();

    public abstract void setMode(Mode var1);

    public abstract Mode getMode();

    public abstract boolean signalDetected();

    public abstract double getAngle();

    public abstract double getStrength();

    public abstract IrSeekerIndividualSensor[] getIndividualSensors();

    public abstract void setI2cAddress(int var1);

    public abstract int getI2cAddress();

    public String toString() {
        if (this.signalDetected()) {
            return String.format("IR Seeker: %3.0f%% signal at %6.1f degrees", this.getStrength() * 100.0, this.getAngle());
        }
        return "IR Seeker:  --% signal at  ---.- degrees";
    }

    public static void throwIfModernRoboticsI2cAddressIsInvalid(int newAddress) {
        if (newAddress < 16 || newAddress > 126) {
            throw new IllegalArgumentException(String.format("New I2C address %d is invalid; valid range is: %d..%d", newAddress, 16, 126));
        }
        if (newAddress % 2 != 0) {
            throw new IllegalArgumentException(String.format("New I2C address %d is invalid; the address must be even.", newAddress));
        }
    }

    public static class IrSeekerIndividualSensor {
        private double a = 0.0;
        private double b = 0.0;

        public IrSeekerIndividualSensor() {
            this(0.0, 0.0);
        }

        public IrSeekerIndividualSensor(double angle, double strength) {
            this.a = angle;
            this.b = strength;
        }

        public double getSensorAngle() {
            return this.a;
        }

        public double getSensorStrength() {
            return this.b;
        }

        public String toString() {
            return String.format("IR Sensor: %3.1f degrees at %3.1f%% power", this.a, this.b * 100.0);
        }
    }

    public static enum Mode {
        MODE_600HZ,
        MODE_1200HZ;
        

        private Mode() {
        }
    }

}

