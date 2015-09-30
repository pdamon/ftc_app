/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.exception;

public class RobotCoreException
extends Exception {
    private Exception a = null;

    public RobotCoreException(String message) {
        super(message);
    }

    public RobotCoreException(String message, Exception e) {
        super(message);
        this.a = e;
    }

    public boolean isChainedException() {
        return this.a != null;
    }

    public Exception getChainedException() {
        return this.a;
    }
}

