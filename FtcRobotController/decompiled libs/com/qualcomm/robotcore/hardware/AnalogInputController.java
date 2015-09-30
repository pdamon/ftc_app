/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.util.SerialNumber;

public interface AnalogInputController
extends HardwareDevice {
    public int getAnalogInputValue(int var1);

    public SerialNumber getSerialNumber();
}

