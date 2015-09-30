/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.util;

public class DifferentialControlLoopCoefficients {
    public double p = 0.0;
    public double i = 0.0;
    public double d = 0.0;

    public DifferentialControlLoopCoefficients() {
    }

    public DifferentialControlLoopCoefficients(double p, double i, double d) {
        this.p = p;
        this.i = i;
        this.d = d;
    }
}

