/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.hardware.usb.UsbDeviceConnection
 *  android.util.Log
 */
package com.ftdi.j2xx;

import android.hardware.usb.UsbDeviceConnection;
import android.util.Log;
import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.FT_EEPROM;

class k {
    private FT_Device d;
    short a;
    int b;
    boolean c;

    k(FT_Device fT_Device) {
        this.d = fT_Device;
    }

    int a(short s) {
        byte[] arrby = new byte[2];
        int n = -1;
        if (s >= 1024) {
            return n;
        }
        short s2 = s;
        this.d.c().controlTransfer(-64, 144, 0, (int)s2, arrby, 2, 0);
        int n2 = arrby[1] & 255;
        n2<<=8;
        return n2|=arrby[0] & 255;
    }

    boolean a(short s, short s2) {
        int n = s2 & 65535;
        int n2 = s & 65535;
        int n3 = 0;
        boolean bl = false;
        if (s >= 1024) {
            return bl;
        }
        n3 = this.d.c().controlTransfer(64, 145, n, n2, null, 0, 0);
        if (n3 == 0) {
            bl = true;
        }
        return bl;
    }

    int c() {
        int n = 0;
        n = this.d.c().controlTransfer(64, 146, 0, 0, null, 0, 0);
        return n;
    }

    short a(FT_EEPROM fT_EEPROM) {
        return 1;
    }

    boolean a(int[] arrn, int n) {
        int n2 = n;
        int n3 = 43690;
        int n4 = 0;
        short s = 0;
        short s2 = 0;
        for (int i = 0; i < n2; ++i) {
            this.a((short)i, (short)arrn[i]);
            n4 = arrn[i] ^ n3;
            s = (short)((n4&=65535) << 1 & 65535);
            s2 = (short)(n4 >> 15 & 65535);
            n3 = s | s2;
            Log.d((String)"FT_EE_Ctrl", (String)("Entered WriteWord Checksum : " + (n3&=65535)));
        }
        this.a((short)n2, (short)n3);
        return true;
    }

    FT_EEPROM a() {
        return null;
    }

    int a(Object object) {
        FT_EEPROM fT_EEPROM = (FT_EEPROM)object;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        n2|=128;
        if (fT_EEPROM.RemoteWakeup) {
            n2|=32;
        }
        if (fT_EEPROM.SelfPowered) {
            n2|=64;
        }
        n3 = fT_EEPROM.MaxPower;
        n3/=2;
        n = (n3<<=8) | n2;
        return n;
    }

    void a(FT_EEPROM fT_EEPROM, int n) {
        byte by = (byte)(n >> 8);
        fT_EEPROM.MaxPower = (short)(2 * by);
        byte by2 = (byte)n;
        fT_EEPROM.SelfPowered = (by2 & 64) == 64 && (by2 & 128) == 128;
        fT_EEPROM.RemoteWakeup = (by2 & 32) == 32;
    }

    int b(Object object) {
        FT_EEPROM fT_EEPROM = (FT_EEPROM)object;
        int n = 0;
        n = fT_EEPROM.PullDownEnable ? (n|=4) : (n&=251);
        n = fT_EEPROM.SerNumEnable ? (n|=8) : (n&=247);
        return n;
    }

    void a(Object object, int n) {
        FT_EEPROM fT_EEPROM = (FT_EEPROM)object;
        fT_EEPROM.PullDownEnable = (n & 4) > 0;
        fT_EEPROM.SerNumEnable = (n & 8) > 0;
    }

    int a(String string, int[] arrn, int n, int n2, boolean bl) {
        int n3 = 0;
        int n4 = string.length() * 2 + 2;
        arrn[n2] = n4 << 8 | n * 2;
        if (bl) {
            int[] arrn2 = arrn;
            int n5 = n2;
            arrn2[n5] = arrn2[n5] + 128;
        }
        char[] arrc = string.toCharArray();
        arrn[n++] = n4 | 768;
        n4-=2;
        do {
            arrn[n++] = arrc[n3];
        } while (++n3 < (n4/=2));
        return n;
    }

    String a(int n, int[] arrn) {
        String string = "";
        int n2 = arrn[n] & 255;
        n2 = n2 / 2 - 1;
        int n3 = ++n + n2;
        for (int i = n; i < n3; ++i) {
            string = String.valueOf(string) + (char)arrn[i];
        }
        return string;
    }

    int a(byte by) throws D2xxManager.D2xxException {
        int n = 192;
        int n2 = 0;
        short s = 0;
        short s2 = (short)(by & -1);
        int[] arrn = new int[3];
        boolean bl = false;
        s = (short)this.a(s2);
        if (s != 65535) {
            switch (s) {
                case 70: {
                    this.a = 70;
                    this.b = 64;
                    this.c = false;
                    return 64;
                }
                case 86: {
                    this.a = 86;
                    this.b = 128;
                    this.c = false;
                    return 128;
                }
                case 102: {
                    this.a = 102;
                    this.b = 128;
                    this.c = false;
                    return 256;
                }
                case 82: {
                    this.a = 82;
                    this.b = 1024;
                    this.c = false;
                    return 1024;
                }
            }
            return 0;
        }
        s2 = 192;
        bl = this.a(s2, (short)n);
        arrn[0] = this.a(192);
        arrn[1] = this.a(64);
        arrn[2] = this.a(0);
        if (!bl) {
            this.a = 255;
            this.b = 0;
            return 0;
        }
        this.c = true;
        s2 = 0;
        n2 = this.a(s2);
        if ((n2 & 255) == 192) {
            this.c();
            this.a = 70;
            this.b = 64;
            return 64;
        }
        s2 = 64;
        n2 = this.a(s2);
        if ((n2 & 255) == 192) {
            this.c();
            this.a = 86;
            this.b = 128;
            return 128;
        }
        s2 = 192;
        n2 = this.a(s2);
        if ((n2 & 255) == 192) {
            this.c();
            this.a = 102;
            this.b = 128;
            return 256;
        }
        this.c();
        return 0;
    }

    int a(byte[] arrby) {
        return 0;
    }

    byte[] a(int n) {
        return null;
    }

    int b() {
        return 0;
    }
}

