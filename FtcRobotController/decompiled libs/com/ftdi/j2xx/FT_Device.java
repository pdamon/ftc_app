/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.hardware.usb.UsbDevice
 *  android.hardware.usb.UsbDeviceConnection
 *  android.hardware.usb.UsbEndpoint
 *  android.hardware.usb.UsbInterface
 *  android.hardware.usb.UsbManager
 *  android.hardware.usb.UsbRequest
 *  android.util.Log
 */
package com.ftdi.j2xx;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.util.Log;
import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_EEPROM;
import com.ftdi.j2xx.a;
import com.ftdi.j2xx.b;
import com.ftdi.j2xx.c;
import com.ftdi.j2xx.d;
import com.ftdi.j2xx.e;
import com.ftdi.j2xx.f;
import com.ftdi.j2xx.g;
import com.ftdi.j2xx.h;
import com.ftdi.j2xx.i;
import com.ftdi.j2xx.j;
import com.ftdi.j2xx.k;
import com.ftdi.j2xx.l;
import com.ftdi.j2xx.o;
import com.ftdi.j2xx.p;
import com.ftdi.j2xx.q;
import com.ftdi.j2xx.r;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FT_Device {
    long a;
    Boolean b;
    UsbDevice c;
    UsbInterface d;
    UsbEndpoint e;
    UsbEndpoint f;
    private UsbRequest k;
    private UsbDeviceConnection l;
    private a m;
    private Thread n;
    private Thread o;
    D2xxManager.FtDeviceInfoListNode g;
    private o p;
    private k q;
    private byte r;
    r h;
    q i;
    private D2xxManager.DriverParameters s;
    private int t = 0;
    Context j;
    private int u;

    public FT_Device(Context parentContext, UsbManager usbManager, UsbDevice dev, UsbInterface itf) {
        byte[] arrby = new byte[255];
        this.j = parentContext;
        this.s = new D2xxManager.DriverParameters();
        try {
            this.c = dev;
            this.d = itf;
            this.e = null;
            this.f = null;
            this.u = 0;
            this.h = new r();
            this.i = new q();
            this.g = new D2xxManager.FtDeviceInfoListNode();
            this.k = new UsbRequest();
            this.a(usbManager.openDevice(this.c));
            if (this.c() == null) {
                Log.e((String)"FTDI_Device::", (String)"Failed to open the device!");
                throw new D2xxManager.D2xxException("Failed to open the device!");
            }
            this.c().claimInterface(this.d, false);
            byte[] arrby2 = this.c().getRawDescriptors();
            int n = this.c.getDeviceId();
            this.t = this.d.getId() + 1;
            this.g.location = n << 4 | this.t & 15;
            ByteBuffer byteBuffer = ByteBuffer.allocate(2);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            byteBuffer.put(arrby2[12]);
            byteBuffer.put(arrby2[13]);
            this.g.bcdDevice = byteBuffer.getShort(0);
            this.g.iSerialNumber = arrby2[16];
            this.g.serialNumber = this.c().getSerial();
            this.g.id = this.c.getVendorId() << 16 | this.c.getProductId();
            this.g.breakOnParam = 8;
            this.c().controlTransfer(-128, 6, 768 | arrby2[15], 0, arrby, 255, 0);
            this.g.description = this.a(arrby);
            switch (this.g.bcdDevice & 65280) {
                case 512: {
                    if (this.g.iSerialNumber == 0) {
                        this.q = new f(this);
                        this.g.type = 0;
                        break;
                    }
                    this.g.type = 1;
                    this.q = new e(this);
                    break;
                }
                case 1024: {
                    this.q = new f(this);
                    this.g.type = 0;
                    break;
                }
                case 1280: {
                    this.q = new d(this);
                    this.g.type = 4;
                    this.n();
                    break;
                }
                case 1536: {
                    this.q = new k(this);
                    short s = (short)(this.q.a(0) & 1);
                    this.q = null;
                    if (s == 0) {
                        this.g.type = 5;
                        this.q = new h(this);
                        break;
                    }
                    this.g.type = 5;
                    this.q = new i(this);
                    break;
                }
                case 1792: {
                    this.g.type = 6;
                    this.n();
                    this.q = new c(this);
                    break;
                }
                case 2048: {
                    this.g.type = 7;
                    this.n();
                    this.q = new j(this);
                    break;
                }
                case 2304: {
                    this.g.type = 8;
                    this.q = new g(this);
                    break;
                }
                case 4096: {
                    this.g.type = 9;
                    this.q = new l(this);
                    break;
                }
                case 6144: {
                    this.g.type = 10;
                    if (this.t == 1) {
                        this.g.flags = 2;
                        break;
                    }
                    this.g.flags = 0;
                    break;
                }
                case 6400: {
                    this.g.type = 11;
                    if (this.t == 4) {
                        int n2 = this.c.getInterface(this.t - 1).getEndpoint(0).getMaxPacketSize();
                        Log.e((String)"dev", (String)("mInterfaceID : " + this.t + "   iMaxPacketSize : " + n2));
                        if (n2 == 8) {
                            this.g.flags = 0;
                            break;
                        }
                        this.g.flags = 2;
                        break;
                    }
                    this.g.flags = 2;
                    break;
                }
                case 5888: {
                    this.g.type = 12;
                    this.g.flags = 2;
                    break;
                }
                default: {
                    this.g.type = 3;
                    this.q = new k(this);
                }
            }
            switch (this.g.bcdDevice & 65280) {
                case 5888: 
                case 6144: 
                case 6400: {
                    if (this.g.serialNumber != null) break;
                    byte[] arrby3 = new byte[16];
                    this.c().controlTransfer(-64, 144, 0, 27, arrby3, 16, 0);
                    String string = "";
                    for (int i = 0; i < 8; ++i) {
                        string = String.valueOf(string) + arrby3[i * 2];
                    }
                    this.g.serialNumber = new String(string);
                    break;
                }
            }
            switch (this.g.bcdDevice & 65280) {
                case 6144: 
                case 6400: {
                    if (this.t == 1) {
                        this.g.description = String.valueOf(this.g.description) + " A";
                        this.g.serialNumber = String.valueOf(this.g.serialNumber) + "A";
                        break;
                    }
                    if (this.t == 2) {
                        this.g.description = String.valueOf(this.g.description) + " B";
                        this.g.serialNumber = String.valueOf(this.g.serialNumber) + "B";
                        break;
                    }
                    if (this.t == 3) {
                        this.g.description = String.valueOf(this.g.description) + " C";
                        this.g.serialNumber = String.valueOf(this.g.serialNumber) + "C";
                        break;
                    }
                    if (this.t != 4) break;
                    this.g.description = String.valueOf(this.g.description) + " D";
                    this.g.serialNumber = String.valueOf(this.g.serialNumber) + "D";
                    break;
                }
            }
            this.c().releaseInterface(this.d);
            this.c().close();
            this.a((UsbDeviceConnection)null);
            this.p();
        }
        catch (Exception var7_8) {
            if (var7_8.getMessage() != null) {
                Log.e((String)"FTDI_Device::", (String)var7_8.getMessage());
            }
            return;
        }
    }

    private final boolean f() {
        if (!(this.i() || this.j() || this.b())) {
            return false;
        }
        return true;
    }

    private final boolean g() {
        if (!(this.m() || this.l() || this.k() || this.j() || this.b() || this.i() || this.h())) {
            return false;
        }
        return true;
    }

    final boolean a() {
        if (!(this.l() || this.j() || this.b())) {
            return false;
        }
        return true;
    }

    private final boolean h() {
        if ((this.g.bcdDevice & 65280) == 4096) {
            return true;
        }
        return false;
    }

    private final boolean i() {
        if ((this.g.bcdDevice & 65280) == 2304) {
            return true;
        }
        return false;
    }

    final boolean b() {
        if ((this.g.bcdDevice & 65280) == 2048) {
            return true;
        }
        return false;
    }

    private final boolean j() {
        if ((this.g.bcdDevice & 65280) == 1792) {
            return true;
        }
        return false;
    }

    private final boolean k() {
        if ((this.g.bcdDevice & 65280) == 1536) {
            return true;
        }
        return false;
    }

    private final boolean l() {
        if ((this.g.bcdDevice & 65280) == 1280) {
            return true;
        }
        return false;
    }

    private final boolean m() {
        if ((this.g.bcdDevice & 65280) != 1024 && ((this.g.bcdDevice & 65280) != 512 || this.g.iSerialNumber != 0)) {
            return false;
        }
        return true;
    }

    private final String a(byte[] arrby) throws UnsupportedEncodingException {
        return new String(arrby, 2, arrby[0] - 2, "UTF-16LE");
    }

    UsbDeviceConnection c() {
        return this.l;
    }

    void a(UsbDeviceConnection usbDeviceConnection) {
        this.l = usbDeviceConnection;
    }

    synchronized boolean a(Context context) {
        boolean bl = false;
        if (context != null) {
            this.j = context;
            bl = true;
        }
        return bl;
    }

    protected void setDriverParameters(D2xxManager.DriverParameters params) {
        this.s.setMaxBufferSize(params.getMaxBufferSize());
        this.s.setMaxTransferSize(params.getMaxTransferSize());
        this.s.setBufferNumber(params.getBufferNumber());
        this.s.setReadTimeout(params.getReadTimeout());
    }

    D2xxManager.DriverParameters d() {
        return this.s;
    }

    public int getReadTimeout() {
        return this.s.getReadTimeout();
    }

    private void n() {
        if (this.t == 1) {
            this.g.serialNumber = String.valueOf(this.g.serialNumber) + "A";
            this.g.description = String.valueOf(this.g.description) + " A";
        } else if (this.t == 2) {
            this.g.serialNumber = String.valueOf(this.g.serialNumber) + "B";
            this.g.description = String.valueOf(this.g.description) + " B";
        } else if (this.t == 3) {
            this.g.serialNumber = String.valueOf(this.g.serialNumber) + "C";
            this.g.description = String.valueOf(this.g.description) + " C";
        } else if (this.t == 4) {
            this.g.serialNumber = String.valueOf(this.g.serialNumber) + "D";
            this.g.description = String.valueOf(this.g.description) + " D";
        }
    }

    synchronized boolean a(UsbManager usbManager) {
        boolean bl = false;
        if (this.isOpen()) {
            return bl;
        }
        if (usbManager == null) {
            Log.e((String)"FTDI_Device::", (String)"UsbManager cannot be null.");
            return bl;
        }
        if (this.c() != null) {
            Log.e((String)"FTDI_Device::", (String)"There should not have an UsbConnection.");
            return bl;
        }
        this.a(usbManager.openDevice(this.c));
        if (this.c() == null) {
            Log.e((String)"FTDI_Device::", (String)"UsbConnection cannot be null.");
            return bl;
        }
        if (!this.c().claimInterface(this.d, true)) {
            Log.e((String)"FTDI_Device::", (String)"ClaimInteface returned false.");
            return bl;
        }
        Log.d((String)"FTDI_Device::", (String)"open SUCCESS");
        if (!this.q()) {
            Log.e((String)"FTDI_Device::", (String)"Failed to find endpoints.");
            return bl;
        }
        this.k.initialize(this.l, this.e);
        Log.d((String)"D2XX::", (String)"**********************Device Opened**********************");
        this.p = new o(this);
        this.m = new a(this, this.p, this.c(), this.f);
        this.o = new Thread(this.m);
        this.o.setName("bulkInThread");
        this.n = new Thread(new p(this.p));
        this.n.setName("processRequestThread");
        this.a(true, true);
        this.o.start();
        this.n.start();
        this.o();
        bl = true;
        return bl;
    }

    public synchronized boolean isOpen() {
        return this.b;
    }

    private synchronized void o() {
        this.b = true;
        --this.g.flags;
    }

    private synchronized void p() {
        this.b = false;
        this.g.flags&=2;
    }

    public synchronized void close() {
        if (this.n != null) {
            this.n.interrupt();
        }
        if (this.o != null) {
            this.o.interrupt();
        }
        if (this.l != null) {
            this.l.releaseInterface(this.d);
            this.l.close();
            this.l = null;
        }
        if (this.p != null) {
            this.p.g();
        }
        this.n = null;
        this.o = null;
        this.m = null;
        this.p = null;
        this.p();
    }

    protected UsbDevice getUsbDevice() {
        return this.c;
    }

    public D2xxManager.FtDeviceInfoListNode getDeviceInfo() {
        return this.g;
    }

    public int read(byte[] data, int length, long wait_ms) {
        int n = 0;
        if (!this.isOpen()) {
            return -1;
        }
        if (length <= 0) {
            return -2;
        }
        if (this.p == null) {
            return -3;
        }
        n = this.p.a(data, length, wait_ms);
        return n;
    }

    public int read(byte[] data, int length) {
        return this.read(data, length, this.s.getReadTimeout());
    }

    public int read(byte[] data) {
        return this.read(data, data.length, this.s.getReadTimeout());
    }

    public int write(byte[] data, int length) {
        return this.write(data, length, true);
    }

    public int write(byte[] data, int length, boolean wait) {
        Object object = this;
        int n = -1;
        if (!this.isOpen()) {
            return n;
        }
        if (length < 0) {
            return n;
        }
        UsbRequest usbRequest = this.k;
        if (wait) {
            usbRequest.setClientData(object);
        }
        if (length == 0) {
            byte[] arrby = new byte[1];
            if (usbRequest.queue(ByteBuffer.wrap(arrby), length)) {
                n = length;
            }
        } else if (usbRequest.queue(ByteBuffer.wrap(data), length)) {
            n = length;
        }
        if (wait) {
            do {
                if ((usbRequest = this.l.requestWait()) == null) {
                    Log.e((String)"FTDI_Device::", (String)"UsbConnection.requestWait() == null");
                    return -99;
                }
                object = usbRequest.getClientData();
            } while (object != this);
        }
        return n;
    }

    public int write(byte[] data) {
        return this.write(data, data.length, true);
    }

    public short getModemStatus() {
        if (!this.isOpen()) {
            return -1;
        }
        if (this.p == null) {
            return -2;
        }
        this.a&=-3;
        return (short)(this.g.modemStatus & 255);
    }

    public short getLineStatus() {
        if (!this.isOpen()) {
            return -1;
        }
        if (this.p == null) {
            return -2;
        }
        return this.g.lineStatus;
    }

    public int getQueueStatus() {
        if (!this.isOpen()) {
            return -1;
        }
        if (this.p == null) {
            return -2;
        }
        return this.p.c();
    }

    public boolean readBufferFull() {
        return this.p.a();
    }

    public long getEventStatus() {
        if (!this.isOpen()) {
            return -1;
        }
        if (this.p == null) {
            return -2;
        }
        long l = this.a;
        this.a = 0;
        return l;
    }

    public boolean setBaudRate(int baudRate) {
        byte by = 1;
        int[] arrn = new int[2];
        int n = 0;
        boolean bl = false;
        if (!this.isOpen()) {
            return bl;
        }
        switch (baudRate) {
            case 300: {
                arrn[0] = 10000;
                break;
            }
            case 600: {
                arrn[0] = 5000;
                break;
            }
            case 1200: {
                arrn[0] = 2500;
                break;
            }
            case 2400: {
                arrn[0] = 1250;
                break;
            }
            case 4800: {
                arrn[0] = 625;
                break;
            }
            case 9600: {
                arrn[0] = 16696;
                break;
            }
            case 19200: {
                arrn[0] = 32924;
                break;
            }
            case 38400: {
                arrn[0] = 49230;
                break;
            }
            case 57600: {
                arrn[0] = 52;
                break;
            }
            case 115200: {
                arrn[0] = 26;
                break;
            }
            case 230400: {
                arrn[0] = 13;
                break;
            }
            case 460800: {
                arrn[0] = 16390;
                break;
            }
            case 921600: {
                arrn[0] = 32771;
                break;
            }
            default: {
                by = this.f() && baudRate >= 1200 ? b.a(baudRate, arrn) : b.a(baudRate, arrn, this.g());
                n = 255;
            }
        }
        if (this.a() || this.i() || this.h()) {
            int[] arrn2 = arrn;
            arrn2[1] = arrn2[1] << 8;
            int[] arrn3 = arrn;
            arrn3[1] = arrn3[1] & 65280;
            int[] arrn4 = arrn;
            arrn4[1] = arrn4[1] | this.t;
        }
        if (by == 1 && (n = this.c().controlTransfer(64, 3, arrn[0], arrn[1], null, 0, 0)) == 0) {
            bl = true;
        }
        return bl;
    }

    public boolean setDataCharacteristics(byte dataBits, byte stopBits, byte parity) {
        short s = 0;
        int n = 0;
        boolean bl = false;
        if (!this.isOpen()) {
            return bl;
        }
        s = dataBits;
        s = (short)(s | parity << 8);
        s = (short)(s | stopBits << 11);
        this.g.breakOnParam = s;
        n = this.c().controlTransfer(64, 4, (int)s, this.t, null, 0, 0);
        if (n == 0) {
            bl = true;
        }
        return bl;
    }

    public boolean setBreakOn() {
        return this.a(16384);
    }

    public boolean setBreakOff() {
        return this.a(0);
    }

    private boolean a(int n) {
        boolean bl = false;
        int n2 = this.g.breakOnParam;
        n2|=n;
        if (!this.isOpen()) {
            return bl;
        }
        int n3 = this.c().controlTransfer(64, 4, n2, this.t, null, 0, 0);
        if (n3 == 0) {
            bl = true;
        }
        return bl;
    }

    public boolean setFlowControl(short flowControl, byte xon, byte xoff) {
        boolean bl = false;
        int n = 0;
        short s = 0;
        short s2 = flowControl;
        if (!this.isOpen()) {
            return bl;
        }
        if (s2 == 1024) {
            s = (short)(xoff << 8);
            s = (short)(s | xon & 255);
        }
        if ((n = this.c().controlTransfer(64, 2, (int)s, this.t | s2, null, 0, 0)) == 0) {
            bl = true;
            if (flowControl == 256) {
                bl = this.setRts();
            } else if (flowControl == 512) {
                bl = this.setDtr();
            }
        }
        return bl;
    }

    public boolean setRts() {
        boolean bl = false;
        int n = 514;
        if (!this.isOpen()) {
            return bl;
        }
        int n2 = this.c().controlTransfer(64, 1, n, this.t, null, 0, 0);
        if (n2 == 0) {
            bl = true;
        }
        return bl;
    }

    public boolean clrRts() {
        boolean bl = false;
        int n = 512;
        if (!this.isOpen()) {
            return bl;
        }
        int n2 = this.c().controlTransfer(64, 1, n, this.t, null, 0, 0);
        if (n2 == 0) {
            bl = true;
        }
        return bl;
    }

    public boolean setDtr() {
        boolean bl = false;
        int n = 257;
        if (!this.isOpen()) {
            return bl;
        }
        int n2 = this.c().controlTransfer(64, 1, n, this.t, null, 0, 0);
        if (n2 == 0) {
            bl = true;
        }
        return bl;
    }

    public boolean clrDtr() {
        boolean bl = false;
        int n = 256;
        if (!this.isOpen()) {
            return bl;
        }
        int n2 = this.c().controlTransfer(64, 1, n, this.t, null, 0, 0);
        if (n2 == 0) {
            bl = true;
        }
        return bl;
    }

    public boolean setChars(byte eventChar, byte eventCharEnable, byte errorChar, byte errorCharEnable) {
        int n;
        boolean bl = false;
        r r = new r();
        r.a = eventChar;
        r.b = eventCharEnable;
        r.c = errorChar;
        r.d = errorCharEnable;
        if (!this.isOpen()) {
            return bl;
        }
        int n2 = eventChar & 255;
        if (eventCharEnable != 0) {
            n2|=256;
        }
        if ((n = this.c().controlTransfer(64, 6, n2, this.t, null, 0, 0)) != 0) {
            return bl;
        }
        n2 = errorChar & 255;
        if (errorCharEnable > 0) {
            n2|=256;
        }
        if ((n = this.c().controlTransfer(64, 7, n2, this.t, null, 0, 0)) == 0) {
            this.h = r;
            bl = true;
        }
        return bl;
    }

    public boolean setBitMode(byte mask, byte bitMode) {
        int n = this.g.type;
        boolean bl = false;
        if (!this.isOpen()) {
            return bl;
        }
        if (n == 1) {
            return bl;
        }
        if (n == 0 && bitMode != 0) {
            if ((bitMode & 1) == 0) {
                return bl;
            }
        } else if (n == 4 && bitMode != 0) {
            if ((bitMode & 31) == 0) {
                return bl;
            }
            if (bitMode == 2 & this.d.getId() != 0) {
                return bl;
            }
        } else if (n == 5 && bitMode != 0) {
            if ((bitMode & 37) == 0) {
                return bl;
            }
        } else if (n == 6 && bitMode != 0) {
            if ((bitMode & 95) == 0) {
                return bl;
            }
            if ((bitMode & 72) > 0 & this.d.getId() != 0) {
                return bl;
            }
        } else if (n == 7 && bitMode != 0) {
            if ((bitMode & 7) == 0) {
                return bl;
            }
            if (bitMode == 2 & this.d.getId() != 0 & this.d.getId() != 1) {
                return bl;
            }
        } else if (n == 8 && bitMode != 0 && bitMode > 64) {
            return bl;
        }
        int n2 = bitMode << 8;
        int n3 = this.c().controlTransfer(64, 11, n2|=mask & 255, this.t, null, 0, 0);
        if (n3 == 0) {
            bl = true;
        }
        return bl;
    }

    public byte getBitMode() {
        int n = 0;
        byte[] arrby = new byte[1];
        if (!this.isOpen()) {
            return -1;
        }
        if (!this.g()) {
            return -2;
        }
        n = this.c().controlTransfer(-64, 12, 0, this.t, arrby, arrby.length, 0);
        if (n == arrby.length) {
            return arrby[0];
        }
        return -3;
    }

    public boolean resetDevice() {
        int n = 0;
        boolean bl = false;
        if (!this.isOpen()) {
            return bl;
        }
        n = this.c().controlTransfer(64, 0, 0, 0, null, 0, 0);
        if (n == 0) {
            bl = true;
        }
        return bl;
    }

    public int VendorCmdSet(int request, int wValue) {
        int n = 0;
        if (!this.isOpen()) {
            return -1;
        }
        n = this.c().controlTransfer(64, request, wValue, this.t, null, 0, 0);
        return n;
    }

    public int VendorCmdSet(int request, int wValue, byte[] buf, int datalen) {
        int n = 0;
        if (!this.isOpen()) {
            Log.e((String)"FTDI_Device::", (String)"VendorCmdSet: Device not open");
            return -1;
        }
        if (datalen < 0) {
            Log.e((String)"FTDI_Device::", (String)"VendorCmdSet: Invalid data length");
            return -1;
        }
        if (buf == null) {
            if (datalen > 0) {
                Log.e((String)"FTDI_Device::", (String)"VendorCmdSet: buf is null!");
                return -1;
            }
        } else if (buf.length < datalen) {
            Log.e((String)"FTDI_Device::", (String)"VendorCmdSet: length of buffer is smaller than data length to set");
            return -1;
        }
        n = this.c().controlTransfer(64, request, wValue, this.t, buf, datalen, 0);
        return n;
    }

    public int VendorCmdGet(int request, int wValue, byte[] buf, int datalen) {
        int n = 0;
        if (!this.isOpen()) {
            Log.e((String)"FTDI_Device::", (String)"VendorCmdGet: Device not open");
            return -1;
        }
        if (datalen < 0) {
            Log.e((String)"FTDI_Device::", (String)"VendorCmdGet: Invalid data length");
            return -1;
        }
        if (buf == null) {
            Log.e((String)"FTDI_Device::", (String)"VendorCmdGet: buf is null");
            return -1;
        }
        if (buf.length < datalen) {
            Log.e((String)"FTDI_Device::", (String)"VendorCmdGet: length of buffer is smaller than data length to get");
            return -1;
        }
        n = this.c().controlTransfer(-64, request, wValue, this.t, buf, datalen, 0);
        return n;
    }

    public void stopInTask() {
        try {
            if (!this.m.c()) {
                this.m.a();
            }
        }
        catch (InterruptedException var1_1) {
            Log.d((String)"FTDI_Device::", (String)"stopInTask called!");
            var1_1.printStackTrace();
        }
    }

    public void restartInTask() {
        this.m.b();
    }

    public boolean stoppedInTask() {
        return this.m.c();
    }

    public boolean purge(byte flags) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if ((flags & 1) == 1) {
            bl = true;
        }
        if ((flags & 2) == 2) {
            bl2 = true;
        }
        return this.a(bl, bl2);
    }

    private boolean a(boolean bl, boolean bl2) {
        boolean bl3 = false;
        int n = 0;
        int n2 = 0;
        if (!this.isOpen()) {
            return bl3;
        }
        if (bl) {
            n2 = 1;
            for (int i = 0; i < 6; ++i) {
                n = this.c().controlTransfer(64, 0, n2, this.t, null, 0, 0);
            }
            if (n > 0) {
                return bl3;
            }
            this.p.e();
        }
        if (bl2) {
            n2 = 2;
            n = this.c().controlTransfer(64, 0, n2, this.t, null, 0, 0);
            if (n == 0) {
                bl3 = true;
            }
        }
        return bl3;
    }

    public boolean setLatencyTimer(byte latency) {
        int n = latency;
        boolean bl = false;
        n&=255;
        if (!this.isOpen()) {
            return bl;
        }
        int n2 = this.c().controlTransfer(64, 9, n, this.t, null, 0, 0);
        if (n2 == 0) {
            this.r = latency;
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }

    public byte getLatencyTimer() {
        byte[] arrby = new byte[1];
        int n = 0;
        if (!this.isOpen()) {
            return -1;
        }
        n = this.c().controlTransfer(-64, 10, 0, this.t, arrby, arrby.length, 0);
        if (n == arrby.length) {
            return arrby[0];
        }
        return 0;
    }

    public boolean setEventNotification(long Mask) {
        boolean bl = false;
        if (!this.isOpen()) {
            return bl;
        }
        if (Mask != 0) {
            this.a = 0;
            this.i.a = Mask;
            bl = true;
        }
        return bl;
    }

    private boolean q() {
        for (int i = 0; i < this.d.getEndpointCount(); ++i) {
            Log.i((String)"FTDI_Device::", (String)("EP: " + String.format("0x%02X", this.d.getEndpoint(i).getAddress())));
            if (this.d.getEndpoint(i).getType() == 2) {
                if (this.d.getEndpoint(i).getDirection() == 128) {
                    this.f = this.d.getEndpoint(i);
                    this.u = this.f.getMaxPacketSize();
                    continue;
                }
                this.e = this.d.getEndpoint(i);
                continue;
            }
            Log.i((String)"FTDI_Device::", (String)"Not Bulk Endpoint");
        }
        if (this.e == null || this.f == null) {
            return false;
        }
        return true;
    }

    public FT_EEPROM eepromRead() {
        if (!this.isOpen()) {
            return null;
        }
        return this.q.a();
    }

    public short eepromWrite(FT_EEPROM eeData) {
        if (!this.isOpen()) {
            return -1;
        }
        return this.q.a(eeData);
    }

    public boolean eepromErase() {
        boolean bl = false;
        if (!this.isOpen()) {
            return bl;
        }
        if (this.q.c() == 0) {
            bl = true;
        }
        return bl;
    }

    public int eepromWriteUserArea(byte[] data) {
        if (!this.isOpen()) {
            return 0;
        }
        return this.q.a(data);
    }

    public byte[] eepromReadUserArea(int length) {
        if (!this.isOpen()) {
            return null;
        }
        return this.q.a(length);
    }

    public int eepromGetUserAreaSize() {
        if (!this.isOpen()) {
            return -1;
        }
        return this.q.b();
    }

    public int eepromReadWord(short offset) {
        int n = -1;
        if (!this.isOpen()) {
            return n;
        }
        n = this.q.a(offset);
        return n;
    }

    public boolean eepromWriteWord(short address, short data) {
        boolean bl = false;
        if (!this.isOpen()) {
            return bl;
        }
        bl = this.q.a(address, data);
        return bl;
    }

    int e() {
        return this.u;
    }
}

