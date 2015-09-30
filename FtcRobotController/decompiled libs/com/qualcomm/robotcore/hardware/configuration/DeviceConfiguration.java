/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.hardware.configuration;

import java.io.Serializable;

public class DeviceConfiguration
implements Serializable {
    public static final String DISABLED_DEVICE_NAME = "NO DEVICE ATTACHED";
    protected String name;
    private ConfigurationType a = ConfigurationType.NOTHING;
    private int b;
    private boolean c = false;

    public DeviceConfiguration(int port, ConfigurationType type, String name, boolean enabled) {
        this.b = port;
        this.a = type;
        this.name = name;
        this.c = enabled;
    }

    public DeviceConfiguration(int port) {
        this.name = "NO DEVICE ATTACHED";
        this.a = ConfigurationType.NOTHING;
        this.b = port;
        this.c = false;
    }

    public DeviceConfiguration(ConfigurationType type) {
        this.name = "";
        this.a = type;
        this.c = false;
    }

    public DeviceConfiguration(int port, ConfigurationType type) {
        this.name = "NO DEVICE ATTACHED";
        this.a = type;
        this.b = port;
        this.c = false;
    }

    public boolean isEnabled() {
        return this.c;
    }

    public void setEnabled(boolean enabled) {
        this.c = enabled;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setType(ConfigurationType type) {
        this.a = type;
    }

    public ConfigurationType getType() {
        return this.a;
    }

    public int getPort() {
        return this.b;
    }

    public void setPort(int port) {
        this.b = port;
    }

    public ConfigurationType typeFromString(String type) {
        for (ConfigurationType configurationType : ConfigurationType.values()) {
            if (!type.equalsIgnoreCase(configurationType.toString())) continue;
            return configurationType;
        }
        return ConfigurationType.NOTHING;
    }

    public static enum ConfigurationType {
        MOTOR,
        SERVO,
        GYRO,
        COMPASS,
        IR_SEEKER,
        LIGHT_SENSOR,
        ACCELEROMETER,
        MOTOR_CONTROLLER,
        SERVO_CONTROLLER,
        LEGACY_MODULE_CONTROLLER,
        DEVICE_INTERFACE_MODULE,
        I2C_DEVICE,
        ANALOG_INPUT,
        TOUCH_SENSOR,
        OPTICAL_DISTANCE_SENSOR,
        ANALOG_OUTPUT,
        DIGITAL_DEVICE,
        PULSE_WIDTH_DEVICE,
        IR_SEEKER_V3,
        TOUCH_SENSOR_MULTIPLEXER,
        MATRIX_CONTROLLER,
        ULTRASONIC_SENSOR,
        ADAFRUIT_COLOR_SENSOR,
        COLOR_SENSOR,
        LED,
        OTHER,
        NOTHING;
        

        private ConfigurationType() {
        }
    }

}

