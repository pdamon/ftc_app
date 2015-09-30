/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.NetworkInfo
 *  android.os.Parcelable
 */
package com.qualcomm.robotcore.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.os.Parcelable;
import com.qualcomm.robotcore.util.RobotLog;

public class WifiAssistant {
    private final IntentFilter a;
    private final Context b;
    private final a c;

    public WifiAssistant(Context context, WifiAssistantCallback callback) {
        this.b = context;
        if (callback == null) {
            RobotLog.v("WifiAssistantCallback is null");
        }
        this.c = new a(callback);
        this.a = new IntentFilter();
        this.a.addAction("android.net.wifi.STATE_CHANGE");
    }

    public void enable() {
        this.b.registerReceiver((BroadcastReceiver)this.c, this.a);
    }

    public void disable() {
        this.b.unregisterReceiver((BroadcastReceiver)this.c);
    }

    private static class a
    extends BroadcastReceiver {
        private WifiState a = null;
        private final WifiAssistantCallback b;

        public a(WifiAssistantCallback wifiAssistantCallback) {
            this.b = wifiAssistantCallback;
        }

        public void onReceive(Context context, Intent intent) {
            String string = intent.getAction();
            if (string.equals("android.net.wifi.STATE_CHANGE")) {
                NetworkInfo networkInfo = (NetworkInfo)intent.getParcelableExtra("networkInfo");
                if (networkInfo.isConnected()) {
                    this.a(WifiState.CONNECTED);
                } else {
                    this.a(WifiState.NOT_CONNECTED);
                }
            }
        }

        private void a(WifiState wifiState) {
            if (this.a == wifiState) {
                return;
            }
            this.a = wifiState;
            if (this.b != null) {
                this.b.wifiEventCallback(this.a);
            }
        }
    }

    public static interface WifiAssistantCallback {
        public void wifiEventCallback(WifiState var1);
    }

    public static enum WifiState {
        CONNECTED,
        NOT_CONNECTED;
        

        private WifiState() {
        }
    }

}

