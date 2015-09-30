/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.qualcomm.modernrobotics.ReadWriteRunnableUsbHandler
 *  com.qualcomm.robotcore.exception.RobotCoreException
 *  com.qualcomm.robotcore.hardware.usb.RobotUsbDevice
 *  com.qualcomm.robotcore.hardware.usb.RobotUsbDevice$Channel
 *  com.qualcomm.robotcore.util.ElapsedTime
 *  com.qualcomm.robotcore.util.RobotLog
 *  com.qualcomm.robotcore.util.SerialNumber
 *  com.qualcomm.robotcore.util.TypeConversion
 */
package com.qualcomm.hardware;

import com.qualcomm.hardware.ReadWriteRunnable;
import com.qualcomm.hardware.ReadWriteRunnableSegment;
import com.qualcomm.modernrobotics.ReadWriteRunnableUsbHandler;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.usb.RobotUsbDevice;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;
import com.qualcomm.robotcore.util.TypeConversion;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;

public class ReadWriteRunnableStandard
implements ReadWriteRunnable {
    protected final byte[] localDeviceReadCache = new byte[256];
    protected final byte[] localDeviceWriteCache = new byte[256];
    protected Map<Integer, ReadWriteRunnableSegment> segments = new HashMap<Integer, ReadWriteRunnableSegment>();
    protected ConcurrentLinkedQueue<Integer> segmentReadQueue = new ConcurrentLinkedQueue();
    protected ConcurrentLinkedQueue<Integer> segmentWriteQueue = new ConcurrentLinkedQueue();
    protected final SerialNumber serialNumber;
    protected final ReadWriteRunnableUsbHandler usbHandler;
    protected int startAddress;
    protected int monitorLength;
    protected volatile boolean running = false;
    protected volatile boolean shutdownComplete = false;
    private volatile boolean a = false;
    protected ReadWriteRunnable.Callback callback;
    protected final boolean DEBUG_LOGGING;

    public ReadWriteRunnableStandard(SerialNumber serialNumber, RobotUsbDevice device, int monitorLength, int startAddress, boolean debug) {
        this.serialNumber = serialNumber;
        this.startAddress = startAddress;
        this.monitorLength = monitorLength;
        this.DEBUG_LOGGING = debug;
        this.callback = new ReadWriteRunnable.EmptyCallback();
        this.usbHandler = new ReadWriteRunnableUsbHandler(device);
    }

    @Override
    public void setCallback(ReadWriteRunnable.Callback callback) {
        this.callback = callback;
    }

    @Override
    public void blockUntilReady() throws RobotCoreException, InterruptedException {
    }

    public void startBlockingWork() {
    }

    public boolean writeNeeded() {
        return this.a;
    }

    public void setWriteNeeded(boolean set) {
        this.a = set;
    }

    @Override
    public void write(int address, byte[] data) {
        byte[] arrby = this.localDeviceWriteCache;
        synchronized (arrby) {
            System.arraycopy(data, 0, this.localDeviceWriteCache, address, data.length);
            this.a = true;
        }
    }

    @Override
    public byte[] readFromWriteCache(int address, int size) {
        byte[] arrby = this.localDeviceWriteCache;
        synchronized (arrby) {
            return Arrays.copyOfRange(this.localDeviceWriteCache, address, address + size);
        }
    }

    @Override
    public byte[] read(int address, int size) {
        byte[] arrby = this.localDeviceReadCache;
        synchronized (arrby) {
            return Arrays.copyOfRange(this.localDeviceReadCache, address, address + size);
        }
    }

    @Override
    public void close() {
        try {
            this.blockUntilReady();
            this.startBlockingWork();
        }
        catch (InterruptedException var1_1) {}
        catch (RobotCoreException var1_2) {}
        finally {
            this.running = false;
            while (!this.shutdownComplete) {
                Thread.yield();
            }
            this.usbHandler.close();
        }
    }

    @Override
    public ReadWriteRunnableSegment createSegment(int key, int address, int size) {
        ReadWriteRunnableSegment readWriteRunnableSegment = new ReadWriteRunnableSegment(address, size);
        this.segments.put(key, readWriteRunnableSegment);
        return readWriteRunnableSegment;
    }

    public void destroySegment(int key) {
        this.segments.remove(key);
    }

    public ReadWriteRunnableSegment getSegment(int key) {
        return this.segments.get(key);
    }

    @Override
    public void queueSegmentRead(int key) {
        this.queueIfNotAlreadyQueued(key, this.segmentReadQueue);
    }

    @Override
    public void queueSegmentWrite(int key) {
        this.queueIfNotAlreadyQueued(key, this.segmentWriteQueue);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        boolean bl = true;
        int n = 0;
        byte[] arrby = new byte[this.monitorLength + this.startAddress];
        ElapsedTime elapsedTime = new ElapsedTime();
        String string = "Device " + this.serialNumber.toString();
        this.running = true;
        RobotLog.v((String)String.format("starting read/write loop for device %s", new Object[]{this.serialNumber}));
        try {
            this.usbHandler.purge(RobotUsbDevice.Channel.RX);
            while (this.running) {
                Object object;
                byte[] arrby2;
                if (this.DEBUG_LOGGING) {
                    elapsedTime.log(string);
                    elapsedTime.reset();
                }
                try {
                    this.usbHandler.read(n, arrby);
                    while (!this.segmentReadQueue.isEmpty()) {
                        object = this.segments.get(this.segmentReadQueue.remove());
                        arrby2 = new byte[object.getReadBuffer().length];
                        this.usbHandler.read(object.getAddress(), arrby2);
                        try {
                            object.getReadLock().lock();
                            System.arraycopy(arrby2, 0, object.getReadBuffer(), 0, object.getReadBuffer().length);
                            continue;
                        }
                        finally {
                            object.getReadLock().unlock();
                            continue;
                        }
                    }
                }
                catch (RobotCoreException var6_7) {
                    RobotLog.w((String)String.format("could not read from device %s: %s", new Object[]{this.serialNumber, var6_7.getMessage()}));
                }
                object = this.localDeviceReadCache;
                // MONITORENTER : object
                System.arraycopy(arrby, 0, this.localDeviceReadCache, n, arrby.length);
                // MONITOREXIT : object
                if (this.DEBUG_LOGGING) {
                    this.dumpBuffers("read", this.localDeviceReadCache);
                }
                this.callback.readComplete();
                this.waitForSyncdEvents();
                if (bl) {
                    n = this.startAddress;
                    arrby = new byte[this.monitorLength];
                    bl = false;
                }
                byte[] arrby3 = this.localDeviceWriteCache;
                object = arrby3;
                // MONITORENTER : arrby3
                System.arraycopy(this.localDeviceWriteCache, n, arrby, 0, arrby.length);
                // MONITOREXIT : object
                try {
                    if (this.writeNeeded()) {
                        this.usbHandler.write(n, arrby);
                        this.setWriteNeeded(false);
                    }
                    while (!this.segmentWriteQueue.isEmpty()) {
                        object = this.segments.get(this.segmentWriteQueue.remove());
                        try {
                            object.getWriteLock().lock();
                            arrby2 = Arrays.copyOf(object.getWriteBuffer(), object.getWriteBuffer().length);
                        }
                        finally {
                            object.getWriteLock().unlock();
                        }
                        this.usbHandler.write(object.getAddress(), arrby2);
                    }
                }
                catch (RobotCoreException var6_8) {
                    RobotLog.w((String)String.format("could not write to device %s: %s", new Object[]{this.serialNumber, var6_8.getMessage()}));
                }
                if (this.DEBUG_LOGGING) {
                    this.dumpBuffers("write", this.localDeviceWriteCache);
                }
                this.callback.writeComplete();
                this.usbHandler.throwIfUsbErrorCountIsTooHigh();
            }
        }
        catch (NullPointerException var6_9) {
            RobotLog.w((String)String.format("could not write to device %s: FTDI Null Pointer Exception", new Object[]{this.serialNumber}));
            RobotLog.logStacktrace((Exception)var6_9);
            RobotLog.setGlobalErrorMsg((String)"There was a problem communicating with a Modern Robotics USB device");
        }
        catch (InterruptedException var6_10) {
            RobotLog.w((String)String.format("could not write to device %s: Interrupted Exception", new Object[]{this.serialNumber}));
        }
        catch (RobotCoreException var6_11) {
            RobotLog.w((String)var6_11.getMessage());
            RobotLog.setGlobalErrorMsg((String)String.format("There was a problem communicating with a Modern Robotics USB device %s", new Object[]{this.serialNumber}));
        }
        RobotLog.v((String)String.format("stopped read/write loop for device %s", new Object[]{this.serialNumber}));
        this.running = false;
        this.shutdownComplete = true;
    }

    protected void waitForSyncdEvents() throws RobotCoreException, InterruptedException {
    }

    protected void dumpBuffers(String name, byte[] byteArray) {
        RobotLog.v((String)("Dumping " + name + " buffers for " + (Object)this.serialNumber));
        StringBuilder stringBuilder = new StringBuilder(1024);
        for (int i = 0; i < this.startAddress + this.monitorLength; ++i) {
            stringBuilder.append(String.format(" %02x", TypeConversion.unsignedByteToInt((byte)byteArray[i])));
            if ((i + 1) % 16 != 0) continue;
            stringBuilder.append("\n");
        }
        RobotLog.v((String)stringBuilder.toString());
    }

    protected void queueIfNotAlreadyQueued(int key, ConcurrentLinkedQueue<Integer> queue) {
        if (!queue.contains(key)) {
            queue.add(key);
        }
    }
}

