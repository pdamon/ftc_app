/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.AnalogOutputController;
import com.qualcomm.robotcore.hardware.HardwareDevice;

public class AnalogOutput
implements HardwareDevice {
    private AnalogOutputController a = null;
    private int b = -1;

    public AnalogOutput(AnalogOutputController controller, int channel) {
        this.a = controller;
        this.b = channel;
    }

    public void setAnalogOutputVoltage(int voltage) {
        this.a.setAnalogOutputVoltage(this.b, voltage);
    }

    public void setAnalogOutputFrequency(int freq) {
        this.a.setAnalogOutputFrequency(this.b, freq);
    }

    public void setAnalogOutputMode(byte mode) {
        this.a.setAnalogOutputMode(this.b, mode);
    }

    @Override
    public String getDeviceName() {
        return "Analog Output";
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

