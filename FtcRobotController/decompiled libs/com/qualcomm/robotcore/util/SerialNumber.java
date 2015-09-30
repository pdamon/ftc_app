/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.util;

import java.io.Serializable;

public class SerialNumber
implements Serializable {
    private String a;

    public SerialNumber() {
        this.a = "N/A";
    }

    public SerialNumber(String serialNumber) {
        this.a = serialNumber;
    }

    public String getSerialNumber() {
        return this.a;
    }

    public void setSerialNumber(String serialNumber) {
        this.a = serialNumber;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (object instanceof SerialNumber) {
            return this.a.equals(((SerialNumber)object).getSerialNumber());
        }
        if (object instanceof String) {
            return this.a.equals(object);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        return this.a;
    }
}

