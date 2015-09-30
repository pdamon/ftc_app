/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.robocol;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.robocol.RobocolParsable;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class RobocolDatagram {
    private DatagramPacket a;

    public RobocolDatagram(RobocolParsable message) throws RobotCoreException {
        this.setData(message.toByteArray());
    }

    public RobocolDatagram(byte[] message) {
        this.setData(message);
    }

    protected RobocolDatagram(DatagramPacket packet) {
        this.a = packet;
    }

    protected RobocolDatagram() {
        this.a = null;
    }

    public RobocolParsable.MsgType getMsgType() {
        return RobocolParsable.MsgType.fromByte(this.a.getData()[0]);
    }

    public int getLength() {
        return this.a.getLength();
    }

    public int getPayloadLength() {
        return this.a.getLength() - 3;
    }

    public byte[] getData() {
        return this.a.getData();
    }

    public void setData(byte[] data) {
        this.a = new DatagramPacket(data, data.length);
    }

    public InetAddress getAddress() {
        return this.a.getAddress();
    }

    public void setAddress(InetAddress address) {
        this.a.setAddress(address);
    }

    public String toString() {
        int n = 0;
        String string = "NONE";
        String string2 = null;
        if (this.a != null && this.a.getAddress() != null && this.a.getLength() > 0) {
            string = RobocolParsable.MsgType.fromByte(this.a.getData()[0]).name();
            n = this.a.getLength();
            string2 = this.a.getAddress().getHostAddress();
        }
        return String.format("RobocolDatagram - type:%s, addr:%s, size:%d", string, string2, n);
    }

    protected DatagramPacket getPacket() {
        return this.a;
    }

    protected void setPacket(DatagramPacket packet) {
        this.a = packet;
    }
}

