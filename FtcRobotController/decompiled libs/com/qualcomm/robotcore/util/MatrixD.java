/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.qualcomm.robotcore.util;

import android.util.Log;

public class MatrixD {
    protected double[][] mData;
    protected int mRows;
    protected int mCols;

    public MatrixD(int rows, int cols) {
        this(new double[rows][cols]);
    }

    public MatrixD(double[] init, int rows, int cols) {
        this(rows, cols);
        if (init == null) {
            throw new IllegalArgumentException("Attempted to initialize MatrixF with null array");
        }
        if (init.length != rows * cols) {
            throw new IllegalArgumentException("Attempted to initialize MatrixF with rows/cols not matching init data");
        }
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                this.mData[i][j] = init[j + cols * i];
            }
        }
    }

    public MatrixD(float[] init, int rows, int cols) {
        this(rows, cols);
        if (init == null) {
            throw new IllegalArgumentException("Attempted to initialize MatrixF with null array");
        }
        if (init.length != rows * cols) {
            throw new IllegalArgumentException("Attempted to initialize MatrixF with rows/cols not matching init data");
        }
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                this.mData[i][j] = init[j + cols * i];
            }
        }
    }

    public MatrixD(double[][] init) {
        this.mData = init;
        if (this.mData == null) {
            throw new IllegalArgumentException("Attempted to initialize MatrixF with null array");
        }
        this.mRows = this.mData.length;
        if (this.mRows <= 0) {
            throw new IllegalArgumentException("Attempted to initialize MatrixF with 0 rows");
        }
        this.mCols = this.mData[0].length;
        for (int i = 0; i < this.mRows; ++i) {
            if (this.mData[i].length == this.mCols) continue;
            throw new IllegalArgumentException("Attempted to initialize MatrixF with rows of unequal length");
        }
    }

    public int numRows() {
        return this.mRows;
    }

    public int numCols() {
        return this.mCols;
    }

    public double[][] data() {
        return this.mData;
    }

    public MatrixD submatrix(int rows, int cols, int rowOffset, int colOffset) {
        if (rows > this.numRows() || cols > this.numCols()) {
            throw new IllegalArgumentException("Attempted to get submatrix with size larger than original");
        }
        if (rowOffset + rows > this.numRows() || colOffset + cols > this.numCols()) {
            throw new IllegalArgumentException("Attempted to access out of bounds data with row or col offset out of range");
        }
        double[][] arrd = new double[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                arrd[i][j] = this.data()[rowOffset + i][colOffset + j];
            }
        }
        return new MatrixD(arrd);
    }

    public boolean setSubmatrix(MatrixD inData, int rows, int cols, int rowOffset, int colOffset) {
        if (inData == null) {
            throw new IllegalArgumentException("Input data to setSubMatrix null");
        }
        if (rows > this.numRows() || cols > this.numCols()) {
            throw new IllegalArgumentException("Attempted to get submatrix with size larger than original");
        }
        if (rowOffset + rows > this.numRows() || colOffset + cols > this.numCols()) {
            throw new IllegalArgumentException("Attempted to access out of bounds data with row or col offset out of range");
        }
        if (rows > inData.numRows() || cols > inData.numCols()) {
            throw new IllegalArgumentException("Input matrix small for setSubMatrix");
        }
        if (rowOffset + rows > inData.numRows() || colOffset + cols > this.numCols()) {
            throw new IllegalArgumentException("Input matrix Attempted to access out of bounds data with row or col offset out of range");
        }
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                this.data()[rowOffset + i][colOffset + j] = inData.data()[i][j];
            }
        }
        return true;
    }

    public MatrixD transpose() {
        int n = this.mRows;
        int n2 = this.mCols;
        double[][] arrd = new double[n2][n];
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                arrd[i][j] = this.mData[j][i];
            }
        }
        return new MatrixD(arrd);
    }

    public MatrixD add(MatrixD other) {
        double[][] arrd = new double[this.numRows()][this.numCols()];
        int n = this.numRows();
        int n2 = this.numCols();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                arrd[i][j] = this.data()[i][j] + other.data()[i][j];
            }
        }
        return new MatrixD(arrd);
    }

    public MatrixD add(double val) {
        double[][] arrd = new double[this.numRows()][this.numCols()];
        int n = this.numRows();
        int n2 = this.numCols();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                arrd[i][j] = this.data()[i][j] + val;
            }
        }
        return new MatrixD(arrd);
    }

    public MatrixD subtract(MatrixD other) {
        double[][] arrd = new double[this.numRows()][this.numCols()];
        int n = this.numRows();
        int n2 = this.numCols();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                arrd[i][j] = this.data()[i][j] - other.data()[i][j];
            }
        }
        return new MatrixD(arrd);
    }

    public MatrixD subtract(double val) {
        double[][] arrd = new double[this.numRows()][this.numCols()];
        int n = this.numRows();
        int n2 = this.numCols();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                arrd[i][j] = this.data()[i][j] - val;
            }
        }
        return new MatrixD(arrd);
    }

    public MatrixD times(MatrixD other) {
        if (this.numCols() != other.numRows()) {
            throw new IllegalArgumentException("Attempted to multiply matrices of invalid dimensions (AB) where A is " + this.numRows() + "x" + this.numCols() + ", B is " + other.numRows() + "x" + other.numCols());
        }
        int n = this.numCols();
        int n2 = this.numRows();
        int n3 = other.numCols();
        double[][] arrd = new double[n2][n3];
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n3; ++j) {
                for (int k = 0; k < n; ++k) {
                    double[] arrd2 = arrd[i];
                    int n4 = j;
                    arrd2[n4] = arrd2[n4] + this.data()[i][k] * other.data()[k][j];
                }
            }
        }
        return new MatrixD(arrd);
    }

    public MatrixD times(double f) {
        double[][] arrd = new double[this.numRows()][this.numCols()];
        for (int i = 0; i < this.numRows(); ++i) {
            for (int j = 0; j < this.numCols(); ++j) {
                arrd[i][j] = this.data()[i][j] * f;
            }
        }
        return new MatrixD(arrd);
    }

    public double length() {
        if (this.numRows() != 1 && this.numCols() != 1) {
            throw new IndexOutOfBoundsException("Not a 1D matrix ( " + this.numRows() + ", " + this.numCols() + " )");
        }
        double d = 0.0;
        for (int i = 0; i < this.numRows(); ++i) {
            for (int j = 0; j < this.numCols(); ++j) {
                d+=this.mData[i][j] * this.mData[i][j];
            }
        }
        return Math.sqrt(d);
    }

    public String toString() {
        String string = new String();
        for (int i = 0; i < this.numRows(); ++i) {
            String string2 = new String();
            for (int j = 0; j < this.numCols(); ++j) {
                string2 = string2 + String.format("%.4f", this.data()[i][j]);
                if (j >= this.numCols() - 1) continue;
                string2 = string2 + ", ";
            }
            string = string + string2;
            if (i >= this.numRows() - 1) continue;
            string = string + "\n";
        }
        string = string + "\n";
        return string;
    }

    public static void test() {
        Log.e((String)"MatrixD", (String)"Hello2 matrix");
        MatrixD matrixD = new MatrixD(new double[][]{{1.0, 0.0, -2.0}, {0.0, 3.0, -1.0}});
        Log.e((String)"MatrixD", (String)"Hello3 matrix");
        Log.e((String)"MatrixD", (String)("A = \n" + matrixD.toString()));
        MatrixD matrixD2 = new MatrixD(new double[][]{{0.0, 3.0}, {-2.0, -1.0}, {0.0, 4.0}});
        Log.e((String)"MatrixD", (String)("B = \n" + matrixD2.toString()));
        MatrixD matrixD3 = matrixD.transpose();
        Log.e((String)"MatrixD", (String)("A transpose = " + matrixD3.toString()));
        MatrixD matrixD4 = matrixD2.transpose();
        Log.e((String)"MatrixD", (String)("B transpose = " + matrixD4.toString()));
        MatrixD matrixD5 = matrixD.times(matrixD2);
        Log.e((String)"MatrixD", (String)("AB = \n" + matrixD5.toString()));
        MatrixD matrixD6 = matrixD2.times(matrixD);
        Log.e((String)"MatrixD", (String)("BA = \n" + matrixD6.toString()));
        MatrixD matrixD7 = matrixD6.times(2.0);
        Log.e((String)"MatrixD", (String)("BA*2 = " + matrixD7.toString()));
        MatrixD matrixD8 = matrixD6.submatrix(3, 2, 0, 1);
        Log.e((String)"MatrixD", (String)("BA submatrix 3,2,0,1 = " + matrixD8));
        MatrixD matrixD9 = matrixD6.submatrix(2, 1, 1, 2);
        Log.e((String)"MatrixD", (String)("BA submatrix 2,1,1,2 = " + matrixD9));
    }
}

