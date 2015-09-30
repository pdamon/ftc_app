/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx.interfaces;

public interface Gpio {
    public int init(int[] var1);

    public int read(int var1, boolean[] var2);

    public int write(int var1, boolean var2);
}

