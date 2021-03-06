/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware.configuration;

import com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.util.SerialNumber;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MotorControllerConfiguration
extends ControllerConfiguration
implements Serializable {
    public MotorControllerConfiguration() {
        super("", new ArrayList<DeviceConfiguration>(), new SerialNumber(ControllerConfiguration.NO_SERIAL_NUMBER.getSerialNumber()), DeviceConfiguration.ConfigurationType.MOTOR_CONTROLLER);
    }

    public MotorControllerConfiguration(String name, List<DeviceConfiguration> motors, SerialNumber serialNumber) {
        super(name, motors, serialNumber, DeviceConfiguration.ConfigurationType.MOTOR_CONTROLLER);
    }

    public List<DeviceConfiguration> getMotors() {
        return super.getDevices();
    }

    public void addMotors(List<DeviceConfiguration> motors) {
        super.addDevices(motors);
    }
}

