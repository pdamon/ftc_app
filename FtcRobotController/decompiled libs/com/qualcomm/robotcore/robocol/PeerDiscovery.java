/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.robocol;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.robocol.RobocolParsable;
import com.qualcomm.robotcore.util.RobotLog;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

public class PeerDiscovery
implements RobocolParsable {
    public static final short PAYLOAD_SIZE = 10;
    public static final short BUFFER_SIZE = 13;
    public static final byte ROBOCOL_VERSION = 1;
    private PeerType a;

    public PeerDiscovery(PeerType peerType) {
        this.a = peerType;
    }

    public PeerType getPeerType() {
        return this.a;
    }

    @Override
    public RobocolParsable.MsgType getRobocolMsgType() {
        return RobocolParsable.MsgType.PEER_DISCOVERY;
    }

    @Override
    public byte[] toByteArray() throws RobotCoreException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(13);
        try {
            byteBuffer.put(this.getRobocolMsgType().asByte());
            byteBuffer.putShort(10);
            byteBuffer.put(1);
            byteBuffer.put(this.a.asByte());
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
        byte by = byteBuffer.get();
        switch (by) {
            case 1: {
                this.a = PeerType.fromByte(byteBuffer.get());
                break;
            }
        }
    }

    public String toString() {
        return String.format("Peer Discovery - peer type: %s", this.a.name());
    }

    public static enum PeerType {
        NOT_SET(0),
        PEER(1),
        GROUP_OWNER(2);
        
        private static final PeerType[] a;
        private int b;

        public static PeerType fromByte(byte b) {
            PeerType peerType = NOT_SET;
            try {
                peerType = a[b];
            }
            catch (ArrayIndexOutOfBoundsException var2_2) {
                RobotLog.w(String.format("Cannot convert %d to Peer: %s", Byte.valueOf(b), var2_2.toString()));
            }
            return peerType;
        }

        private PeerType(int type) {
            this.b = type;
        }

        public byte asByte() {
            return (byte)this.b;
        }

        static {
            a = PeerType.values();
        }
    }

}

