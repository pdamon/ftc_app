/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.eventloop;

import com.qualcomm.robotcore.exception.RobotCoreException;

public interface SyncdDevice {
    public void blockUntilReady() throws RobotCoreException, InterruptedException;

    public void startBlockingWork();
}

