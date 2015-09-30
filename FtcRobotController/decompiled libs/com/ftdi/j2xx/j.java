/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx;

import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.FT_EEPROM;
import com.ftdi.j2xx.FT_EEPROM_4232H;
import com.ftdi.j2xx.k;

class j
extends k {
    j(FT_Device fT_Device) throws D2xxManager.D2xxException {
        super(fT_Device);
        this.a(12);
    }

    short a(FT_EEPROM fT_EEPROM) {
        int[] arrn = new int[this.b];
        if (fT_EEPROM.getClass() != FT_EEPROM_4232H.class) {
            return 1;
        }
        FT_EEPROM_4232H fT_EEPROM_4232H = (FT_EEPROM_4232H)fT_EEPROM;
        try {
            short s;
            short s2;
            arrn[0] = 0;
            if (fT_EEPROM_4232H.AL_LoadVCP) {
                int[] arrn2 = arrn;
                arrn2[0] = arrn2[0] | 8;
            }
            if (fT_EEPROM_4232H.BL_LoadVCP) {
                int[] arrn3 = arrn;
                arrn3[0] = arrn3[0] | 128;
            }
            if (fT_EEPROM_4232H.AH_LoadVCP) {
                int[] arrn4 = arrn;
                arrn4[0] = arrn4[0] | 2048;
            }
            if (fT_EEPROM_4232H.BH_LoadVCP) {
                int[] arrn5 = arrn;
                arrn5[0] = arrn5[0] | 32768;
            }
            arrn[1] = fT_EEPROM_4232H.VendorId;
            arrn[2] = fT_EEPROM_4232H.ProductId;
            arrn[3] = 2048;
            arrn[4] = this.a((Object)fT_EEPROM);
            arrn[5] = this.b(fT_EEPROM);
            if (fT_EEPROM_4232H.AL_LoadRI_RS485) {
                arrn[5] = (short)(arrn[5] | 4096);
            }
            if (fT_EEPROM_4232H.AH_LoadRI_RS485) {
                arrn[5] = (short)(arrn[5] | 8192);
            }
            if (fT_EEPROM_4232H.BL_LoadRI_RS485) {
                arrn[5] = (short)(arrn[5] | 16384);
            }
            if (fT_EEPROM_4232H.BH_LoadRI_RS485) {
                arrn[5] = (short)(arrn[5] | 32768);
            }
            arrn[6] = 0;
            byte by = fT_EEPROM_4232H.AL_DriveCurrent;
            if (by == -1) {
                by = 0;
            }
            int[] arrn6 = arrn;
            arrn6[6] = arrn6[6] | by;
            if (fT_EEPROM_4232H.AL_SlowSlew) {
                int[] arrn7 = arrn;
                arrn7[6] = arrn7[6] | 4;
            }
            if (fT_EEPROM_4232H.AL_SchmittInput) {
                int[] arrn8 = arrn;
                arrn8[6] = arrn8[6] | 8;
            }
            if ((s2 = fT_EEPROM_4232H.AH_DriveCurrent) == -1) {
                s2 = 0;
            }
            s2 = (short)(s2 << 4);
            int[] arrn9 = arrn;
            arrn9[6] = arrn9[6] | s2;
            if (fT_EEPROM_4232H.AH_SlowSlew) {
                int[] arrn10 = arrn;
                arrn10[6] = arrn10[6] | 64;
            }
            if (fT_EEPROM_4232H.AH_SchmittInput) {
                int[] arrn11 = arrn;
                arrn11[6] = arrn11[6] | 128;
            }
            if ((s = fT_EEPROM_4232H.BL_DriveCurrent) == -1) {
                s = 0;
            }
            s = (short)(s << 8);
            int[] arrn12 = arrn;
            arrn12[6] = arrn12[6] | s;
            if (fT_EEPROM_4232H.BL_SlowSlew) {
                int[] arrn13 = arrn;
                arrn13[6] = arrn13[6] | 1024;
            }
            if (fT_EEPROM_4232H.BL_SchmittInput) {
                int[] arrn14 = arrn;
                arrn14[6] = arrn14[6] | 2048;
            }
            short s3 = fT_EEPROM_4232H.BH_DriveCurrent;
            s3 = (short)(s3 << 12);
            int[] arrn15 = arrn;
            arrn15[6] = arrn15[6] | s3;
            if (fT_EEPROM_4232H.BH_SlowSlew) {
                int[] arrn16 = arrn;
                arrn16[6] = arrn16[6] | 16384;
            }
            if (fT_EEPROM_4232H.BH_SchmittInput) {
                int[] arrn17 = arrn;
                arrn17[6] = arrn17[6] | 32768;
            }
            boolean bl = false;
            int n = 77;
            if (this.a == 70) {
                n = 13;
                bl = true;
            }
            n = this.a(fT_EEPROM_4232H.Manufacturer, arrn, n, 7, bl);
            n = this.a(fT_EEPROM_4232H.Product, arrn, n, 8, bl);
            if (fT_EEPROM_4232H.SerNumEnable) {
                n = this.a(fT_EEPROM_4232H.SerialNumber, arrn, n, 9, bl);
            }
            switch (fT_EEPROM_4232H.TPRDRV) {
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
        FT_EEPROM_4232H fT_EEPROM_4232H = new FT_EEPROM_4232H();
        int[] arrn = new int[this.b];
        if (this.c) {
            return fT_EEPROM_4232H;
        }
        try {
            short s;
            for (s = 0; s < this.b; s = (short)(s + 1)) {
                arrn[s] = this.a(s);
            }
            s = (short)((arrn[0] & 8) >> 3);
            if (s == 1) {
                fT_EEPROM_4232H.AL_LoadVCP = true;
                fT_EEPROM_4232H.AL_LoadD2XX = false;
            } else {
                fT_EEPROM_4232H.AL_LoadVCP = false;
                fT_EEPROM_4232H.AL_LoadD2XX = true;
            }
            short s2 = (short)((arrn[0] & 128) >> 7);
            if (s2 == 1) {
                fT_EEPROM_4232H.BL_LoadVCP = true;
                fT_EEPROM_4232H.BL_LoadD2XX = false;
            } else {
                fT_EEPROM_4232H.BL_LoadVCP = false;
                fT_EEPROM_4232H.BL_LoadD2XX = true;
            }
            short s3 = (short)((arrn[0] & 2048) >> 11);
            if (s3 == 1) {
                fT_EEPROM_4232H.AH_LoadVCP = true;
                fT_EEPROM_4232H.AH_LoadD2XX = false;
            } else {
                fT_EEPROM_4232H.AH_LoadVCP = false;
                fT_EEPROM_4232H.AH_LoadD2XX = true;
            }
            short s4 = (short)((arrn[0] & 32768) >> 15);
            if (s4 == 1) {
                fT_EEPROM_4232H.BH_LoadVCP = true;
                fT_EEPROM_4232H.BH_LoadD2XX = false;
            } else {
                fT_EEPROM_4232H.BH_LoadVCP = false;
                fT_EEPROM_4232H.BH_LoadD2XX = true;
            }
            fT_EEPROM_4232H.VendorId = (short)arrn[1];
            fT_EEPROM_4232H.ProductId = (short)arrn[2];
            this.a(fT_EEPROM_4232H, arrn[4]);
            this.a((Object)fT_EEPROM_4232H, arrn[5]);
            if ((arrn[5] & 4096) == 4096) {
                fT_EEPROM_4232H.AL_LoadRI_RS485 = true;
            }
            if ((arrn[5] & 8192) == 8192) {
                fT_EEPROM_4232H.AH_LoadRI_RS485 = true;
            }
            if ((arrn[5] & 16384) == 16384) {
                fT_EEPROM_4232H.AH_LoadRI_RS485 = true;
            }
            if ((arrn[5] & 32768) == 32768) {
                fT_EEPROM_4232H.AH_LoadRI_RS485 = true;
            }
            short s5 = (short)(arrn[6] & 3);
            switch (s5) {
                case 0: {
                    fT_EEPROM_4232H.AL_DriveCurrent = 0;
                    break;
                }
                case 1: {
                    fT_EEPROM_4232H.AL_DriveCurrent = 1;
                    break;
                }
                case 2: {
                    fT_EEPROM_4232H.AL_DriveCurrent = 2;
                    break;
                }
                case 3: {
                    fT_EEPROM_4232H.AL_DriveCurrent = 3;
                    break;
                }
            }
            short s6 = (short)(arrn[6] & 4);
            fT_EEPROM_4232H.AL_SlowSlew = s6 == 4;
            short s7 = (short)(arrn[6] & 8);
            fT_EEPROM_4232H.AL_SchmittInput = s7 == 8;
            short s8 = (short)((arrn[6] & 48) >> 4);
            switch (s8) {
                case 0: {
                    fT_EEPROM_4232H.AH_DriveCurrent = 0;
                    break;
                }
                case 1: {
                    fT_EEPROM_4232H.AH_DriveCurrent = 1;
                    break;
                }
                case 2: {
                    fT_EEPROM_4232H.AH_DriveCurrent = 2;
                    break;
                }
                case 3: {
                    fT_EEPROM_4232H.AH_DriveCurrent = 3;
                    break;
                }
            }
            short s9 = (short)(arrn[6] & 64);
            fT_EEPROM_4232H.AH_SlowSlew = s9 == 64;
            short s10 = (short)(arrn[6] & 128);
            fT_EEPROM_4232H.AH_SchmittInput = s10 == 128;
            short s11 = (short)((arrn[6] & 768) >> 8);
            switch (s11) {
                case 0: {
                    fT_EEPROM_4232H.BL_DriveCurrent = 0;
                    break;
                }
                case 1: {
                    fT_EEPROM_4232H.BL_DriveCurrent = 1;
                    break;
                }
                case 2: {
                    fT_EEPROM_4232H.BL_DriveCurrent = 2;
                    break;
                }
                case 3: {
                    fT_EEPROM_4232H.BL_DriveCurrent = 3;
                    break;
                }
            }
            short s12 = (short)(arrn[6] & 1024);
            fT_EEPROM_4232H.BL_SlowSlew = s12 == 1024;
            short s13 = (short)(arrn[6] & 2048);
            fT_EEPROM_4232H.BL_SchmittInput = s13 == 2048;
            short s14 = (short)((arrn[6] & 12288) >> 12);
            switch (s14) {
                case 0: {
                    fT_EEPROM_4232H.BH_DriveCurrent = 0;
                    break;
                }
                case 1: {
                    fT_EEPROM_4232H.BH_DriveCurrent = 1;
                    break;
                }
                case 2: {
                    fT_EEPROM_4232H.BH_DriveCurrent = 2;
                    break;
                }
                case 3: {
                    fT_EEPROM_4232H.BH_DriveCurrent = 3;
                    break;
                }
            }
            short s15 = (short)(arrn[6] & 16384);
            fT_EEPROM_4232H.BH_SlowSlew = s15 == 16384;
            short s16 = (short)(arrn[6] & 32768);
            fT_EEPROM_4232H.BH_SchmittInput = s16 == 32768;
            short s17 = (short)((arrn[11] & 24) >> 3);
            fT_EEPROM_4232H.TPRDRV = s17 < 4 ? (int)s17 : 0;
            int n = arrn[7] & 255;
            if (this.a == 70) {
                n-=128;
                fT_EEPROM_4232H.Manufacturer = this.a(n/=2, arrn);
                n = arrn[8] & 255;
                n-=128;
                fT_EEPROM_4232H.Product = this.a(n/=2, arrn);
                n = arrn[9] & 255;
                n-=128;
                fT_EEPROM_4232H.SerialNumber = this.a(n/=2, arrn);
            } else {
                fT_EEPROM_4232H.Manufacturer = this.a(n/=2, arrn);
                n = arrn[8] & 255;
                fT_EEPROM_4232H.Product = this.a(n/=2, arrn);
                n = arrn[9] & 255;
                fT_EEPROM_4232H.SerialNumber = this.a(n/=2, arrn);
            }
            return fT_EEPROM_4232H;
        }
        catch (Exception var3_4) {
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

