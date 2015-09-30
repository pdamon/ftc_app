/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.robocol;

import com.qualcomm.robotcore.robocol.RobocolConfig;
import com.qualcomm.robotcore.robocol.RobocolDatagram;
import com.qualcomm.robotcore.util.RobotLog;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketException;

public class RobocolDatagramSocket {
    private final byte[] a = new byte[4098];
    private DatagramSocket b;
    private final DatagramPacket c = new DatagramPacket(this.a, this.a.length);
    private final RobocolDatagram d = new RobocolDatagram();
    private volatile State e = State.CLOSED;

    public void listen(InetAddress destAddress) throws SocketException {
        this.bind(new InetSocketAddress(RobocolConfig.determineBindAddress(destAddress), 20884));
    }

    public void bind(InetSocketAddress bindAddress) throws SocketException {
        if (this.e != State.CLOSED) {
            this.close();
        }
        this.e = State.LISTENING;
        RobotLog.d("RobocolDatagramSocket binding to " + bindAddress.toString());
        this.b = new DatagramSocket(bindAddress);
    }

    public void connect(InetAddress connectAddress) throws SocketException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(connectAddress, 20884);
        RobotLog.d("RobocolDatagramSocket connected to " + inetSocketAddress.toString());
        this.b.connect(inetSocketAddress);
    }

    public void close() {
        this.e = State.CLOSED;
        if (this.b != null) {
            this.b.close();
        }
        RobotLog.d("RobocolDatagramSocket is closed");
    }

    public void send(RobocolDatagram message) {
        try {
            this.b.send(message.getPacket());
        }
        catch (IllegalArgumentException var2_2) {
            RobotLog.w("Unable to send RobocolDatagram: " + var2_2.toString());
            RobotLog.w("               " + message.toString());
        }
        catch (IOException var2_3) {
            RobotLog.w("Unable to send RobocolDatagram: " + var2_3.toString());
            RobotLog.w("               " + message.toString());
        }
        catch (NullPointerException var2_4) {
            RobotLog.w("Unable to send RobocolDatagram: " + var2_4.toString());
            RobotLog.w("               " + message.toString());
        }
    }

    public RobocolDatagram recv() {
        try {
            this.b.receive(this.c);
        }
        catch (PortUnreachableException var1_1) {
            RobotLog.d("RobocolDatagramSocket receive error: remote port unreachable");
            return null;
        }
        catch (IOException var1_2) {
            RobotLog.d("RobocolDatagramSocket receive error: " + var1_2.toString());
            return null;
        }
        catch (NullPointerException var1_3) {
            RobotLog.d("RobocolDatagramSocket receive error: " + var1_3.toString());
        }
        this.d.setPacket(this.c);
        return this.d;
    }

    public State getState() {
        return this.e;
    }

    public InetAddress getInetAddress() {
        if (this.b == null) {
            return null;
        }
        return this.b.getInetAddress();
    }

    public InetAddress getLocalAddress() {
        if (this.b == null) {
            return null;
        }
        return this.b.getLocalAddress();
    }

    public boolean isRunning() {
        return this.e == State.LISTENING;
    }

    public boolean isClosed() {
        return this.e == State.CLOSED;
    }

    public static enum State {
        LISTENING,
        CLOSED,
        ERROR;
        

        private State() {
        }
    }

}

