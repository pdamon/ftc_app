/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx.protocol;

public class SpiSlaveEvent {
    private int a;
    private boolean b;
    private Object c;
    private Object d;
    private Object e;

    public SpiSlaveEvent(int iEventType, boolean bSync, Object pArg0, Object pArg1, Object pArg2) {
        this.a = iEventType;
        this.b = bSync;
        this.c = pArg0;
        this.d = pArg1;
        this.e = pArg2;
    }

    public Object getArg0() {
        return this.c;
    }

    public void setArg0(Object arg) {
        this.c = arg;
    }

    public Object getArg1() {
        return this.d;
    }

    public void setArg1(Object arg) {
        this.d = arg;
    }

    public Object getArg2() {
        return this.e;
    }

    public void setArg2(Object arg) {
        this.e = arg;
    }

    public int getEventType() {
        return this.a;
    }

    public void setEventType(int type) {
        this.a = type;
    }

    public boolean getSync() {
        return this.b;
    }

    public void setSync(boolean bSync) {
        this.b = bSync;
    }
}

