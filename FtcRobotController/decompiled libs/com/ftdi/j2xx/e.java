/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx;

import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.FT_EEPROM;
import com.ftdi.j2xx.k;

class e
extends k {
    e(FT_Device fT_Device) {
        super(fT_Device);
    }

    short a(FT_EEPROM fT_EEPROM) {
        int[] arrn = new int[64];
        if (fT_EEPROM.getClass() != FT_EEPROM.class) {
            return 1;
        }
        FT_EEPROM fT_EEPROM2 = fT_EEPROM;
        try {
            arrn[1] = fT_EEPROM2.VendorId;
            arrn[2] = fT_EEPROM2.ProductId;
            arrn[3] = 512;
            arrn[4] = this.a((Object)fT_EEPROM);
            int n = 10;
            n = this.a(fT_EEPROM2.Manufacturer, arrn, n, 7, true);
            n+=fT_EEPROM2.Manufacturer.length() + 2;
            n = this.a(fT_EEPROM2.Product, arrn, n, 8, true);
            n+=fT_EEPROM2.Product.length() + 2;
            if (fT_EEPROM2.SerNumEnable) {
                n = this.a(fT_EEPROM2.SerialNumber, arrn, n, 9, true);
                n+=fT_EEPROM2.SerialNumber.length() + 2;
            }
            if (arrn[1] != 0 && arrn[2] != 0) {
                boolean bl = false;
                bl = this.a(arrn, 63);
                if (bl) {
                    return 0;
                }
                return 1;
            }
            return 2;
        }
        catch (Exception var4_5) {
            var4_5.printStackTrace();
            return 0;
        }
    }

    FT_EEPROM a() {
        FT_EEPROM fT_EEPROM = new FT_EEPROM();
        int[] arrn = new int[64];
        try {
            int n;
            for (n = 0; n < 64; ++n) {
                arrn[n] = this.a((short)n);
            }
            fT_EEPROM.VendorId = (short)arrn[1];
            fT_EEPROM.ProductId = (short)arrn[2];
            this.a(fT_EEPROM, arrn[4]);
            n = 10;
            fT_EEPROM.Manufacturer = this.a(n, arrn);
            fT_EEPROM.Product = this.a(n+=fT_EEPROM.Manufacturer.length() + 1, arrn);
            fT_EEPROM.SerialNumber = this.a(n+=fT_EEPROM.Product.length() + 1, arrn);
            return fT_EEPROM;
        }
        catch (Exception var3_4) {
            return null;
        }
    }

    int b() {
        int n = this.a(7);
        int n2 = (n & 65280) >> 8;
        n = this.a(8);
        int n3 = (n & 65280) >> 8;
        int n4 = 10 + (n2/=2) + (n3/=2) + 1;
        n = this.a(9);
        int n5 = (n & 65280) >> 8;
        return (63 - n4 - 1 - (n5/=2)) * 2;
    }

    int a(byte[] arrby) {
        int n;
        int n2 = 0;
        short s = 0;
        if (arrby.length > this.b()) {
            return 0;
        }
        int[] arrn = new int[64];
        for (n = 0; n < 64; n = (short)(n + 1)) {
            arrn[n] = this.a((short)n);
        }
        s = (short)(63 - this.b() / 2 - 1);
        s = (short)(s & 65535);
        for (n = 0; n < arrby.length; n+=2) {
            n2 = n + 1 < arrby.length ? arrby[n + 1] & 255 : 0;
            n2<<=8;
            short s2 = s;
            s = (short)(s2 + 1);
            arrn[s2] = n2|=arrby[n] & 255;
        }
        if (arrn[1] != 0 && arrn[2] != 0) {
            n = 0;
            n = (int)this.a(arrn, 63) ? 1 : 0;
            if (n == 0) {
                return 0;
            }
        } else {
            return 0;
        }
        return arrby.length;
    }

    byte[] a(int n) {
        byte by = 0;
        byte by2 = 0;
        int n2 = 0;
        byte[] arrby = new byte[n];
        if (n == 0 || n > this.b() - 1) {
            return null;
        }
        short s = (short)(63 - this.b() / 2 - 1);
        s = (short)(s & 65535);
        for (int i = 0; i < n; i+=2) {
            short s2 = s;
            s = (short)(s2 + 1);
            n2 = this.a(s2);
            if (i + 1 < arrby.length) {
                arrby[i + 1] = by = (byte)(n2 & 255);
            } else {
                by2 = 0;
            }
            arrby[i] = by2 = (byte)((n2 & 65280) >> 8);
        }
        return arrby;
    }
}

