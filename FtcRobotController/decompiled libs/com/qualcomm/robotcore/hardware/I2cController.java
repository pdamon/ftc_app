/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.util.SerialNumber;
import java.util.concurrent.locks.Lock;

public interface I2cController
extends HardwareDevice {
    public static final byte I2C_BUFFER_START_ADDRESS = 4;

    public SerialNumber getSerialNumber();

    public void enableI2cReadMode(int var1, int var2, int var3, int var4);

    public void enableI2cWriteMode(int var1, int var2, int var3, int var4);

    public byte[] getCopyOfReadBuffer(int var1);

    public byte[] getCopyOfWriteBuffer(int var1);

    public void copyBufferIntoWriteBuffer(int var1, byte[] var2);

    public void setI2cPortActionFlag(int var1);

    public boolean isI2cPortActionFlagSet(int var1);

    public void readI2cCacheFromController(int var1);

    public void writeI2cCacheToController(int var1);

    public void writeI2cPortFlagOnlyToController(int var1);

    public boolean isI2cPortInReadMode(int var1);

    public boolean isI2cPortInWriteMode(int var1);

    public boolean isI2cPortReady(int var1);

    public Lock getI2cReadCacheLock(int var1);

    public Lock getI2cWriteCacheLock(int var1);

    public byte[] getI2cReadCache(int var1);

    public byte[] getI2cWriteCache(int var1);

    public void registerForI2cPortReadyCallback(I2cPortReadyCallback var1, int var2);

    public void deregisterForPortReadyCallback(int var1);

    @Deprecated
    public void readI2cCacheFromModule(int var1);

    @Deprecated
    public void writeI2cCacheToModule(int var1);

    @Deprecated
    public void writeI2cPortFlagOnlyToModule(int var1);

    public static interface I2cPortReadyCallback {
        public void portIsReady(int var1);
    }

}

