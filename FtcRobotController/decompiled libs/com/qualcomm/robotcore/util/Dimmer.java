/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Handler
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 */
package com.qualcomm.robotcore.util;

import android.app.Activity;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class Dimmer {
    public static final int DEFAULT_DIM_TIME = 30000;
    public static final int LONG_BRIGHT_TIME = 60000;
    public static final float MAXIMUM_BRIGHTNESS = 1.0f;
    public static final float MINIMUM_BRIGHTNESS = 0.05f;
    Handler a = new Handler();
    Activity b;
    final WindowManager.LayoutParams c;
    long d;
    float e = 1.0f;

    public Dimmer(Activity activity) {
        this(30000, activity);
    }

    public Dimmer(long waitTime, Activity activity) {
        this.d = waitTime;
        this.b = activity;
        this.c = activity.getWindow().getAttributes();
        this.e = this.c.screenBrightness;
    }

    private float a() {
        float f = 0.05f * this.e;
        if (f < 0.05f) {
            return 0.05f;
        }
        return f;
    }

    public void handleDimTimer() {
        this.a(this.e);
        this.a.removeCallbacks(null);
        this.a.postDelayed(new Runnable(){

            @Override
            public void run() {
                Dimmer.this.a(Dimmer.this.a());
            }
        }, this.d);
    }

    private void a(float f) {
        this.c.screenBrightness = f;
        this.b.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                Dimmer.this.b.getWindow().setAttributes(Dimmer.this.c);
            }
        });
    }

    public void longBright() {
        this.a(this.e);
        Runnable runnable = new Runnable(){

            @Override
            public void run() {
                Dimmer.this.a(Dimmer.this.a());
            }
        };
        this.a.removeCallbacksAndMessages((Object)null);
        this.a.postDelayed(runnable, 60000);
    }

}

