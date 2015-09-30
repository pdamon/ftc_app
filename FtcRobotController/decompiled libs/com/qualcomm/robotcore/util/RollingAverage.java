/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.util;

import java.util.LinkedList;
import java.util.Queue;

public class RollingAverage {
    public static final int DEFAULT_SIZE = 100;
    private final Queue<Integer> a = new LinkedList<Integer>();
    private long b;
    private int c;

    public RollingAverage() {
        this.resize(100);
    }

    public RollingAverage(int size) {
        this.resize(size);
    }

    public int size() {
        return this.c;
    }

    public void resize(int size) {
        this.c = size;
        this.a.clear();
    }

    public void addNumber(int number) {
        if (this.a.size() >= this.c) {
            int n = this.a.remove();
            this.b-=(long)n;
        }
        this.a.add(number);
        this.b+=(long)number;
    }

    public int getAverage() {
        if (this.a.isEmpty()) {
            return 0;
        }
        return (int)(this.b / (long)this.a.size());
    }

    public void reset() {
        this.a.clear();
    }
}

