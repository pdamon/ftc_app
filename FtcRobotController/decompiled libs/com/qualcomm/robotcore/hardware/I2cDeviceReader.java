/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.I2cController;
import com.qualcomm.robotcore.hardware.I2cDevice;

public class I2cDeviceReader {
    private final I2cDevice a;

    public I2cDeviceReader(I2cDevice i2cDevice, int i2cAddress, int memAddress, int length) {
        this.a = i2cDevice;
        i2cDevice.enableI2cReadMode(i2cAddress, memAddress, length);
        i2cDevice.setI2cPortActionFlag();
        i2cDevice.writeI2cCacheToModule();
        i2cDevice.registerForI2cPortReadyCallback(new I2cController.I2cPortReadyCallback(){

            @Override
            public void portIsReady(int port) {
                I2cDeviceReader.this.a();
            }
        });
    }

    public byte[] getReadBuffer() {
        return this.a.getCopyOfReadBuffer();
    }

    private void a() {
        this.a.setI2cPortActionFlag();
        this.a.readI2cCacheFromModule();
        this.a.writeI2cPortFlagOnlyToModule();
    }

}

