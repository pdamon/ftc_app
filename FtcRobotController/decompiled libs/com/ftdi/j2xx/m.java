/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx;

class m {
    private int a;
    private int b;

    m(int n, int n2) {
        this.a = n;
        this.b = n2;
    }

    m() {
        this.a = 0;
        this.b = 0;
    }

    public int a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    public String toString() {
        return "Vendor: " + String.format("%04x", this.a) + ", Product: " + String.format("%04x", this.b);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof m)) {
            return false;
        }
        m m = (m)o;
        if (this.a != m.a) {
            return false;
        }
        if (this.b != m.b) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        throw new UnsupportedOperationException();
    }
}

