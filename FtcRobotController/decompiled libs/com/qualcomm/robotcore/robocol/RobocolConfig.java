/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.robocol;

import com.qualcomm.robotcore.util.Network;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.TypeConversion;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class RobocolConfig {
    public static final int MAX_PACKET_SIZE = 4098;
    public static final int PORT_NUMBER = 20884;
    public static final int TTL = 3;
    public static final int TIMEOUT = 1000;
    public static final int WIFI_P2P_SUBNET_MASK = -256;

    public static InetAddress determineBindAddress(InetAddress destAddress) {
        ArrayList<InetAddress> arrayList = Network.getLocalIpAddresses();
        arrayList = Network.removeLoopbackAddresses(arrayList);
        arrayList = Network.removeIPv6Addresses(arrayList);
        for (InetAddress inetAddress : arrayList) {
            try {
                NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
                Enumeration<InetAddress> enumeration = networkInterface.getInetAddresses();
                while (enumeration.hasMoreElements()) {
                    InetAddress inetAddress2 = enumeration.nextElement();
                    if (!inetAddress2.equals(destAddress)) continue;
                    return inetAddress2;
                }
                continue;
            }
            catch (SocketException var4_5) {
                RobotLog.v(String.format("socket exception while trying to get network interface of %s", inetAddress.getHostAddress()));
                continue;
            }
        }
        return RobocolConfig.determineBindAddressBasedOnWifiP2pSubnet(arrayList, destAddress);
    }

    public static InetAddress determineBindAddressBasedOnWifiP2pSubnet(ArrayList<InetAddress> localIpAddresses, InetAddress destAddress) {
        int n = TypeConversion.byteArrayToInt(destAddress.getAddress());
        for (InetAddress inetAddress : localIpAddresses) {
            int n2 = TypeConversion.byteArrayToInt(inetAddress.getAddress());
            if ((n2 & -256) != (n & -256)) continue;
            return inetAddress;
        }
        return Network.getLoopbackAddress();
    }

    public static InetAddress determineBindAddressBasedOnIsReachable(ArrayList<InetAddress> localIpAddresses, InetAddress destAddress) {
        for (InetAddress inetAddress : localIpAddresses) {
            try {
                NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
                if (!inetAddress.isReachable(networkInterface, 3, 1000)) continue;
                return inetAddress;
            }
            catch (SocketException var4_5) {
                RobotLog.v(String.format("socket exception while trying to get network interface of %s", inetAddress.getHostAddress()));
                continue;
            }
            catch (IOException var4_6) {
                RobotLog.v(String.format("IO exception while trying to determine if %s is reachable via %s", destAddress.getHostAddress(), inetAddress.getHostAddress()));
                continue;
            }
        }
        return Network.getLoopbackAddress();
    }
}

