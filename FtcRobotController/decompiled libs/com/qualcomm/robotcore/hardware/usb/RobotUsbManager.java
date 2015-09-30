/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware.usb;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.usb.RobotUsbDevice;
import com.qualcomm.robotcore.util.SerialNumber;

public interface RobotUsbManager {
    public int scanForDevices() throws RobotCoreException;

    public SerialNumber getDeviceSerialNumberByIndex(int var1) throws RobotCoreException;

    public String getDeviceDescriptionByIndex(int var1) throws RobotCoreException;

    public RobotUsbDevice openBySerialNumber(SerialNumber var1) throws RobotCoreException;
}

