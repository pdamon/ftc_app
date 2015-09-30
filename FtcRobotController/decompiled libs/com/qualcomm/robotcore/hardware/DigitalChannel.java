/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.HardwareDevice;

public class DigitalChannel
implements HardwareDevice {
    private DigitalChannelController a = null;
    private int b = -1;

    public DigitalChannel(DigitalChannelController controller, int channel) {
        this.a = controller;
        this.b = channel;
    }

    public DigitalChannelController.Mode getMode() {
        return this.a.getDigitalChannelMode(this.b);
    }

    public void setMode(DigitalChannelController.Mode mode) {
        this.a.setDigitalChannelMode(this.b, mode);
    }

    public boolean getState() {
        return this.a.getDigitalChannelState(this.b);
    }

    public void setState(boolean state) {
        this.a.setDigitalChannelState(this.b, state);
    }

    @Override
    public String getDeviceName() {
        return "Digital Channel";
    }

    @Override
    public String getConnectionInfo() {
        return this.a.getConnectionInfo() + "; digital port " + this.b;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void close() {
    }
}

