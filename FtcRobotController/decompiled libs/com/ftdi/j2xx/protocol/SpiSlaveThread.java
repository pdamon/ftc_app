/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.ftdi.j2xx.protocol;

import android.util.Log;
import com.ftdi.j2xx.protocol.SpiSlaveEvent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class SpiSlaveThread
extends Thread {
    public static final int THREAD_INIT = 0;
    public static final int THREAD_RUNNING = 1;
    public static final int THREAD_DESTORYED = 2;
    private Queue<SpiSlaveEvent> a = new LinkedList<SpiSlaveEvent>();
    private Lock b = new ReentrantLock();
    private Object c = new Object();
    private Object d = new Object();
    private boolean e;
    private boolean f;
    private int g = 0;

    protected abstract boolean pollData();

    protected abstract void requestEvent(SpiSlaveEvent var1);

    protected abstract boolean isTerminateEvent(SpiSlaveEvent var1);

    public SpiSlaveThread() {
        this.setName("SpiSlaveThread");
    }

    public boolean sendMessage(SpiSlaveEvent event) {
        Object object;
        while (this.g != 1) {
            try {
                Thread.sleep(100);
                continue;
            }
            catch (InterruptedException var2_2) {
                // empty catch block
            }
        }
        this.b.lock();
        if (this.a.size() > 10) {
            this.b.unlock();
            Log.d((String)"FTDI", (String)"SpiSlaveThread sendMessage Buffer full!!");
            return false;
        }
        this.a.add(event);
        if (this.a.size() == 1) {
            object = this.c;
            synchronized (object) {
                this.e = true;
                this.c.notify();
            }
        }
        this.b.unlock();
        if (event.getSync()) {
            object = this.d;
            synchronized (object) {
                this.f = false;
                while (!this.f) {
                    try {
                        this.d.wait();
                        continue;
                    }
                    catch (InterruptedException var3_4) {
                        this.f = true;
                    }
                }
            }
        }
        return true;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void run() {
        boolean bl = false;
        this.g = 1;
        while (!(Thread.interrupted() || bl)) {
            SpiSlaveEvent spiSlaveEvent;
            block11 : {
                this.pollData();
                this.b.lock();
                if (this.a.size() <= 0) {
                    this.b.unlock();
                    continue;
                }
                spiSlaveEvent = this.a.peek();
                this.a.remove();
                this.b.unlock();
                this.requestEvent(spiSlaveEvent);
                if (spiSlaveEvent.getSync()) {
                    Object object = this.d;
                    // MONITORENTER : object
                    do {
                        do {
                            if (!this.f) {
                                this.f = true;
                                this.d.notify();
                                // MONITOREXIT : object
                                break block11;
                            }
                            try {
                                Thread.sleep(100);
                            }
                            catch (InterruptedException var4_4) {
                                bl = true;
                            }
                        } while (true);
                        break;
                    } while (true);
                    catch (Throwable throwable) {
                        // MONITOREXIT : object
                        throw throwable;
                    }
                }
            }
            bl = this.isTerminateEvent(spiSlaveEvent);
        }
        this.g = 2;
    }
}

