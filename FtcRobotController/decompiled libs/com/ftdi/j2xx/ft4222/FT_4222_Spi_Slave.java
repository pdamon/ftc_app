/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.ftdi.j2xx.ft4222;

import android.util.Log;
import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.ft4222.FT_4222_Device;
import com.ftdi.j2xx.ft4222.a;
import com.ftdi.j2xx.ft4222.b;
import com.ftdi.j2xx.interfaces.SpiSlave;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FT_4222_Spi_Slave
implements SpiSlave {
    private FT_4222_Device a;
    private FT_Device b;
    private Lock c;

    public FT_4222_Spi_Slave(FT_4222_Device pDevice) {
        this.a = pDevice;
        this.b = pDevice.mFtDev;
        this.c = new ReentrantLock();
    }

    public int init() {
        int n = 0;
        b b = this.a.mChipStatus;
        a a = this.a.mSpiMasterCfg;
        int n2 = 1;
        int n3 = 2;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        byte by = 1;
        int n7 = 4;
        a.a = n2;
        a.b = n3;
        a.c = n4;
        a.d = n5;
        a.e = by;
        this.c.lock();
        this.a.cleanRxData();
        if (this.b.VendorCmdSet(33, 66 | a.a << 8) < 0) {
            n = 4;
        }
        if (this.b.VendorCmdSet(33, 68 | a.b << 8) < 0) {
            n = 4;
        }
        if (this.b.VendorCmdSet(33, 69 | a.c << 8) < 0) {
            n = 4;
        }
        if (this.b.VendorCmdSet(33, 70 | a.d << 8) < 0) {
            n = 4;
        }
        if (this.b.VendorCmdSet(33, 67 | n6 << 8) < 0) {
            n = 4;
        }
        if (this.b.VendorCmdSet(33, 72 | a.e << 8) < 0) {
            n = 4;
        }
        if (this.b.VendorCmdSet(33, 5 | n7 << 8) < 0) {
            n = 4;
        }
        this.c.unlock();
        b.g = 4;
        return n;
    }

    public int getRxStatus(int[] pRxSize) {
        if (pRxSize == null) {
            return 1009;
        }
        int n = this.a();
        if (n != 0) {
            return n;
        }
        this.c.lock();
        int n2 = this.b.getQueueStatus();
        this.c.unlock();
        if (n2 >= 0) {
            pRxSize[0] = n2;
            n = 0;
        } else {
            pRxSize[0] = -1;
            n = 4;
        }
        return n;
    }

    public int read(byte[] buffer, int bufferSize, int[] sizeOfRead) {
        int n = 0;
        this.c.lock();
        if (!(this.b != null && this.b.isOpen())) {
            this.c.unlock();
            return 3;
        }
        int n2 = this.b.read(buffer, bufferSize);
        this.c.unlock();
        sizeOfRead[0] = n2;
        n = n2 >= 0 ? 0 : 4;
        return n;
    }

    public int write(byte[] buffer, int bufferSize, int[] sizeTransferred) {
        int n = 0;
        if (sizeTransferred == null || buffer == null) {
            return 1009;
        }
        n = this.a();
        if (n != 0) {
            return n;
        }
        if (bufferSize > 512) {
            return 1010;
        }
        this.c.lock();
        sizeTransferred[0] = this.b.write(buffer, bufferSize);
        this.c.unlock();
        if (sizeTransferred[0] != bufferSize) {
            Log.e((String)"FTDI_Device::", (String)("Error write =" + bufferSize + " tx=" + sizeTransferred[0]));
            n = 4;
        }
        return n;
    }

    private int a() {
        b b = this.a.mChipStatus;
        if (b.g != 4) {
            return 1003;
        }
        return 0;
    }

    public int reset() {
        int n = 0;
        int n2 = 0;
        this.c.lock();
        if (this.b.VendorCmdSet(33, 74 | n << 8) < 0) {
            n2 = 4;
        }
        this.c.unlock();
        return n2;
    }

    public int setDrivingStrength(int clkStrength, int ioStrength, int ssoStregth) {
        int n = 0;
        b b = this.a.mChipStatus;
        if (b.g != 3 && b.g != 4) {
            return 1003;
        }
        int n2 = clkStrength << 4;
        n2|=ioStrength << 2;
        int n3 = b.g == 3 ? 3 : 4;
        this.c.lock();
        if (this.b.VendorCmdSet(33, 160 | (n2|=ssoStregth) << 8) < 0) {
            n = 4;
        }
        if (this.b.VendorCmdSet(33, 5 | n3 << 8) < 0) {
            n = 4;
        }
        this.c.unlock();
        return n;
    }
}

