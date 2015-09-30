/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.eventloop;

import com.qualcomm.robotcore.eventloop.EventLoopManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.robocol.Command;

public interface EventLoop {
    public void init(EventLoopManager var1) throws RobotCoreException, InterruptedException;

    public void loop() throws RobotCoreException, InterruptedException;

    public void teardown() throws RobotCoreException, InterruptedException;

    public void processCommand(Command var1);

    public OpModeManager getOpModeManager();
}

