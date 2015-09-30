/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx.protocol;

import com.ftdi.j2xx.protocol.SpiSlaveResponseEvent;

public interface SpiSlaveListener {
    public boolean OnDataReceived(SpiSlaveResponseEvent var1);
}

