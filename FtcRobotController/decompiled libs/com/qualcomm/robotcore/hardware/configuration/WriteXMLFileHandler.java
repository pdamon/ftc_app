/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Xml
 *  org.xmlpull.v1.XmlSerializer
 */
package com.qualcomm.robotcore.hardware.configuration;

import android.content.Context;
import android.util.Xml;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.hardware.configuration.DeviceInterfaceModuleConfiguration;
import com.qualcomm.robotcore.hardware.configuration.MatrixControllerConfiguration;
import com.qualcomm.robotcore.util.SerialNumber;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.xmlpull.v1.XmlSerializer;

public class WriteXMLFileHandler {
    private XmlSerializer a = Xml.newSerializer();
    private HashSet<String> b = new HashSet();
    private ArrayList<String> c = new ArrayList();
    private String[] d = new String[]{"    ", "        ", "            "};
    private int e = 0;

    public WriteXMLFileHandler(Context context) {
    }

    public String writeXml(ArrayList<ControllerConfiguration> deviceControllerConfigurations) {
        this.c = new ArrayList();
        this.b = new HashSet();
        StringWriter stringWriter = new StringWriter();
        try {
            this.a.setOutput((Writer)stringWriter);
            this.a.startDocument("UTF-8", Boolean.valueOf(true));
            this.a.ignorableWhitespace("\n");
            this.a.startTag("", "Robot");
            this.a.ignorableWhitespace("\n");
            for (ControllerConfiguration controllerConfiguration : deviceControllerConfigurations) {
                String string = controllerConfiguration.getType().toString();
                if (string.equalsIgnoreCase(DeviceConfiguration.ConfigurationType.MOTOR_CONTROLLER.toString()) || string.equalsIgnoreCase(DeviceConfiguration.ConfigurationType.SERVO_CONTROLLER.toString())) {
                    this.a(controllerConfiguration, true);
                }
                if (string.equalsIgnoreCase(DeviceConfiguration.ConfigurationType.LEGACY_MODULE_CONTROLLER.toString())) {
                    this.b(controllerConfiguration);
                }
                if (!string.equalsIgnoreCase(DeviceConfiguration.ConfigurationType.DEVICE_INTERFACE_MODULE.toString())) continue;
                this.a(controllerConfiguration);
            }
            this.a.endTag("", "Robot");
            this.a.ignorableWhitespace("\n");
            this.a.endDocument();
            return stringWriter.toString();
        }
        catch (Exception var3_4) {
            throw new RuntimeException(var3_4);
        }
    }

    private void a(String string) {
        if (string.equalsIgnoreCase("NO DEVICE ATTACHED")) {
            return;
        }
        if (this.b.contains(string)) {
            this.c.add(string);
        } else {
            this.b.add(string);
        }
    }

    private void a(ControllerConfiguration controllerConfiguration) throws IOException {
        Object object5;
        Object object22;
        Object object32;
        this.a.ignorableWhitespace(this.d[this.e]);
        this.a.startTag("", this.b(controllerConfiguration.getType().toString()));
        String string = controllerConfiguration.getName();
        this.a(string);
        this.a.attribute("", "name", controllerConfiguration.getName());
        this.a.attribute("", "serialNumber", controllerConfiguration.getSerialNumber().toString());
        this.a.ignorableWhitespace("\n");
        ++this.e;
        DeviceInterfaceModuleConfiguration deviceInterfaceModuleConfiguration = (DeviceInterfaceModuleConfiguration)controllerConfiguration;
        ArrayList arrayList = (ArrayList)deviceInterfaceModuleConfiguration.getPwmDevices();
        for (Object object22 : arrayList) {
            this.a((DeviceConfiguration)object22);
        }
        Object object4 = (ArrayList)deviceInterfaceModuleConfiguration.getI2cDevices();
        for (Object object5 : object4) {
            this.a((DeviceConfiguration)object5);
        }
        object22 = (ArrayList)deviceInterfaceModuleConfiguration.getAnalogInputDevices();
        for (Object object32 : object22) {
            this.a((DeviceConfiguration)object32);
        }
        object5 = (ArrayList)deviceInterfaceModuleConfiguration.getDigitalDevices();
        for (Object object6 : object5) {
            this.a((DeviceConfiguration)object6);
        }
        object32 = (ArrayList)deviceInterfaceModuleConfiguration.getAnalogOutputDevices();
        for (DeviceConfiguration deviceConfiguration : object32) {
            this.a(deviceConfiguration);
        }
        --this.e;
        this.a.ignorableWhitespace(this.d[this.e]);
        this.a.endTag("", this.b(controllerConfiguration.getType().toString()));
        this.a.ignorableWhitespace("\n");
    }

