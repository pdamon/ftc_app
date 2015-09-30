/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.os.Bundle
 */
package com.qualcomm.robotcore.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class GenericDialogFragment
extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String string = "App error condition!";
        String string2 = this.getArguments().getString("dialogMsg", "App error condition!");
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this.getActivity());
        builder.setMessage((CharSequence)string2).setPositiveButton((CharSequence)"OK", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int id) {
            }
        });
        return builder.create();
    }

}

