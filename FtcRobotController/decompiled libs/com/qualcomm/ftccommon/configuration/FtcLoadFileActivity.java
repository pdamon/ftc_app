/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  com.qualcomm.ftccommon.R
 *  com.qualcomm.ftccommon.R$id
 *  com.qualcomm.ftccommon.R$layout
 *  com.qualcomm.ftccommon.R$string
 *  com.qualcomm.robotcore.hardware.configuration.Utility
 */
package com.qualcomm.ftccommon.configuration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftccommon.R;
import com.qualcomm.ftccommon.configuration.AutoConfigureActivity;
import com.qualcomm.ftccommon.configuration.FtcConfigurationActivity;
import com.qualcomm.robotcore.hardware.configuration.Utility;
import java.io.File;
import java.util.ArrayList;

public class FtcLoadFileActivity
extends Activity {
    private ArrayList<String> b = new ArrayList();
    private Context c;
    private Utility d;
    DialogInterface.OnClickListener a;
    public static final String CONFIGURE_FILENAME = "CONFIGURE_FILENAME";

    public FtcLoadFileActivity() {
        this.a = new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int button) {
            }
        };
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_load);
        this.c = this;
        this.d = new Utility((Activity)this);
        this.d.createConfigFolder();
        this.a();
    }

    protected void onStart() {
        super.onStart();
        this.b = this.d.getXMLFiles();
        this.b();
        this.d.updateHeader("No current file!", R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);
        this.c();
    }

    private void a() {
        Button button = (Button)this.findViewById(R.id.files_holder).findViewById(R.id.info_btn);
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AlertDialog.Builder builder = FtcLoadFileActivity.this.d.buildBuilder("Available files", "These are the files the Hardware Wizard was able to find. You can edit each file by clicking the edit button. The 'Activate' button will set that file as the current configuration file, which will be used to start the robot.");
                builder.setPositiveButton((CharSequence)"Ok", FtcLoadFileActivity.this.a);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                TextView textView = (TextView)alertDialog.findViewById(16908299);
                textView.setTextSize(14.0f);
            }
        });
        Button button2 = (Button)this.findViewById(R.id.autoconfigure_holder).findViewById(R.id.info_btn);
        button2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AlertDialog.Builder builder = FtcLoadFileActivity.this.d.buildBuilder("AutoConfigure", "This is the fastest way to get a new machine up and running. The AutoConfigure tool will automatically enter some default names for devices. AutoConfigure expects certain devices.  If there are other devices attached, the AutoConfigure tool will not name them.");
                builder.setPositiveButton((CharSequence)"Ok", FtcLoadFileActivity.this.a);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                TextView textView = (TextView)alertDialog.findViewById(16908299);
                textView.setTextSize(14.0f);
            }
        });
    }

    private void b() {
        if (this.b.size() == 0) {
            String string = "No files found!";
            String string2 = "In order to proceed, you must create a new file";
            this.d.setOrangeText(string, string2, R.id.empty_filelist, R.layout.orange_warning, R.id.orangetext0, R.id.orangetext1);
        } else {
            ViewGroup viewGroup = (ViewGroup)this.findViewById(R.id.empty_filelist);
            viewGroup.removeAllViews();
            viewGroup.setVisibility(8);
        }
    }

    private void c() {
        ViewGroup viewGroup = (ViewGroup)this.findViewById(R.id.inclusionlayout);
        viewGroup.removeAllViews();
        for (String string : this.b) {
            View view = LayoutInflater.from((Context)this).inflate(R.layout.file_info, null);
            viewGroup.addView(view);
            TextView textView = (TextView)view.findViewById(R.id.filename_editText);
            textView.setText((CharSequence)string);
        }
    }

    public void new_button(View v) {
        this.d.saveToPreferences("No current file!", R.string.pref_hardware_config_filename);
        Intent intent = new Intent(this.c, (Class)FtcConfigurationActivity.class);
        this.startActivity(intent);
    }

    public void file_edit_button(View v) {
        String string = this.a(v, true);
        this.d.saveToPreferences(string, R.string.pref_hardware_config_filename);
        Intent intent = new Intent(this.c, (Class)FtcConfigurationActivity.class);
        this.startActivity(intent);
    }

    public void file_activate_button(View v) {
        String string = this.a(v, false);
        this.d.saveToPreferences(string, R.string.pref_hardware_config_filename);
        this.d.updateHeader("No current file!", R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);
    }

    public void file_delete_button(View v) {
        String string = this.a(v, true);
        File file = new File(Utility.CONFIG_FILES_DIR + string);
        if (file.exists()) {
            file.delete();
        } else {
            this.d.complainToast("That file does not exist: " + string, this.c);
            DbgLog.error("Tried to delete a file that does not exist: " + string);
        }
        this.b = this.d.getXMLFiles();
        this.d.saveToPreferences("No current file!", R.string.pref_hardware_config_filename);
        this.d.updateHeader("No current file!", R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);
        this.c();
    }

    private String a(View view, boolean bl) {
        LinearLayout linearLayout = (LinearLayout)view.getParent();
        LinearLayout linearLayout2 = (LinearLayout)linearLayout.getParent();
        TextView textView = (TextView)linearLayout2.findViewById(R.id.filename_editText);
        String string = textView.getText().toString();
        if (bl) {
            string = string + ".xml";
        }
        return string;
    }

    public void launch_autoConfigure(View v) {
        this.startActivity(new Intent(this.getBaseContext(), (Class)AutoConfigureActivity.class));
    }

    public void onBackPressed() {
        String string = this.d.getFilenameFromPrefs(R.string.pref_hardware_config_filename, "No current file!");
        Intent intent = new Intent();
        intent.putExtra("CONFIGURE_FILENAME", string);
        this.setResult(-1, intent);
        this.finish();
    }

}

