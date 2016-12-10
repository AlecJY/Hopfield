package com.alebit.hopfield;

/**
 * Created by Alec on 2016/12/10.
 */
public class Matrix {
    double[][] data;

    public static Matrix identityMatrix(int dim) {
        double[][] iData = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            iData[i][i] = 1;
        }
        return new Matrix(iData);
    }

    public Matrix(double[][] matrixData) {
        data = new double[matrixData.length][matrixData[0].length];
        for (int i = 0; i < matrixData.length; i++) {
            System.arraycopy(matrixData[i], 0, data[i], 0, matrixData[i].length);
        }
    }

    public Matrix transpose() {
        double[][] tData = new double[data[0].length][data.length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                tData[j][i] = data[i][j];
            }
        }
        return new Matrix(tData);
    }

    public Matrix multiply(Matrix matrix) {
        if (data[0].length != matrix.data.length) {
            throw new NumberFormatException();
        }
        double[][] mData = new double[data.length][matrix.data[0].length];
        for (int i = 0; i < mData.length; i++) {
            for (int j = 0; j < mData[0].length; j++) {
                double sum = 0;
                for (int k = 0; k < data[0].length; k++) {
                    sum += data[i][k] * matrix.data[k][j];
                }
                mData[i][j] = sum;
            }
        }
        return new Matrix(mData);
    }

    public Matrix multiply(double multiplier) {
        double[][] mData = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                mData[i][j] = data[i][j] * multiplier;
            }
        }
        return new Matrix(mData);
    }

    public Matrix add(Matrix matrix) {
        if (data.length != matrix.data.length || data[0].length != matrix.data[0].length) {
            throw new NumberFormatException();
        }
        double[][] aData = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                aData[i][j] = data[i][j] + matrix.data[i][j];
            }
        }
        return new Matrix(aData);
    }

    public Matrix subtract(Matrix matrix) {
        if (data.length != matrix.data.length || data[0].length != matrix.data[0].length) {
            throw new NumberFormatException();
        }
        double[][] sData = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                sData[i][j] = data[i][j] - matrix.data[i][j];
            }
        }
        return new Matrix(sData);
    }
}
