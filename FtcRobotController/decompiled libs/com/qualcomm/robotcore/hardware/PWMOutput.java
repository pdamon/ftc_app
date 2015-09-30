/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.PWMOutputController;

public class PWMOutput
implements HardwareDevice {
    private PWMOutputController a = null;
    private int b = -1;

    public PWMOutput(PWMOutputController controller, int port) {
        this.a = controller;
        this.b = port;
    }

    public void setPulseWidthOutputTime(int time) {
        this.a.setPulseWidthOutputTime(this.b, time);
    }

    public int getPulseWidthOutputTime() {
        return this.a.getPulseWidthOutputTime(this.b);
    }

    public void setPulseWidthPeriod(int period) {
        this.a.setPulseWidthPeriod(this.b, period);
    }

    public int getPulseWidthPeriod() {
        return this.a.getPulseWidthPeriod(this.b);
    }

    @Override
    public String getDeviceName() {
        return "PWM Output";
    }

    @Override
    public String getConnectionInfo() {
        return this.a.getConnectionInfo() + "; port " + this.b;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void close() {
    }
}

