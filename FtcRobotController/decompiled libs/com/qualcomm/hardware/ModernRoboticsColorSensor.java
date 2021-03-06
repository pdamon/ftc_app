/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  com.qualcomm.robotcore.hardware.ColorSensor
 *  com.qualcomm.robotcore.hardware.DeviceInterfaceModule
 *  com.qualcomm.robotcore.hardware.I2cController
 *  com.qualcomm.robotcore.hardware.I2cController$I2cPortReadyCallback
 *  com.qualcomm.robotcore.util.TypeConversion
 */
package com.qualcomm.hardware;

import android.graphics.Color;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.I2cController;
import com.qualcomm.robotcore.util.TypeConversion;
import java.util.concurrent.locks.Lock;

public class ModernRoboticsColorSensor
extends ColorSensor
implements I2cController.I2cPortReadyCallback {
    public static final int ADDRESS_I2C = 60;
    public static final int ADDRESS_COMMAND = 3;
    public static final int ADDRESS_COLOR_NUMBER = 4;
    public static final int OFFSET_COMMAND = 4;
    public static final int OFFSET_COLOR_NUMBER = 5;
    public static final int OFFSET_RED_READING = 6;
    public static final int OFFSET_GREEN_READING = 7;
    public static final int OFFSET_BLUE_READING = 8;
    public static final int OFFSET_ALPHA_VALUE = 9;
    public static final int BUFFER_LENGTH = 6;
    public static final int COMMAND_PASSIVE_LED = 1;
    public static final int COMMAND_ACTIVE_LED = 0;
    private final DeviceInterfaceModule a;
    private final byte[] b;
    private final Lock c;
    private final byte[] d;
    private final Lock e;
    private a f = a.a;
    private volatile int g = 0;
    private final int h;

    ModernRoboticsColorSensor(DeviceInterfaceModule deviceInterfaceModule, int physicalPort) {
        this.a = deviceInterfaceModule;
        this.h = physicalPort;
        this.b = deviceInterfaceModule.getI2cReadCache(physicalPort);
        this.c = deviceInterfaceModule.getI2cReadCacheLock(physicalPort);
        this.d = deviceInterfaceModule.getI2cWriteCache(physicalPort);
        this.e = deviceInterfaceModule.getI2cWriteCacheLock(physicalPort);
        deviceInterfaceModule.enableI2cReadMode(physicalPort, 60, 3, 6);
        deviceInterfaceModule.setI2cPortActionFlag(physicalPort);
        deviceInterfaceModule.writeI2cCacheToController(physicalPort);
        deviceInterfaceModule.registerForI2cPortReadyCallback((I2cController.I2cPortReadyCallback)this, physicalPort);
    }

    public int red() {
        return this.a(6);
    }

    public int green() {
        return this.a(7);
    }

    public int blue() {
        return this.a(8);
    }

    public int alpha() {
        return this.a(9);
    }

    public int argb() {
        return Color.argb((int)this.alpha(), (int)this.red(), (int)this.green(), (int)this.blue());
    }

    public void enableLed(boolean enable) {
        int n = 1;
        if (enable) {
            n = 0;
        }
        if (this.g == n) {
            return;
        }
        this.g = n;
        this.f = a.b;
        try {
            this.e.lock();
            this.d[4] = n;
        }
        finally {
            this.e.unlock();
        }
    }

    private int a(int n) {
        byte by;
        try {
            this.c.lock();
            by = this.b[n];
        }
        finally {
            this.c.unlock();
        }
        return TypeConversion.unsignedByteToInt((byte)by);
    }

    public String getDeviceName() {
        return "Modern Robotics Color Sensor";
    }

    public String getConnectionInfo() {
        return this.a.getConnectionInfo() + "; I2C port: " + this.h;
    }

    public int getVersion() {
        return 1;
    }

    public void close() {
    }

    public void portIsReady(int port) {
        this.a.setI2cPortActionFlag(this.h);
        this.a.readI2cCacheFromController(this.h);
        if (this.f == a.b) {
            this.a.enableI2cWriteMode(this.h, 60, 3, 6);
            this.a.writeI2cCacheToController(this.h);
            this.f = a.c;
        } else if (this.f == a.c) {
            this.a.enableI2cReadMode(this.h, 60, 3, 6);
            this.a.writeI2cCacheToController(this.h);
            this.f = a.a;
        } else {
            this.a.writeI2cPortFlagOnlyToController(this.h);
        }
    }

    private static enum a {
        a,
        b,
        c;
        

        private a() {
        }
    }

}

