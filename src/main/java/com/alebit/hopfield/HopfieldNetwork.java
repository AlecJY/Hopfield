package com.alebit.hopfield;

import java.util.ArrayList;

/**
 * Created by Alec on 2016/12/10.
 */
public class HopfieldNetwork {
    private Matrix memoryMatrix;
    private Matrix threshold;

    public void training(ArrayList<double[][]> trainingData) {
        int dim = trainingData.get(0).length * trainingData.get(0)[0].length;
        Matrix[] trainingMatrix = new Matrix[trainingData.size()];
        for (int i = 0; i < trainingData.size(); i++) {
            double[][] trainingDatum = trainingData.get(i);
            double[][] data = new double[trainingDatum.length * trainingDatum[0].length][1];
            for (int j = 0; j < trainingDatum.length; j++) {
                for (int k = 0; k < trainingDatum[0].length; k++) {
                    data[j * trainingDatum[0].length + k][0] = trainingDatum[j][k];
                }
            }
            trainingMatrix[i] = new Matrix(data);
        }

        Matrix sumMatrix = new Matrix(new double[dim][dim]);
        for (Matrix matrix: trainingMatrix) {
            sumMatrix = sumMatrix.add(matrix.multiply(matrix.transpose()));
        }
        memoryMatrix = sumMatrix.subtract(Matrix.identityMatrix(dim).multiply(trainingMatrix.length)).multiply((double) 1 / dim);
        double[][] m = new double[dim][1];
        for (int i = 0; i < dim; i++) {
            m[i][0] = 1;
        }
        threshold = memoryMatrix.multiply(new Matrix(m));
    }
}
