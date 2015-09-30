/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.ftdi.j2xx;

import android.util.Log;
import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.n;
import com.ftdi.j2xx.o;

class p
implements Runnable {
    private o b;
    int a;

    p(o o) {
        this.b = o;
        this.a = this.b.b().getBufferNumber();
    }

    public void run() {
        n n = null;
        int n2 = 0;
        try {
            do {
                if ((n = this.b.c(n2)).b() > 0) {
                    this.b.a(n);
                    n.c();
                }
                this.b.d(n2);
                ++n2;
                n2%=this.a;
            } while (!Thread.interrupted());
            throw new InterruptedException();
        }
        catch (InterruptedException var3_3) {
            Log.d((String)"ProcessRequestThread::", (String)"Device has been closed.");
            var3_3.printStackTrace();
        }
        catch (Exception var3_4) {
            Log.e((String)"ProcessRequestThread::", (String)"Fatal error!");
            var3_4.printStackTrace();
        }
    }
}

