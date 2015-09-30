/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.util;

import com.qualcomm.robotcore.util.RobotLog;

public class ElapsedTime {
    private long a = 0;

    public ElapsedTime() {
        this.reset();
    }

    public ElapsedTime(long startTime) {
        this.a = startTime;
    }

    public void reset() {
        this.a = System.nanoTime();
    }

    public double startTime() {
        return (double)this.a / 1.0E9;
    }

    public double time() {
        return (double)(System.nanoTime() - this.a) / 1.0E9;
    }

    public void log(String label) {
        RobotLog.v(String.format("TIMER: %20s - %1.3f", label, this.time()));
    }

    public String toString() {
        return String.format("%1.4f seconds", this.time());
    }
}

