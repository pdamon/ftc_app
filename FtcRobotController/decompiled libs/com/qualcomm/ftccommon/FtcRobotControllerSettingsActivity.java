/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.os.Build
 *  android.os.Bundle
 *  android.preference.Preference
 *  android.preference.Preference$OnPreferenceClickListener
 *  android.preference.PreferenceFragment
 *  android.widget.Toast
 *  com.qualcomm.ftccommon.R
 *  com.qualcomm.ftccommon.R$string
 *  com.qualcomm.ftccommon.R$xml
 */
package com.qualcomm.ftccommon;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;
import com.qualcomm.ftccommon.R;

public class FtcRobotControllerSettingsActivity
extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getFragmentManager().beginTransaction().replace(16908290, (Fragment)new SettingsFragment()).commit();
    }

    public static class SettingsFragment
    extends PreferenceFragment {
        Preference.OnPreferenceClickListener a;

        public SettingsFragment() {
            this.a = new Preference.OnPreferenceClickListener(){

                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(preference.getIntent().getAction());
                    SettingsFragment.this.startActivityForResult(intent, 3);
                    return true;
                }
            };
        }

        public void onCreate(Bundle savedInstanceState) {
            Preference preference;
            super.onCreate(savedInstanceState);
            this.addPreferencesFromResource(R.xml.preferences);
            Preference preference2 = this.findPreference((CharSequence)this.getString(R.string.pref_launch_configure));
            preference2.setOnPreferenceClickListener(this.a);
            Preference preference3 = this.findPreference((CharSequence)this.getString(R.string.pref_launch_autoconfigure));
            preference3.setOnPreferenceClickListener(this.a);
            if (Build.MANUFACTURER.equalsIgnoreCase("zte") && Build.MODEL.equalsIgnoreCase("N9130")) {
                preference = this.findPreference((CharSequence)this.getString(R.string.pref_launch_settings));
                preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                    public boolean onPreferenceClick(Preference preference) {
                        Intent intent = SettingsFragment.this.getActivity().getPackageManager().getLaunchIntentForPackage("com.zte.wifichanneleditor");
                        try {
                            SettingsFragment.this.startActivity(intent);
                        }
                        catch (NullPointerException var3_3) {
                            Toast.makeText((Context)SettingsFragment.this.getActivity(), (CharSequence)"Unable to launch ZTE WifiChannelEditor", (int)0).show();
                        }
                        return true;
                    }
                });
            } else {
                preference = this.findPreference((CharSequence)this.getString(R.string.pref_launch_settings));
                preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                    public boolean onPreferenceClick(Preference preference) {
                        Intent intent = new Intent(preference.getIntent().getAction());
                        SettingsFragment.this.startActivity(intent);
                        return true;
                    }
                });
            }
            if (Build.MODEL.equals("FL7007")) {
                preference = this.findPreference((CharSequence)this.getString(R.string.pref_launch_settings));
                preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                    public boolean onPreferenceClick(Preference preference) {
                        Intent intent = new Intent("android.settings.SETTINGS");
                        SettingsFragment.this.startActivity(intent);
                        return true;
                    }
                });
            }
        }

        public void onActivityResult(int request, int result, Intent intent) {
            if (request == 3 && result == -1) {
                this.getActivity().setResult(-1, intent);
            }
        }

    }

}

