/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.eventloop;

import com.qualcomm.robotcore.eventloop.EventLoop;
import com.qualcomm.robotcore.eventloop.SyncdDevice;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.robocol.Command;
import com.qualcomm.robotcore.robocol.Heartbeat;
import com.qualcomm.robotcore.robocol.PeerDiscovery;
import com.qualcomm.robotcore.robocol.RobocolDatagram;
import com.qualcomm.robotcore.robocol.RobocolDatagramSocket;
import com.qualcomm.robotcore.robocol.RobocolParsable;
import com.qualcomm.robotcore.robocol.Telemetry;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class EventLoopManager {
    public static final String SYSTEM_TELEMETRY = "SYSTEM_TELEMETRY";
    public static final String ROBOT_BATTERY_LEVEL_KEY = "Robot Battery Level";
    public static final String RC_BATTERY_LEVEL_KEY = "RobotController Battery Level";
    private static final EventLoop a = new a();
    public State state = State.NOT_STARTED;
    private Thread b = new Thread();
    private Thread c = new Thread();
    private final RobocolDatagramSocket d;
    private boolean e = false;
    private ElapsedTime f = new ElapsedTime();
    private EventLoop g = a;
    private final Gamepad[] h = new Gamepad[]{new Gamepad(), new Gamepad()};
    private Heartbeat i = new Heartbeat(Heartbeat.Token.EMPTY);
    private EventLoopMonitor j = null;
    private final Set<SyncdDevice> k = new CopyOnWriteArraySet<SyncdDevice>();
    private final Command[] l = new Command[8];
    private int m = 0;
    private final Set<Command> n = new CopyOnWriteArraySet<Command>();
    private InetAddress o;

    public void handleDroppedConnection() {
        OpModeManager opModeManager = this.g.getOpModeManager();
        String string = "Lost connection while running op mode: " + opModeManager.getActiveOpModeName();
        opModeManager.initActiveOpMode("Stop Robot");
        this.a(State.DROPPED_CONNECTION);
        RobotLog.i(string);
    }

    public EventLoopManager(RobocolDatagramSocket socket) {
        this.d = socket;
        this.a(State.NOT_STARTED);
    }

    public void setMonitor(EventLoopMonitor monitor) {
        this.j = monitor;
    }

    public void start(EventLoop eventLoop) throws RobotCoreException {
        this.e = false;
        this.c = new Thread(new d());
        this.c.start();
        new Thread(new c()).start();
        this.setEventLoop(eventLoop);
    }

    public void shutdown() {
        this.d.close();
        this.c.interrupt();
        this.e = true;
        this.b();
    }

    public void registerSyncdDevice(SyncdDevice device) {
        this.k.add(device);
    }

    public void unregisterSyncdDevice(SyncdDevice device) {
        this.k.remove(device);
    }

    public void setEventLoop(EventLoop eventLoop) throws RobotCoreException {
        if (eventLoop == null) {
            eventLoop = a;
            RobotLog.d("Event loop cannot be null, using empty event loop");
        }
        this.b();
        this.g = eventLoop;
        this.a();
    }

    public EventLoop getEventLoop() {
        return this.g;
    }

    public Gamepad getGamepad() {
        return this.getGamepad(0);
    }

    public Gamepad getGamepad(int port) {
        Range.throwIfRangeIsInvalid(port, 0.0, 1.0);
        return this.h[port];
    }

    public Gamepad[] getGamepads() {
        return this.h;
    }

    public Heartbeat getHeartbeat() {
        return this.i;
    }

    public void sendTelemetryData(Telemetry telemetry) {
        try {
            this.d.send(new RobocolDatagram(telemetry.toByteArray()));
        }
        catch (RobotCoreException var2_2) {
            RobotLog.w("Failed to send telemetry data");
            RobotLog.logStacktrace(var2_2);
        }
        telemetry.clearData();
    }

    public void sendCommand(Command command) {
        this.n.add(command);
    }

    private void a() throws RobotCoreException {
        try {
            this.a(State.INIT);
            this.g.init(this);
            for (SyncdDevice syncdDevice : this.k) {
                syncdDevice.startBlockingWork();
            }
        }
        catch (Exception var1_2) {
            RobotLog.w("Caught exception during looper init: " + var1_2.toString());
            RobotLog.logStacktrace(var1_2);
            this.a(State.EMERGENCY_STOP);
            if (RobotLog.hasGlobalErrorMsg()) {
                this.buildAndSendTelemetry("SYSTEM_TELEMETRY", RobotLog.getGlobalErrorMsg());
            }
            throw new RobotCoreException("Robot failed to start: " + var1_2.getMessage());
        }
        this.f = new ElapsedTime(0);
        this.a(State.RUNNING);
        this.b = new Thread(new b());
        this.b.start();
    }

    private void b() {
        this.b.interrupt();
        try {
            Thread.sleep(200);
        }
        catch (InterruptedException var1_1) {
            // empty catch block
        }
        this.a(State.STOPPED);
        this.c();
        this.g = a;
        this.k.clear();
    }

    private void c() {
        block2 : {
            try {
                this.g.teardown();
            }
            catch (Exception var1_1) {
                RobotLog.w("Caught exception during looper teardown: " + var1_1.toString());
                RobotLog.logStacktrace(var1_1);
                if (!RobotLog.hasGlobalErrorMsg()) break block2;
                this.buildAndSendTelemetry("SYSTEM_TELEMETRY", RobotLog.getGlobalErrorMsg());
            }
        }
    }

    private void a(State state) {
        this.state = state;
        RobotLog.v("EventLoopManager state is " + state.toString());
        if (this.j != null) {
            this.j.onStateChange(state);
        }
    }

    private void a(RobocolDatagram robocolDatagram) throws RobotCoreException {
        Gamepad gamepad = new Gamepad();
        gamepad.fromByteArray(robocolDatagram.getData());
        if (gamepad.user < 1 || gamepad.user > 2) {
            RobotLog.d("Gamepad with user %d received. Only users 1 and 2 are valid");
            return;
        }
        int n = gamepad.user - 1;
        this.h[n] = gamepad;
        if (this.h[0].id == this.h[1].id) {
            RobotLog.v("Gamepad moved position, removing stale gamepad");
            if (n == 0) {
                this.h[1] = new Gamepad();
            }
            if (n == 1) {
                this.h[0] = new Gamepad();
            }
        }
    }

    private void b(RobocolDatagram robocolDatagram) throws RobotCoreException {
        this.d.send(robocolDatagram);
        Heartbeat heartbeat = new Heartbeat(Heartbeat.Token.EMPTY);
        heartbeat.fromByteArray(robocolDatagram.getData());
        this.f.reset();
        this.i = heartbeat;
    }

    private void c(RobocolDatagram robocolDatagram) throws RobotCoreException {
        if (robocolDatagram.getAddress().equals(this.o)) {
            return;
        }
        if (this.state == State.DROPPED_CONNECTION) {
            this.a(State.RUNNING);
        }
        if (this.g == a) {
            return;
        }
        this.o = robocolDatagram.getAddress();
        RobotLog.i("new remote peer discovered: " + this.o.getHostAddress());
        try {
            this.d.connect(this.o);
        }
        catch (SocketException var2_2) {
            RobotLog.e("Unable to connect to peer:" + var2_2.toString());
        }
        PeerDiscovery peerDiscovery = new PeerDiscovery(PeerDiscovery.PeerType.PEER);
        RobotLog.v("Sending peer discovery packet");
        RobocolDatagram robocolDatagram2 = new RobocolDatagram(peerDiscovery);
        if (this.d.getInetAddress() == null) {
            robocolDatagram2.setAddress(this.o);
        }
        this.d.send(robocolDatagram2);
    }

    private void d(RobocolDatagram robocolDatagram) throws RobotCoreException {
        Command command = new Command(robocolDatagram.getData());
        if (command.isAcknowledged()) {
            this.n.remove(command);
            return;
        }
        command.acknowledge();
        this.d.send(new RobocolDatagram(command));
        for (Command command2 : this.l) {
            if (command2 == null || !command2.equals(command)) continue;
            return;
        }
        this.l[this.m++ % this.l.length] = command;
        try {
            this.g.processCommand(command);
        }
        catch (Exception var3_4) {
            RobotLog.e("Event loop threw an exception while processing a command");
            RobotLog.logStacktrace(var3_4);
        }
    }

    private void d() {
    }

    private void e(RobocolDatagram robocolDatagram) {
        RobotLog.w("RobotCore event loop received unknown event type: " + robocolDatagram.getMsgType().name());
    }

    public void buildAndSendTelemetry(String tag, String msg) {
        Telemetry telemetry = new Telemetry();
        telemetry.setTag(tag);
        telemetry.addData(tag, msg);
        this.sendTelemetryData(telemetry);
    }

    public static enum State {
        NOT_STARTED,
        INIT,
        RUNNING,
        STOPPED,
        EMERGENCY_STOP,
        DROPPED_CONNECTION;
        

        private State() {
        }
    }

    private static class a
    implements EventLoop {
        private a() {
        }

        @Override
        public void init(EventLoopManager eventProcessor) {
        }

        @Override
        public void loop() {
        }

        @Override
        public void teardown() {
        }

        @Override
        public void processCommand(Command command) {
            RobotLog.w("Dropping command " + command.getName() + ", no active event loop");
        }

        @Override
        public OpModeManager getOpModeManager() {
            return null;
        }
    }

    private class b
    implements Runnable {
        private b() {
        }

        @Override
        public void run() {
            RobotLog.v("EventLoopRunnable has started");
            try {
                ElapsedTime elapsedTime = new ElapsedTime();
                double d = 0.001;
                long l = 5;
                while (!Thread.interrupted()) {
                    while (elapsedTime.time() < 0.001) {
                        Thread.sleep(5);
                    }
                    elapsedTime.reset();
                    if (RobotLog.hasGlobalErrorMsg()) {
                        EventLoopManager.this.buildAndSendTelemetry("SYSTEM_TELEMETRY", RobotLog.getGlobalErrorMsg());
                    }
                    if (EventLoopManager.this.f.startTime() == 0.0) {
                        Thread.sleep(500);
                    } else if (EventLoopManager.this.f.time() > 2.0) {
                        EventLoopManager.this.handleDroppedConnection();
                        EventLoopManager.this.o = null;
                        EventLoopManager.this.f = new ElapsedTime(0);
                    }
                    for (Object object2 : EventLoopManager.this.k) {
                        object2.blockUntilReady();
                    }
                    try {
                        EventLoopManager.this.g.loop();
                        continue;
                    }
                    catch (Exception var6_7) {
                        Object object2;
                        RobotLog.e("Event loop threw an exception");
                        RobotLog.logStacktrace(var6_7);
                        object2 = var6_7.getMessage();
                        RobotLog.setGlobalErrorMsg("User code threw an uncaught exception: " + (String)object2);
                        EventLoopManager.this.buildAndSendTelemetry("SYSTEM_TELEMETRY", RobotLog.getGlobalErrorMsg());
                        throw new RobotCoreException("EventLoop Exception in loop()");
                    }
                    finally {
                        for (SyncdDevice syncdDevice : EventLoopManager.this.k) {
                            syncdDevice.startBlockingWork();
                        }
                        continue;
                    }
                }
            }
            catch (InterruptedException var1_2) {
                RobotLog.v("EventLoopRunnable interrupted");
            }
            catch (RobotCoreException var1_3) {
                RobotLog.v("RobotCoreException in EventLoopManager: " + var1_3.getMessage());
                EventLoopManager.this.a(State.EMERGENCY_STOP);
                EventLoopManager.this.buildAndSendTelemetry("SYSTEM_TELEMETRY", RobotLog.getGlobalErrorMsg());
            }
            EventLoopManager.this.c();
            RobotLog.v("EventLoopRunnable has exited");
        }
    }

    private class c
    implements Runnable {
        ElapsedTime a;

        private c() {
            this.a = new ElapsedTime();
        }

        @Override
        public void run() {
            block9 : do {
                RobocolDatagram robocolDatagram = EventLoopManager.this.d.recv();
                if (EventLoopManager.this.e || EventLoopManager.this.d.isClosed()) {
                    return;
                }
                if (robocolDatagram == null) {
                    Thread.yield();
                    continue;
                }
                if (RobotLog.hasGlobalErrorMsg()) {
                    EventLoopManager.this.buildAndSendTelemetry("SYSTEM_TELEMETRY", RobotLog.getGlobalErrorMsg());
                }
                try {
                    switch (robocolDatagram.getMsgType()) {
                        case GAMEPAD: {
                            EventLoopManager.this.a(robocolDatagram);
                            continue block9;
                        }
                        case HEARTBEAT: {
                            EventLoopManager.this.b(robocolDatagram);
                            continue block9;
                        }
                        case PEER_DISCOVERY: {
                            EventLoopManager.this.c(robocolDatagram);
                            continue block9;
                        }
                        case COMMAND: {
                            EventLoopManager.this.d(robocolDatagram);
                            continue block9;
                        }
                        case EMPTY: {
                            EventLoopManager.this.d();
                            continue block9;
                        }
                    }
                    EventLoopManager.this.e(robocolDatagram);
                    continue;
                }
                catch (RobotCoreException var2_2) {
                    RobotLog.w("RobotCore event loop cannot process event: " + var2_2.toString());
                    continue;
                }
                break;
            } while (true);
        }
    }

    private class d
    implements Runnable {
        private Set<Command> b;

        private d() {
            this.b = new HashSet<Command>();
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                for (Command command : EventLoopManager.this.n) {
                    if (command.getAttempts() > 10) {
                        RobotLog.w("Failed to send command, too many attempts: " + command.toString());
                        this.b.add(command);
                        continue;
                    }
                    if (command.isAcknowledged()) {
                        RobotLog.v("Command " + command.getName() + " has been acknowledged by remote device");
                        this.b.add(command);
                        continue;
                    }
                    try {
                        RobotLog.v("Sending command: " + command.getName() + ", attempt " + command.getAttempts());
                        EventLoopManager.this.d.send(new RobocolDatagram(command.toByteArray()));
                    }
                    catch (RobotCoreException var3_4) {
                        RobotLog.w("Failed to send command " + command.getName());
                        RobotLog.logStacktrace(var3_4);
                    }
                }
                EventLoopManager.this.n.removeAll(this.b);
                this.b.clear();
                try {
                    Thread.sleep(100);
                    continue;
                }
                catch (InterruptedException var1_2) {
                    return;
                }
            }
        }
    }

    public static interface EventLoopMonitor {
        public void onStateChange(State var1);
    }

}

