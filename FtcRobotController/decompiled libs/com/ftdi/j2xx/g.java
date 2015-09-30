/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx;

import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.FT_EEPROM;
import com.ftdi.j2xx.FT_EEPROM_232H;
import com.ftdi.j2xx.k;

class g
extends k {
    g(FT_Device fT_Device) throws D2xxManager.D2xxException {
        super(fT_Device);
        this.a(15);
    }

    short a(FT_EEPROM fT_EEPROM) {
        int[] arrn;
        block24 : {
            arrn = new int[this.b];
            if (fT_EEPROM.getClass() != FT_EEPROM_232H.class) {
                return 1;
            }
            FT_EEPROM_232H fT_EEPROM_232H = (FT_EEPROM_232H)fT_EEPROM;
            try {
                byte by;
                if (fT_EEPROM_232H.FIFO) {
                    int[] arrn2 = arrn;
                    arrn2[0] = arrn2[0] | 1;
                } else if (fT_EEPROM_232H.FIFOTarget) {
                    int[] arrn3 = arrn;
                    arrn3[0] = arrn3[0] | 2;
                } else if (fT_EEPROM_232H.FastSerial) {
                    int[] arrn4 = arrn;
                    arrn4[0] = arrn4[0] | 4;
                }
                if (fT_EEPROM_232H.FT1248) {
                    int[] arrn5 = arrn;
                    arrn5[0] = arrn5[0] | 8;
                }
                if (fT_EEPROM_232H.LoadVCP) {
                    int[] arrn6 = arrn;
                    arrn6[0] = arrn6[0] | 16;
                }
                if (fT_EEPROM_232H.FT1248ClockPolarity) {
                    int[] arrn7 = arrn;
                    arrn7[0] = arrn7[0] | 256;
                }
                if (fT_EEPROM_232H.FT1248LSB) {
                    int[] arrn8 = arrn;
                    arrn8[0] = arrn8[0] | 512;
                }
                if (fT_EEPROM_232H.FT1248FlowControl) {
                    int[] arrn9 = arrn;
                    arrn9[0] = arrn9[0] | 1024;
                }
                if (fT_EEPROM_232H.PowerSaveEnable) {
                    int[] arrn10 = arrn;
                    arrn10[0] = arrn10[0] | 32768;
                }
                arrn[1] = fT_EEPROM_232H.VendorId;
                arrn[2] = fT_EEPROM_232H.ProductId;
                arrn[3] = 2304;
                arrn[4] = this.a((Object)fT_EEPROM);
                arrn[5] = this.b(fT_EEPROM);
                byte by2 = fT_EEPROM_232H.AL_DriveCurrent;
                if (by2 == -1) {
                    by2 = 0;
                }
                int[] arrn11 = arrn;
                arrn11[6] = arrn11[6] | by2;
                if (fT_EEPROM_232H.AL_SlowSlew) {
                    int[] arrn12 = arrn;
                    arrn12[6] = arrn12[6] | 4;
                }
                if (fT_EEPROM_232H.AL_SchmittInput) {
                    int[] arrn13 = arrn;
                    arrn13[6] = arrn13[6] | 8;
                }
                if ((by = fT_EEPROM_232H.BL_DriveCurrent) == -1) {
                    by = 0;
                }
                int[] arrn14 = arrn;
                arrn14[6] = arrn14[6] | (short)(by << 8);
                if (fT_EEPROM_232H.BL_SlowSlew) {
                    int[] arrn15 = arrn;
                    arrn15[6] = arrn15[6] | 1024;
                }
                if (fT_EEPROM_232H.BL_SchmittInput) {
                    int[] arrn16 = arrn;
                    arrn16[6] = arrn16[6] | 2048;
                }
                int n = 80;
                n = this.a(fT_EEPROM_232H.Manufacturer, arrn, n, 7, false);
                n = this.a(fT_EEPROM_232H.Product, arrn, n, 8, false);
                if (fT_EEPROM_232H.SerNumEnable) {
                    n = this.a(fT_EEPROM_232H.SerialNumber, arrn, n, 9, false);
                }
                arrn[10] = 0;
                arrn[11] = 0;
                arrn[12] = 0;
                byte by3 = fT_EEPROM_232H.CBus0;
                int n2 = fT_EEPROM_232H.CBus1;
                int n3 = fT_EEPROM_232H.CBus2;
                int n4 = fT_EEPROM_232H.CBus3;
                arrn[12] = by3 | (n2<<=4) | (n3<<=8) | (n4<<=12);
                arrn[13] = 0;
                byte by4 = fT_EEPROM_232H.CBus4;
                int n5 = fT_EEPROM_232H.CBus5;
                int n6 = fT_EEPROM_232H.CBus6;
                int n7 = fT_EEPROM_232H.CBus7;
                arrn[13] = by4 | (n5<<=4) | (n6<<=8) | (n7<<=12);
                arrn[14] = 0;
                byte by5 = fT_EEPROM_232H.CBus8;
                int n8 = fT_EEPROM_232H.CBus9;
                arrn[14] = by5 | (n8<<=4);
                arrn[15] = this.a;
                arrn[69] = 72;
                if (this.a != 70) break block24;
                return 1;
            }
            catch (Exception var4_5) {
                var4_5.printStackTrace();
                return 0;
            }
        }
        if (arrn[1] != 0 && arrn[2] != 0) {
            boolean bl = false;
            bl = this.a(arrn, this.b - 1);
            if (bl) {
                return 0;
            }
            return 1;
        }
        return 2;
    }

    FT_EEPROM a() {
        FT_EEPROM_232H fT_EEPROM_232H = new FT_EEPROM_232H();
        int[] arrn = new int[this.b];
        if (this.c) {
            return fT_EEPROM_232H;
        }
        try {
            int n;
            for (n = 0; n < this.b; n = (short)(n + 1)) {
                arrn[n] = this.a((short)n);
            }
            fT_EEPROM_232H.UART = false;
            switch (arrn[0] & 15) {
                case 0: {
                    fT_EEPROM_232H.UART = true;
                    break;
                }
                case 1: {
                    fT_EEPROM_232H.FIFO = true;
                    break;
                }
                case 2: {
                    fT_EEPROM_232H.FIFOTarget = true;
                    break;
                }
                case 4: {
                    fT_EEPROM_232H.FastSerial = true;
                    break;
                }
                case 8: {
                    fT_EEPROM_232H.FT1248 = true;
                    break;
                }
                default: {
                    fT_EEPROM_232H.UART = true;
                }
            }
            if ((arrn[0] & 16) > 0) {
                fT_EEPROM_232H.LoadVCP = true;
                fT_EEPROM_232H.LoadD2XX = false;
            } else {
                fT_EEPROM_232H.LoadVCP = false;
                fT_EEPROM_232H.LoadD2XX = true;
            }
            fT_EEPROM_232H.FT1248ClockPolarity = (arrn[0] & 256) > 0;
            fT_EEPROM_232H.FT1248LSB = (arrn[0] & 512) > 0;
            fT_EEPROM_232H.FT1248FlowControl = (arrn[0] & 1024) > 0;
            if ((arrn[0] & 32768) > 0) {
                fT_EEPROM_232H.PowerSaveEnable = true;
            }
            fT_EEPROM_232H.VendorId = (short)arrn[1];
            fT_EEPROM_232H.ProductId = (short)arrn[2];
            this.a(fT_EEPROM_232H, arrn[4]);
            this.a((Object)fT_EEPROM_232H, arrn[5]);
            n = arrn[6] & 3;
            switch (n) {
                case 0: {
                    fT_EEPROM_232H.AL_DriveCurrent = 0;
                    break;
                }
                case 1: {
                    fT_EEPROM_232H.AL_DriveCurrent = 1;
                    break;
                }
                case 2: {
                    fT_EEPROM_232H.AL_DriveCurrent = 2;
                    break;
                }
                case 3: {
                    fT_EEPROM_232H.AL_DriveCurrent = 3;
                    break;
                }
            }
            fT_EEPROM_232H.AL_SlowSlew = (arrn[6] & 4) > 0;
            fT_EEPROM_232H.AL_SchmittInput = (arrn[6] & 8) > 0;
            short s = (short)((arrn[6] & 768) >> 8);
            switch (s) {
                case 0: {
                    fT_EEPROM_232H.BL_DriveCurrent = 0;
                    break;
                }
                case 1: {
                    fT_EEPROM_232H.BL_DriveCurrent = 1;
                    break;
                }
                case 2: {
                    fT_EEPROM_232H.BL_DriveCurrent = 2;
                    break;
                }
                case 3: {
                    fT_EEPROM_232H.BL_DriveCurrent = 3;
                    break;
                }
            }
            fT_EEPROM_232H.BL_SlowSlew = (arrn[6] & 1024) > 0;
            fT_EEPROM_232H.BL_SchmittInput = (arrn[6] & 2048) > 0;
            short s2 = (short)(arrn[12] >> 0 & 15);
            fT_EEPROM_232H.CBus0 = (byte)s2;
            short s3 = (short)(arrn[12] >> 4 & 15);
            fT_EEPROM_232H.CBus1 = (byte)s3;
            short s4 = (short)(arrn[12] >> 8 & 15);
            fT_EEPROM_232H.CBus2 = (byte)s4;
            short s5 = (short)(arrn[12] >> 12 & 15);
            fT_EEPROM_232H.CBus3 = (byte)s5;
            short s6 = (short)(arrn[13] >> 0 & 15);
            fT_EEPROM_232H.CBus4 = (byte)s6;
            short s7 = (short)(arrn[13] >> 4 & 15);
            fT_EEPROM_232H.CBus5 = (byte)s7;
            short s8 = (short)(arrn[13] >> 8 & 15);
            fT_EEPROM_232H.CBus6 = (byte)s8;
            short s9 = (short)(arrn[13] >> 12 & 15);
            fT_EEPROM_232H.CBus7 = (byte)s9;
            short s10 = (short)(arrn[14] >> 0 & 15);
            fT_EEPROM_232H.CBus8 = (byte)s10;
            short s11 = (short)(arrn[14] >> 4 & 15);
            fT_EEPROM_232H.CBus9 = (byte)s11;
            int n2 = arrn[7] & 255;
            fT_EEPROM_232H.Manufacturer = this.a(n2/=2, arrn);
            n2 = arrn[8] & 255;
            fT_EEPROM_232H.Product = this.a(n2/=2, arrn);
            n2 = arrn[9] & 255;
            fT_EEPROM_232H.SerialNumber = this.a(n2/=2, arrn);
            return fT_EEPROM_232H;
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
        n3/=2;
        return (this.b - ++n2 - 1 - ++n3) * 2;
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

