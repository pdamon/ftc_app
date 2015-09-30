/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.AnalogInputController;
import com.qualcomm.robotcore.hardware.HardwareDevice;

public class AnalogInput
implements HardwareDevice {
    private AnalogInputController a = null;
    private int b = -1;

    public AnalogInput(AnalogInputController controller, int channel) {
        this.a = controller;
        this.b = channel;
    }

    public int getValue() {
        return this.a.getAnalogInputValue(this.b);
    }

    @Override
    public String getDeviceName() {
        return "Analog Input";
    }

    @Override
    public String getConnectionInfo() {
        return this.a.getConnectionInfo() + "; analog port " + this.b;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void close() {
    }
}

