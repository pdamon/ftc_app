/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware.usb;

import com.qualcomm.robotcore.exception.RobotCoreException;

public interface RobotUsbDevice {
    public void setBaudRate(int var1) throws RobotCoreException;

    public void setDataCharacteristics(byte var1, byte var2, byte var3) throws RobotCoreException;

    public void setLatencyTimer(int var1) throws RobotCoreException;

    public void purge(Channel var1) throws RobotCoreException;

    public void write(byte[] var1) throws RobotCoreException;

    public int read(byte[] var1) throws RobotCoreException;

    public int read(byte[] var1, int var2, int var3) throws RobotCoreException;

    public void close();

    public static enum Channel {
        RX,
        TX,
        NONE,
        BOTH;
        

        private Channel() {
        }
    }

}

