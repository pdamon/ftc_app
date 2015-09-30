/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.Intent
 *  android.net.wifi.WifiManager
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.Toast
 *  com.qualcomm.ftccommon.R
 *  com.qualcomm.ftccommon.R$array
 *  com.qualcomm.ftccommon.R$id
 *  com.qualcomm.ftccommon.R$layout
 *  com.qualcomm.wirelessp2p.WifiDirectChannelSelection
 */
package com.qualcomm.ftccommon;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftccommon.R;
import com.qualcomm.wirelessp2p.WifiDirectChannelSelection;
import java.io.IOException;

public class FtcWifiChannelSelectorActivity
extends Activity
implements View.OnClickListener,
AdapterView.OnItemSelectedListener {
    private static int a = 0;
    private Button b;
    private Button c;
    private Spinner d;
    private ProgressDialog e;
    private WifiDirectChannelSelection f;
    private int g = -1;
    private int h = -1;
    private Context i;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_ftc_wifi_channel_selector);
        this.i = this;
        this.d = (Spinner)this.findViewById(R.id.spinnerChannelSelect);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource((Context)this, (int)R.array.wifi_direct_channels, (int)17367048);
        arrayAdapter.setDropDownViewResource(17367049);
        this.d.setAdapter((SpinnerAdapter)arrayAdapter);
        this.d.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)this);
        this.b = (Button)this.findViewById(R.id.buttonConfigure);
        this.b.setOnClickListener((View.OnClickListener)this);
        this.c = (Button)this.findViewById(R.id.buttonWifiSettings);
        this.c.setOnClickListener((View.OnClickListener)this);
        WifiManager wifiManager = (WifiManager)this.getSystemService("wifi");
        this.f = new WifiDirectChannelSelection((Context)this, wifiManager);
    }

    protected void onStart() {
        super.onStart();
        this.d.setSelection(a);
    }

    public void onItemSelected(AdapterView<?> av, View v, int item, long l) {
        switch (item) {
            case 0: {
                this.g = -1;
                this.h = -1;
                break;
            }
            case 1: {
                this.g = 81;
                this.h = 1;
                break;
            }
            case 2: {
                this.g = 81;
                this.h = 6;
                break;
            }
            case 3: {
                this.g = 81;
                this.h = 11;
                break;
            }
            case 4: {
                this.g = 124;
                this.h = 149;
                break;
            }
            case 5: {
                this.g = 124;
                this.h = 153;
                break;
            }
            case 6: {
                this.g = 124;
                this.h = 157;
                break;
            }
            case 7: {
                this.g = 124;
                this.h = 161;
            }
        }
    }

    public void onNothingSelected(AdapterView<?> av) {
    }

    public void onClick(View v) {
        if (v.getId() == R.id.buttonConfigure) {
            a = this.d.getSelectedItemPosition();
            this.a();
        } else if (v.getId() == R.id.buttonWifiSettings) {
            DbgLog.msg("launch wifi settings");
            this.startActivity(new Intent("android.net.wifi.PICK_WIFI_NETWORK"));
        }
    }

    private void a() {
        DbgLog.msg(String.format("configure p2p channel - class %d channel %d", this.g, this.h));
        try {
            this.e = ProgressDialog.show((Context)this, (CharSequence)"Configuring Channel", (CharSequence)"Please Wait", (boolean)true);
            this.f.config(this.g, this.h);
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException var1_1) {
                        // empty catch block
                    }
                    FtcWifiChannelSelectorActivity.this.runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            FtcWifiChannelSelectorActivity.this.setResult(-1);
                            FtcWifiChannelSelectorActivity.this.e.dismiss();
                            FtcWifiChannelSelectorActivity.this.finish();
                        }
                    });
                }

            }).start();
        }
        catch (IOException var1_1) {
            this.a("Failed - root is required", 0);
            var1_1.printStackTrace();
        }
    }

    private void a(final String string, final int n) {
        Runnable runnable = new Runnable(){

            @Override
            public void run() {
                Toast.makeText((Context)FtcWifiChannelSelectorActivity.this.i, (CharSequence)string, (int)n).show();
            }
        };
        this.runOnUiThread(runnable);
    }

}

