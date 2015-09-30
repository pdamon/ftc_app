/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.util.SerialNumber;

public interface AnalogOutputController
extends HardwareDevice {
    public SerialNumber getSerialNumber();

    public void setAnalogOutputVoltage(int var1, int var2);

    public void setAnalogOutputFrequency(int var1, int var2);

    public void setAnalogOutputMode(int var1, byte var2);
}

