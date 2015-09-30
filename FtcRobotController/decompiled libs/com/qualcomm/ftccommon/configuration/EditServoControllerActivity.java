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
 *  android.text.TextWatcher
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.CheckBox
 *  android.widget.EditText
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  com.qualcomm.ftccommon.R
 *  com.qualcomm.ftccommon.R$id
 *  com.qualcomm.ftccommon.R$layout
 *  com.qualcomm.ftccommon.R$string
 *  com.qualcomm.ftccommon.R$xml
 *  com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration
 *  com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration
 *  com.qualcomm.robotcore.hardware.configuration.ServoControllerConfiguration
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
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qualcomm.ftccommon.R;
import com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.hardware.configuration.ServoControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.Utility;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditServoControllerActivity
extends Activity {
    public static final String EDIT_SERVO_ACTIVITY = "Edit Servo ControllerConfiguration Activity";
    private Utility a;
    private ServoControllerConfiguration b;
    private ArrayList<DeviceConfiguration> c;
    private EditText d;
    private View e;
    private View f;
    private View g;
    private View h;
    private View i;
    private View j;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.servos);
        PreferenceManager.setDefaultValues((Context)this, (int)R.xml.preferences, (boolean)false);
        this.a = new Utility((Activity)this);
        RobotLog.writeLogcatToDisk((Context)this, (int)1024);
        this.d = (EditText)this.findViewById(R.id.servocontroller_name);
        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.linearLayout_servo1);
        this.e = this.getLayoutInflater().inflate(R.layout.servo, (ViewGroup)linearLayout, true);
        TextView textView = (TextView)this.e.findViewById(R.id.port_number_servo);
        textView.setText((CharSequence)"1");
        LinearLayout linearLayout2 = (LinearLayout)this.findViewById(R.id.linearLayout_servo2);
        this.f = this.getLayoutInflater().inflate(R.layout.servo, (ViewGroup)linearLayout2, true);
        TextView textView2 = (TextView)this.f.findViewById(R.id.port_number_servo);
        textView2.setText((CharSequence)"2");
        LinearLayout linearLayout3 = (LinearLayout)this.findViewById(R.id.linearLayout_servo3);
        this.g = this.getLayoutInflater().inflate(R.layout.servo, (ViewGroup)linearLayout3, true);
        TextView textView3 = (TextView)this.g.findViewById(R.id.port_number_servo);
        textView3.setText((CharSequence)"3");
        LinearLayout linearLayout4 = (LinearLayout)this.findViewById(R.id.linearLayout_servo4);
        this.h = this.getLayoutInflater().inflate(R.layout.servo, (ViewGroup)linearLayout4, true);
        TextView textView4 = (TextView)this.h.findViewById(R.id.port_number_servo);
        textView4.setText((CharSequence)"4");
        LinearLayout linearLayout5 = (LinearLayout)this.findViewById(R.id.linearLayout_servo5);
        this.i = this.getLayoutInflater().inflate(R.layout.servo, (ViewGroup)linearLayout5, true);
        TextView textView5 = (TextView)this.i.findViewById(R.id.port_number_servo);
        textView5.setText((CharSequence)"5");
        LinearLayout linearLayout6 = (LinearLayout)this.findViewById(R.id.linearLayout_servo6);
        this.j = this.getLayoutInflater().inflate(R.layout.servo, (ViewGroup)linearLayout6, true);
        TextView textView6 = (TextView)this.j.findViewById(R.id.port_number_servo);
        textView6.setText((CharSequence)"6");
    }

    protected void onStart() {
        super.onStart();
        this.a.updateHeader("No current file!", R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);
        Intent intent = this.getIntent();
        Serializable serializable = intent.getSerializableExtra("Edit Servo ControllerConfiguration Activity");
        if (serializable != null) {
            this.b = (ServoControllerConfiguration)serializable;
            this.c = (ArrayList)this.b.getServos();
        }
        this.d.setText((CharSequence)this.b.getName());
        TextView textView = (TextView)this.findViewById(R.id.servo_controller_serialNumber);
        String string = this.b.getSerialNumber().toString();
        if (string.equalsIgnoreCase(ControllerConfiguration.NO_SERIAL_NUMBER.toString())) {
            string = "No serial number";
        }
        textView.setText((CharSequence)string);
        for (int i = 0; i < this.c.size(); ++i) {
            this.c(i + 1);
            this.a(i + 1);
            this.b(i + 1);
        }
    }

    private void a(int n) {
        View view = this.d(n);
        EditText editText = (EditText)view.findViewById(R.id.editTextResult_servo);
        editText.addTextChangedListener((TextWatcher)new a(view));
    }

    private void b(int n) {
        View view = this.d(n);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox_port_servo);
        DeviceConfiguration deviceConfiguration = this.c.get(n - 1);
        if (deviceConfiguration.isEnabled()) {
            checkBox.setChecked(true);
            EditText editText = (EditText)view.findViewById(R.id.editTextResult_servo);
            editText.setText((CharSequence)deviceConfiguration.getName());
        } else {
            checkBox.setChecked(true);
            checkBox.performClick();
        }
    }

    private void c(int n) {
        View view = this.d(n);
        final EditText editText = (EditText)view.findViewById(R.id.editTextResult_servo);
        final DeviceConfiguration deviceConfiguration = this.c.get(n - 1);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox_port_servo);
        checkBox.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (((CheckBox)view).isChecked()) {
                    editText.setEnabled(true);
                    editText.setText((CharSequence)"");
                    deviceConfiguration.setEnabled(true);
                    deviceConfiguration.setName("");
                } else {
                    editText.setEnabled(false);
                    deviceConfiguration.setEnabled(false);
                    deviceConfiguration.setName("NO DEVICE ATTACHED");
                    editText.setText((CharSequence)"NO DEVICE ATTACHED");
                }
            }
        });
    }

    private View d(int n) {
        switch (n) {
            case 1: {
                return this.e;
            }
            case 2: {
                return this.f;
            }
            case 3: {
                return this.g;
            }
            case 4: {
                return this.h;
            }
            case 5: {
                return this.i;
            }
            case 6: {
                return this.j;
            }
        }
        return null;
    }

    public void saveServoController(View v) {
        this.a();
    }

    private void a() {
        Intent intent = new Intent();
        this.b.addServos(this.c);
        this.b.setName(this.d.getText().toString());
        intent.putExtra("Edit Servo ControllerConfiguration Activity", (Serializable)this.b);
        this.setResult(-1, intent);
        this.finish();
    }

    public void cancelServoController(View v) {
        this.setResult(0, new Intent());
        this.finish();
    }

    private class a
    implements TextWatcher {
        private int b;

        private a(View view) {
            TextView textView = (TextView)view.findViewById(R.id.port_number_servo);
            this.b = Integer.parseInt(textView.getText().toString());
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            DeviceConfiguration deviceConfiguration = (DeviceConfiguration)EditServoControllerActivity.this.c.get(this.b - 1);
            String string = editable.toString();
            deviceConfiguration.setName(string);
        }
    }

}

