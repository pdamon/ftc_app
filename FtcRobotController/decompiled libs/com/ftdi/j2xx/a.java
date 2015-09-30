/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.hardware.usb.UsbDeviceConnection
 *  android.hardware.usb.UsbEndpoint
 *  android.util.Log
 */
package com.ftdi.j2xx;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.util.Log;
import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.n;
import com.ftdi.j2xx.o;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.Semaphore;

class a
implements Runnable {
    UsbDeviceConnection a;
    UsbEndpoint b;
    o c;
    FT_Device d;
    int e;
    int f;
    int g;
    Semaphore h;
    boolean i;

    a(FT_Device fT_Device, o o, UsbDeviceConnection usbDeviceConnection, UsbEndpoint usbEndpoint) {
        this.d = fT_Device;
        this.b = usbEndpoint;
        this.a = usbDeviceConnection;
        this.c = o;
        this.e = this.c.b().getBufferNumber();
        this.f = this.c.b().getMaxTransferSize();
        this.g = this.d.d().getReadTimeout();
        this.h = new Semaphore(1);
        this.i = false;
    }

    void a() throws InterruptedException {
        this.h.acquire();
        this.i = true;
    }

    void b() {
        this.i = false;
        this.h.release();
    }

    boolean c() {
        return this.i;
    }

    public void run() {
        ByteBuffer byteBuffer = null;
        n n = null;
        int n2 = 0;
        int n3 = 0;
        Object object = null;
        try {
            do {
                if (this.i) {
                    this.h.acquire();
                    this.h.release();
                }
                if ((n = this.c.b(n2)).b() == 0) {
                    byteBuffer = n.a();
                    byteBuffer.clear();
                    n.a(n2);
                    object = byteBuffer.array();
                    n3 = this.a.bulkTransfer(this.b, (byte[])object, this.f, this.g);
                    if (n3 > 0) {
                        byteBuffer.position(n3);
                        byteBuffer.flip();
                        n.b(n3);
                        this.c.e(n2);
                    }
                }
                ++n2;
                n2%=this.e;
            } while (!Thread.interrupted());
            throw new InterruptedException();
        }
        catch (InterruptedException var6_6) {
            try {
                this.c.f();
                this.c.e();
            }
            catch (Exception var7_8) {
                Log.d((String)"BulkIn::", (String)"Stop BulkIn thread");
                var7_8.printStackTrace();
            }
        }
        catch (Exception var6_7) {
            var6_7.printStackTrace();
            Log.e((String)"BulkIn::", (String)"Fatal error in BulkIn thread");
        }
    }
}

