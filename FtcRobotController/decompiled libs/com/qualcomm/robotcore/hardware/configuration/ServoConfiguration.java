/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware.configuration;

import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;

public class ServoConfiguration
extends DeviceConfiguration {
    public ServoConfiguration(int port, String name, boolean enabled) {
        super(port, DeviceConfiguration.ConfigurationType.SERVO, name, enabled);
    }

    public ServoConfiguration(int port) {
        super(port, DeviceConfiguration.ConfigurationType.SERVO, "NO DEVICE ATTACHED", false);
    }

    public ServoConfiguration(String name) {
        super(DeviceConfiguration.ConfigurationType.SERVO);
        super.setName(name);
        super.setType(DeviceConfiguration.ConfigurationType.SERVO);
    }
}

