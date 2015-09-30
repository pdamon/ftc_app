/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx;

import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.FT_EEPROM;
import com.ftdi.j2xx.FT_EEPROM_2232D;
import com.ftdi.j2xx.k;

class d
extends k {
    d(FT_Device fT_Device) throws D2xxManager.D2xxException {
        super(fT_Device);
        this.a(10);
    }

    short a(FT_EEPROM fT_EEPROM) {
        int[] arrn = new int[this.b];
        if (fT_EEPROM.getClass() != FT_EEPROM_2232D.class) {
            return 1;
        }
        FT_EEPROM_2232D fT_EEPROM_2232D = (FT_EEPROM_2232D)fT_EEPROM;
        try {
            arrn[0] = 0;
            if (fT_EEPROM_2232D.A_FIFO) {
                int[] arrn2 = arrn;
                arrn2[0] = arrn2[0] | 1;
            } else if (fT_EEPROM_2232D.A_FIFOTarget) {
                int[] arrn3 = arrn;
                arrn3[0] = arrn3[0] | 2;
            } else {
                int[] arrn4 = arrn;
                arrn4[0] = arrn4[0] | 4;
            }
            if (fT_EEPROM_2232D.A_HighIO) {
                int[] arrn5 = arrn;
                arrn5[0] = arrn5[0] | 16;
            }
            if (fT_EEPROM_2232D.A_LoadVCP) {
                int[] arrn6 = arrn;
                arrn6[0] = arrn6[0] | 8;
            } else if (fT_EEPROM_2232D.B_FIFO) {
                int[] arrn7 = arrn;
                arrn7[0] = arrn7[0] | 256;
            } else if (fT_EEPROM_2232D.B_FIFOTarget) {
                int[] arrn8 = arrn;
                arrn8[0] = arrn8[0] | 512;
            } else {
                int[] arrn9 = arrn;
                arrn9[0] = arrn9[0] | 1024;
            }
            if (fT_EEPROM_2232D.B_HighIO) {
                int[] arrn10 = arrn;
                arrn10[0] = arrn10[0] | 4096;
            }
            if (fT_EEPROM_2232D.B_LoadVCP) {
                int[] arrn11 = arrn;
                arrn11[0] = arrn11[0] | 2048;
            }
            arrn[1] = fT_EEPROM_2232D.VendorId;
            arrn[2] = fT_EEPROM_2232D.ProductId;
            arrn[3] = 1280;
            arrn[4] = this.a((Object)fT_EEPROM);
            arrn[4] = this.b(fT_EEPROM);
            boolean bl = false;
            int n = 75;
            if (this.a == 70) {
                n = 11;
                bl = true;
            }
            n = this.a(fT_EEPROM_2232D.Manufacturer, arrn, n, 7, bl);
            n = this.a(fT_EEPROM_2232D.Product, arrn, n, 8, bl);
            if (fT_EEPROM_2232D.SerNumEnable) {
                n = this.a(fT_EEPROM_2232D.SerialNumber, arrn, n, 9, bl);
            }
            arrn[10] = this.a;
            if (arrn[1] != 0 && arrn[2] != 0) {
                boolean bl2 = false;
                bl2 = this.a(arrn, this.b - 1);
                if (bl2) {
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
        FT_EEPROM_2232D fT_EEPROM_2232D = new FT_EEPROM_2232D();
        int[] arrn = new int[this.b];
        try {
            int n;
            for (n = 0; n < this.b; ++n) {
                arrn[n] = this.a((short)n);
            }
            n = (short)(arrn[0] & 7);
            switch (n) {
                case 0: {
                    fT_EEPROM_2232D.A_UART = true;
                    break;
                }
                case 1: {
                    fT_EEPROM_2232D.A_FIFO = true;
                    break;
                }
                case 2: {
                    fT_EEPROM_2232D.A_FIFOTarget = true;
                    break;
                }
                case 4: {
                    fT_EEPROM_2232D.A_FastSerial = true;
                    break;
                }
            }
            short s = (short)((arrn[0] & 8) >> 3);
            if (s == 1) {
                fT_EEPROM_2232D.A_LoadVCP = true;
            } else {
                fT_EEPROM_2232D.A_HighIO = true;
            }
            short s2 = (short)((arrn[0] & 16) >> 4);
            if (s2 == 1) {
                fT_EEPROM_2232D.A_HighIO = true;
            }
            short s3 = (short)((arrn[0] & 1792) >> 8);
            switch (s3) {
                case 0: {
                    fT_EEPROM_2232D.B_UART = true;
                    break;
                }
                case 1: {
                    fT_EEPROM_2232D.B_FIFO = true;
                    break;
                }
                case 2: {
                    fT_EEPROM_2232D.B_FIFOTarget = true;
                    break;
                }
                case 4: {
                    fT_EEPROM_2232D.B_FastSerial = true;
                    break;
                }
            }
            short s4 = (short)((arrn[0] & 2048) >> 11);
            if (s4 == 1) {
                fT_EEPROM_2232D.B_LoadVCP = true;
            } else {
                fT_EEPROM_2232D.B_LoadD2XX = true;
            }
            short s5 = (short)((arrn[0] & 4096) >> 12);
            if (s5 == 1) {
                fT_EEPROM_2232D.B_HighIO = true;
            }
            fT_EEPROM_2232D.VendorId = (short)arrn[1];
            fT_EEPROM_2232D.ProductId = (short)arrn[2];
            this.a(fT_EEPROM_2232D, arrn[4]);
            int n2 = arrn[7] & 255;
            if (this.a == 70) {
                n2-=128;
                fT_EEPROM_2232D.Manufacturer = this.a(n2/=2, arrn);
                n2 = arrn[8] & 255;
                n2-=128;
                fT_EEPROM_2232D.Product = this.a(n2/=2, arrn);
                n2 = arrn[9] & 255;
                n2-=128;
                fT_EEPROM_2232D.SerialNumber = this.a(n2/=2, arrn);
            } else {
                fT_EEPROM_2232D.Manufacturer = this.a(n2/=2, arrn);
                n2 = arrn[8] & 255;
                fT_EEPROM_2232D.Product = this.a(n2/=2, arrn);
                n2 = arrn[9] & 255;
                fT_EEPROM_2232D.SerialNumber = this.a(n2/=2, arrn);
            }
            return fT_EEPROM_2232D;
        }
        catch (Exception var3_4) {
            return null;
        }
    }

    int b() {
        int n = this.a(9);
        int n2 = n & 255;
        int n3 = (n & 65280) >> 8;
        return (this.b - 1 - 1 - (n2+=n3 / 2)) * 2;
    }

    int a(byte[] arrby) {
        int n;
        int n2 = 0;
        short s = 0;
        if (arrby.length > this.b()) {
            return 0;
        }
        int[] arrn = new int[this.b];
        for (n = 0; n < this.b; n = (short)(n + 1)) {
            arrn[n] = this.a((short)n);
        }
        s = (short)(this.b - this.b() / 2 - 1 - 1);
        for (n = 0; n < arrby.length; n+=2) {
            n2 = n + 1 < arrby.length ? arrby[n + 1] & 255 : 0;
            n2<<=8;
            short s2 = s;
            s = (short)(s2 + 1);
            arrn[s2] = n2|=arrby[n] & 255;
        }
        if (arrn[1] != 0 && arrn[2] != 0) {
            n = 0;
            n = (int)this.a(arrn, this.b - 1) ? 1 : 0;
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
        if (n == 0 || n > this.b()) {
            return null;
        }
        short s = (short)(this.b - this.b() / 2 - 1 - 1);
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

