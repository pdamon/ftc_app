/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.text.Editable
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.CheckBox
 *  android.widget.EditText
 *  android.widget.TextView
 *  com.qualcomm.ftccommon.R
 *  com.qualcomm.ftccommon.R$id
 *  com.qualcomm.ftccommon.R$layout
 *  com.qualcomm.ftccommon.R$string
 *  com.qualcomm.ftccommon.R$xml
 *  com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration
 *  com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration
 *  com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration$ConfigurationType
 *  com.qualcomm.robotcore.hardware.configuration.MotorConfiguration
 *  com.qualcomm.robotcore.hardware.configuration.MotorControllerConfiguration
 *  com.qualcomm.robotcore.hardware.configuration.Utility
 *  com.qualcomm.robotcore.util.RobotLog
 *  com.qualcomm.robotcore.util.SerialNumber
 */
package com.qualcomm.ftccommon.configuration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.qualcomm.ftccommon.R;
import com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.hardware.configuration.MotorConfiguration;
import com.qualcomm.robotcore.hardware.configuration.MotorControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.Utility;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditMotorControllerActivity
extends Activity {
    private Utility a;
    public static final String EDIT_MOTOR_CONTROLLER_CONFIG = "EDIT_MOTOR_CONTROLLER_CONFIG";
    private MotorControllerConfiguration b;
    private ArrayList<DeviceConfiguration> c = new ArrayList();
    private MotorConfiguration d = new MotorConfiguration(1);
    private MotorConfiguration e = new MotorConfiguration(2);
    private EditText f;
    private boolean g = true;
    private boolean h = true;
    private CheckBox i;
    private CheckBox j;
    private EditText k;
    private EditText l;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.motors);
        PreferenceManager.setDefaultValues((Context)this, (int)R.xml.preferences, (boolean)false);
        this.a = new Utility((Activity)this);
        RobotLog.writeLogcatToDisk((Context)this, (int)1024);
        this.f = (EditText)this.findViewById(R.id.controller_name);
        this.i = (CheckBox)this.findViewById(R.id.checkbox_port7);
        this.j = (CheckBox)this.findViewById(R.id.checkbox_port6);
        this.k = (EditText)this.findViewById(R.id.editTextResult_analogInput7);
        this.l = (EditText)this.findViewById(R.id.editTextResult_analogInput6);
    }

    protected void onStart() {
        super.onStart();
        this.a.updateHeader("No current file!", R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);
        Intent intent = this.getIntent();
        Serializable serializable = intent.getSerializableExtra("EDIT_MOTOR_CONTROLLER_CONFIG");
        if (serializable != null) {
            this.b = (MotorControllerConfiguration)serializable;
            this.c = (ArrayList)this.b.getMotors();
            this.d = (MotorConfiguration)this.c.get(0);
            this.e = (MotorConfiguration)this.c.get(1);
            this.f.setText((CharSequence)this.b.getName());
            TextView textView = (TextView)this.findViewById(R.id.motor_controller_serialNumber);
            String string = this.b.getSerialNumber().toString();
            if (string.equalsIgnoreCase(ControllerConfiguration.NO_SERIAL_NUMBER.toString())) {
                string = "No serial number";
            }
            textView.setText((CharSequence)string);
            this.k.setText((CharSequence)this.d.getName());
            this.l.setText((CharSequence)this.e.getName());
            this.a();
            this.a(this.d, this.i);
            this.b();
            this.a(this.e, this.j);
        }
    }

    private void a(MotorConfiguration motorConfiguration, CheckBox checkBox) {
        if (motorConfiguration.getName().equals("NO DEVICE ATTACHED") || motorConfiguration.getType() == DeviceConfiguration.ConfigurationType.NOTHING) {
            checkBox.setChecked(true);
            checkBox.performClick();
        } else {
            checkBox.setChecked(true);
        }
    }

    private void a() {
        this.i.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (((CheckBox)view).isChecked()) {
                    EditMotorControllerActivity.this.g = true;
                    EditMotorControllerActivity.this.k.setEnabled(true);
                    EditMotorControllerActivity.this.k.setText((CharSequence)"");
                    EditMotorControllerActivity.this.d.setPort(1);
                    EditMotorControllerActivity.this.d.setType(DeviceConfiguration.ConfigurationType.MOTOR);
                } else {
                    EditMotorControllerActivity.this.g = false;
                    EditMotorControllerActivity.this.k.setEnabled(false);
                    EditMotorControllerActivity.this.k.setText((CharSequence)"NO DEVICE ATTACHED");
                    EditMotorControllerActivity.this.d.setType(DeviceConfiguration.ConfigurationType.NOTHING);
                }
            }
        });
    }

    private void b() {
        this.j.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (((CheckBox)view).isChecked()) {
                    EditMotorControllerActivity.this.h = true;
                    EditMotorControllerActivity.this.l.setEnabled(true);
                    EditMotorControllerActivity.this.l.setText((CharSequence)"");
                    EditMotorControllerActivity.this.e.setPort(2);
                    EditMotorControllerActivity.this.d.setType(DeviceConfiguration.ConfigurationType.MOTOR);
                } else {
                    EditMotorControllerActivity.this.h = false;
                    EditMotorControllerActivity.this.l.setEnabled(false);
                    EditMotorControllerActivity.this.l.setText((CharSequence)"NO DEVICE ATTACHED");
                    EditMotorControllerActivity.this.d.setType(DeviceConfiguration.ConfigurationType.NOTHING);
                }
            }
        });
    }

    public void saveMotorController(View v) {
        this.c();
    }

    private void c() {
        String string;
        MotorConfiguration motorConfiguration;
        Intent intent = new Intent();
        ArrayList<MotorConfiguration> arrayList = new ArrayList<MotorConfiguration>();
        if (this.g) {
            string = this.k.getText().toString();
            motorConfiguration = new MotorConfiguration(string);
            motorConfiguration.setEnabled(true);
            motorConfiguration.setPort(1);
            arrayList.add(motorConfiguration);
        } else {
            arrayList.add(new MotorConfiguration(1));
        }
        if (this.h) {
            string = this.l.getText().toString();
            motorConfiguration = new MotorConfiguration(string);
            motorConfiguration.setEnabled(true);
            motorConfiguration.setPort(2);
            arrayList.add(motorConfiguration);
        } else {
            arrayList.add(new MotorConfiguration(2));
        }
        this.b.addMotors(arrayList);
        this.b.setName(this.f.getText().toString());
        intent.putExtra("EDIT_MOTOR_CONTROLLER_CONFIG", (Serializable)this.b);
        this.setResult(-1, intent);
        this.finish();
    }

    public void cancelMotorController(View v) {
        this.setResult(0, new Intent());
        this.finish();
    }

}

