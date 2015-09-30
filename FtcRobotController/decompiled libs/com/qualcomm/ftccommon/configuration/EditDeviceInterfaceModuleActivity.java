/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.EditText
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  com.qualcomm.ftccommon.R
 *  com.qualcomm.ftccommon.R$array
 *  com.qualcomm.ftccommon.R$id
 *  com.qualcomm.ftccommon.R$layout
 *  com.qualcomm.ftccommon.R$string
 *  com.qualcomm.ftccommon.R$xml
 *  com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration
 *  com.qualcomm.robotcore.hardware.configuration.DeviceInterfaceModuleConfiguration
 *  com.qualcomm.robotcore.hardware.configuration.Utility
 *  com.qualcomm.robotcore.util.RobotLog
 *  com.qualcomm.robotcore.util.SerialNumber
 */
package com.qualcomm.ftccommon.configuration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.qualcomm.ftccommon.R;
import com.qualcomm.ftccommon.configuration.EditAnalogInputDevicesActivity;
import com.qualcomm.ftccommon.configuration.EditAnalogOutputDevicesActivity;
import com.qualcomm.ftccommon.configuration.EditDigitalDevicesActivity;
import com.qualcomm.ftccommon.configuration.EditI2cDevicesActivity;
import com.qualcomm.ftccommon.configuration.EditPWMDevicesActivity;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.hardware.configuration.DeviceInterfaceModuleConfiguration;
import com.qualcomm.robotcore.hardware.configuration.Utility;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EditDeviceInterfaceModuleActivity
extends Activity {
    private Utility a;
    private String b;
    private Context c;
    public static final String EDIT_DEVICE_INTERFACE_MODULE_CONFIG = "EDIT_DEVICE_INTERFACE_MODULE_CONFIG";
    public static final int EDIT_PWM_PORT_REQUEST_CODE = 201;
    public static final int EDIT_I2C_PORT_REQUEST_CODE = 202;
    public static final int EDIT_ANALOG_INPUT_REQUEST_CODE = 203;
    public static final int EDIT_DIGITAL_REQUEST_CODE = 204;
    public static final int EDIT_ANALOG_OUTPUT_REQUEST_CODE = 205;
    private DeviceInterfaceModuleConfiguration d;
    private EditText e;
    private ArrayList<DeviceConfiguration> f = new ArrayList();
    private AdapterView.OnItemClickListener g;

    public EditDeviceInterfaceModuleActivity() {
        this.g = new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        EditDeviceInterfaceModuleActivity.this.a(201, EditDeviceInterfaceModuleActivity.this.d.getPwmDevices(), EditPWMDevicesActivity.class);
                        break;
                    }
                    case 1: {
                        EditDeviceInterfaceModuleActivity.this.a(202, EditDeviceInterfaceModuleActivity.this.d.getI2cDevices(), EditI2cDevicesActivity.class);
                        break;
                    }
                    case 2: {
                        EditDeviceInterfaceModuleActivity.this.a(203, EditDeviceInterfaceModuleActivity.this.d.getAnalogInputDevices(), EditAnalogInputDevicesActivity.class);
                        break;
                    }
                    case 3: {
                        EditDeviceInterfaceModuleActivity.this.a(204, EditDeviceInterfaceModuleActivity.this.d.getDigitalDevices(), EditDigitalDevicesActivity.class);
                        break;
                    }
                    case 4: {
                        EditDeviceInterfaceModuleActivity.this.a(205, EditDeviceInterfaceModuleActivity.this.d.getAnalogOutputDevices(), EditAnalogOutputDevicesActivity.class);
                    }
                }
            }
        };
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.device_interface_module);
        Object[] arrobject = this.getResources().getStringArray(R.array.device_interface_module_options_array);
        ListView listView = (ListView)this.findViewById(R.id.listView_devices);
        listView.setAdapter((ListAdapter)new ArrayAdapter((Context)this, 17367043, arrobject));
        listView.setOnItemClickListener(this.g);
        this.c = this;
        PreferenceManager.setDefaultValues((Context)this, (int)R.xml.preferences, (boolean)false);
        this.a = new Utility((Activity)this);
        RobotLog.writeLogcatToDisk((Context)this, (int)1024);
        this.e = (EditText)this.findViewById(R.id.device_interface_module_name);
        this.e.addTextChangedListener((TextWatcher)new a());
    }

    protected void onStart() {
        super.onStart();
        this.a.updateHeader("No current file!", R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);
        this.b = this.a.getFilenameFromPrefs(R.string.pref_hardware_config_filename, "No current file!");
        Intent intent = this.getIntent();
        Serializable serializable = intent.getSerializableExtra("EDIT_DEVICE_INTERFACE_MODULE_CONFIG");
        if (serializable != null) {
            this.d = (DeviceInterfaceModuleConfiguration)serializable;
            this.f = (ArrayList)this.d.getDevices();
            this.e.setText((CharSequence)this.d.getName());
            TextView textView = (TextView)this.findViewById(R.id.device_interface_module_serialNumber);
            textView.setText((CharSequence)this.d.getSerialNumber().toString());
        }
    }

    private void a(int n, List<DeviceConfiguration> list, Class class_) {
        Bundle bundle = new Bundle();
        for (int i = 0; i < list.size(); ++i) {
            bundle.putSerializable(String.valueOf(i), (Serializable)list.get(i));
        }
        Intent intent = new Intent(this.c, class_);
        intent.putExtras(bundle);
        this.setResult(-1, intent);
        this.startActivityForResult(intent, n);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = null;
        if (resultCode == -1) {
            if (requestCode == 201) {
                bundle = data.getExtras();
                if (bundle != null) {
                    this.d.setPwmDevices(this.a(bundle));
                }
            } else if (requestCode == 203) {
                bundle = data.getExtras();
                if (bundle != null) {
                    this.d.setAnalogInputDevices(this.a(bundle));
                }
            } else if (requestCode == 204) {
                bundle = data.getExtras();
                if (bundle != null) {
                    this.d.setDigitalDevices(this.a(bundle));
                }
            } else if (requestCode == 202) {
                bundle = data.getExtras();
                if (bundle != null) {
                    this.d.setI2cDevices(this.a(bundle));
                }
            } else if (requestCode == 205 && (bundle = data.getExtras()) != null) {
                this.d.setAnalogOutputDevices(this.a(bundle));
            }
            this.a();
        }
    }

    private ArrayList<DeviceConfiguration> a(Bundle bundle) {
        ArrayList<DeviceConfiguration> arrayList = new ArrayList<DeviceConfiguration>();
        for (String string : bundle.keySet()) {
            DeviceConfiguration deviceConfiguration = (DeviceConfiguration)bundle.getSerializable(string);
            arrayList.add(Integer.parseInt(string), deviceConfiguration);
        }
        return arrayList;
    }

    private void a() {
        String string = this.b;
        if (!string.toLowerCase().contains((CharSequence)"Unsaved".toLowerCase())) {
            string = "Unsaved " + this.b;
            this.a.saveToPreferences(string, R.string.pref_hardware_config_filename);
            this.b = string;
        }
        this.a.updateHeader("No current file!", R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);
    }

    public void saveDeviceInterface(View v) {
        this.b();
    }

    private void b() {
        Intent intent = new Intent();
        this.d.setName(this.e.getText().toString());
        intent.putExtra("EDIT_DEVICE_INTERFACE_MODULE_CONFIG", (Serializable)this.d);
        this.setResult(-1, intent);
        this.finish();
    }

    public void cancelDeviceInterface(View v) {
        this.setResult(0, new Intent());
        this.finish();
    }

    private class a
    implements TextWatcher {
        private a() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String string = editable.toString();
            EditDeviceInterfaceModuleActivity.this.d.setName(string);
        }
    }

}

