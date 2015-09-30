/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Handler
 */
package com.qualcomm.robotcore.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import com.qualcomm.robotcore.util.RobotLog;

public class BatteryChecker {
    private Context b;
    private long c;
    private long d = 5000;
    private BatteryWatcher e;
    protected Handler batteryHandler;
    Runnable a;

    public BatteryChecker(Context context, BatteryWatcher watcher, long delay) {
        this.a = new Runnable(){

            @Override
            public void run() {
                float f = BatteryChecker.this.getBatteryLevel();
                BatteryChecker.this.e.updateBatteryLevel(f);
                RobotLog.i("Battery Checker, Level Remaining: " + f);
                BatteryChecker.this.batteryHandler.postDelayed(BatteryChecker.this.a, BatteryChecker.this.c);
            }
        };
        this.b = context;
        this.e = watcher;
        this.c = delay;
        this.batteryHandler = new Handler();
    }

    public float getBatteryLevel() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        Intent intent = this.b.registerReceiver(null, intentFilter);
        int n = intent.getIntExtra("level", -1);
        int n2 = intent.getIntExtra("scale", -1);
        int n3 = -1;
        if (n >= 0 && n2 > 0) {
            n3 = n * 100 / n2;
        }
        return n3;
    }

    public void startBatteryMonitoring() {
        this.batteryHandler.postDelayed(this.a, this.d);
    }

    public void endBatteryMonitoring() {
        this.batteryHandler.removeCallbacks(this.a);
    }

    public static interface BatteryWatcher {
        public void updateBatteryLevel(float var1);
    }

}

