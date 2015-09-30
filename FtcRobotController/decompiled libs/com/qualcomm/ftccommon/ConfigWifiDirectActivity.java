/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.net.wifi.WifiManager
 *  android.os.Bundle
 *  com.qualcomm.ftccommon.R
 *  com.qualcomm.ftccommon.R$layout
 *  com.qualcomm.ftccommon.R$style
 *  com.qualcomm.robotcore.wifi.FixWifiDirectSetup
 */
package com.qualcomm.ftccommon;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftccommon.R;
import com.qualcomm.robotcore.wifi.FixWifiDirectSetup;

public class ConfigWifiDirectActivity
extends Activity {
    private ProgressDialog a;
    private Context b;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_config_wifi_direct);
        this.b = this;
    }

    protected void onResume() {
        super.onResume();
        new Thread(new a()).start();
    }

    private class a
    implements Runnable {
        private a() {
        }

        @Override
        public void run() {
            DbgLog.msg("attempting to reconfigure Wifi Direct");
            ConfigWifiDirectActivity.this.runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    ConfigWifiDirectActivity.this.a = new ProgressDialog(ConfigWifiDirectActivity.this.b, R.style.CustomAlertDialog);
                    ConfigWifiDirectActivity.this.a.setMessage((CharSequence)"Please wait");
                    ConfigWifiDirectActivity.this.a.setTitle((CharSequence)"Configuring Wifi Direct");
                    ConfigWifiDirectActivity.this.a.setIndeterminate(true);
                    ConfigWifiDirectActivity.this.a.show();
                }
            });
            WifiManager wifiManager = (WifiManager)ConfigWifiDirectActivity.this.getSystemService("wifi");
            try {
                FixWifiDirectSetup.fixWifiDirectSetup((WifiManager)wifiManager);
            }
            catch (InterruptedException var2_2) {
                DbgLog.msg("Cannot fix wifi setup - interrupted");
            }
            ConfigWifiDirectActivity.this.runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    ConfigWifiDirectActivity.this.a.dismiss();
                    ConfigWifiDirectActivity.this.finish();
                }
            });
        }

    }

}

