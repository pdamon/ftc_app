/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware.configuration;

import com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.util.SerialNumber;
import java.util.List;

public class DeviceInterfaceModuleConfiguration
extends ControllerConfiguration {
    private List<DeviceConfiguration> a;
    private List<DeviceConfiguration> b;
    private List<DeviceConfiguration> c;
    private List<DeviceConfiguration> d;
    private List<DeviceConfiguration> e;

    public DeviceInterfaceModuleConfiguration(String name, SerialNumber serialNumber) {
        super(name, serialNumber, DeviceConfiguration.ConfigurationType.DEVICE_INTERFACE_MODULE);
    }

    public void setPwmDevices(List<DeviceConfiguration> pwmDevices) {
        this.a = pwmDevices;
    }

    public List<DeviceConfiguration> getPwmDevices() {
        return this.a;
    }

    public List<DeviceConfiguration> getI2cDevices() {
        return this.b;
    }

    public void setI2cDevices(List<DeviceConfiguration> i2cDevices) {
        this.b = i2cDevices;
    }

    public List<DeviceConfiguration> getAnalogInputDevices() {
        return this.c;
    }

    public void setAnalogInputDevices(List<DeviceConfiguration> analogInputDevices) {
        this.c = analogInputDevices;
    }

    public List<DeviceConfiguration> getDigitalDevices() {
        return this.d;
    }

    public void setDigitalDevices(List<DeviceConfiguration> digitalDevices) {
        this.d = digitalDevices;
    }

    public List<DeviceConfiguration> getAnalogOutputDevices() {
        return this.e;
    }

    public void setAnalogOutputDevices(List<DeviceConfiguration> analogOutputDevices) {
        this.e = analogOutputDevices;
    }
}

