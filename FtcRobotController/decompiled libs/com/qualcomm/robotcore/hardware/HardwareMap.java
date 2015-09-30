/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.qualcomm.robotcore.hardware;

import android.content.Context;
import com.qualcomm.robotcore.hardware.AccelerationSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogOutput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.PWMOutput;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.TouchSensorMultiplexer;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.RobotLog;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HardwareMap {
    public DeviceMapping<DcMotorController> dcMotorController = new DeviceMapping();
    public DeviceMapping<DcMotor> dcMotor = new DeviceMapping();
    public DeviceMapping<ServoController> servoController = new DeviceMapping();
    public DeviceMapping<Servo> servo = new DeviceMapping();
    public DeviceMapping<LegacyModule> legacyModule = new DeviceMapping();
    public DeviceMapping<TouchSensorMultiplexer> touchSensorMultiplexer = new DeviceMapping();
    public DeviceMapping<DeviceInterfaceModule> deviceInterfaceModule = new DeviceMapping();
    public DeviceMapping<AnalogInput> analogInput = new DeviceMapping();
    public DeviceMapping<DigitalChannel> digitalChannel = new DeviceMapping();
    public DeviceMapping<OpticalDistanceSensor> opticalDistanceSensor = new DeviceMapping();
    public DeviceMapping<TouchSensor> touchSensor = new DeviceMapping();
    public DeviceMapping<PWMOutput> pwmOutput = new DeviceMapping();
    public DeviceMapping<I2cDevice> i2cDevice = new DeviceMapping();
    public DeviceMapping<AnalogOutput> analogOutput = new DeviceMapping();
    public DeviceMapping<ColorSensor> colorSensor = new DeviceMapping();
    public DeviceMapping<LED> led = new DeviceMapping();
    public DeviceMapping<AccelerationSensor> accelerationSensor = new DeviceMapping();
    public DeviceMapping<CompassSensor> compassSensor = new DeviceMapping();
    public DeviceMapping<GyroSensor> gyroSensor = new DeviceMapping();
    public DeviceMapping<IrSeekerSensor> irSeekerSensor = new DeviceMapping();
    public DeviceMapping<LightSensor> lightSensor = new DeviceMapping();
    public DeviceMapping<UltrasonicSensor> ultrasonicSensor = new DeviceMapping();
    public DeviceMapping<VoltageSensor> voltageSensor = new DeviceMapping();
    public Context appContext = null;

    public void logDevices() {
        RobotLog.i("========= Device Information ===================================================");
        RobotLog.i(String.format("%-45s %-30s %s", "Type", "Name", "Connection"));
        this.dcMotorController.logDevices();
        this.dcMotor.logDevices();
        this.servoController.logDevices();
        this.servo.logDevices();
        this.legacyModule.logDevices();
        this.touchSensorMultiplexer.logDevices();
        this.deviceInterfaceModule.logDevices();
        this.analogInput.logDevices();
        this.digitalChannel.logDevices();
        this.opticalDistanceSensor.logDevices();
        this.touchSensor.logDevices();
        this.pwmOutput.logDevices();
        this.i2cDevice.logDevices();
        this.analogOutput.logDevices();
        this.colorSensor.logDevices();
        this.led.logDevices();
        this.accelerationSensor.logDevices();
        this.compassSensor.logDevices();
        this.gyroSensor.logDevices();
        this.irSeekerSensor.logDevices();
        this.lightSensor.logDevices();
        this.ultrasonicSensor.logDevices();
        this.voltageSensor.logDevices();
    }

    public static class DeviceMapping<DEVICE_TYPE>
    implements Iterable<DEVICE_TYPE> {
        private Map<String, DEVICE_TYPE> a = new HashMap<String, DEVICE_TYPE>();

        public DEVICE_TYPE get(String deviceName) {
            DEVICE_TYPE DEVICE_TYPE = this.a.get(deviceName);
            if (DEVICE_TYPE == null) {
                String string = String.format("Unable to find a hardware device with the name \"%s\"", deviceName);
                throw new IllegalArgumentException(string);
            }
            return DEVICE_TYPE;
        }

        public void put(String deviceName, DEVICE_TYPE device) {
            this.a.put(deviceName, device);
        }

        @Override
        public Iterator<DEVICE_TYPE> iterator() {
            return this.a.values().iterator();
        }

        public Set<Map.Entry<String, DEVICE_TYPE>> entrySet() {
            return this.a.entrySet();
        }

        public int size() {
            return this.a.size();
        }

        public void logDevices() {
            if (this.a.isEmpty()) {
                return;
            }
            for (Map.Entry<String, DEVICE_TYPE> entry : this.a.entrySet()) {
                if (!(entry.getValue() instanceof HardwareDevice)) continue;
                HardwareDevice hardwareDevice = (HardwareDevice)entry.getValue();
                String string = hardwareDevice.getConnectionInfo();
                String string2 = entry.getKey();
                String string3 = hardwareDevice.getDeviceName();
                RobotLog.i(String.format("%-45s %-30s %s", string3, string2, string));
            }
        }
    }

}

