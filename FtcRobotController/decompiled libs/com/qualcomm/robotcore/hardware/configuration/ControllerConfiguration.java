/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware.configuration;

import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.util.SerialNumber;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ControllerConfiguration
extends DeviceConfiguration
implements Serializable {
    public static final SerialNumber NO_SERIAL_NUMBER = new SerialNumber("-1");
    private List<DeviceConfiguration> a;
    private SerialNumber b;

    public ControllerConfiguration(String name, SerialNumber serialNumber, DeviceConfiguration.ConfigurationType type) {
        this(name, new ArrayList<DeviceConfiguration>(), serialNumber, type);
    }

    public ControllerConfiguration(String name, List<DeviceConfiguration> devices, SerialNumber serialNumber, DeviceConfiguration.ConfigurationType type) {
        super(type);
        super.setName(name);
        this.a = devices;
        this.b = serialNumber;
    }

    public List<DeviceConfiguration> getDevices() {
        return this.a;
    }

    @Override
    public DeviceConfiguration.ConfigurationType getType() {
        return super.getType();
    }

    public SerialNumber getSerialNumber() {
        return this.b;
    }

    public void addDevices(List<DeviceConfiguration> devices) {
        this.a = devices;
    }

    public DeviceConfiguration.ConfigurationType deviceTypeToConfigType(DeviceManager.DeviceType type) {
        if (type == DeviceManager.DeviceType.MODERN_ROBOTICS_USB_DC_MOTOR_CONTROLLER) {
            return DeviceConfiguration.ConfigurationType.MOTOR_CONTROLLER;
        }
        if (type == DeviceManager.DeviceType.MODERN_ROBOTICS_USB_SERVO_CONTROLLER) {
            return DeviceConfiguration.ConfigurationType.SERVO_CONTROLLER;
        }
        if (type == DeviceManager.DeviceType.MODERN_ROBOTICS_USB_LEGACY_MODULE) {
            return DeviceConfiguration.ConfigurationType.LEGACY_MODULE_CONTROLLER;
        }
        return DeviceConfiguration.ConfigurationType.NOTHING;
    }

    public DeviceManager.DeviceType configTypeToDeviceType(DeviceConfiguration.ConfigurationType type) {
        if (type == DeviceConfiguration.ConfigurationType.MOTOR_CONTROLLER) {
            return DeviceManager.DeviceType.MODERN_ROBOTICS_USB_DC_MOTOR_CONTROLLER;
        }
        if (type == DeviceConfiguration.ConfigurationType.SERVO_CONTROLLER) {
            return DeviceManager.DeviceType.MODERN_ROBOTICS_USB_SERVO_CONTROLLER;
        }
        if (type == DeviceConfiguration.ConfigurationType.LEGACY_MODULE_CONTROLLER) {
            return DeviceManager.DeviceType.MODERN_ROBOTICS_USB_LEGACY_MODULE;
        }
        return DeviceManager.DeviceType.FTDI_USB_UNKNOWN_DEVICE;
    }
}

