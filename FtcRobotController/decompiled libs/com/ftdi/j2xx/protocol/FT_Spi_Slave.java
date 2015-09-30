/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  junit.framework.Assert
 */
package com.ftdi.j2xx.protocol;

import com.ftdi.j2xx.interfaces.SpiSlave;
import com.ftdi.j2xx.protocol.SpiSlaveEvent;
import com.ftdi.j2xx.protocol.SpiSlaveListener;
import com.ftdi.j2xx.protocol.SpiSlaveRequestEvent;
import com.ftdi.j2xx.protocol.SpiSlaveResponseEvent;
import com.ftdi.j2xx.protocol.SpiSlaveThread;
import junit.framework.Assert;

public class FT_Spi_Slave
extends SpiSlaveThread {
    private a a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private byte[] g;
    private int h;
    private int i;
    private SpiSlave j;
    private SpiSlaveListener k;
    private boolean l;
    private static /* synthetic */ int[] m;

    public FT_Spi_Slave(SpiSlave pSlaveInterface) {
        this.j = pSlaveInterface;
        this.a = a.a;
    }

    public void registerSpiSlaveListener(SpiSlaveListener pListener) {
        this.k = pListener;
    }

    public int open() {
        if (this.l) {
            return 1;
        }
        this.l = true;
        this.j.init();
        this.start();
        return 0;
    }

    public int close() {
        if (!this.l) {
            return 3;
        }
        SpiSlaveRequestEvent spiSlaveRequestEvent = new SpiSlaveRequestEvent(-1, true, null, null, null);
        this.sendMessage(spiSlaveRequestEvent);
        this.l = false;
        return 0;
    }

    public int write(byte[] wrBuf) {
        if (!this.l) {
            return 3;
        }
        if (wrBuf.length > 65536) {
            return 1010;
        }
        int[] arrn = new int[1];
        int n = 0;
        int n2 = wrBuf.length;
        int n3 = this.a(wrBuf, 90, 129, this.i, n2);
        byte[] arrby = new byte[8 + wrBuf.length];
        arrby[n++] = 0;
        arrby[n++] = 90;
        arrby[n++] = -127;
        arrby[n++] = (byte)this.i;
        arrby[n++] = (byte)((n2 & 65280) >> 8);
        arrby[n++] = (byte)(n2 & 255);
        for (int i = 0; i < wrBuf.length; ++i) {
            arrby[n++] = wrBuf[i];
        }
        arrby[n++] = (byte)((n3 & 65280) >> 8);
        arrby[n++] = (byte)(n3 & 255);
        this.j.write(arrby, arrby.length, arrn);
        if (arrn[0] != arrby.length) {
            return 4;
        }
        ++this.i;
        if (this.i >= 256) {
            this.i = 0;
        }
        return 0;
    }

    private boolean a(int n) {
        if (n == 128 || n == 130 || n == 136) {
            return true;
        }
        return false;
    }

    private int a(byte[] arrby, int n, int n2, int n3, int n4) {
        int n5 = 0;
        if (arrby != null) {
            for (int i = 0; i < arrby.length; ++i) {
                n5+=arrby[i] & 255;
            }
        }
        n5+=n;
        n5+=n2;
        n5+=n3;
        n5+=(n4 & 65280) >> 8;
        return n5+=n4 & 255;
    }

    private void b() {
        int n = 0;
        byte[] arrby = new byte[8];
        arrby[n++] = 0;
        arrby[n++] = 90;
        arrby[n++] = -124;
        arrby[n++] = (byte)this.d;
        arrby[n++] = 0;
        arrby[n++] = 0;
        int n2 = this.a(null, 90, 132, this.d, 0);
        arrby[n++] = (byte)((n2 & 65280) >> 8);
        arrby[n++] = (byte)(n2 & 255);
        int[] arrn = new int[1];
        this.j.write(arrby, arrby.length, arrn);
    }

    private void a(byte[] arrby) {
        boolean bl = false;
        boolean bl2 = false;
        for (int i = 0; i < arrby.length; ++i) {
            int n = arrby[i] & 255;
            switch (FT_Spi_Slave.a()[this.a.ordinal()]) {
                case 1: {
                    if (n != 90) {
                        bl = true;
                        break;
                    }
                    this.a = a.b;
                    this.b = n;
                    break;
                }
                case 2: {
                    if (!this.a(n)) {
                        bl = true;
                        bl2 = true;
                    } else {
                        this.c = n;
                    }
                    this.a = a.c;
                    break;
                }
                case 3: {
                    this.d = n;
                    this.a = a.d;
                    break;
                }
                case 4: {
                    this.e = n * 256;
                    this.a = a.e;
                    break;
                }
                case 5: {
                    this.e+=n;
                    this.f = 0;
                    this.g = new byte[this.e];
                    this.a = a.f;
                    break;
                }
                case 6: {
                    this.g[this.f] = arrby[i];
                    ++this.f;
                    if (this.f != this.e) break;
                    this.a = a.g;
                    break;
                }
                case 7: {
                    this.h = n * 256;
                    this.a = a.h;
                    break;
                }
                case 8: {
                    this.h+=n;
                    int n2 = this.a(this.g, this.b, this.c, this.d, this.e);
                    if (this.h == n2) {
                        if (this.c == 128) {
                            this.b();
                            if (this.k != null) {
                                SpiSlaveResponseEvent spiSlaveResponseEvent = new SpiSlaveResponseEvent(3, 0, this.g, null, null);
                                this.k.OnDataReceived(spiSlaveResponseEvent);
                            }
                        }
                    } else {
                        bl2 = true;
                    }
                    bl = true;
                }
            }
            if (bl2 && this.k != null) {
                SpiSlaveResponseEvent spiSlaveResponseEvent = new SpiSlaveResponseEvent(3, 1, null, null, null);
                this.k.OnDataReceived(spiSlaveResponseEvent);
            }
            if (!bl) continue;
            this.a = a.a;
            this.b = 0;
            this.c = 0;
            this.d = 0;
            this.e = 0;
            this.f = 0;
            this.h = 0;
            this.g = null;
            bl = false;
            bl2 = false;
        }
    }

    protected boolean pollData() {
        Object object;
        int n = 0;
        int[] arrn = new int[1];
        n = this.j.getRxStatus(arrn);
        if (arrn[0] > 0 && n == 0 && (n = this.j.read((byte[])(object = new byte[arrn[0]]), object.length, arrn)) == 0) {
            this.a((byte[])object);
        }
        if (n == 4 && this.k != null) {
            object = new SpiSlaveResponseEvent(3, 2, this.g, null, null);
            this.k.OnDataReceived((SpiSlaveResponseEvent)object);
        }
        try {
            Thread.sleep(10);
        }
        catch (InterruptedException var3_4) {
            // empty catch block
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void requestEvent(SpiSlaveEvent pEvent) {
        if (pEvent instanceof SpiSlaveRequestEvent) {
            Object var2_2 = null;
            switch (pEvent.getEventType()) {
                case -1: {
                    return;
                }
            }
            return;
        }
        Assert.assertTrue((String)("processEvent wrong type" + pEvent.getEventType()), (boolean)false);
    }

    protected boolean isTerminateEvent(SpiSlaveEvent pEvent) {
        if (!Thread.interrupted()) {
            return true;
        }
        if (pEvent instanceof SpiSlaveRequestEvent) {
            switch (pEvent.getEventType()) {
                case -1: {
                    return true;
                }
            }
        } else {
            Assert.assertTrue((String)("processEvent wrong type" + pEvent.getEventType()), (boolean)false);
        }
        return false;
    }

    static /* synthetic */ int[] a() {
        int[] arrn;
        int[] arrn2 = m;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[a.values().length];
        try {
            arrn[a.g.ordinal()] = 7;
        }
        catch (NoSuchFieldError v1) {}
        try {
            arrn[a.h.ordinal()] = 8;
        }
        catch (NoSuchFieldError v2) {}
        try {
            arrn[a.b.ordinal()] = 2;
        }
        catch (NoSuchFieldError v3) {}
        try {
            arrn[a.f.ordinal()] = 6;
        }
        catch (NoSuchFieldError v4) {}
        try {
            arrn[a.d.ordinal()] = 4;
        }
        catch (NoSuchFieldError v5) {}
        try {
            arrn[a.e.ordinal()] = 5;
        }
        catch (NoSuchFieldError v6) {}
        try {
            arrn[a.c.ordinal()] = 3;
        }
        catch (NoSuchFieldError v7) {}
        try {
            arrn[a.a.ordinal()] = 1;
        }
        catch (NoSuchFieldError v8) {}
        m = arrn;
        return m;
    }

    private static enum a {
        a,
        b,
        c,
        d,
        e,
        f,
        g,
        h;
        

        private a(String string2, int n2) {
        }
    }

}

