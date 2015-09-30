/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

public interface HardwareDevice {
    public String getDeviceName();

    public String getConnectionInfo();

    public int getVersion();

    public void close();
}

