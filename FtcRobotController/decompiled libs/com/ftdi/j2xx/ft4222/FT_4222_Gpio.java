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
import com.ftdi.j2xx.ft4222.b;
import com.ftdi.j2xx.ft4222.c;
import com.ftdi.j2xx.ft4222.d;
import com.ftdi.j2xx.ft4222.e;
import com.ftdi.j2xx.interfaces.Gpio;

public class FT_4222_Gpio
implements Gpio {
    private FT_4222_Device b;
    private FT_Device c;
    boolean a = true;

    public FT_4222_Gpio(FT_4222_Device ft4222Device) {
        this.b = ft4222Device;
        this.c = this.b.mFtDev;
    }

    public int cmdSet(int wValue1, int wValue2) {
        return this.c.VendorCmdSet(33, wValue1 | wValue2 << 8);
    }

    public int cmdSet(int wValue1, int wValue2, byte[] buf, int datalen) {
        return this.c.VendorCmdSet(33, wValue1 | wValue2 << 8, buf, datalen);
    }

    public int cmdGet(int wValue1, int wValue2, byte[] buf, int datalen) {
        return this.c.VendorCmdGet(32, wValue1 | wValue2 << 8, buf, datalen);
    }

    public int init(int[] gpio) {
        b b = this.b.mChipStatus;
        d d = new d();
        byte[] arrby = new byte[1];
        e e = new e();
        this.cmdSet(7, 0);
        this.cmdSet(6, 0);
        int n = this.b.init();
        if (n != 0) {
            Log.e((String)"GPIO_M", (String)("FT4222_GPIO init - 1 NG ftStatus:" + n));
            return n;
        }
        if (b.a == 2 || b.a == 3) {
            return 1013;
        }
        this.a(d);
        byte by = d.c;
        arrby[0] = d.d[0];
        for (int i = 0; i < 4; ++i) {
            by = gpio[i] == 1 ? (byte)((by | 1 << i) & 15) : (byte)(by & ~ (1 << i) & 15);
        }
        e.c = arrby[0];
        this.cmdSet(33, by);
        return 0;
    }

    public int read(int portNum, boolean[] bValue) {
        d d = new d();
        int n = this.a(portNum);
        if (n != 0) {
            return n;
        }
        n = this.a(d);
        if (n != 0) {
            return n;
        }
        this.a(portNum, d.d[0], bValue);
        return 0;
    }

    public int newRead(int portNum, boolean[] bValue) {
        int n = this.a(portNum);
        if (n != 0) {
            return n;
        }
        int n2 = this.c.getQueueStatus();
        if (n2 <= 0) {
            return -1;
        }
        byte[] arrby = new byte[n2];
        this.c.read(arrby, n2);
        this.a(portNum, arrby[n2 - 1], bValue);
        return n2;
    }

    public int write(int portNum, boolean bValue) {
        d d = new d();
        int n = this.a(portNum);
        if (n != 0) {
            return n;
        }
        if (!this.c(portNum)) {
            return 1015;
        }
        this.a(d);
        if (bValue) {
            byte[] arrby = d.d;
            arrby[0] = (byte)(arrby[0] | 1 << portNum);
        } else {
            byte[] arrby = d.d;
            arrby[0] = (byte)(arrby[0] & (~ (1 << portNum) & 15));
        }
        int n2 = this.c.write(d.d, 1);
        return n2;
    }

    public int newWrite(int portNum, boolean bValue) {
        d d = new d();
        int n = this.a(portNum);
        if (n != 0) {
            return n;
        }
        if (!this.c(portNum)) {
            return 1015;
        }
        this.a(d);
        if (bValue) {
            byte[] arrby = d.d;
            arrby[0] = (byte)(arrby[0] | 1 << portNum);
        } else {
            byte[] arrby = d.d;
            arrby[0] = (byte)(arrby[0] & (~ (1 << portNum) & 15));
        }
        if (this.a) {
            byte[] arrby = d.d;
            arrby[0] = (byte)(arrby[0] | 8);
        } else {
            byte[] arrby = d.d;
            arrby[0] = (byte)(arrby[0] & 7);
        }
        int n2 = this.c.write(d.d, 1);
        this.a = !this.a;
        return n2;
    }

    int a(int n) {
        b b = this.b.mChipStatus;
        if (b.a == 2 || b.a == 3) {
            return 1013;
        }
        if (n >= 4) {
            return 1014;
        }
        return 0;
    }

    int a(d d) {
        byte[] arrby = new byte[8];
        int n = this.cmdGet(32, 0, arrby, 8);
        d.a.a = arrby[0];
        d.a.b = arrby[1];
        d.b = arrby[5];
        d.c = arrby[6];
        d.d[0] = arrby[7];
        if (n == 8) {
            return 0;
        }
        return n;
    }

    void a(int n, byte by, boolean[] arrbl) {
        arrbl[0] = this.d((by & 1 << n) >> n & 1);
    }

    boolean b(int n) {
        b b = this.b.mChipStatus;
        boolean bl = true;
        switch (b.a) {
            case 0: {
                if ((n == 0 || n == 1) && (b.g == 1 || b.g == 2)) {
                    bl = false;
                }
                if (this.d(b.i) && n == 2) {
                    bl = false;
                }
                if (!this.d(b.j) || n != 3) break;
                bl = false;
                break;
            }
            case 1: {
                if (n == 0 || n == 1) {
                    bl = false;
                }
                if (this.d(b.i) && n == 2) {
                    bl = false;
                }
                if (!this.d(b.j) || n != 3) break;
                bl = false;
                break;
            }
            case 2: 
            case 3: {
                bl = false;
            }
        }
        return bl;
    }

    boolean c(int n) {
        d d = new d();
        boolean bl = this.b(n);
        this.a(d);
        if (bl && (d.c >> n & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    boolean d(int n) {
        return n != 0;
    }
}

