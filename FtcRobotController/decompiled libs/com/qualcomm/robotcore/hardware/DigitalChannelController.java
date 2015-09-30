/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.util.SerialNumber;

public interface DigitalChannelController
extends HardwareDevice {
    public SerialNumber getSerialNumber();

    public Mode getDigitalChannelMode(int var1);

    public void setDigitalChannelMode(int var1, Mode var2);

    public boolean getDigitalChannelState(int var1);

    public void setDigitalChannelState(int var1, boolean var2);

    public static enum Mode {
        INPUT,
        OUTPUT;
        

        private Mode() {
        }
    }

}

