/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx.ft4222;

import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.ft4222.FT_4222_Gpio;
import com.ftdi.j2xx.ft4222.FT_4222_I2c_Master;
import com.ftdi.j2xx.ft4222.FT_4222_I2c_Slave;
import com.ftdi.j2xx.ft4222.FT_4222_Spi_Master;
import com.ftdi.j2xx.ft4222.FT_4222_Spi_Slave;
import com.ftdi.j2xx.ft4222.a;
import com.ftdi.j2xx.ft4222.b;
import com.ftdi.j2xx.ft4222.e;
import com.ftdi.j2xx.interfaces.Gpio;
import com.ftdi.j2xx.interfaces.I2cMaster;
import com.ftdi.j2xx.interfaces.I2cSlave;
import com.ftdi.j2xx.interfaces.SpiMaster;
import com.ftdi.j2xx.interfaces.SpiSlave;

public class FT_4222_Device {
    protected String TAG = "FT4222";
    protected FT_Device mFtDev;
    protected b mChipStatus;
    protected a mSpiMasterCfg;
    protected e mGpio;

    public FT_4222_Device(FT_Device ftDev) {
        this.mFtDev = ftDev;
        this.mChipStatus = new b();
        this.mSpiMasterCfg = new a();
        this.mGpio = new e();
    }

    public int init() {
        byte[] arrby = new byte[13];
        int n = this.mFtDev.VendorCmdGet(32, 1, arrby, 13);
        if (n != 13) {
            return 18;
        }
        this.mChipStatus.a(arrby);
        return 0;
    }

    public int setClock(byte clk) {
        if (clk == this.mChipStatus.f) {
            return 0;
        }
        int n = this.mFtDev.VendorCmdSet(33, 4 | clk << 8);
        if (n == 0) {
            this.mChipStatus.f = clk;
        }
        return n;
    }

    public int getClock(byte[] clk) {
        if (this.mFtDev.VendorCmdGet(32, 4, clk, 1) >= 0) {
            this.mChipStatus.f = clk[0];
            return 0;
        }
        return 18;
    }

    public boolean cleanRxData() {
        byte[] arrby;
        int n = this.mFtDev.getQueueStatus();
        if (n > 0 && (n = this.mFtDev.read(arrby = new byte[n], n)) != arrby.length) {
            return false;
        }
        return true;
    }

    protected int getMaxBuckSize() {
        if (this.mChipStatus.c != 0) {
            return 64;
        }
        switch (this.mChipStatus.a) {
            default: {
                return 512;
            }
            case 1: 
            case 2: 
        }
        return 256;
    }

    public boolean isFT4222Device() {
        if (this.mFtDev != null) {
            switch (this.mFtDev.getDeviceInfo().bcdDevice & 65280) {
                case 6144: {
                    this.mFtDev.getDeviceInfo().type = 10;
                    return true;
                }
                case 6400: {
                    this.mFtDev.getDeviceInfo().type = 11;
                    return true;
                }
                case 5888: {
                    this.mFtDev.getDeviceInfo().type = 12;
                    return true;
                }
            }
        }
        return false;
    }

    public I2cMaster getI2cMasterDevice() {
        if (!this.isFT4222Device()) {
            return null;
        }
        return new FT_4222_I2c_Master(this);
    }

    public I2cSlave getI2cSlaveDevice() {
        if (!this.isFT4222Device()) {
            return null;
        }
        return new FT_4222_I2c_Slave(this);
    }

    public SpiMaster getSpiMasterDevice() {
        if (!this.isFT4222Device()) {
            return null;
        }
        return new FT_4222_Spi_Master(this);
    }

    public SpiSlave getSpiSlaveDevice() {
        if (!this.isFT4222Device()) {
            return null;
        }
        return new FT_4222_Spi_Slave(this);
    }

    public Gpio getGpioDevice() {
        if (!this.isFT4222Device()) {
            return null;
        }
        return new FT_4222_Gpio(this);
    }
}

