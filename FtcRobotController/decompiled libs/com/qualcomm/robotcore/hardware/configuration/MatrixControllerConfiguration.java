/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware.configuration;

import com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.util.SerialNumber;
import java.util.ArrayList;
import java.util.List;

public class MatrixControllerConfiguration
extends ControllerConfiguration {
    private List<DeviceConfiguration> a;
    private List<DeviceConfiguration> b;

    public MatrixControllerConfiguration(String name, List<DeviceConfiguration> motors, List<DeviceConfiguration> servos, SerialNumber serialNumber) {
        super(name, serialNumber, DeviceConfiguration.ConfigurationType.MATRIX_CONTROLLER);
        this.a = servos;
        this.b = motors;
    }

    public List<DeviceConfiguration> getServos() {
        return this.a;
    }

    public void addServos(ArrayList<DeviceConfiguration> servos) {
        this.a = servos;
    }

    public List<DeviceConfiguration> getMotors() {
        return this.b;
    }

    public void addMotors(ArrayList<DeviceConfiguration> motors) {
        this.b = motors;
    }
}