    private void b(ControllerConfiguration controllerConfiguration) throws IOException {
        this.a.ignorableWhitespace(this.d[this.e]);
        this.a.startTag("", this.b(controllerConfiguration.getType().toString()));
        String string = controllerConfiguration.getName();
        this.a(string);
        this.a.attribute("", "name", controllerConfiguration.getName());
        this.a.attribute("", "serialNumber", controllerConfiguration.getSerialNumber().toString());
        this.a.ignorableWhitespace("\n");
        ++this.e;
        ArrayList arrayList = (ArrayList)controllerConfiguration.getDevices();
        for (DeviceConfiguration deviceConfiguration : arrayList) {
            String string2 = deviceConfiguration.getType().toString();
            if (string2.equalsIgnoreCase(DeviceConfiguration.ConfigurationType.MOTOR_CONTROLLER.toString()) || string2.equalsIgnoreCase(DeviceConfiguration.ConfigurationType.SERVO_CONTROLLER.toString()) || string2.equalsIgnoreCase(DeviceConfiguration.ConfigurationType.MATRIX_CONTROLLER.toString())) {
                this.a((ControllerConfiguration)deviceConfiguration, false);
                continue;
            }
            if (!deviceConfiguration.isEnabled()) continue;
            this.a(deviceConfiguration);
        }
        --this.e;
        this.a.ignorableWhitespace(this.d[this.e]);
        this.a.endTag("", this.b(controllerConfiguration.getType().toString()));
        this.a.ignorableWhitespace("\n");
    }

    private void a(ControllerConfiguration controllerConfiguration, boolean bl) throws IOException {
        this.a.ignorableWhitespace(this.d[this.e]);
        this.a.startTag("", this.b(controllerConfiguration.getType().toString()));
        String string = controllerConfiguration.getName();
        this.a(string);
        this.a.attribute("", "name", controllerConfiguration.getName());
        if (bl) {
            this.a.attribute("", "serialNumber", controllerConfiguration.getSerialNumber().toString());
        } else {
            this.a.attribute("", "port", String.valueOf(controllerConfiguration.getPort()));
        }
        this.a.ignorableWhitespace("\n");
        ++this.e;
        ArrayList arrayList = (ArrayList)controllerConfiguration.getDevices();
        for (Object object2 : arrayList) {
            if (!object2.isEnabled()) continue;
            this.a((DeviceConfiguration)object2);
        }
        if (controllerConfiguration.getType() == DeviceConfiguration.ConfigurationType.MATRIX_CONTROLLER) {
            Object object2;
            Object object3 = (ArrayList)((MatrixControllerConfiguration)controllerConfiguration).getMotors();
            for (Object object4 : object3) {
                if (!object4.isEnabled()) continue;
                this.a((DeviceConfiguration)object4);
            }
            object2 = (ArrayList)((MatrixControllerConfiguration)controllerConfiguration).getServos();
            for (DeviceConfiguration deviceConfiguration : object2) {
                if (!deviceConfiguration.isEnabled()) continue;
                this.a(deviceConfiguration);
            }
        }
        --this.e;
        this.a.ignorableWhitespace(this.d[this.e]);
        this.a.endTag("", this.b(controllerConfiguration.getType().toString()));
        this.a.ignorableWhitespace("\n");
    }

    private void a(DeviceConfiguration deviceConfiguration) {
        if (!deviceConfiguration.isEnabled()) {
            return;
        }
        try {
            this.a.ignorableWhitespace(this.d[this.e]);
            this.a.startTag("", this.b(deviceConfiguration.getType().toString()));
            String string = deviceConfiguration.getName();
            this.a(string);
            this.a.attribute("", "name", deviceConfiguration.getName());
            this.a.attribute("", "port", String.valueOf(deviceConfiguration.getPort()));
            this.a.endTag("", this.b(deviceConfiguration.getType().toString()));
            this.a.ignorableWhitespace("\n");
        }
        catch (Exception var2_3) {
            throw new RuntimeException(var2_3);
        }
    }

    public void writeToFile(String data, String folderName, String filename) throws RobotCoreException, IOException {
        if (this.c.size() > 0) {
            throw new IOException("Duplicate names: " + this.c);
        }
        filename = filename.replaceFirst("[.][^.]+$", "");
        File file = new File(folderName);
        boolean bl = true;
        if (!file.exists()) {
            bl = file.mkdir();
        }
        if (bl) {
            File file2 = new File(folderName + filename + ".xml");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file2);
                fileOutputStream.write(data.getBytes());
            }
            catch (Exception var8_9) {
                var8_9.printStackTrace();
            }
            finally {
                try {
                    fileOutputStream.close();
                }
                catch (IOException var8_10) {
                    var8_10.printStackTrace();
                }
            }
        }
        throw new RobotCoreException("Unable to create directory");
    }

    private String b(String string) {
        String string2 = string.substring(0, 1) + string.substring(1).toLowerCase();
        int n = string.lastIndexOf("_");
        while (n > 0) {
            int n2 = n + 1;
            String string3 = string2.substring(0, n);
            String string4 = string2.substring(n2, n2 + 1).toUpperCase();
            String string5 = string2.substring(n2 + 1);
            string2 = string3 + string4 + string5;
            n = string2.lastIndexOf("_");
        }
        return string2;
    }
}

