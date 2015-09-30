/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.I2cController;

public interface LegacyModule
extends HardwareDevice,
I2cController {
    public void enableAnalogReadMode(int var1);

    public void enable9v(int var1, boolean var2);

    public void setDigitalLine(int var1, int var2, boolean var3);

    public byte[] readAnalog(int var1);
}

