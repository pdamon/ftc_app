/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.os.Environment
 *  android.util.Log
 */
package com.qualcomm.robotcore.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ExtractAssets {
    private static final String a = ExtractAssets.class.getSimpleName();

    public static ArrayList<String> ExtractToStorage(Context context, ArrayList<String> files, boolean useInternalStorage) throws IOException {
        Object object;
        if (!(useInternalStorage || "mounted".equals(object = Environment.getExternalStorageState()))) {
            throw new IOException("External Storage not accessible");
        }
        object = new ArrayList();
        for (String string : files) {
            ExtractAssets.a(context, string, useInternalStorage, object);
            if (object == null) continue;
            Log.d((String)a, (String)("got " + object.size() + " elements"));
        }
        return object;
    }

    private static ArrayList<String> a(Context context, String string, boolean bl, ArrayList<String> arrayList) {
        Log.d((String)a, (String)("Extracting assests for " + string));
        String[] arrstring = null;
        AssetManager assetManager = context.getAssets();
        try {
            arrstring = assetManager.list(string);
        }
        catch (IOException var6_6) {
            var6_6.printStackTrace();
        }
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        if (arrstring.length == 0) {
            try {
                File file;
                inputStream = assetManager.open(string);
                Log.d((String)a, (String)("File: " + string + " opened for streaming"));
                if (!string.startsWith(File.separator)) {
                    string = File.separator + string;
                }
                File file2 = null;
                file2 = bl ? context.getFilesDir() : context.getExternalFilesDir(null);
                String string2 = file2.getPath();
                String string3 = string2.concat(string);
                if (arrayList != null && arrayList.contains(string3)) {
                    Log.e((String)a, (String)("Ignoring Duplicate entry for " + string3));
                    ArrayList<String> arrayList2 = arrayList;
                    return arrayList2;
                }
                int n = string3.lastIndexOf(File.separatorChar);
                String string4 = string3.substring(0, n);
                String string5 = string3.substring(n, string3.length());
                File file3 = new File(string4);
                if (file3.mkdirs()) {
                    Log.d((String)a, (String)("Dir created " + string4));
                }
                if ((fileOutputStream = new FileOutputStream(file = new File(file3, string5))) != null) {
                    byte[] arrby = new byte[1024];
                    int n2 = 0;
                    while ((n2 = inputStream.read(arrby)) != -1) {
                        fileOutputStream.write(arrby, 0, n2);
                    }
                }
                fileOutputStream.close();
                if (arrayList != null) {
                    arrayList.add(string3);
                }
            }
            catch (IOException var8_12) {
                Log.d((String)a, (String)("File: " + string + " doesn't exist"));
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    }
                    catch (IOException var8_13) {
                        Log.d((String)a, (String)"Unable to close in stream");
                        var8_13.printStackTrace();
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        }
                        catch (IOException var8_15) {
                            Log.d((String)a, (String)"Unable to close out stream");
                            var8_15.printStackTrace();
                        }
                    }
                }
            }
            return arrayList;
        }
        String string6 = string;
        if (!(string.equals("") || string.endsWith(File.separator))) {
            string6 = string6.concat(File.separator);
        }
        for (String string7 : arrstring) {
            String string8 = string6.concat(string7);
            ExtractAssets.a(context, string8, bl, arrayList);
        }
        return arrayList;
    }
}

