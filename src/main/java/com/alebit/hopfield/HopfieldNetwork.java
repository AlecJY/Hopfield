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
        Matrix[] trainingMatrices = dataToMatrices(trainingData);

        Matrix sumMatrix = new Matrix(new double[dim][dim]);
        for (Matrix matrix: trainingMatrices) {
            sumMatrix = sumMatrix.add(matrix.multiply(matrix.transpose()));
        }
        memoryMatrix = sumMatrix.subtract(Matrix.identityMatrix(dim).multiply(trainingMatrices.length)).multiply((double) 1 / dim);
        double[][] m = new double[dim][1];
        for (int i = 0; i < dim; i++) {
            m[i][0] = 1;
        }
        threshold = memoryMatrix.multiply(new Matrix(m));
    }

    public ArrayList<double[][]> testing(ArrayList<double[][]> testingData) {
        int dim = testingData.get(0).length * testingData.get(0)[0].length;
        Matrix[] testingMatrices = dataToMatrices(testingData);
        for (int index = 0; index < testingMatrices.length; index++) {
            int sameCount = 0;
            Matrix lastMatrix = null;
            while (true) {
                for (int i = 0; i < memoryMatrix.getWidth(); i++) {
                    double value = 0;
                    for (int j = 0; j < dim; j++) {
                        value += memoryMatrix.get(i, j) * testingMatrices[index].get(j, 0);
                    }
                    value -= threshold.get(i, 0);
                     if (value < 0) {
                        value = -1;
                    } else if (value > 0){
                        value = 1;
                    } else {
                         value = testingMatrices[index].get(i, 0);
                     }
                    testingMatrices[index].set(value, i, 0);
                }
                if (lastMatrix == null) {
                    lastMatrix = testingMatrices[index];
                } else if (lastMatrix.equals(testingMatrices[index])) {
                    sameCount++;
                    if (sameCount > 3) {
                        break;
                    }
                } else {
                    sameCount = 0;
                    lastMatrix = testingMatrices[index].clone();
                }
            }
        }
        ArrayList<double[][]> result = matricesToData(testingData, testingMatrices);
        return result;
    }

    public ArrayList<double[][]> asynchronousTesting(ArrayList<double[][]> testingData) {
        int dim = testingData.get(0).length * testingData.get(0)[0].length;
        Matrix[] testingMatrices = dataToMatrices(testingData);
        for (int index = 0; index < testingMatrices.length; index++) {
            int sameCount = 0;
            Matrix lastMatrix = null;
            while (true) {
                Matrix tmpMatrix = memoryMatrix.multiply(testingMatrices[index]).subtract(threshold);
                for (int i = 0; i < memoryMatrix.getWidth(); i++) {
                    if (tmpMatrix.get(i, 0) < 0) {
                        tmpMatrix.set(-1, i, 0);
                    } else if (tmpMatrix.get(i, 0) > 0) {
                        tmpMatrix.set(1, i, 0);
                    } else {
                        tmpMatrix.set(testingMatrices[index].get(i, 0), i, 0);
                    }
                }
                testingMatrices[index] = tmpMatrix;
                if (lastMatrix == null) {
                    lastMatrix = testingMatrices[index];
                } else if (lastMatrix.equals(testingMatrices[index])) {
                    sameCount++;
                    if (sameCount > 3) {
                        break;
                    }
                } else {
                    sameCount = 0;
                    lastMatrix = testingMatrices[index].clone();
                }
            }
        }
        ArrayList<double[][]> result = matricesToData(testingData, testingMatrices);
        return result;
    }

    private Matrix[] dataToMatrices(ArrayList<double[][]> dataCollection) {
        Matrix[] matrices = new Matrix[dataCollection.size()];
        for (int i = 0; i < dataCollection.size(); i++) {
            double[][] datum = dataCollection.get(i);
            double[][] data = new double[datum.length * datum[0].length][1];
            for (int j = 0; j < datum.length; j++) {
                for (int k = 0; k < datum[0].length; k++) {
                    data[j * datum[0].length + k][0] = datum[j][k];
                }
            }
            matrices[i] = new Matrix(data);
        }
        return matrices;
    }

    private ArrayList<double[][]> matricesToData(ArrayList<double[][]> dataCollection, Matrix[] matrices) {
        ArrayList<double[][]> data = new ArrayList<>();
        for (int i = 0; i < matrices.length; i++) {
            double[][] datum = new double[dataCollection.get(i).length][dataCollection.get(i)[0].length];
            for (int j = 0; j < datum.length; j++) {
                for (int k = 0; k < datum[0].length; k++) {
                    datum[j][k] = matrices[i].get(j * datum[0].length + k, 0);
                }
            }
            data.add(datum);
        }
        return data;
    }
}
