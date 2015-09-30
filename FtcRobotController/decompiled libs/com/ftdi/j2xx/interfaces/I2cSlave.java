/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx.interfaces;

public interface I2cSlave {
    public int init();

    public int reset();

    public int getAddress(int[] var1);

    public int setAddress(int var1);

    public int read(byte[] var1, int var2, int[] var3);

    public int write(byte[] var1, int var2, int[] var3);
}

