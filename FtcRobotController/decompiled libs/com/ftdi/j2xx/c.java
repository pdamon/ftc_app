/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx;

import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.FT_EEPROM;
import com.ftdi.j2xx.FT_EEPROM_2232H;
import com.ftdi.j2xx.k;

class c
extends k {
    c(FT_Device fT_Device) throws D2xxManager.D2xxException {
        super(fT_Device);
        this.a(12);
    }

    short a(FT_EEPROM fT_EEPROM) {
        int[] arrn = new int[this.b];
        if (fT_EEPROM.getClass() != FT_EEPROM_2232H.class) {
            return 1;
        }
        FT_EEPROM_2232H fT_EEPROM_2232H = (FT_EEPROM_2232H)fT_EEPROM;
        try {
            short s;
            short s2;
            if (!fT_EEPROM_2232H.A_UART) {
                if (fT_EEPROM_2232H.A_FIFO) {
                    int[] arrn2 = arrn;
                    arrn2[0] = arrn2[0] | 1;
                } else if (fT_EEPROM_2232H.A_FIFOTarget) {
                    int[] arrn3 = arrn;
                    arrn3[0] = arrn3[0] | 2;
                } else {
                    int[] arrn4 = arrn;
                    arrn4[0] = arrn4[0] | 4;
                }
            }
            if (fT_EEPROM_2232H.A_LoadVCP) {
                int[] arrn5 = arrn;
                arrn5[0] = arrn5[0] | 8;
            }
            if (!fT_EEPROM_2232H.B_UART) {
                if (fT_EEPROM_2232H.B_FIFO) {
                    int[] arrn6 = arrn;
                    arrn6[0] = arrn6[0] | 256;
                } else if (fT_EEPROM_2232H.B_FIFOTarget) {
                    int[] arrn7 = arrn;
                    arrn7[0] = arrn7[0] | 512;
                } else {
                    int[] arrn8 = arrn;
                    arrn8[0] = arrn8[0] | 1024;
                }
            }
            if (fT_EEPROM_2232H.B_LoadVCP) {
                int[] arrn9 = arrn;
                arrn9[0] = arrn9[0] | 2048;
            }
            if (fT_EEPROM_2232H.PowerSaveEnable) {
                int[] arrn10 = arrn;
                arrn10[0] = arrn10[0] | 32768;
            }
            arrn[1] = fT_EEPROM_2232H.VendorId;
            arrn[2] = fT_EEPROM_2232H.ProductId;
            arrn[3] = 1792;
            arrn[4] = this.a((Object)fT_EEPROM);
            arrn[5] = this.b(fT_EEPROM);
            arrn[6] = 0;
            byte by = fT_EEPROM_2232H.AL_DriveCurrent;
            if (by == -1) {
                by = 0;
            }
            int[] arrn11 = arrn;
            arrn11[6] = arrn11[6] | by;
            if (fT_EEPROM_2232H.AL_SlowSlew) {
                int[] arrn12 = arrn;
                arrn12[6] = arrn12[6] | 4;
            }
            if (fT_EEPROM_2232H.AL_SchmittInput) {
                int[] arrn13 = arrn;
                arrn13[6] = arrn13[6] | 8;
            }
            if ((s2 = fT_EEPROM_2232H.AH_DriveCurrent) == -1) {
                s2 = 0;
            }
            s2 = (short)(s2 << 4);
            int[] arrn14 = arrn;
            arrn14[6] = arrn14[6] | s2;
            if (fT_EEPROM_2232H.AH_SlowSlew) {
                int[] arrn15 = arrn;
                arrn15[6] = arrn15[6] | 64;
            }
            if (fT_EEPROM_2232H.AH_SchmittInput) {
                int[] arrn16 = arrn;
                arrn16[6] = arrn16[6] | 128;
            }
            if ((s = fT_EEPROM_2232H.BL_DriveCurrent) == -1) {
                s = 0;
            }
            s = (short)(s << 8);
            int[] arrn17 = arrn;
            arrn17[6] = arrn17[6] | s;
            if (fT_EEPROM_2232H.BL_SlowSlew) {
                int[] arrn18 = arrn;
                arrn18[6] = arrn18[6] | 1024;
            }
            if (fT_EEPROM_2232H.BL_SchmittInput) {
                int[] arrn19 = arrn;
                arrn19[6] = arrn19[6] | 2048;
            }
            short s3 = fT_EEPROM_2232H.BH_DriveCurrent;
            s3 = (short)(s3 << 12);
            int[] arrn20 = arrn;
            arrn20[6] = arrn20[6] | s3;
            if (fT_EEPROM_2232H.BH_SlowSlew) {
                int[] arrn21 = arrn;
                arrn21[6] = arrn21[6] | 16384;
            }
            if (fT_EEPROM_2232H.BH_SchmittInput) {
                int[] arrn22 = arrn;
                arrn22[6] = arrn22[6] | 32768;
            }
            boolean bl = false;
            int n = 77;
            if (this.a == 70) {
                n = 13;
                bl = true;
            }
            n = this.a(fT_EEPROM_2232H.Manufacturer, arrn, n, 7, bl);
            n = this.a(fT_EEPROM_2232H.Product, arrn, n, 8, bl);
            if (fT_EEPROM_2232H.SerNumEnable) {
                n = this.a(fT_EEPROM_2232H.SerialNumber, arrn, n, 9, bl);
            }
            switch (fT_EEPROM_2232H.TPRDRV) {
                case 0: {
                    arrn[11] = 0;
                    break;
                }
                case 1: {
                    arrn[11] = 8;
                    break;
                }
                case 2: {
                    arrn[11] = 16;
                    break;
                }
                case 3: {
                    arrn[11] = 24;
                    break;
                }
                default: {
                    arrn[11] = 0;
                }
            }
            arrn[12] = this.a;
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
        FT_EEPROM_2232H fT_EEPROM_2232H = new FT_EEPROM_2232H();
        int[] arrn = new int[this.b];
        if (this.c) {
            return fT_EEPROM_2232H;
        }
        try {
            short s;
            for (s = 0; s < this.b; s = (short)(s + 1)) {
                arrn[s] = this.a(s);
            }
            int n = 0;
            n = arrn[0];
            s = (short)(n & 7);
            switch (s) {
                case 0: {
                    fT_EEPROM_2232H.A_UART = true;
                    break;
                }
                case 1: {
                    fT_EEPROM_2232H.A_FIFO = true;
                    break;
                }
                case 2: {
                    fT_EEPROM_2232H.A_FIFOTarget = true;
                    break;
                }
                case 4: {
                    fT_EEPROM_2232H.A_FastSerial = true;
                    break;
                }
                default: {
                    fT_EEPROM_2232H.A_UART = true;
                }
            }
            short s2 = (short)((n & 8) >> 3);
            if (s2 == 1) {
                fT_EEPROM_2232H.A_LoadVCP = true;
                fT_EEPROM_2232H.A_LoadD2XX = false;
            } else {
                fT_EEPROM_2232H.A_LoadVCP = false;
                fT_EEPROM_2232H.A_LoadD2XX = true;
            }
            short s3 = (short)((n & 1792) >> 8);
            switch (s3) {
                case 0: {
                    fT_EEPROM_2232H.B_UART = true;
                    break;
                }
                case 1: {
                    fT_EEPROM_2232H.B_FIFO = true;
                    break;
                }
                case 2: {
                    fT_EEPROM_2232H.B_FIFOTarget = true;
                    break;
                }
                case 4: {
                    fT_EEPROM_2232H.B_FastSerial = true;
                    break;
                }
                default: {
                    fT_EEPROM_2232H.B_UART = true;
                }
            }
            short s4 = (short)((n & 2048) >> 11);
            if (s4 == 1) {
                fT_EEPROM_2232H.B_LoadVCP = true;
                fT_EEPROM_2232H.B_LoadD2XX = false;
            } else {
                fT_EEPROM_2232H.B_LoadVCP = false;
                fT_EEPROM_2232H.B_LoadD2XX = true;
            }
            short s5 = (short)((n & 32768) >> 15);
            fT_EEPROM_2232H.PowerSaveEnable = s5 == 1;
            fT_EEPROM_2232H.VendorId = (short)arrn[1];
            fT_EEPROM_2232H.ProductId = (short)arrn[2];
            this.a(fT_EEPROM_2232H, arrn[4]);
            this.a((Object)fT_EEPROM_2232H, arrn[5]);
            short s6 = (short)(arrn[6] & 3);
            switch (s6) {
                case 0: {
                    fT_EEPROM_2232H.AL_DriveCurrent = 0;
                    break;
                }
                case 1: {
                    fT_EEPROM_2232H.AL_DriveCurrent = 1;
                    break;
                }
                case 2: {
                    fT_EEPROM_2232H.AL_DriveCurrent = 2;
                    break;
                }
                case 3: {
                    fT_EEPROM_2232H.AL_DriveCurrent = 3;
                    break;
                }
            }
            short s7 = (short)(arrn[6] & 4);
            fT_EEPROM_2232H.AL_SlowSlew = s7 == 4;
            short s8 = (short)(arrn[6] & 8);
            fT_EEPROM_2232H.AL_SchmittInput = s8 == 8;
            short s9 = (short)((arrn[6] & 48) >> 4);
            switch (s9) {
                case 0: {
                    fT_EEPROM_2232H.AH_DriveCurrent = 0;
                    break;
                }
                case 1: {
                    fT_EEPROM_2232H.AH_DriveCurrent = 1;
                    break;
                }
                case 2: {
                    fT_EEPROM_2232H.AH_DriveCurrent = 2;
                    break;
                }
                case 3: {
                    fT_EEPROM_2232H.AH_DriveCurrent = 3;
                    break;
                }
            }
            short s10 = (short)(arrn[6] & 64);
            fT_EEPROM_2232H.AH_SlowSlew = s10 == 64;
            short s11 = (short)(arrn[6] & 128);
            fT_EEPROM_2232H.AH_SchmittInput = s11 == 128;
            short s12 = (short)((arrn[6] & 768) >> 8);
            switch (s12) {
                case 0: {
                    fT_EEPROM_2232H.BL_DriveCurrent = 0;
                    break;
                }
                case 1: {
                    fT_EEPROM_2232H.BL_DriveCurrent = 1;
                    break;
                }
                case 2: {
                    fT_EEPROM_2232H.BL_DriveCurrent = 2;
                    break;
                }
                case 3: {
                    fT_EEPROM_2232H.BL_DriveCurrent = 3;
                    break;
                }
            }
            short s13 = (short)(arrn[6] & 1024);
            fT_EEPROM_2232H.BL_SlowSlew = s13 == 1024;
            short s14 = (short)(arrn[6] & 2048);
            fT_EEPROM_2232H.BL_SchmittInput = s11 == 2048;
            short s15 = (short)((arrn[6] & 12288) >> 12);
            switch (s15) {
                case 0: {
                    fT_EEPROM_2232H.BH_DriveCurrent = 0;
                    break;
                }
                case 1: {
                    fT_EEPROM_2232H.BH_DriveCurrent = 1;
                    break;
                }
                case 2: {
                    fT_EEPROM_2232H.BH_DriveCurrent = 2;
                    break;
                }
                case 3: {
                    fT_EEPROM_2232H.BH_DriveCurrent = 3;
                    break;
                }
            }
            short s16 = (short)(arrn[6] & 16384);
            fT_EEPROM_2232H.BH_SlowSlew = s16 == 16384;
            short s17 = (short)(arrn[6] & 32768);
            fT_EEPROM_2232H.BH_SchmittInput = s17 == 32768;
            short s18 = (short)((arrn[11] & 24) >> 3);
            fT_EEPROM_2232H.TPRDRV = s18 < 4 ? (int)s18 : 0;
            int n2 = arrn[7] & 255;
            if (this.a == 70) {
                n2-=128;
                fT_EEPROM_2232H.Manufacturer = this.a(n2/=2, arrn);
                n2 = arrn[8] & 255;
                n2-=128;
                fT_EEPROM_2232H.Product = this.a(n2/=2, arrn);
                n2 = arrn[9] & 255;
                n2-=128;
                fT_EEPROM_2232H.SerialNumber = this.a(n2/=2, arrn);
            } else {
                fT_EEPROM_2232H.Manufacturer = this.a(n2/=2, arrn);
                n2 = arrn[8] & 255;
                fT_EEPROM_2232H.Product = this.a(n2/=2, arrn);
                n2 = arrn[9] & 255;
                fT_EEPROM_2232H.SerialNumber = this.a(n2/=2, arrn);
            }
            return fT_EEPROM_2232H;
        }
        catch (Exception var4_4) {
            return null;
        }
    }

    int b() {
        int n = this.a(9);
        int n2 = n & 255;
        n2/=2;
        int n3 = (n & 65280) >> 8;
        n2+=n3 / 2;
        return (this.b - 1 - 1 - ++n2) * 2;
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

