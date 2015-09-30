/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.qualcomm.robotcore.eventloop.SyncdDevice
 *  com.qualcomm.robotcore.exception.RobotCoreException
 */
package com.qualcomm.hardware;

import com.qualcomm.hardware.ReadWriteRunnableSegment;
import com.qualcomm.robotcore.eventloop.SyncdDevice;
import com.qualcomm.robotcore.exception.RobotCoreException;

interface ReadWriteRunnable
extends SyncdDevice,
Runnable {
    public void setCallback(Callback var1);

    public void blockUntilReady() throws RobotCoreException, InterruptedException;

    public void write(int var1, byte[] var2);

    public byte[] readFromWriteCache(int var1, int var2);

    public byte[] read(int var1, int var2);

    public void close();

    public ReadWriteRunnableSegment createSegment(int var1, int var2, int var3);

    public void queueSegmentRead(int var1);

    public void queueSegmentWrite(int var1);

    public static class EmptyCallback
    implements Callback {
        @Override
        public void readComplete() throws InterruptedException {
        }

        @Override
        public void writeComplete() throws InterruptedException {
        }
    }

    public static interface Callback {
        public void readComplete() throws InterruptedException;

        public void writeComplete() throws InterruptedException;
    }

    public static enum BlockingState {
        BLOCKING,
        WAITING;
        

        private BlockingState() {
        }
    }

}

