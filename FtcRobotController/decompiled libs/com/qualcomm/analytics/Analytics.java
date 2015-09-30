/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.NetworkInfo$State
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Bundle
 *  android.os.Environment
 *  android.preference.PreferenceManager
 *  com.qualcomm.robotcore.util.RobotLog
 *  com.qualcomm.robotcore.util.Version
 */
package com.qualcomm.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.Version;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Analytics
extends BroadcastReceiver {
    public static final String UUID_PATH = ".analytics_id";
    public static final String DATA_COLLECTION_PATH = ".ftcdc";
    static String a = "https://ftcdc.qualcomm.com/DataApi";
    public static final String RC_COMMAND_STRING = "update_rc";
    public static final String DS_COMMAND_STRING = "update_ds";
    public static final String EXTERNAL_STORAGE_DIRECTORY_PATH = Environment.getExternalStorageDirectory() + "/";
    public static final String LAST_UPLOAD_DATE = "last_upload_date";
    public static int MAX_ENTRIES_SIZE = 100;
    public static int TRIMMED_SIZE = 90;
    private static final Charset l = Charset.forName("UTF-8");
    static long b;
    static UUID c;
    static String d;
    String e;
    static String f;
    Context g;
    SharedPreferences h;
    boolean i;
    long j = 0;
    static final HostnameVerifier k;

    public void onReceive(Context context, Intent intent) {
        NetworkInfo networkInfo;
        NetworkInfo.State state;
        Bundle bundle = intent.getExtras();
        if (bundle != null && bundle.containsKey("networkInfo") && (state = (networkInfo = (NetworkInfo)bundle.get("networkInfo")).getState()).equals((Object)NetworkInfo.State.CONNECTED)) {
            RobotLog.i((String)"Analytics detected NetworkInfo.State.CONNECTED");
            this.communicateWithServer();
        }
    }

    private static String b(String string, int n) {
        int n2 = Analytics.findNthIndex(string, n, ']');
        return string.substring(n2 + 1);
    }

    public static int findNthIndex(String entries, int n, char c) {
        int n2 = entries.indexOf(c, 0);
        while (n > 0 && n2 != -1) {
            n2 = entries.indexOf(c, n2 + 1);
            --n;
        }
        return n2;
    }

    public void unregister() {
        this.g.unregisterReceiver((BroadcastReceiver)this);
    }

    public void register() {
        this.g.registerReceiver((BroadcastReceiver)this, new IntentFilter("android.net.wifi.STATE_CHANGE"));
    }

    public Analytics(Context context, String commandString) {
        this.g = context;
        this.e = commandString;
        try {
            try {
                this.h = PreferenceManager.getDefaultSharedPreferences((Context)context);
                b = System.currentTimeMillis();
                f = Version.getLibraryVersion();
                this.handleUUID(".analytics_id");
                Analytics.setApplicationName(context.getApplicationInfo().loadLabel(context.getPackageManager()).toString());
                String string = EXTERNAL_STORAGE_DIRECTORY_PATH + ".ftcdc";
                String string2 = this.incrementAndSetCount(string, Analytics.getDateFromTime(b));
                this.handleCreateNewFile(string, string2);
                context.registerReceiver((BroadcastReceiver)this, new IntentFilter("android.net.wifi.STATE_CHANGE"));
            }
            catch (Exception var3_4) {
                this.i = true;
            }
            if (this.i) {
                this.a();
            }
        }
        catch (Exception var3_5) {
            RobotLog.i((String)"Analytics encountered a problem");
            RobotLog.logStacktrace((Exception)var3_5);
        }
    }

    private void a() {
        SharedPreferences.Editor editor = this.h.edit();
        editor.putLong("last_upload_date", this.j);
        editor.apply();
        File file = new File(EXTERNAL_STORAGE_DIRECTORY_PATH + ".ftcdc");
        file.delete();
        File file2 = new File(EXTERNAL_STORAGE_DIRECTORY_PATH + ".analytics_id");
        file2.delete();
        this.i = false;
    }

    public void communicateWithServer() {
        String[] arrstring = new String[]{a};
        new a().execute((Object[])arrstring);
    }

    public static void setApplicationName(String name) {
        d = name;
    }

    public void handleUUID(String filename) {
        File file = new File(EXTERNAL_STORAGE_DIRECTORY_PATH + filename);
        if (!file.exists()) {
            c = UUID.randomUUID();
            this.handleCreateNewFile(EXTERNAL_STORAGE_DIRECTORY_PATH + filename, c.toString());
        }
        String string = this.readFromFile(file);
        try {
            c = UUID.fromString(string);
        }
        catch (IllegalArgumentException var4_4) {
            RobotLog.i((String)"Analytics encountered an IllegalArgumentException");
            c = UUID.randomUUID();
            this.handleCreateNewFile(EXTERNAL_STORAGE_DIRECTORY_PATH + filename, c.toString());
        }
    }

    protected String readFromFile(File file) {
        try {
            char[] arrc = new char[4096];
            FileReader fileReader = new FileReader(file);
            int n = fileReader.read(arrc);
            fileReader.close();
            String string = new String(arrc, 0, n);
            return string.trim();
        }
        catch (FileNotFoundException var2_3) {
            RobotLog.i((String)"Analytics encountered a FileNotFoundException while trying to read a file.");
        }
        catch (IOException var2_4) {
            RobotLog.i((String)"Analytics encountered an IOException while trying to read.");
        }
        return "";
    }

    protected void handleCreateNewFile(String filepath, String data) {
        Writer writer = null;
        try {
            File file = new File(filepath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            writer = new BufferedWriter(new OutputStreamWriter((OutputStream)fileOutputStream, "utf-8"));
            writer.write(data);
        }
        catch (IOException var4_6) {
            RobotLog.i((String)("Analytics encountered an IOException: " + var4_6.toString()));
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException var4_7) {}
            }
        }
    }

    public static String getDateFromTime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String string = simpleDateFormat.format(new Date(time));
        return string;
    }

    public String incrementAndSetCount(String filepath, String date) {
        int n;
        String string = "";
        File file = new File(filepath);
        if (!file.exists()) {
            string = "[" + date + " 1] ";
            return string;
        }
        String string2 = this.readFromFile(file);
        int n2 = Analytics.findNthIndex(string2, MAX_ENTRIES_SIZE, ']');
        if (n2 > 0) {
            string2 = Analytics.b(string2, MAX_ENTRIES_SIZE - TRIMMED_SIZE);
        }
        if ((n = string2.lastIndexOf("[")) < 0) {
            string = "[" + date + " 1] ";
            return string;
        }
        DateCount dateCount = Analytics.c(string2, n);
        if (Analytics.getDateFromTime(b).equals(dateCount.date())) {
            int n3 = Integer.parseInt(dateCount.count());
            String string3 = string2.substring(0, n);
            string = string3.trim() + " [" + dateCount.date() + " " + Integer.toString(++n3) + "] ";
        } else {
            string = string2 + " [" + date + " " + "1] ";
        }
        return string;
    }

    private static DateCount c(String string, int n) {
        String string2 = string.substring(n);
        string2 = string2.replace((CharSequence)"[", (CharSequence)"");
        string2 = string2.replace((CharSequence)"]", (CharSequence)"");
        String[] arrstring = string2.split(" ");
        return new DateCount(arrstring[0].trim(), arrstring[1].trim());
    }

    protected static ArrayList<DateCount> parseDateCountFile(String str) {
        String[] arrstring;
        if (str == null || str.isEmpty()) {
            return null;
        }
        ArrayList<DateCount> arrayList = new ArrayList<DateCount>();
        for (String string : arrstring = str.split("]")) {
            String[] arrstring2 = (string = string.replace((CharSequence)"[", (CharSequence)"").trim()).split(" ");
            if (arrstring2.length != 2) continue;
            arrayList.add(new DateCount(arrstring2[0], arrstring2[1]));
        }
        return arrayList;
    }

    protected static UUID getUuid() {
        return c;
    }

    public String updateStats(String user, ArrayList<DateCount> dateInfo, String commandString) {
        String string = this.a("cmd", "=", commandString) + "&" + this.a("uuid", "=", user) + "&" + this.a("device_hw", "=", Build.MANUFACTURER) + "&" + this.a("device_ver", "=", Build.MODEL) + "&" + this.a("chip_type", "=", this.b()) + "&" + this.a("sw_ver", "=", f) + "&";
        String string2 = "";
        for (int i = 0; i < dateInfo.size(); ++i) {
            if (i > 0) {
                string2 = string2 + ",";
            }
            string2 = string2 + this.a(dateInfo.get(i).date(), ",", dateInfo.get(i).count());
        }
        string = string + this.a("dc", "=", "");
        string = string + string2;
        return string;
    }

    private String a(String string, String string2, String string3) {
        String string4 = "";
        try {
            string4 = URLEncoder.encode(string, l.name()) + string2 + URLEncoder.encode(string3, l.name());
        }
        catch (UnsupportedEncodingException var5_5) {
            RobotLog.i((String)"Analytics caught an UnsupportedEncodingException");
        }
        return string4;
    }

    private String b() {
        String string = "UNKNOWN";
        String string2 = "/proc/cpuinfo";
        String[] arrstring = new String[]{"CPU implementer", "Hardware"};
        HashMap<String, String> hashMap = new HashMap<String, String>();
        try {
            String[] arrstring2;
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String string3 = bufferedReader.readLine();
            while (string3 != null) {
                arrstring2 = (string3 = string3.toLowerCase()).split(":");
                if (arrstring2.length >= 2) {
                    hashMap.put(arrstring2[0].trim(), arrstring2[1].trim());
                }
                string3 = bufferedReader.readLine();
            }
            bufferedReader.close();
            arrstring2 = "";
            for (String string4 : arrstring) {
                arrstring2 = (String)arrstring2 + (String)hashMap.get(string4.toLowerCase()) + " ";
            }
            if ((arrstring2 = arrstring2.trim()).isEmpty()) {
                return string;
            }
            return arrstring2;
        }
        catch (FileNotFoundException var5_6) {
            RobotLog.i((String)"Analytics encountered a FileNotFoundException while looking for CPU info");
        }
        catch (IOException var5_7) {
            RobotLog.i((String)"Analytics encountered an IOException while looking for CPU info");
        }
        return string;
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)this.g.getSystemService("connectivity");
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String ping(URL baseUrl, String data) {
        String string = Analytics.call(baseUrl, data);
        return string;
    }

    public static String call(URL url, String data) {
        String string = null;
        if (url != null && data != null) {
            try {
                String string2;
                Object object;
                long l = System.currentTimeMillis();
                HttpURLConnection httpURLConnection = null;
                if (url.getProtocol().toLowerCase().equals("https")) {
                    Analytics.c();
                    object = (HttpsURLConnection)url.openConnection();
                    object.setHostnameVerifier(k);
                    httpURLConnection = object;
                } else {
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                }
                httpURLConnection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                outputStreamWriter.write(data);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                object = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                string = new String();
                while ((string2 = object.readLine()) != null) {
                    string = string + string2;
                }
                object.close();
                RobotLog.i((String)("Analytics took: " + (System.currentTimeMillis() - l) + "ms"));
            }
            catch (IOException var5_5) {
                RobotLog.i((String)"Analytics Failed to process command.");
            }
        }
        return string;
    }

    private static void c() {
        TrustManager[] arrtrustManager = new TrustManager[]{new X509TrustManager(){

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        }};
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, arrtrustManager, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
        }
        catch (Exception var1_2) {
            var1_2.printStackTrace();
        }
    }

    static {
        c = null;
        f = "";
        k = new HostnameVerifier(){

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    private class a
    extends AsyncTask {
        private a() {
        }

        protected Object doInBackground(Object[] params) {
            if (Analytics.this.isConnected()) {
                try {
                    URL uRL = null;
                    uRL = new URL(Analytics.a);
                    long l = Analytics.this.h.getLong("last_upload_date", Analytics.this.j);
                    if (!Analytics.getDateFromTime(Analytics.b).equals(Analytics.getDateFromTime(l))) {
                        String string;
                        String string2 = Analytics.this.a("cmd", "=", "ping");
                        String string3 = Analytics.ping(uRL, string2);
                        String string4 = "\"rc\": \"OK\"";
                        if (!(string3 != null && string3.contains((CharSequence)string4))) {
                            RobotLog.e((String)"Analytics: Ping failed.");
                            return null;
                        }
                        RobotLog.i((String)"Analytics ping succeeded.");
                        String string5 = Analytics.EXTERNAL_STORAGE_DIRECTORY_PATH + ".ftcdc";
                        File file = new File(string5);
                        if (!file.exists()) {
                            string = Analytics.this.incrementAndSetCount(string5, Analytics.getDateFromTime(Analytics.b));
                            Analytics.this.handleCreateNewFile(string5, string);
                        }
                        string = Analytics.this.readFromFile(file);
                        ArrayList<DateCount> arrayList = Analytics.parseDateCountFile(string);
                        String string6 = Analytics.this.updateStats(Analytics.c.toString(), arrayList, Analytics.this.e);
                        String string7 = Analytics.call(uRL, string6);
                        if (!(string7 != null && string7.contains((CharSequence)string4))) {
                            RobotLog.e((String)"Analytics: Upload failed.");
                            if (arrayList.size() > Analytics.MAX_ENTRIES_SIZE) {
                                String string8 = Analytics.b(string, Analytics.MAX_ENTRIES_SIZE - Analytics.TRIMMED_SIZE);
                                Analytics.this.handleCreateNewFile(string5, string8);
                                RobotLog.i((String)"Analytics trimmed the data file.");
                            }
                            return null;
                        }
                        RobotLog.i((String)"Analytics: Upload succeeded.");
                        SharedPreferences.Editor editor = Analytics.this.h.edit();
                        editor.putLong("last_upload_date", Analytics.b);
                        editor.apply();
                        boolean bl = file.delete();
                    }
                }
                catch (MalformedURLException var2_3) {
                    RobotLog.e((String)"Analytics encountered a malformed URL exception");
                }
            }
            return null;
        }
    }

    public static class DateCount {
        private final String a;
        private final String b;

        public DateCount(String adate, String acount) {
            this.a = adate;
            this.b = acount;
        }

        public String date() {
            return this.a;
        }

        public String count() {
            return this.b;
        }
    }

}

