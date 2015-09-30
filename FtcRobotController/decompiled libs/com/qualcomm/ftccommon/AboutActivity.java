/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ArrayAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  com.qualcomm.ftccommon.R
 *  com.qualcomm.ftccommon.R$id
 *  com.qualcomm.ftccommon.R$layout
 *  com.qualcomm.robotcore.util.Version
 */
package com.qualcomm.ftccommon;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.qualcomm.ftccommon.R;
import com.qualcomm.robotcore.util.Version;

public class AboutActivity
extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.about);
        ListView listView = (ListView)this.findViewById(R.id.aboutList);
        ArrayAdapter<String[]> arrayAdapter = new ArrayAdapter<String[]>((Context)this, 17367044, 16908308){

            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView)view.findViewById(16908308);
                TextView textView2 = (TextView)view.findViewById(16908309);
                String[] arrstring = this.a(position);
                if (arrstring.length == 2) {
                    textView.setText((CharSequence)arrstring[0]);
                    textView2.setText((CharSequence)arrstring[1]);
                }
                return view;
            }

            public int getCount() {
                return 2;
            }

            public String[] a(int n) {
                String[] arrstring = new String[2];
                if (n == 0) {
                    try {
                        arrstring[0] = "App Version";
                        arrstring[1] = AboutActivity.this.getPackageManager().getPackageInfo((String)AboutActivity.this.getPackageName(), (int)0).versionName;
                    }
                    catch (PackageManager.NameNotFoundException var3_3) {}
                } else if (n == 1) {
                    arrstring[0] = "Library Version";
                    arrstring[1] = Version.getLibraryVersion();
                }
                return arrstring;
            }

            public /* synthetic */ Object getItem(int n) {
                return this.a(n);
            }
        };
        listView.setAdapter((ListAdapter)arrayAdapter);
    }

}

