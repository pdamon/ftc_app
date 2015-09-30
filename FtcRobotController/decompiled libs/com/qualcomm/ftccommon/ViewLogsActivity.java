/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Environment
 *  android.text.Spannable
 *  android.text.SpannableString
 *  android.text.style.ForegroundColorSpan
 *  android.view.View
 *  android.widget.ScrollView
 *  android.widget.TextView
 *  com.qualcomm.ftccommon.R
 *  com.qualcomm.ftccommon.R$id
 *  com.qualcomm.ftccommon.R$layout
 *  com.qualcomm.robotcore.util.RobotLog
 */
package com.qualcomm.ftccommon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import com.qualcomm.ftccommon.R;
import com.qualcomm.robotcore.util.RobotLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;

public class ViewLogsActivity
extends Activity {
    TextView a;
    int b = 300;
    public static final String FILENAME = "Filename";
    String c = " ";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_view_logs);
        this.a = (TextView)this.findViewById(R.id.textAdbLogs);
        final ScrollView scrollView = (ScrollView)this.findViewById(R.id.scrollView);
        scrollView.post(new Runnable(){

            @Override
            public void run() {
                scrollView.fullScroll(130);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        Intent intent = this.getIntent();
        Serializable serializable = intent.getSerializableExtra("Filename");
        if (serializable != null) {
            this.c = (String)serializable;
        }
        this.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                try {
                    String string = ViewLogsActivity.this.readNLines(ViewLogsActivity.this.b);
                    Spannable spannable = ViewLogsActivity.this.a(string);
                    ViewLogsActivity.this.a.setText((CharSequence)spannable);
                }
                catch (IOException var1_2) {
                    RobotLog.e((String)var1_2.toString());
                    ViewLogsActivity.this.a.setText((CharSequence)("File not found: " + ViewLogsActivity.this.c));
                }
            }
        });
    }

    public String readNLines(int n) throws IOException {
        int n2;
        File file = Environment.getExternalStorageDirectory();
        File file2 = new File(this.c);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
        String[] arrstring = new String[n];
        int n3 = 0;
        String string = null;
        while ((string = bufferedReader.readLine()) != null) {
            arrstring[n3 % arrstring.length] = string;
            ++n3;
        }
        int n4 = n3 - n;
        if (n4 < 0) {
            n4 = 0;
        }
        String string2 = "";
        for (n2 = n4; n2 < n3; ++n2) {
            int n5 = n2 % arrstring.length;
            String string3 = arrstring[n5];
            string2 = string2 + string3 + "\n";
        }
        n2 = string2.lastIndexOf("--------- beginning");
        if (n2 < 0) {
            return string2;
        }
        return string2.substring(n2);
    }

    private Spannable a(String string) {
        SpannableString spannableString = new SpannableString((CharSequence)string);
        String[] arrstring = string.split("\\n");
        int n = 0;
        for (String string2 : arrstring) {
            if (string2.contains((CharSequence)"E/RobotCore") || string2.contains((CharSequence)"### ERROR: ")) {
                spannableString.setSpan((Object)new ForegroundColorSpan(-65536), n, n + string2.length(), 33);
            }
            n+=string2.length();
            ++n;
        }
        return spannableString;
    }

}

