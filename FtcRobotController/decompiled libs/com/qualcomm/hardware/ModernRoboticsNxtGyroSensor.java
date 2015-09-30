/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.qualcomm.robotcore.hardware.GyroSensor
 *  com.qualcomm.robotcore.util.SerialNumber
 *  com.qualcomm.robotcore.util.TypeConversion
 */
package com.qualcomm.hardware;

import com.qualcomm.hardware.ModernRoboticsUsbLegacyModule;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.SerialNumber;
import com.qualcomm.robotcore.util.TypeConversion;
import java.nio.ByteOrder;

public class ModernRoboticsNxtGyroSensor
extends GyroSensor {
    private final ModernRoboticsUsbLegacyModule a;
    private final int b;

    ModernRoboticsNxtGyroSensor(ModernRoboticsUsbLegacyModule legacyModule, int physicalPort) {
        legacyModule.enableAnalogReadMode(physicalPort);
        this.a = legacyModule;
        this.b = physicalPort;
    }

    public double getRotation() {
        byte[] arrby = this.a.readAnalog(this.b);
        return TypeConversion.byteArrayToShort((byte[])arrby, (ByteOrder)ByteOrder.LITTLE_ENDIAN);
    }

    public String status() {
        return String.format("NXT Gyro Sensor, connected via device %s, port %d", this.a.getSerialNumber().toString(), this.b);
    }

    public String getDeviceName() {
        return "NXT Gyro Sensor";
    }

    public String getConnectionInfo() {
        return this.a.getConnectionInfo() + "; port " + this.b;
    }

    public int getVersion() {
        return 1;
    }

    public void close() {
    }
}

