/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.qualcomm.robotcore.exception.RobotCoreException
 *  com.qualcomm.robotcore.hardware.usb.RobotUsbDevice
 *  com.qualcomm.robotcore.hardware.usb.RobotUsbManager
 *  com.qualcomm.robotcore.util.RobotLog
 *  com.qualcomm.robotcore.util.SerialNumber
 */
package com.qualcomm.modernrobotics;

import com.qualcomm.modernrobotics.RobotUsbDeviceEmulator;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.usb.RobotUsbDevice;
import com.qualcomm.robotcore.hardware.usb.RobotUsbManager;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;
import java.util.ArrayList;

public class RobotUsbManagerEmulator
implements RobotUsbManager {
    private ArrayList<RobotUsbDeviceEmulator> a = new ArrayList();

    public int scanForDevices() throws RobotCoreException {
        return this.a.size();
    }

    public SerialNumber getDeviceSerialNumberByIndex(int index) throws RobotCoreException {
        return this.a.get((int)index).serialNumber;
    }

    public String getDeviceDescriptionByIndex(int index) throws RobotCoreException {
        return this.a.get((int)index).description;
    }

    public RobotUsbDevice openBySerialNumber(SerialNumber serialNumber) throws RobotCoreException {
        RobotLog.d((String)("attempting to open emulated device " + (Object)serialNumber));
        for (RobotUsbDeviceEmulator robotUsbDeviceEmulator : this.a) {
            if (!robotUsbDeviceEmulator.serialNumber.equals((Object)serialNumber)) continue;
            return robotUsbDeviceEmulator;
        }
        throw new RobotCoreException("cannot open device - could not find device with serial number " + (Object)serialNumber);
    }
}

