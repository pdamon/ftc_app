/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public interface DcMotorController
extends HardwareDevice {
    public void setMotorControllerDeviceMode(DeviceMode var1);

    public DeviceMode getMotorControllerDeviceMode();

    public void setMotorChannelMode(int var1, RunMode var2);

    public RunMode getMotorChannelMode(int var1);

    public void setMotorPower(int var1, double var2);

    public double getMotorPower(int var1);

    public boolean isBusy(int var1);

    public void setMotorPowerFloat(int var1);

    public boolean getMotorPowerFloat(int var1);

    public void setMotorTargetPosition(int var1, int var2);

    public int getMotorTargetPosition(int var1);

    public int getMotorCurrentPosition(int var1);

    public static enum DeviceMode {
        SWITCHING_TO_READ_MODE,
        SWITCHING_TO_WRITE_MODE,
        READ_ONLY,
        WRITE_ONLY,
        READ_WRITE;
        

        private DeviceMode() {
        }
    }

    public static enum RunMode {
        RUN_USING_ENCODERS,
        RUN_WITHOUT_ENCODERS,
        RUN_TO_POSITION,
        RESET_ENCODERS;
        

        private RunMode() {
        }
    }

}

