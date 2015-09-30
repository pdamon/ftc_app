/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.robocol;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.robocol.RobocolParsable;
import com.qualcomm.robotcore.util.RobotLog;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

public class Heartbeat
implements RobocolParsable {
    public static final short PAYLOAD_SIZE = 10;
    public static final short BUFFER_SIZE = 13;
    public static final short MAX_SEQUENCE_NUMBER = 10000;
    private static short a = 0;
    private long b;
    private short c;

    public Heartbeat() {
        this.c = Heartbeat.a();
        this.b = System.nanoTime();
    }

    public Heartbeat(Token token) {
        switch (token) {
            case EMPTY: {
                this.c = 0;
                this.b = 0;
            }
        }
    }

    public long getTimestamp() {
        return this.b;
    }

    public double getElapsedTime() {
        return (double)(System.nanoTime() - this.b) / 1.0E9;
    }

    public short getSequenceNumber() {
        return this.c;
    }

    @Override
    public RobocolParsable.MsgType getRobocolMsgType() {
        return RobocolParsable.MsgType.HEARTBEAT;
    }

    @Override
    public byte[] toByteArray() throws RobotCoreException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(13);
        try {
            byteBuffer.put(this.getRobocolMsgType().asByte());
            byteBuffer.putShort(10);
            byteBuffer.putShort(this.c);
            byteBuffer.putLong(this.b);
        }
        catch (BufferOverflowException var2_2) {
            RobotLog.logStacktrace(var2_2);
        }
        return byteBuffer.array();
    }

    @Override
    public void fromByteArray(byte[] byteArray) throws RobotCoreException {
        if (byteArray.length < 13) {
            throw new RobotCoreException("Expected buffer of at least 13 bytes, received " + byteArray.length);
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray, 3, 10);
        this.c = byteBuffer.getShort();
        this.b = byteBuffer.getLong();
    }

    public String toString() {
        return String.format("Heartbeat - seq: %4d, time: %d", this.c, this.b);
    }

    private static synchronized short a() {
        short s = a;
        if ((Heartbeat.a = (short)(a + 1)) > 10000) {
            a = 0;
        }
        return s;
    }

    public static enum Token {
        EMPTY;
        

        private Token() {
        }
    }

}

