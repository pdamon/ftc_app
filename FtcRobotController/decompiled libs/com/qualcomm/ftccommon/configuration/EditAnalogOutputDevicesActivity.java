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
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.EditText
 *  android.widget.LinearLayout
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 *  com.qualcomm.ftccommon.R
 *  com.qualcomm.ftccommon.R$id
 *  com.qualcomm.ftccommon.R$layout
 *  com.qualcomm.ftccommon.R$string
 *  com.qualcomm.ftccommon.R$xml
 *  com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration
 *  com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration$ConfigurationType
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
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.qualcomm.ftccommon.R;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.hardware.configuration.Utility;
import com.qualcomm.robotcore.util.RobotLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class EditAnalogOutputDevicesActivity
extends Activity {
    private Utility a;
    private View b;
    private View c;
    private ArrayList<DeviceConfiguration> d = new ArrayList();
    private AdapterView.OnItemSelectedListener e;

    public EditAnalogOutputDevicesActivity() {
        this.e = new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                String string = parent.getItemAtPosition(pos).toString();
                LinearLayout linearLayout = (LinearLayout)view.getParent().getParent().getParent();
                if (string.equalsIgnoreCase(DeviceConfiguration.ConfigurationType.NOTHING.toString())) {
                    EditAnalogOutputDevicesActivity.this.a(linearLayout);
                } else {
                    EditAnalogOutputDevicesActivity.this.a(linearLayout, string);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.analog_outputs);
        PreferenceManager.setDefaultValues((Context)this, (int)R.xml.preferences, (boolean)false);
        this.a = new Utility((Activity)this);
        RobotLog.writeLogcatToDisk((Context)this, (int)1024);
        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.linearLayout_analogOutput0);
        this.b = this.getLayoutInflater().inflate(R.layout.analog_output_device, (ViewGroup)linearLayout, true);
        TextView textView = (TextView)this.b.findViewById(R.id.port_number_analogOutput);
        textView.setText((CharSequence)"0");
        LinearLayout linearLayout2 = (LinearLayout)this.findViewById(R.id.linearLayout_analogOutput1);
        this.c = this.getLayoutInflater().inflate(R.layout.analog_output_device, (ViewGroup)linearLayout2, true);
        TextView textView2 = (TextView)this.c.findViewById(R.id.port_number_analogOutput);
        textView2.setText((CharSequence)"1");
    }

    protected void onStart() {
        super.onStart();
        this.a.updateHeader("No current file!", R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            DeviceConfiguration deviceConfiguration;
            for (String string2 : bundle.keySet()) {
                deviceConfiguration = (DeviceConfiguration)bundle.getSerializable(string2);
                this.d.add(Integer.parseInt(string2), deviceConfiguration);
            }
            for (int i = 0; i < this.d.size(); ++i) {
                String string2;
                string2 = this.a(i);
                deviceConfiguration = this.d.get(i);
                this.a((View)string2);
                this.b((View)string2, deviceConfiguration);
                this.a((View)string2, deviceConfiguration);
            }
        }
    }

    private void a(View view, DeviceConfiguration deviceConfiguration) {
        Spinner spinner = (Spinner)view.findViewById(R.id.choiceSpinner_analogOutput);
        ArrayAdapter arrayAdapter = (ArrayAdapter)spinner.getAdapter();
        if (deviceConfiguration.isEnabled()) {
            int n = arrayAdapter.getPosition((Object)deviceConfiguration.getType().toString());
            spinner.setSelection(n);
        } else {
            spinner.setSelection(0);
        }
        spinner.setOnItemSelectedListener(this.e);
    }

    private void b(View view, DeviceConfiguration deviceConfiguration) {
        EditText editText = (EditText)view.findViewById(R.id.editTextResult_analogOutput);
        if (deviceConfiguration.isEnabled()) {
            editText.setText((CharSequence)deviceConfiguration.getName());
            editText.setEnabled(true);
        } else {
            editText.setText((CharSequence)"NO DEVICE ATTACHED");
            editText.setEnabled(false);
        }
    }

    private void a(View view) {
        EditText editText = (EditText)view.findViewById(R.id.editTextResult_analogOutput);
        editText.addTextChangedListener((TextWatcher)new a(view));
    }

    private View a(int n) {
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

    public void saveanalogOutputDevices(View v) {
        this.a();
    }

    private void a() {
        Bundle bundle = new Bundle();
        for (int i = 0; i < this.d.size(); ++i) {
            DeviceConfiguration deviceConfiguration = this.d.get(i);
            bundle.putSerializable(String.valueOf(i), (Serializable)deviceConfiguration);
        }
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.putExtras(bundle);
        this.setResult(-1, intent);
        this.finish();
    }

    public void cancelanalogOutputDevices(View v) {
        this.setResult(0, new Intent());
        this.finish();
    }

    private void a(LinearLayout linearLayout) {
        TextView textView = (TextView)linearLayout.findViewById(R.id.port_number_analogOutput);
        int n = Integer.parseInt(textView.getText().toString());
        EditText editText = (EditText)linearLayout.findViewById(R.id.editTextResult_analogOutput);
        editText.setEnabled(false);
        editText.setText((CharSequence)"NO DEVICE ATTACHED");
        DeviceConfiguration deviceConfiguration = this.d.get(n);
        deviceConfiguration.setEnabled(false);
    }

    private void a(LinearLayout linearLayout, String string) {
        TextView textView = (TextView)linearLayout.findViewById(R.id.port_number_analogOutput);
        int n = Integer.parseInt(textView.getText().toString());
        EditText editText = (EditText)linearLayout.findViewById(R.id.editTextResult_analogOutput);
        editText.setEnabled(true);
        DeviceConfiguration deviceConfiguration = this.d.get(n);
        DeviceConfiguration.ConfigurationType configurationType = deviceConfiguration.typeFromString(string);
        deviceConfiguration.setType(configurationType);
        deviceConfiguration.setEnabled(true);
        this.a(editText, deviceConfiguration);
    }

    private void a(EditText editText, DeviceConfiguration deviceConfiguration) {
        if (editText.getText().toString().equalsIgnoreCase("NO DEVICE ATTACHED")) {
            editText.setText((CharSequence)"");
            deviceConfiguration.setName("");
        } else {
            editText.setText((CharSequence)deviceConfiguration.getName());
        }
    }

    private class a
    implements TextWatcher {
        private int b;

        private a(View view) {
            TextView textView = (TextView)view.findViewById(R.id.port_number_analogOutput);
            this.b = Integer.parseInt(textView.getText().toString());
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            DeviceConfiguration deviceConfiguration = (DeviceConfiguration)EditAnalogOutputDevicesActivity.this.d.get(this.b);
            String string = editable.toString();
            deviceConfiguration.setName(string);
        }
    }

}

