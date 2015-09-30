/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

public class Network {
    public static InetAddress getLoopbackAddress() {
        try {
            return InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        }
        catch (UnknownHostException var0) {
            return null;
        }
    }

    public static ArrayList<InetAddress> getLocalIpAddresses() {
        ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>();
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                arrayList.addAll(Collections.list(networkInterface.getInetAddresses()));
            }
        }
        catch (SocketException var1_2) {
            // empty catch block
        }
        return arrayList;
    }

    public static ArrayList<InetAddress> getLocalIpAddress(String networkInterface) {
        ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>();
        try {
            for (NetworkInterface networkInterface2 : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface2.getName() != networkInterface) continue;
                arrayList.addAll(Collections.list(networkInterface2.getInetAddresses()));
            }
        }
        catch (SocketException var2_3) {
            // empty catch block
        }
        return arrayList;
    }

    public static ArrayList<InetAddress> removeIPv6Addresses(Collection<InetAddress> addresses) {
        ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>();
        for (InetAddress inetAddress : addresses) {
            if (!(inetAddress instanceof Inet4Address)) continue;
            arrayList.add(inetAddress);
        }
        return arrayList;
    }

    public static ArrayList<InetAddress> removeIPv4Addresses(Collection<InetAddress> addresses) {
        ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>();
        for (InetAddress inetAddress : addresses) {
            if (!(inetAddress instanceof Inet6Address)) continue;
            arrayList.add(inetAddress);
        }
        return arrayList;
    }

    public static ArrayList<InetAddress> removeLoopbackAddresses(Collection<InetAddress> addresses) {
        ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>();
        for (InetAddress inetAddress : addresses) {
            if (inetAddress.isLoopbackAddress()) continue;
            arrayList.add(inetAddress);
        }
        return arrayList;
    }

    public static ArrayList<String> getHostAddresses(Collection<InetAddress> addresses) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (InetAddress inetAddress : addresses) {
            String string = inetAddress.getHostAddress();
            if (string.contains((CharSequence)"%")) {
                string = string.substring(0, string.indexOf(37));
            }
            arrayList.add(string);
        }
        return arrayList;
    }
}

