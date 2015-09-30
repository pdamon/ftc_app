/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.Range;

public class Servo
implements HardwareDevice {
    public static final double MIN_POSITION = 0.0;
    public static final double MAX_POSITION = 1.0;
    protected ServoController controller = null;
    protected int portNumber = -1;
    protected Direction direction = Direction.FORWARD;
    protected double minPosition = 0.0;
    protected double maxPosition = 1.0;

    public Servo(ServoController controller, int portNumber) {
        this(controller, portNumber, Direction.FORWARD);
    }

    public Servo(ServoController controller, int portNumber, Direction direction) {
        this.direction = direction;
        this.controller = controller;
        this.portNumber = portNumber;
    }

    @Override
    public String getDeviceName() {
        return "Servo";
    }

    @Override
    public String getConnectionInfo() {
        return this.controller.getConnectionInfo() + "; port " + this.portNumber;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void close() {
    }

    public ServoController getController() {
        return this.controller;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getPortNumber() {
        return this.portNumber;
    }

    public void setPosition(double position) {
        if (this.direction == Direction.REVERSE) {
            position = this.a(position);
        }
        double d = Range.scale(position, 0.0, 1.0, this.minPosition, this.maxPosition);
        this.controller.setServoPosition(this.portNumber, d);
    }

    public double getPosition() {
        double d = this.controller.getServoPosition(this.portNumber);
        if (this.direction == Direction.REVERSE) {
            d = this.a(d);
        }
        double d2 = Range.scale(d, this.minPosition, this.maxPosition, 0.0, 1.0);
        return Range.clip(d2, 0.0, 1.0);
    }

    public void scaleRange(double min, double max) throws IllegalArgumentException {
        Range.throwIfRangeIsInvalid(min, 0.0, 1.0);
        Range.throwIfRangeIsInvalid(max, 0.0, 1.0);
        if (min >= max) {
            throw new IllegalArgumentException("min must be less than max");
        }
        this.minPosition = min;
        this.maxPosition = max;
    }

    private double a(double d) {
        return 1.0 - d + 0.0;
    }

    public static enum Direction {
        FORWARD,
        REVERSE;
        

        private Direction() {
        }
    }

}

