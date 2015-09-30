/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.wifi.WifiManager
 *  com.qualcomm.robotcore.util.RobotLog
 *  com.qualcomm.robotcore.util.RunShellCommand
 */
package com.qualcomm.wirelessp2p;

import android.content.Context;
import android.net.wifi.WifiManager;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.RunShellCommand;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WifiDirectChannelSelection {
    public static final int INVALID = -1;
    private final String a;
    private final String b;
    private final String c;
    private final WifiManager d;
    private final RunShellCommand e = new RunShellCommand();

    public WifiDirectChannelSelection(Context context, WifiManager wifiManager) {
        this.a = context.getFilesDir().getAbsolutePath() + "/";
        this.d = wifiManager;
        this.b = this.a + "get_current_wifi_direct_staus";
        this.c = this.a + "config_wifi_direct";
    }

    public void config(int wifiClass, int wifiChannel) throws IOException {
        try {
            this.d.setWifiEnabled(false);
            this.c();
            this.e.runAsRoot(this.b);
            this.a(wifiClass, wifiChannel);
            this.b();
            this.e.runAsRoot(this.c);
            this.d.setWifiEnabled(true);
        }
        finally {
            this.d();
        }
    }

    private int a() throws RuntimeException {
        String string = this.e.run("/system/bin/ps");
        for (String string2 : string.split("\n")) {
            if (!string2.contains((CharSequence)"wpa_supplicant")) continue;
            String[] arrstring = string2.split("\\s+");
            return Integer.parseInt(arrstring[1]);
        }
        throw new RuntimeException("could not find wpa_supplicant PID");
    }

    private void b() {
        try {
            char[] arrc = new char[4096];
            FileReader fileReader = new FileReader(this.a + "wpa_supplicant.conf");
            int n = fileReader.read(arrc);
            fileReader.close();
            String string = new String(arrc, 0, n);
            RobotLog.v((String)("WPA FILE: \n" + string));
            string = string.replaceAll("(?s)network\\s*=\\{.*\\}", "");
            string = string.replaceAll("(?m)^\\s+$", "");
            RobotLog.v((String)("WPA REPLACE: \n" + string));
            FileWriter fileWriter = new FileWriter(this.a + "wpa_supplicant.conf");
            fileWriter.write(string);
            fileWriter.close();
        }
        catch (FileNotFoundException var1_2) {
            RobotLog.e((String)("File not found: " + var1_2.toString()));
            var1_2.printStackTrace();
        }
        catch (IOException var1_3) {
            RobotLog.e((String)("FIO exception: " + var1_3.toString()));
            var1_3.printStackTrace();
        }
    }

    private void a(int n, int n2) {
        try {
            char[] arrc = new char[8192];
            FileReader fileReader = new FileReader(this.a + "p2p_supplicant.conf");
            int n3 = fileReader.read(arrc);
            fileReader.close();
            String string = new String(arrc, 0, n3);
            RobotLog.v((String)("P2P ORIG FILE: \n" + string));
            string = string.replaceAll("p2p_listen_reg_class\\w*=.*", "");
            string = string.replaceAll("p2p_listen_channel\\w*=.*", "");
            string = string.replaceAll("p2p_oper_reg_class\\w*=.*", "");
            string = string.replaceAll("p2p_oper_channel\\w*=.*", "");
            string = string.replaceAll("p2p_pref_chan\\w*=.*", "");
            string = string.replaceAll("(?s)network\\s*=\\{.*\\}", "");
            string = string.replaceAll("(?m)^\\s+$", "");
            if (n != -1 && n2 != -1) {
                string = string + "p2p_oper_reg_class=" + n + "\n";
                string = string + "p2p_oper_channel=" + n2 + "\n";
                string = string + "p2p_pref_chan=" + n + ":" + n2 + "\n";
            }
            RobotLog.v((String)("P2P NEW FILE: \n" + string));
            FileWriter fileWriter = new FileWriter(this.a + "p2p_supplicant.conf");
            fileWriter.write(string);
            fileWriter.close();
        }
        catch (FileNotFoundException var3_4) {
            RobotLog.e((String)("File not found: " + var3_4.toString()));
            var3_4.printStackTrace();
        }
        catch (IOException var3_5) {
            RobotLog.e((String)("FIO exception: " + var3_5.toString()));
            var3_5.printStackTrace();
        }
    }

    private void c() throws IOException {
        String string = String.format("cp /data/misc/wifi/wpa_supplicant.conf %s/wpa_supplicant.conf \ncp /data/misc/wifi/p2p_supplicant.conf %s/p2p_supplicant.conf \nchmod 666 %s/*supplicant* \n", this.a, this.a, this.a);
        String string2 = String.format("cp %s/p2p_supplicant.conf /data/misc/wifi/p2p_supplicant.conf \ncp %s/wpa_supplicant.conf /data/misc/wifi/wpa_supplicant.conf \nrm %s/*supplicant* \nchown system.wifi /data/misc/wifi/wpa_supplicant.conf \nchown system.wifi /data/misc/wifi/p2p_supplicant.conf \nkill -HUP %d \n", this.a, this.a, this.a, this.a());
        FileWriter fileWriter = new FileWriter(this.b);
        fileWriter.write(string);
        fileWriter.close();
        fileWriter = new FileWriter(this.c);
        fileWriter.write(string2);
        fileWriter.close();
        this.e.run("chmod 700 " + this.b);
        this.e.run("chmod 700 " + this.c);
    }

    private void d() {
        File file = new File(this.b);
        file.delete();
        file = new File(this.c);
        file.delete();
    }
}

