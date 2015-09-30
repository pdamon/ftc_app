/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  com.qualcomm.modernrobotics.ModernRoboticsUsbUtil
 *  com.qualcomm.modernrobotics.RobotUsbManagerEmulator
 *  com.qualcomm.robotcore.eventloop.EventLoopManager
 *  com.qualcomm.robotcore.exception.RobotCoreException
 *  com.qualcomm.robotcore.hardware.AccelerationSensor
 *  com.qualcomm.robotcore.hardware.AnalogInput
 *  com.qualcomm.robotcore.hardware.AnalogInputController
 *  com.qualcomm.robotcore.hardware.AnalogOutput
 *  com.qualcomm.robotcore.hardware.AnalogOutputController
 *  com.qualcomm.robotcore.hardware.ColorSensor
 *  com.qualcomm.robotcore.hardware.CompassSensor
 *  com.qualcomm.robotcore.hardware.DcMotorController
 *  com.qualcomm.robotcore.hardware.DeviceInterfaceModule
 *  com.qualcomm.robotcore.hardware.DeviceManager
 *  com.qualcomm.robotcore.hardware.DeviceManager$DeviceType
 *  com.qualcomm.robotcore.hardware.DigitalChannel
 *  com.qualcomm.robotcore.hardware.DigitalChannelController
 *  com.qualcomm.robotcore.hardware.GyroSensor
 *  com.qualcomm.robotcore.hardware.I2cController
 *  com.qualcomm.robotcore.hardware.I2cDevice
 *  com.qualcomm.robotcore.hardware.IrSeekerSensor
 *  com.qualcomm.robotcore.hardware.LED
 *  com.qualcomm.robotcore.hardware.LegacyModule
 *  com.qualcomm.robotcore.hardware.LightSensor
 *  com.qualcomm.robotcore.hardware.OpticalDistanceSensor
 *  com.qualcomm.robotcore.hardware.PWMOutput
 *  com.qualcomm.robotcore.hardware.PWMOutputController
 *  com.qualcomm.robotcore.hardware.ServoController
 *  com.qualcomm.robotcore.hardware.TouchSensor
 *  com.qualcomm.robotcore.hardware.TouchSensorMultiplexer
 *  com.qualcomm.robotcore.hardware.UltrasonicSensor
 *  com.qualcomm.robotcore.hardware.usb.RobotUsbDevice
 *  com.qualcomm.robotcore.hardware.usb.RobotUsbManager
 *  com.qualcomm.robotcore.hardware.usb.ftdi.RobotUsbManagerFtdi
 *  com.qualcomm.robotcore.util.RobotLog
 *  com.qualcomm.robotcore.util.SerialNumber
 */
package com.qualcomm.hardware;

