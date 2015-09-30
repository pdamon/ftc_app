/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.robocol;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.robocol.RobocolParsable;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.TypeConversion;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Comparator;

public class Command
implements RobocolParsable,
Comparable<Command>,
Comparator<Command> {
    public static final int MAX_COMMAND_LENGTH = 256;
    private static final Charset h = Charset.forName("UTF-8");
    String a;
    String b;
    byte[] c;
    byte[] d;
    long e;
    boolean f = false;
    byte g = 0;

    public Command(String name) {
        this(name, "");
    }

    public Command(String name, String extra) {
        this.a = name;
        this.b = extra;
        this.c = TypeConversion.stringToUtf8(this.a);
        this.d = TypeConversion.stringToUtf8(this.b);
        this.e = Command.generateTimestamp();
        if (this.c.length > 256) {
            throw new IllegalArgumentException(String.format("command name length is too long (MAX: %d)", 256));
        }
        if (this.d.length > 256) {
            throw new IllegalArgumentException(String.format("command extra data length is too long (MAX: %d)", 256));
        }
    }

    public Command(byte[] byteArray) throws RobotCoreException {
        this.fromByteArray(byteArray);
    }

    public void acknowledge() {
        this.f = true;
    }

    public boolean isAcknowledged() {
        return this.f;
    }

    public String getName() {
        return this.a;
    }

    public String getExtra() {
        return this.b;
    }

    public byte getAttempts() {
        return this.g;
    }

    public long getTimestamp() {
        return this.e;
    }

    @Override
    public RobocolParsable.MsgType getRobocolMsgType() {
        return RobocolParsable.MsgType.COMMAND;
    }

    @Override
    public byte[] toByteArray() throws RobotCoreException {
        if (this.g != 127) {
            this.g = (byte)(this.g + 1);
        }
        short s = (short)(11 + this.c.length + this.d.length);
        ByteBuffer byteBuffer = ByteBuffer.allocate(3 + s);
        try {
            byteBuffer.put(this.getRobocolMsgType().asByte());
            byteBuffer.putShort(s);
            byteBuffer.putLong(this.e);
            byteBuffer.put(this.f ? 1 : 0);
            byteBuffer.put((byte)this.c.length);
            byteBuffer.put(this.c);
            byteBuffer.put((byte)this.d.length);
            byteBuffer.put(this.d);
        }
        catch (BufferOverflowException var3_3) {
            RobotLog.logStacktrace(var3_3);
        }
        return byteBuffer.array();
    }

    @Override
    public void fromByteArray(byte[] byteArray) throws RobotCoreException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray, 3, byteArray.length - 3);
        this.e = byteBuffer.getLong();
        this.f = byteBuffer.get() == 1;
        int n = TypeConversion.unsignedByteToInt(byteBuffer.get());
        this.c = new byte[n];
        byteBuffer.get(this.c);
        this.a = TypeConversion.utf8ToString(this.c);
        n = TypeConversion.unsignedByteToInt(byteBuffer.get());
        this.d = new byte[n];
        byteBuffer.get(this.d);
        this.b = TypeConversion.utf8ToString(this.d);
    }

    public String toString() {
        return String.format("command: %20d %5s %s", this.e, this.f, this.a);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Command) {
            Command command = (Command)o;
            if (this.a.equals(command.a) && this.e == command.e) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (int)((long)this.a.hashCode() & this.e);
    }

    @Override
    public int compareTo(Command another) {
        int n = this.a.compareTo(another.a);
        if (n != 0) {
            return n;
        }
        if (this.e < another.e) {
            return -1;
        }
        if (this.e > another.e) {
            return 1;
        }
        return 0;
    }

    @Override
    public int compare(Command c1, Command c2) {
        return c1.compareTo(c2);
    }

    public static long generateTimestamp() {
        return System.nanoTime();
    }
}

