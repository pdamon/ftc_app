/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.robocol;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.robocol.RobocolParsable;
import com.qualcomm.robotcore.util.TypeConversion;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Telemetry
implements RobocolParsable {
    public static final String DEFAULT_TAG = "TELEMETRY_DATA";
    private static final Charset a = Charset.forName("UTF-8");
    private final Map<String, String> b = new HashMap<String, String>();
    private final Map<String, Float> c = new HashMap<String, Float>();
    private String d = "";
    private long e = 0;

    public Telemetry() {
    }

    public Telemetry(byte[] byteArray) throws RobotCoreException {
        this.fromByteArray(byteArray);
    }

    public synchronized long getTimestamp() {
        return this.e;
    }

    public synchronized void setTag(String tag) {
        this.d = tag;
    }

    public synchronized String getTag() {
        if (this.d.length() == 0) {
            return "TELEMETRY_DATA";
        }
        return this.d;
    }

    public synchronized void addData(String key, String msg) {
        this.b.put(key, msg);
    }

    public synchronized void addData(String key, Object msg) {
        this.b.put(key, msg.toString());
    }

    public synchronized void addData(String key, float msg) {
        this.c.put(key, Float.valueOf(msg));
    }

    public synchronized void addData(String key, double msg) {
        this.c.put(key, Float.valueOf((float)msg));
    }

    public synchronized Map<String, String> getDataStrings() {
        return this.b;
    }

    public synchronized Map<String, Float> getDataNumbers() {
        return this.c;
    }

    public synchronized boolean hasData() {
        return !this.b.isEmpty() || !this.c.isEmpty();
    }

    public synchronized void clearData() {
        this.e = 0;
        this.b.clear();
        this.c.clear();
    }

    @Override
    public RobocolParsable.MsgType getRobocolMsgType() {
        return RobocolParsable.MsgType.TELEMETRY;
    }

    @Override
    public synchronized byte[] toByteArray() throws RobotCoreException {
        byte[] arrby;
        this.e = System.currentTimeMillis();
        if (this.b.size() > 256) {
            throw new RobotCoreException("Cannot have more than 256 string data points");
        }
        if (this.c.size() > 256) {
            throw new RobotCoreException("Cannot have more than 256 number data points");
        }
        int n = this.a() + 8;
        int n2 = 3 + n;
        if (n2 > 4098) {
            throw new RobotCoreException(String.format("Cannot send telemetry data of %d bytes; max is %d", n2, 4098));
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.put(this.getRobocolMsgType().asByte());
        byteBuffer.putShort((short)n);
        byteBuffer.putLong(this.e);
        if (this.d.length() == 0) {
            byteBuffer.put(0);
        } else {
            byte[] iterator = this.d.getBytes(a);
            if (iterator.length > 256) {
                throw new RobotCoreException(String.format("Telemetry tag cannot exceed 256 bytes [%s]", this.d));
            }
            byteBuffer.put((byte)iterator.length);
            byteBuffer.put(iterator);
        }
        byteBuffer.put((byte)this.b.size());
        for (Map.Entry entry2 : this.b.entrySet()) {
            arrby = entry2.getKey().getBytes(a);
            byte[] arrby2 = entry2.getValue().getBytes(a);
            if (arrby.length > 256 || arrby2.length > 256) {
                throw new RobotCoreException(String.format("Telemetry elements cannot exceed 256 bytes [%s:%s]", entry2.getKey(), entry2.getValue()));
            }
            byteBuffer.put((byte)arrby.length);
            byteBuffer.put(arrby);
            byteBuffer.put((byte)arrby2.length);
            byteBuffer.put(arrby2);
        }
        byteBuffer.put((byte)this.c.size());
        for (Map.Entry entry2 : this.c.entrySet()) {
            arrby = ((String)entry2.getKey()).getBytes(a);
            float f = ((Float)entry2.getValue()).floatValue();
            if (arrby.length > 256) {
                throw new RobotCoreException(String.format("Telemetry elements cannot exceed 256 bytes [%s:%f]", entry2.getKey(), Float.valueOf(f)));
            }
            byteBuffer.put((byte)arrby.length);
            byteBuffer.put(arrby);
            byteBuffer.putFloat(f);
        }
        return byteBuffer.array();
    }

    @Override
    public synchronized void fromByteArray(byte[] byteArray) throws RobotCoreException {
        int n;
        Object object;
        int n2;
        this.clearData();
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray, 3, byteArray.length - 3);
        this.e = byteBuffer.getLong();
        int n3 = TypeConversion.unsignedByteToInt(byteBuffer.get());
        if (n3 == 0) {
            this.d = "";
        } else {
            byte[] arrby = new byte[n3];
            byteBuffer.get(arrby);
            this.d = new String(arrby, a);
        }
        int n4 = byteBuffer.get();
        for (n = 0; n < n4; ++n) {
            n2 = TypeConversion.unsignedByteToInt(byteBuffer.get());
            byte[] arrby = new byte[n2];
            byteBuffer.get(arrby);
            int n5 = TypeConversion.unsignedByteToInt(byteBuffer.get());
            object = new byte[n5];
            byteBuffer.get((byte[])object);
            String string = new String(arrby, a);
            String string2 = new String((byte[])object, a);
            this.b.put(string, string2);
        }
        n = byteBuffer.get();
        for (n2 = 0; n2 < n; ++n2) {
            int n6 = TypeConversion.unsignedByteToInt(byteBuffer.get());
            byte[] arrby = new byte[n6];
            byteBuffer.get(arrby);
            object = new String(arrby, a);
            float f = byteBuffer.getFloat();
            this.c.put((String)object, Float.valueOf(f));
        }
    }

    private int a() {
        int n = 0;
        n+=1 + this.d.getBytes(a).length;
        ++n;
        for (Map.Entry<String, String> entry2 : this.b.entrySet()) {
            n+=1 + entry2.getKey().getBytes(a).length;
            n+=1 + entry2.getValue().getBytes(a).length;
        }
        ++n;
        for (Map.Entry entry : this.c.entrySet()) {
            n+=1 + ((String)entry.getKey()).getBytes(a).length;
            n+=4;
        }
        return n;
    }
}

