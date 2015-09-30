/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx.interfaces;

public interface SpiMaster {
    public int init(int var1, int var2, int var3, int var4, byte var5);

    public int reset();

    public int setLines(int var1);

    public int singleWrite(byte[] var1, int var2, int[] var3, boolean var4);

    public int singleRead(byte[] var1, int var2, int[] var3, boolean var4);

    public int singleReadWrite(byte[] var1, byte[] var2, int var3, int[] var4, boolean var5);

    public int multiReadWrite(byte[] var1, byte[] var2, int var3, int var4, int var5, int[] var6);
}

