/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.util.SerialNumber;

public interface PWMOutputController
extends HardwareDevice {
    public SerialNumber getSerialNumber();

    public void setPulseWidthOutputTime(int var1, int var2);

    public void setPulseWidthPeriod(int var1, int var2);

    public int getPulseWidthOutputTime(int var1);

    public int getPulseWidthPeriod(int var1);
}

