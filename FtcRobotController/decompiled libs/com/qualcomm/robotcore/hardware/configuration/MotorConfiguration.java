/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware.configuration;

import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;

public class MotorConfiguration
extends DeviceConfiguration {
    public MotorConfiguration(int port, String name, boolean enabled) {
        super(port, DeviceConfiguration.ConfigurationType.MOTOR, name, enabled);
    }

    public MotorConfiguration(int port) {
        super(DeviceConfiguration.ConfigurationType.MOTOR);
        super.setName("NO DEVICE ATTACHED");
        super.setPort(port);
    }

    public MotorConfiguration(String name) {
        super(DeviceConfiguration.ConfigurationType.MOTOR);
        super.setName(name);
        super.setType(DeviceConfiguration.ConfigurationType.MOTOR);
    }
}

