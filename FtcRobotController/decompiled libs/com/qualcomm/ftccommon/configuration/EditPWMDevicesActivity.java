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
 *  com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration
 *  com.qualcomm.robotcore.hardware.configuration.Utility
 *  com.qualcomm.robotcore.util.RobotLog
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
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.hardware.configuration.Utility;
import com.qualcomm.robotcore.util.RobotLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class EditPWMDevicesActivity
extends Activity {
    private Utility a;
    public static final String EDIT_PWM_DEVICES = "EDIT_PWM_DEVICES";
    private View b;
    private View c;
    private ArrayList<DeviceConfiguration> d = new ArrayList();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.pwms);
        PreferenceManager.setDefaultValues((Context)this, (int)R.xml.preferences, (boolean)false);
        this.a = new Utility((Activity)this);
        RobotLog.writeLogcatToDisk((Context)this, (int)1024);
        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.linearLayout_pwm0);
        this.b = this.getLayoutInflater().inflate(R.layout.pwm_device, (ViewGroup)linearLayout, true);
        TextView textView = (TextView)this.b.findViewById(R.id.port_number_pwm);
        textView.setText((CharSequence)"0");
        LinearLayout linearLayout2 = (LinearLayout)this.findViewById(R.id.linearLayout_pwm1);
        this.c = this.getLayoutInflater().inflate(R.layout.pwm_device, (ViewGroup)linearLayout2, true);
        TextView textView2 = (TextView)this.c.findViewById(R.id.port_number_pwm);
        textView2.setText((CharSequence)"1");
    }

    protected void onStart() {
        super.onStart();
        this.a.updateHeader("No current file!", R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            for (String string : bundle.keySet()) {
                this.d.add(Integer.parseInt(string), (DeviceConfiguration)bundle.getSerializable(string));
            }
            for (int i = 0; i < this.d.size(); ++i) {
                this.c(i);
                this.b(i);
                this.a(i);
            }
        }
    }

    private void a(int n) {
        View view = this.d(n);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox_port_pwm);
        DeviceConfiguration deviceConfiguration = this.d.get(n);
        if (deviceConfiguration.isEnabled()) {
            checkBox.setChecked(true);
            EditText editText = (EditText)view.findViewById(R.id.editTextResult_pwm);
            editText.setText((CharSequence)deviceConfiguration.getName());
        } else {
            checkBox.setChecked(true);
            checkBox.performClick();
        }
    }

    private void b(int n) {
        View view = this.d(n);
        EditText editText = (EditText)view.findViewById(R.id.editTextResult_pwm);
        editText.addTextChangedListener((TextWatcher)new a(view));
    }

    private void c(int n) {
        View view = this.d(n);
        final EditText editText = (EditText)view.findViewById(R.id.editTextResult_pwm);
        final DeviceConfiguration deviceConfiguration = this.d.get(n);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox_port_pwm);
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
            case 0: {
                return this.b;
            }
            case 1: {
                return this.c;
            }
        }
        return null;
    }

    public void savePWMDevices(View v) {
        this.a();
    }

    private void a() {
        Bundle bundle = new Bundle();
        for (int i = 0; i < this.d.size(); ++i) {
            bundle.putSerializable(String.valueOf(i), (Serializable)this.d.get(i));
        }
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.putExtras(bundle);
        this.setResult(-1, intent);
        this.finish();
    }

    public void cancelPWMDevices(View v) {
        this.setResult(0, new Intent());
        this.finish();
    }

    private class a
    implements TextWatcher {
        private int b;

        private a(View view) {
            TextView textView = (TextView)view.findViewById(R.id.port_number_pwm);
            this.b = Integer.parseInt(textView.getText().toString());
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            DeviceConfiguration deviceConfiguration = (DeviceConfiguration)EditPWMDevicesActivity.this.d.get(this.b);
            String string = editable.toString();
            deviceConfiguration.setName(string);
        }
    }

}

