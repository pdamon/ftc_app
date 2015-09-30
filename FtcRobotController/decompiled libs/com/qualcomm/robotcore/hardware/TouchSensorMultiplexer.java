/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public abstract class TouchSensorMultiplexer
implements HardwareDevice {
    public abstract boolean isTouchSensorPressed(int var1);

    public abstract int getSwitches();
}

