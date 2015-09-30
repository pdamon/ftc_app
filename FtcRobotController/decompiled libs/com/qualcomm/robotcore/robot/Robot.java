/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.robot;

import com.qualcomm.robotcore.eventloop.EventLoop;
import com.qualcomm.robotcore.eventloop.EventLoopManager;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.robocol.RobocolDatagram;
import com.qualcomm.robotcore.robocol.RobocolDatagramSocket;
import com.qualcomm.robotcore.util.RobotLog;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;

public class Robot {
    public EventLoopManager eventLoopManager = null;
    public RobocolDatagramSocket socket = null;
    public ArrayBlockingQueue<RobocolDatagram> sendQueue = null;
    public ArrayBlockingQueue<RobocolDatagram> eventQueue = null;

    public void start(InetAddress driverStationAddr, EventLoop eventLoop) throws RobotCoreException {
        try {
            this.socket.listen(driverStationAddr);
            this.eventLoopManager.start(eventLoop);
        }
        catch (SocketException var3_3) {
            RobotLog.logStacktrace(var3_3);
            throw new RobotCoreException("Robot start failed: " + var3_3.toString());
        }
    }

    public void shutdown() {
        if (this.eventLoopManager != null) {
            this.eventLoopManager.shutdown();
        }
        if (this.socket != null) {
            this.socket.close();
        }
    }
}

