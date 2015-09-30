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
 *  com.qualcomm.robotcore.hardware.configuration.MatrixControllerConfiguration
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
import com.qualcomm.robotcore.hardware.configuration.MatrixControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.Utility;
import com.qualcomm.robotcore.util.RobotLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditMatrixControllerActivity
extends Activity {
    public static final String EDIT_MATRIX_ACTIVITY = "Edit Matrix ControllerConfiguration Activity";
    private Utility a;
    private MatrixControllerConfiguration b;
    private ArrayList<DeviceConfiguration> c;
    private ArrayList<DeviceConfiguration> d;
    private EditText e;
    private View f;
    private View g;
    private View h;
    private View i;
    private View j;
    private View k;
    private View l;
    private View m;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.matrices);
        PreferenceManager.setDefaultValues((Context)this, (int)R.xml.preferences, (boolean)false);
        this.a = new Utility((Activity)this);
        RobotLog.writeLogcatToDisk((Context)this, (int)1024);
        this.e = (EditText)this.findViewById(R.id.matrixcontroller_name);
        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.linearLayout_matrix1);
        this.f = this.getLayoutInflater().inflate(R.layout.matrix_devices, (ViewGroup)linearLayout, true);
        TextView textView = (TextView)this.f.findViewById(R.id.port_number_matrix);
        textView.setText((CharSequence)"1");
        LinearLayout linearLayout2 = (LinearLayout)this.findViewById(R.id.linearLayout_matrix2);
        this.g = this.getLayoutInflater().inflate(R.layout.matrix_devices, (ViewGroup)linearLayout2, true);
        TextView textView2 = (TextView)this.g.findViewById(R.id.port_number_matrix);
        textView2.setText((CharSequence)"2");
        LinearLayout linearLayout3 = (LinearLayout)this.findViewById(R.id.linearLayout_matrix3);
        this.h = this.getLayoutInflater().inflate(R.layout.matrix_devices, (ViewGroup)linearLayout3, true);
        TextView textView3 = (TextView)this.h.findViewById(R.id.port_number_matrix);
        textView3.setText((CharSequence)"3");
        LinearLayout linearLayout4 = (LinearLayout)this.findViewById(R.id.linearLayout_matrix4);
        this.i = this.getLayoutInflater().inflate(R.layout.matrix_devices, (ViewGroup)linearLayout4, true);
        TextView textView4 = (TextView)this.i.findViewById(R.id.port_number_matrix);
        textView4.setText((CharSequence)"4");
        LinearLayout linearLayout5 = (LinearLayout)this.findViewById(R.id.linearLayout_matrix5);
        this.j = this.getLayoutInflater().inflate(R.layout.matrix_devices, (ViewGroup)linearLayout5, true);
        TextView textView5 = (TextView)this.j.findViewById(R.id.port_number_matrix);
        textView5.setText((CharSequence)"1");
        LinearLayout linearLayout6 = (LinearLayout)this.findViewById(R.id.linearLayout_matrix6);
        this.k = this.getLayoutInflater().inflate(R.layout.matrix_devices, (ViewGroup)linearLayout6, true);
        TextView textView6 = (TextView)this.k.findViewById(R.id.port_number_matrix);
        textView6.setText((CharSequence)"2");
        LinearLayout linearLayout7 = (LinearLayout)this.findViewById(R.id.linearLayout_matrix7);
        this.l = this.getLayoutInflater().inflate(R.layout.matrix_devices, (ViewGroup)linearLayout7, true);
        TextView textView7 = (TextView)this.l.findViewById(R.id.port_number_matrix);
        textView7.setText((CharSequence)"3");
        LinearLayout linearLayout8 = (LinearLayout)this.findViewById(R.id.linearLayout_matrix8);
        this.m = this.getLayoutInflater().inflate(R.layout.matrix_devices, (ViewGroup)linearLayout8, true);
        TextView textView8 = (TextView)this.m.findViewById(R.id.port_number_matrix);
        textView8.setText((CharSequence)"4");
    }

    protected void onStart() {
        int n;
        View view;
        super.onStart();
        this.a.updateHeader("No current file!", R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);
        Intent intent = this.getIntent();
        Serializable serializable = intent.getSerializableExtra("Edit Matrix ControllerConfiguration Activity");
        if (serializable != null) {
            this.b = (MatrixControllerConfiguration)serializable;
            this.c = (ArrayList)this.b.getMotors();
            this.d = (ArrayList)this.b.getServos();
        }
        this.e.setText((CharSequence)this.b.getName());
        for (n = 0; n < this.c.size(); ++n) {
            view = this.b(n + 1);
            this.b(n + 1, view, this.c);
            this.a(view, this.c.get(n));
            this.a(n + 1, view, this.c);
        }
        for (n = 0; n < this.d.size(); ++n) {
            view = this.a(n + 1);
            this.b(n + 1, view, this.d);
            this.a(view, this.d.get(n));
            this.a(n + 1, view, this.d);
        }
    }

    private void a(View view, DeviceConfiguration deviceConfiguration) {
        EditText editText = (EditText)view.findViewById(R.id.editTextResult_matrix);
        editText.addTextChangedListener((TextWatcher)new a(deviceConfiguration));
    }

    private void a(int n, View view, ArrayList<DeviceConfiguration> arrayList) {
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox_port_matrix);
        DeviceConfiguration deviceConfiguration = arrayList.get(n - 1);
        if (deviceConfiguration.isEnabled()) {
            checkBox.setChecked(true);
            EditText editText = (EditText)view.findViewById(R.id.editTextResult_matrix);
            editText.setText((CharSequence)deviceConfiguration.getName());
        } else {
            checkBox.setChecked(true);
            checkBox.performClick();
        }
    }

    private void b(int n, View view, ArrayList<DeviceConfiguration> arrayList) {
        final EditText editText = (EditText)view.findViewById(R.id.editTextResult_matrix);
        final DeviceConfiguration deviceConfiguration = arrayList.get(n - 1);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox_port_matrix);
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

    private View a(int n) {
        switch (n) {
            case 1: {
                return this.f;
            }
            case 2: {
                return this.g;
            }
            case 3: {
                return this.h;
            }
            case 4: {
                return this.i;
            }
        }
        return null;
    }

    private View b(int n) {
        switch (n) {
            case 1: {
                return this.j;
            }
            case 2: {
                return this.k;
            }
            case 3: {
                return this.l;
            }
            case 4: {
                return this.m;
            }
        }
        return null;
    }

    public void saveMatrixController(View v) {
        this.a();
    }

    private void a() {
        Intent intent = new Intent();
        this.b.addServos(this.d);
        this.b.addMotors(this.c);
        this.b.setName(this.e.getText().toString());
        intent.putExtra("Edit Matrix ControllerConfiguration Activity", (Serializable)this.b);
        this.setResult(-1, intent);
        this.finish();
    }

    public void cancelMatrixController(View v) {
        this.setResult(0, new Intent());
        this.finish();
    }

    private class a
    implements TextWatcher {
        private DeviceConfiguration b;

        private a(DeviceConfiguration deviceConfiguration) {
            this.b = deviceConfiguration;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String string = editable.toString();
            this.b.setName(string);
        }
    }

}

