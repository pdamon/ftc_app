/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.qualcomm.robotcore.util.RobotLog
 *  com.qualcomm.robotcore.util.TypeConversion
 */
package com.qualcomm.modernrobotics;

import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.TypeConversion;

public class ModernRoboticsPacket {
    static boolean a(byte[] arrby, int n) {
        return ModernRoboticsPacket.b(arrby, n);
    }

    private static boolean b(byte[] arrby, int n) {
        if (arrby.length < 5) {
            RobotLog.w((String)"header length is too short");
            return false;
        }
        if (arrby[0] != 51 || arrby[1] != -52) {
            RobotLog.w((String)"header sync bytes are incorrect");
            return false;
        }
        if (TypeConversion.unsignedByteToInt((byte)arrby[4]) != n) {
            RobotLog.w((String)"header reported unexpected packet size");
            return false;
        }
        return true;
    }
}