import android.content.Context;
import com.qualcomm.hardware.AdafruitColorSensor;
import com.qualcomm.hardware.ModernRoboticsColorSensor;
import com.qualcomm.hardware.ModernRoboticsIrSeekerSensorV3;
import com.qualcomm.hardware.ModernRoboticsNxtAccelerationSensor;
import com.qualcomm.hardware.ModernRoboticsNxtColorSensor;
import com.qualcomm.hardware.ModernRoboticsNxtCompassSensor;
import com.qualcomm.hardware.ModernRoboticsNxtDcMotorController;
import com.qualcomm.hardware.ModernRoboticsNxtGyroSensor;
import com.qualcomm.hardware.ModernRoboticsNxtIrSeekerSensor;
import com.qualcomm.hardware.ModernRoboticsNxtLightSensor;
import com.qualcomm.hardware.ModernRoboticsNxtServoController;
import com.qualcomm.hardware.ModernRoboticsNxtTouchSensor;
import com.qualcomm.hardware.ModernRoboticsNxtTouchSensorMultiplexer;
import com.qualcomm.hardware.ModernRoboticsNxtUltrasonicSensor;
import com.qualcomm.hardware.ModernRoboticsOpticalDistanceSensor;
import com.qualcomm.hardware.ModernRoboticsTouchSensor;
import com.qualcomm.hardware.ModernRoboticsUsbDcMotorController;
import com.qualcomm.hardware.ModernRoboticsUsbDeviceInterfaceModule;
import com.qualcomm.hardware.ModernRoboticsUsbLegacyModule;
import com.qualcomm.hardware.ModernRoboticsUsbServoController;
import com.qualcomm.modernrobotics.ModernRoboticsUsbUtil;
import com.qualcomm.modernrobotics.RobotUsbManagerEmulator;
import com.qualcomm.robotcore.eventloop.EventLoopManager;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.AccelerationSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogInputController;
import com.qualcomm.robotcore.hardware.AnalogOutput;
import com.qualcomm.robotcore.hardware.AnalogOutputController;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.I2cController;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.PWMOutput;
import com.qualcomm.robotcore.hardware.PWMOutputController;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.TouchSensorMultiplexer;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.usb.RobotUsbDevice;
import com.qualcomm.robotcore.hardware.usb.RobotUsbManager;
import com.qualcomm.robotcore.hardware.usb.ftdi.RobotUsbManagerFtdi;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class ModernRoboticsDeviceManager
extends DeviceManager {
    private static a a = a.a;
    private RobotUsbManager b;
    private final EventLoopManager c;

    public ModernRoboticsDeviceManager(Context context, EventLoopManager manager) throws RobotCoreException {
        this.c = manager;
        switch (a) {
            case b: {
                this.b = new RobotUsbManagerEmulator();
                break;
            }
            default: {
                this.b = new RobotUsbManagerFtdi(context);
            }
        }
    }

    public Map<SerialNumber, DeviceManager.DeviceType> scanForUsbDevices() throws RobotCoreException {
        HashMap<SerialNumber, DeviceManager.DeviceType> hashMap = new HashMap<SerialNumber, DeviceManager.DeviceType>();
        try {
            int n = this.b.scanForDevices();
            for (int i = 0; i < n; ++i) {
                SerialNumber serialNumber = this.b.getDeviceSerialNumberByIndex(i);
                RobotUsbDevice robotUsbDevice = ModernRoboticsUsbUtil.openUsbDevice((RobotUsbManager)this.b, (SerialNumber)serialNumber);
                hashMap.put(serialNumber, ModernRoboticsUsbUtil.getDeviceType((byte[])ModernRoboticsUsbUtil.getUsbDeviceHeader((RobotUsbDevice)robotUsbDevice)));
                robotUsbDevice.close();
            }
        }
        catch (RobotCoreException var2_3) {
            RobotLog.setGlobalErrorMsgAndThrow((String)"Error while scanning for USB devices", (RobotCoreException)var2_3);
        }
        return hashMap;
    }

    public DcMotorController createUsbDcMotorController(SerialNumber serialNumber) throws RobotCoreException, InterruptedException {
        RobotLog.v((String)("Creating Modern Robotics USB DC Motor Controller - " + serialNumber.toString()));
        ModernRoboticsUsbDcMotorController modernRoboticsUsbDcMotorController = null;
        try {
            RobotUsbDevice robotUsbDevice = ModernRoboticsUsbUtil.openUsbDevice((RobotUsbManager)this.b, (SerialNumber)serialNumber);
            byte[] arrby = ModernRoboticsUsbUtil.getUsbDeviceHeader((RobotUsbDevice)robotUsbDevice);
            DeviceManager.DeviceType deviceType = ModernRoboticsUsbUtil.getDeviceType((byte[])arrby);
            if (deviceType != DeviceManager.DeviceType.MODERN_ROBOTICS_USB_DC_MOTOR_CONTROLLER) {
                this.a(serialNumber.toString() + " is not a Modern Robotics USB DC Motor Controller");
            }
            modernRoboticsUsbDcMotorController = new ModernRoboticsUsbDcMotorController(serialNumber, robotUsbDevice, this.c);
        }
        catch (RobotCoreException var3_4) {
            RobotLog.setGlobalErrorMsgAndThrow((String)"Unable to open Modern Robotics USB DC Motor Controller", (RobotCoreException)var3_4);
        }
        return modernRoboticsUsbDcMotorController;
    }

    public ServoController createUsbServoController(SerialNumber serialNumber) throws RobotCoreException, InterruptedException {
        RobotLog.v((String)("Creating Modern Robotics USB Servo Controller - " + serialNumber.toString()));
        ModernRoboticsUsbServoController modernRoboticsUsbServoController = null;
        try {
            RobotUsbDevice robotUsbDevice = ModernRoboticsUsbUtil.openUsbDevice((RobotUsbManager)this.b, (SerialNumber)serialNumber);
            byte[] arrby = ModernRoboticsUsbUtil.getUsbDeviceHeader((RobotUsbDevice)robotUsbDevice);
            DeviceManager.DeviceType deviceType = ModernRoboticsUsbUtil.getDeviceType((byte[])arrby);
            if (deviceType != DeviceManager.DeviceType.MODERN_ROBOTICS_USB_SERVO_CONTROLLER) {
                this.a(serialNumber.toString() + " is not a Modern Robotics USB Servo Controller");
            }
            modernRoboticsUsbServoController = new ModernRoboticsUsbServoController(serialNumber, robotUsbDevice, this.c);
        }
        catch (RobotCoreException var3_4) {
            RobotLog.setGlobalErrorMsgAndThrow((String)"Unable to open Modern Robotics USB Servo Controller", (RobotCoreException)var3_4);
        }
        return modernRoboticsUsbServoController;
    }

    public DeviceInterfaceModule createDeviceInterfaceModule(SerialNumber serialNumber) throws RobotCoreException, InterruptedException {
        RobotLog.v((String)("Creating Modern Robotics USB Core Device Interface Module - " + serialNumber.toString()));
        ModernRoboticsUsbDeviceInterfaceModule modernRoboticsUsbDeviceInterfaceModule = null;
        try {
            RobotUsbDevice robotUsbDevice = ModernRoboticsUsbUtil.openUsbDevice((RobotUsbManager)this.b, (SerialNumber)serialNumber);
            byte[] arrby = ModernRoboticsUsbUtil.getUsbDeviceHeader((RobotUsbDevice)robotUsbDevice);
            DeviceManager.DeviceType deviceType = ModernRoboticsUsbUtil.getDeviceType((byte[])arrby);
            if (deviceType != DeviceManager.DeviceType.MODERN_ROBOTICS_USB_DEVICE_INTERFACE_MODULE) {
                this.a(serialNumber.toString() + " is not a Modern Robotics USB Core Device Interface Module");
            }
            modernRoboticsUsbDeviceInterfaceModule = new ModernRoboticsUsbDeviceInterfaceModule(serialNumber, robotUsbDevice, this.c);
        }
        catch (RobotCoreException var3_4) {
            RobotLog.setGlobalErrorMsgAndThrow((String)"Unable to open Modern Robotics USB Core Device Interface Module", (RobotCoreException)var3_4);
        }
        return modernRoboticsUsbDeviceInterfaceModule;
    }

    public LegacyModule createUsbLegacyModule(SerialNumber serialNumber) throws RobotCoreException, InterruptedException {
        RobotLog.v((String)("Creating Modern Robotics USB Legacy Module - " + serialNumber.toString()));
        ModernRoboticsUsbLegacyModule modernRoboticsUsbLegacyModule = null;
        try {
            RobotUsbDevice robotUsbDevice = ModernRoboticsUsbUtil.openUsbDevice((RobotUsbManager)this.b, (SerialNumber)serialNumber);
            byte[] arrby = ModernRoboticsUsbUtil.getUsbDeviceHeader((RobotUsbDevice)robotUsbDevice);
            DeviceManager.DeviceType deviceType = ModernRoboticsUsbUtil.getDeviceType((byte[])arrby);
            if (deviceType != DeviceManager.DeviceType.MODERN_ROBOTICS_USB_LEGACY_MODULE) {
                this.a(serialNumber.toString() + " is not a Modern Robotics USB Legacy Module");
            }
            modernRoboticsUsbLegacyModule = new ModernRoboticsUsbLegacyModule(serialNumber, robotUsbDevice, this.c);
        }
        catch (RobotCoreException var3_4) {
            RobotLog.setGlobalErrorMsgAndThrow((String)"Unable to open Modern Robotics USB Legacy Module", (RobotCoreException)var3_4);
        }
        return modernRoboticsUsbLegacyModule;
    }

    public DcMotorController createNxtDcMotorController(LegacyModule legacyModule, int physicalPort) {
        RobotLog.v((String)("Creating Modern Robotics NXT DC Motor Controller - Port: " + physicalPort));
        return new ModernRoboticsNxtDcMotorController(this.a(legacyModule), physicalPort);
    }

    public ServoController createNxtServoController(LegacyModule legacyModule, int physicalPort) {
        RobotLog.v((String)("Creating Modern Robotics NXT Servo Controller - Port: " + physicalPort));
        return new ModernRoboticsNxtServoController(this.a(legacyModule), physicalPort);
    }

    public CompassSensor createNxtCompassSensor(LegacyModule legacyModule, int physicalPort) {
        RobotLog.v((String)("Creating Modern Robotics NXT Compass Sensor - Port: " + physicalPort));
        return new ModernRoboticsNxtCompassSensor(this.a(legacyModule), physicalPort);
    }

    public TouchSensor createTouchSensor(DeviceInterfaceModule deviceInterfaceModule, int physicalPort) {
        RobotLog.v((String)("Creating Modern Robotics Touch Sensor - Port: " + physicalPort));
        return new ModernRoboticsTouchSensor(this.a(deviceInterfaceModule), physicalPort);
    }

    public AccelerationSensor createNxtAccelerationSensor(LegacyModule legacyModule, int physicalPort) {
        RobotLog.v((String)("Creating Modern Robotics NXT Acceleration Sensor - Port: " + physicalPort));
        return new ModernRoboticsNxtAccelerationSensor(this.a(legacyModule), physicalPort);
    }

    public LightSensor createNxtLightSensor(LegacyModule legacyModule, int physicalPort) {
        RobotLog.v((String)("Creating Modern Robotics NXT Light Sensor - Port: " + physicalPort));
        return new ModernRoboticsNxtLightSensor(this.a(legacyModule), physicalPort);
    }

    public GyroSensor createNxtGyroSensor(LegacyModule legacyModule, int physicalPort) {
        RobotLog.v((String)("Creating Modern Robotics NXT Gyro Sensor - Port: " + physicalPort));
        return new ModernRoboticsNxtGyroSensor(this.a(legacyModule), physicalPort);
    }

    public IrSeekerSensor createNxtIrSeekerSensor(LegacyModule legacyModule, int physicalPort) {
        RobotLog.v((String)("Creating Modern Robotics NXT IR Seeker Sensor - Port: " + physicalPort));
        return new ModernRoboticsNxtIrSeekerSensor(this.a(legacyModule), physicalPort);
    }

    public IrSeekerSensor createIrSeekerSensorV3(DeviceInterfaceModule deviceInterfaceModule, int physicalPort) {
        RobotLog.v((String)("Creating Modern Robotics IR Seeker Sensor V3 - Port: " + physicalPort));
        return new ModernRoboticsIrSeekerSensorV3(this.a(deviceInterfaceModule), physicalPort);
    }

    public UltrasonicSensor createNxtUltrasonicSensor(LegacyModule legacyModule, int physicalPort) {
        RobotLog.v((String)("Creating Modern Robotics NXT Ultrasonic Sensor - Port: " + physicalPort));
        return new ModernRoboticsNxtUltrasonicSensor(this.a(legacyModule), physicalPort);
    }

    public OpticalDistanceSensor createOpticalDistanceSensor(DeviceInterfaceModule deviceInterfaceModule, int physicalPort) {
        RobotLog.v((String)("Creating Modern Robotics Optical Distance Sensor - Port: " + physicalPort));
        return new ModernRoboticsOpticalDistanceSensor(this.a(deviceInterfaceModule), physicalPort);
    }

    public TouchSensor createNxtTouchSensor(LegacyModule legacyModule, int physicalPort) {
        RobotLog.v((String)("Creating Modern Robotics NXT Touch Sensor - Port: " + physicalPort));
        return new ModernRoboticsNxtTouchSensor(this.a(legacyModule), physicalPort);
    }

    public TouchSensorMultiplexer createNxtTouchSensorMultiplexer(LegacyModule legacyModule, int port) {
        RobotLog.v((String)("Creating Modern Robotics NXT Touch Sensor Multiplexer - Port: " + port));
        return new ModernRoboticsNxtTouchSensorMultiplexer(this.a(legacyModule), port);
    }

    public AnalogInput createAnalogInputDevice(AnalogInputController controller, int channel) {
        RobotLog.v((String)("Creating Analog Input Device - Port: " + channel));
        return new AnalogInput(controller, channel);
    }

    public AnalogOutput createAnalogOutputDevice(AnalogOutputController controller, int channel) {
        RobotLog.v((String)("Creating Analog Output Device - Port: " + channel));
        return new AnalogOutput(controller, channel);
    }

    public DigitalChannel createDigitalChannelDevice(DigitalChannelController controller, int channel) {
        RobotLog.v((String)("Creating Digital Channel Device - Port: " + channel));
        return new DigitalChannel(controller, channel);
    }

    public PWMOutput createPwmOutputDevice(DeviceInterfaceModule controller, int channel) {
        RobotLog.v((String)("Creating PWM Output Device - Port: " + channel));
        return new PWMOutput((PWMOutputController)controller, channel);
    }

    public I2cDevice createI2cDevice(I2cController controller, int channel) {
        RobotLog.v((String)("Creating I2C Device - Port: " + channel));
        return new I2cDevice(controller, channel);
    }

    public ColorSensor createAdafruitColorSensor(DeviceInterfaceModule controller, int channel) {
        RobotLog.v((String)("Creating Adafruit Color Sensor - Port: " + channel));
        return new AdafruitColorSensor(controller, channel);
    }

    public ColorSensor createNxtColorSensor(LegacyModule controller, int channel) {
        RobotLog.v((String)("Creating NXT Color Sensor - Port: " + channel));
        return new ModernRoboticsNxtColorSensor(controller, channel);
    }

    public ColorSensor createModernRoboticsColorSensor(DeviceInterfaceModule controller, int channel) {
        RobotLog.v((String)("Creating ModernRobotics Color Sensor - Port: " + channel));
        return new ModernRoboticsColorSensor(controller, channel);
    }

    public LED createLED(DigitalChannelController controller, int channel) {
        RobotLog.v((String)("Creating LED - Port: " + channel));
        return new LED(controller, channel);
    }

    public static void enableDeviceEmulation() {
        a = a.b;
    }

    public static void disableDeviceEmulation() {
        a = a.a;
    }

    private ModernRoboticsUsbLegacyModule a(LegacyModule legacyModule) {
        if (!(legacyModule instanceof ModernRoboticsUsbLegacyModule)) {
            throw new IllegalArgumentException("Modern Robotics Device Manager needs a Modern Robotics LegacyModule");
        }
        return (ModernRoboticsUsbLegacyModule)legacyModule;
    }

    private ModernRoboticsUsbDeviceInterfaceModule a(DeviceInterfaceModule deviceInterfaceModule) {
        if (!(deviceInterfaceModule instanceof ModernRoboticsUsbDeviceInterfaceModule)) {
            throw new IllegalArgumentException("Modern Robotics Device Manager needs a Modern Robotics Device Interface Module");
        }
        return (ModernRoboticsUsbDeviceInterfaceModule)deviceInterfaceModule;
    }

    private void a(String string) throws RobotCoreException {
        System.err.println(string);
        throw new RobotCoreException(string);
    }

    private static enum a {
        a,
        b;
        

        private a() {
        }
    }

}

