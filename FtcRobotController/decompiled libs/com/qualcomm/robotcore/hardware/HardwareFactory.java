/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.eventloop.EventLoopManager;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.HardwareMap;

public interface HardwareFactory {
    public HardwareMap createHardwareMap(EventLoopManager var1) throws RobotCoreException, InterruptedException;
}

