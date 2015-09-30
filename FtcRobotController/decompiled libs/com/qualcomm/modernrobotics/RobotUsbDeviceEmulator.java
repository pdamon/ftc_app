/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.qualcomm.robotcore.exception.RobotCoreException
 *  com.qualcomm.robotcore.hardware.usb.RobotUsbDevice
 *  com.qualcomm.robotcore.hardware.usb.RobotUsbDevice$Channel
 *  com.qualcomm.robotcore.util.RobotLog
 *  com.qualcomm.robotcore.util.SerialNumber
 *  com.qualcomm.robotcore.util.TypeConversion
 */
package com.qualcomm.modernrobotics;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.usb.RobotUsbDevice;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;
import com.qualcomm.robotcore.util.TypeConversion;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RobotUsbDeviceEmulator
implements RobotUsbDevice {
    public final boolean DEBUG_LOGGING;
    public static final int MFG_CODE_MODERN_ROBOTICS = 77;
    public SerialNumber serialNumber;
    public String description;
    private byte[] a = new byte[256];
    private byte[] b = null;
    private BlockingQueue<byte[]> c = new LinkedBlockingQueue<byte[]>();
    protected final byte[] writeRsp = new byte[]{51, -52, 0, 0, 0};
    protected final byte[] readRsp = new byte[]{51, -52, -128, 0, 0};

    public RobotUsbDeviceEmulator(SerialNumber serialNumber, String description, int deviceType) {
        this(serialNumber, description, deviceType, false);
    }

    public RobotUsbDeviceEmulator(SerialNumber serialNumber, String description, int deviceType, boolean debug) {
        this.DEBUG_LOGGING = debug;
        this.serialNumber = serialNumber;
        this.description = description;
        this.a[0] = -1;
        this.a[1] = 77;
        this.a[2] = (byte)deviceType;
    }

    public void setBaudRate(int rate) throws RobotCoreException {
    }

    public void setDataCharacteristics(byte dataBits, byte stopBits, byte parity) throws RobotCoreException {
    }

    public void setLatencyTimer(int latencyTimer) throws RobotCoreException {
    }

    public void purge(RobotUsbDevice.Channel channel) throws RobotCoreException {
        this.c.clear();
    }

    public void write(byte[] data) throws RobotCoreException {
        this.a(data);
    }

    public int read(byte[] data) throws RobotCoreException {
        return this.read(data, data.length, Integer.MAX_VALUE);
    }

    public int read(byte[] data, int length, int timeout) throws RobotCoreException {
        return this.a(data, length, timeout);
    }

    public void close() {
    }

    private void a(final byte[] arrby) {
        if (this.DEBUG_LOGGING) {
            RobotLog.d((String)((Object)this.serialNumber + " USB recd: " + Arrays.toString(arrby)));
        }
        new Thread(){

            @Override
            public void run() {
                int n = TypeConversion.unsignedByteToInt((byte)arrby[3]);
                int n2 = TypeConversion.unsignedByteToInt((byte)arrby[4]);
                try {
                    byte[] arrby2;
                    Thread.sleep(10);
                    switch (arrby[2]) {
                        case -128: {
                            arrby2 = new byte[RobotUsbDeviceEmulator.this.readRsp.length + n2];
                            System.arraycopy(RobotUsbDeviceEmulator.this.readRsp, 0, arrby2, 0, RobotUsbDeviceEmulator.this.readRsp.length);
                            arrby2[3] = arrby[3];
                            arrby2[4] = arrby[4];
                            System.arraycopy(RobotUsbDeviceEmulator.this.a, n, arrby2, RobotUsbDeviceEmulator.this.readRsp.length, n2);
                            break;
                        }
                        case 0: {
                            arrby2 = new byte[RobotUsbDeviceEmulator.this.writeRsp.length];
                            System.arraycopy(RobotUsbDeviceEmulator.this.writeRsp, 0, arrby2, 0, RobotUsbDeviceEmulator.this.writeRsp.length);
                            arrby2[3] = arrby[3];
                            arrby2[4] = 0;
                            System.arraycopy(arrby, 5, RobotUsbDeviceEmulator.this.a, n, n2);
                            break;
                        }
                        default: {
                            arrby2 = Arrays.copyOf(arrby, arrby.length);
                            arrby2[2] = -1;
                            arrby2[3] = arrby[3];
                            arrby2[4] = 0;
                        }
                    }
                    RobotUsbDeviceEmulator.this.c.put(arrby2);
                }
                catch (InterruptedException var3_4) {
                    RobotLog.w((String)"USB mock bus interrupted during write");
                }
            }
        }.start();
    }

    private int a(byte[] arrby, int n, int n2) {
        Object object = null;
        if (this.b != null) {
            object = Arrays.copyOf(this.b, this.b.length);
            this.b = null;
        } else {
            try {
                object = this.c.poll(n2, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException var5_5) {
                RobotLog.w((String)"USB mock bus interrupted during read");
            }
        }
        if (object == null) {
            RobotLog.w((String)"USB mock bus read timeout");
            System.arraycopy(this.readRsp, 0, arrby, 0, this.readRsp.length);
            arrby[2] = -1;
            arrby[4] = 0;
        } else {
            System.arraycopy(object, 0, arrby, 0, n);
        }
        if (object != null && n < object.length) {
            this.b = new byte[object.length - n];
            System.arraycopy(object, arrby.length, this.b, 0, this.b.length);
        }
        if (this.DEBUG_LOGGING) {
            RobotLog.d((String)((Object)this.serialNumber + " USB send: " + Arrays.toString(arrby)));
        }
        return arrby.length;
    }

}

