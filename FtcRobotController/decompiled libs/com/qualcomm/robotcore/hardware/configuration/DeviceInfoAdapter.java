/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.ListAdapter
 *  android.widget.TextView
 */
package com.qualcomm.robotcore.hardware.configuration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration;
import com.qualcomm.robotcore.util.SerialNumber;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DeviceInfoAdapter
extends BaseAdapter
implements ListAdapter {
    private Map<SerialNumber, ControllerConfiguration> a = new HashMap<SerialNumber, ControllerConfiguration>();
    private SerialNumber[] b;
    private Context c;
    private int d;
    private int e;

    public DeviceInfoAdapter(Activity context, int list_id, Map<SerialNumber, ControllerConfiguration> deviceControllers) {
        this.c = context;
        this.a = deviceControllers;
        this.b = deviceControllers.keySet().toArray(new SerialNumber[deviceControllers.size()]);
        this.d = list_id;
    }

    public int getCount() {
        return this.a.size();
    }

    public Object getItem(int arg0) {
        return this.a.get(this.b[arg0]);
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        Object object;
        View view = convertView;
        if (view == null) {
            object = ((Activity)this.c).getLayoutInflater();
            view = object.inflate(this.d, parent, false);
        }
        object = this.b[pos].toString();
        TextView textView = (TextView)view.findViewById(16908309);
        textView.setText((CharSequence)object);
        String string = this.a.get(this.b[pos]).getName();
        TextView textView2 = (TextView)view.findViewById(16908308);
        textView2.setText((CharSequence)string);
        return view;
    }
}

