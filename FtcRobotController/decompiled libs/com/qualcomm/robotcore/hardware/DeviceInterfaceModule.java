/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.AnalogInputController;
import com.qualcomm.robotcore.hardware.AnalogOutputController;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.I2cController;
import com.qualcomm.robotcore.hardware.PWMOutputController;

public interface DeviceInterfaceModule
extends AnalogInputController,
AnalogOutputController,
DigitalChannelController,
I2cController,
PWMOutputController {
    public int getDigitalInputStateByte();

    public void setDigitalIOControlByte(byte var1);

    public byte getDigitalIOControlByte();

    public void setDigitalOutputByte(byte var1);

    public byte getDigitalOutputStateByte();

    public boolean getLEDState(int var1);

    public void setLED(int var1, boolean var2);
}

