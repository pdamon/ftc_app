/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware.configuration;

import com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.util.SerialNumber;
import java.util.List;

public class LegacyModuleControllerConfiguration
extends ControllerConfiguration {
    public LegacyModuleControllerConfiguration(String name, List<DeviceConfiguration> modules, SerialNumber serialNumber) {
        super(name, modules, serialNumber, DeviceConfiguration.ConfigurationType.LEGACY_MODULE_CONTROLLER);
    }
}

