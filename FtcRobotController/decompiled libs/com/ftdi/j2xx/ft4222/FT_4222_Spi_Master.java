/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  junit.framework.Assert
 */
package com.ftdi.j2xx.ft4222;

import android.util.Log;
import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.ft4222.FT_4222_Device;
import com.ftdi.j2xx.ft4222.a;
import com.ftdi.j2xx.ft4222.b;
import com.ftdi.j2xx.interfaces.SpiMaster;
import junit.framework.Assert;

public class FT_4222_Spi_Master
implements SpiMaster {
    private FT_4222_Device a;
    private FT_Device b;

    public FT_4222_Spi_Master(FT_4222_Device pDevice) {
        this.a = pDevice;
        this.b = pDevice.mFtDev;
    }

    public int init(int ioLine, int clock, int cpol, int cpha, byte ssoMap) {
        b b = this.a.mChipStatus;
        int n = 0;
        a a = this.a.mSpiMasterCfg;
        a.a = ioLine;
        a.b = clock;
        a.c = cpol;
        a.d = cpha;
        a.e = ssoMap;
        if (a.a != 1 && a.a != 2 && a.a != 4) {
            return 6;
        }
        this.a.cleanRxData();
        switch (b.a) {
            case 0: {
                n = 1;
                break;
            }
            case 1: {
                n = 7;
                break;
            }
            case 2: {
                n = 15;
                break;
            }
            case 3: {
                n = 1;
            }
        }
        if ((n & a.e) == 0) {
            return 6;
        }
        a.e = (byte)(a.e & n);
        int n2 = 0;
        int n3 = 3;
        if (this.b.VendorCmdSet(33, 66 | a.a << 8) < 0) {
            return 4;
        }
        if (this.b.VendorCmdSet(33, 68 | a.b << 8) < 0) {
            return 4;
        }
        if (this.b.VendorCmdSet(33, 69 | a.c << 8) < 0) {
            return 4;
        }
        if (this.b.VendorCmdSet(33, 70 | a.d << 8) < 0) {
            return 4;
        }
        if (this.b.VendorCmdSet(33, 67 | n2 << 8) < 0) {
            return 4;
        }
        if (this.b.VendorCmdSet(33, 72 | a.e << 8) < 0) {
            return 4;
        }
        if (this.b.VendorCmdSet(33, 5 | n3 << 8) < 0) {
            return 4;
        }
        b.g = 3;
        return 0;
    }

    public int setLines(int spiMode) {
        int n = 1;
        b b = this.a.mChipStatus;
        if (b.g != 3) {
            return 1003;
        }
        if (spiMode == 0) {
            return 17;
        }
        if (this.b.VendorCmdSet(33, 66 | spiMode << 8) < 0) {
            return 4;
        }
        if (this.b.VendorCmdSet(33, 74 | n << 8) < 0) {
            return 4;
        }
        a a = this.a.mSpiMasterCfg;
        a.a = spiMode;
        return 0;
    }

    public int singleWrite(byte[] writeBuffer, int sizeToTransfer, int[] sizeTransferred, boolean isEndTransaction) {
        byte[] arrby = new byte[writeBuffer.length];
        return this.singleReadWrite(arrby, writeBuffer, sizeToTransfer, sizeTransferred, isEndTransaction);
    }

    public int singleRead(byte[] readBuffer, int sizeToTransfer, int[] sizeOfRead, boolean isEndTransaction) {
        byte[] arrby = new byte[readBuffer.length];
        return this.singleReadWrite(readBuffer, arrby, sizeToTransfer, sizeOfRead, isEndTransaction);
    }

    public int singleReadWrite(byte[] readBuffer, byte[] writeBuffer, int sizeToTransfer, int[] sizeTransferred, boolean isEndTransaction) {
        b b = this.a.mChipStatus;
        a a = this.a.mSpiMasterCfg;
        if (writeBuffer == null || readBuffer == null || sizeTransferred == null) {
            return 1009;
        }
        sizeTransferred[0] = 0;
        if (b.g != 3 || a.a != 1) {
            return 1005;
        }
        if (sizeToTransfer == 0) {
            return 6;
        }
        if (sizeToTransfer > writeBuffer.length || sizeToTransfer > readBuffer.length) {
            Assert.assertTrue((String)"sizeToTransfer > writeBuffer.length || sizeToTransfer > readBuffer.length", (boolean)false);
        }
        if (writeBuffer.length != readBuffer.length || writeBuffer.length == 0) {
            Assert.assertTrue((String)"writeBuffer.length != readBuffer.length || writeBuffer.length == 0", (boolean)false);
        }
        sizeTransferred[0] = this.a(this.b, writeBuffer, readBuffer, sizeToTransfer);
        if (isEndTransaction) {
            this.b.write(null, 0);
        }
        return 0;
    }

    public int multiReadWrite(byte[] readBuffer, byte[] writeBuffer, int singleWriteBytes, int multiWriteBytes, int multiReadBytes, int[] sizeOfRead) {
        b b = this.a.mChipStatus;
        a a = this.a.mSpiMasterCfg;
        if (multiReadBytes > 0 && readBuffer == null) {
            return 1009;
        }
        if (singleWriteBytes + multiWriteBytes > 0 && writeBuffer == null) {
            return 1009;
        }
        if (multiReadBytes > 0 && sizeOfRead == null) {
            return 1009;
        }
        if (b.g != 3 || a.a == 1) {
            return 1006;
        }
        if (singleWriteBytes > 15) {
            Log.e((String)"FTDI_Device::", (String)"The maxium single write bytes are 15 bytes");
            return 6;
        }
        int n = 5 + singleWriteBytes + multiWriteBytes;
        byte[] arrby = new byte[n];
        arrby[0] = (byte)(128 | singleWriteBytes & 15);
        arrby[1] = (byte)((multiWriteBytes & 65280) >> 8);
        arrby[2] = (byte)(multiWriteBytes & 255);
        arrby[3] = (byte)((multiReadBytes & 65280) >> 8);
        arrby[4] = (byte)(multiReadBytes & 255);
        for (int i = 0; i < singleWriteBytes + multiWriteBytes; ++i) {
            arrby[i + 5] = writeBuffer[i];
        }
        sizeOfRead[0] = this.a(this.b, arrby, readBuffer);
        return 0;
    }

    public int reset() {
        int n = 0;
        if (this.b.VendorCmdSet(33, 74 | n << 8) < 0) {
            return 4;
        }
        return 0;
    }

    public int setDrivingStrength(int clkStrength, int ioStrength, int ssoStregth) {
        b b = this.a.mChipStatus;
        if (b.g != 3 && b.g != 4) {
            return 1003;
        }
        int n = clkStrength << 4;
        n|=ioStrength << 2;
        int n2 = b.g == 3 ? 3 : 4;
        if (this.b.VendorCmdSet(33, 160 | (n|=ssoStregth) << 8) < 0) {
            return 4;
        }
        if (this.b.VendorCmdSet(33, 5 | n2 << 8) < 0) {
            return 4;
        }
        return 0;
    }

    private int a(FT_Device fT_Device, byte[] arrby, byte[] arrby2) {
        int n = 0;
        int n2 = 0;
        int n3 = 10;
        int n4 = 30000;
        int n5 = 0;
        if (!(fT_Device != null && fT_Device.isOpen())) {
            return -1;
        }
        n = fT_Device.write(arrby, arrby.length);
        while (n5 < arrby2.length && n2 < n4) {
            n = fT_Device.getQueueStatus();
            if (n > 0) {
                n2 = 0;
                byte[] arrby3 = new byte[n];
                Assert.assertEquals((boolean)(arrby3.length == (n = fT_Device.read(arrby3, n))), (boolean)true);
                for (int i = 0; i < arrby3.length; ++i) {
                    if (n5 + i >= arrby2.length) continue;
                    arrby2[n5 + i] = arrby3[i];
                }
                n5+=n;
            }
            try {
                Thread.sleep(n3);
                n2+=n3;
                continue;
            }
            catch (InterruptedException var9_10) {
                n2 = n4;
            }
        }
        if (arrby2.length != n5 || n2 > n4) {
            Log.e((String)"FTDI_Device::", (String)"MultiReadWritePackage timeout!!!!");
            return -1;
        }
        return n5;
    }

    private int a(FT_Device fT_Device, byte[] arrby, byte[] arrby2, int n) {
        int n2;
        byte[] arrby3 = new byte[16384];
        byte[] arrby4 = new byte[arrby3.length];
        int n3 = n / arrby3.length;
        int n4 = n % arrby3.length;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            int n8;
            for (n8 = 0; n8 < arrby3.length; ++n8) {
                arrby3[n8] = arrby[n6];
                ++n6;
            }
            n7 = this.b(fT_Device, arrby3, arrby4);
            if (n7 <= 0) {
                return -1;
            }
            for (n8 = 0; n8 < arrby4.length; ++n8) {
                arrby2[n5] = arrby4[n8];
                ++n5;
            }
        }
        if (n4 > 0) {
            arrby3 = new byte[n4];
            arrby4 = new byte[arrby3.length];
            for (n2 = 0; n2 < arrby3.length; ++n2) {
                arrby3[n2] = arrby[n6];
                ++n6;
            }
            n7 = this.b(fT_Device, arrby3, arrby4);
            if (n7 <= 0) {
                return -1;
            }
            for (n2 = 0; n2 < arrby4.length; ++n2) {
                arrby2[n5] = arrby4[n2];
                ++n5;
            }
        }
        return n5;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Lifted jumps to return sites
     */
    private int b(FT_Device var1_1, byte[] var2_2, byte[] var3_3) {
        var4_4 = 0;
        var5_5 = 0;
        var6_6 = 10;
        var7_7 = 30000;
        var8_8 = 0;
        if (var1_1 == null) return -1;
        if (!var1_1.isOpen()) {
            return -1;
        }
        Assert.assertEquals((boolean)(var2_2.length == var3_3.length), (boolean)true);
        var4_4 = var1_1.write(var2_2, var2_2.length);
        if (var2_2.length == var4_4) ** GOTO lbl30
        Log.e((String)"FTDI_Device::", (String)"setReadWritePackage Incomplete Write Error!!!");
        return -1;
lbl-1000: // 1 sources:
        {
            var4_4 = var1_1.getQueueStatus();
            if (var4_4 > 0) {
                var5_5 = 0;
                var9_9 = new byte[var4_4];
                Assert.assertEquals((boolean)(var9_9.length == (var4_4 = var1_1.read(var9_9, var4_4))), (boolean)true);
                for (var10_11 = 0; var10_11 < var9_9.length; ++var10_11) {
                    if (var8_8 + var10_11 >= var3_3.length) continue;
                    var3_3[var8_8 + var10_11] = var9_9[var10_11];
                }
                var8_8+=var4_4;
            }
            try {
                Thread.sleep(var6_6);
                var5_5+=var6_6;
                continue;
            }
            catch (InterruptedException var9_10) {
                var5_5 = var7_7;
            }
lbl30: // 3 sources:
            ** while (var8_8 < var3_3.length && var5_5 < var7_7)
        }
lbl31: // 1 sources:
        if (var3_3.length == var8_8) {
            if (var5_5 <= var7_7) return var8_8;
        }
        Log.e((String)"FTDI_Device::", (String)"SingleReadWritePackage timeout!!!!");
        return -1;
    }
}

