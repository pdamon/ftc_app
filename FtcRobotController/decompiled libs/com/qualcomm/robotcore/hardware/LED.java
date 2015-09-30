/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.HardwareDevice;

public class LED
implements HardwareDevice {
    private DigitalChannelController a = null;
    private int b = -1;

    public LED(DigitalChannelController controller, int physicalPort) {
        this.a = controller;
        this.b = physicalPort;
        controller.setDigitalChannelMode(physicalPort, DigitalChannelController.Mode.OUTPUT);
    }

    public void enable(boolean set) {
        this.a.setDigitalChannelState(this.b, set);
    }

    @Override
    public String getDeviceName() {
        return null;
    }

    @Override
    public String getConnectionInfo() {
        return null;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void close() {
    }
}

