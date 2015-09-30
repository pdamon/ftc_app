/*
 * Decompiled with CFR 0_101.
 */
package com.ftdi.j2xx;

import java.nio.Buffer;
import java.nio.ByteBuffer;

class n {
    private int a;
    private ByteBuffer b;
    private int c;
    private boolean d;

    public n(int n) {
        this.b = ByteBuffer.allocate(n);
        this.b(0);
    }

    void a(int n) {
        this.a = n;
    }

    ByteBuffer a() {
        return this.b;
    }

    int b() {
        return this.c;
    }

    void b(int n) {
        this.c = n;
    }

    synchronized void c() {
        this.b.clear();
        this.b(0);
    }

    synchronized boolean d() {
        return this.d;
    }

    synchronized ByteBuffer c(int n) {
        ByteBuffer byteBuffer = null;
        if (!this.d) {
            this.d = true;
            this.a = n;
            byteBuffer = this.b;
        }
        return byteBuffer;
    }

    synchronized boolean d(int n) {
        boolean bl = false;
        if (this.d && n == this.a) {
            this.d = false;
            bl = true;
        }
        return bl;
    }
}

