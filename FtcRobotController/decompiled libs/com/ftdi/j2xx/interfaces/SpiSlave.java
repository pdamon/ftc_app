/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx.interfaces;

public interface SpiSlave {
    public int init();

    public int getRxStatus(int[] var1);

    public int read(byte[] var1, int var2, int[] var3);

    public int write(byte[] var1, int var2, int[] var3);

    public int reset();
}

