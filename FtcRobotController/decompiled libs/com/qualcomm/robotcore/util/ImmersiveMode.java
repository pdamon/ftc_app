/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Message
 *  android.view.View
 */
package com.qualcomm.robotcore.util;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class ImmersiveMode {
    View a;
    Handler b;

    public ImmersiveMode(View decorView) {
        this.b = new Handler(){

            public void handleMessage(Message msg) {
                ImmersiveMode.this.hideSystemUI();
            }
        };
        this.a = decorView;
    }

    public void cancelSystemUIHide() {
        this.b.removeMessages(0);
    }

    public void hideSystemUI() {
        this.a.setSystemUiVisibility(4098);
    }

    public static boolean apiOver19() {
        int n = Build.VERSION.SDK_INT;
        return n >= 19;
    }

}

