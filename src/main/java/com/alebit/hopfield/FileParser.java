package com.alebit.hopfield;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Alec on 2016/12/10.
 */
public class FileParser {
    int maxWidth;
    int maxHeight;

    public ArrayList<double[][]> parseTrainingData(String path) {
        ArrayList<double[][]> trainingData = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            ArrayList<String> data = new ArrayList<>();
            int height = 0;
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    if (height > maxHeight) {
                        maxHeight = height;
                    }
                    break;
                } else {
                    data.add(line);
                    if (!line.matches("\\A\\s*\\z")) {
                        int width = line.replaceAll("\\s+$", "").length();
                        if (width > maxWidth) {
                            maxWidth = width;
                        }
                        height++;
                    } else {
                        if (height > maxHeight) {
                            maxHeight = height;
                        }
                        height = 0;
                    }
                }
            }
            for (int i = 0; i < data.size();) {
                double[][] datum = new double[maxHeight][maxWidth];
                for (int j = 0; j < datum.length; j++) {
                    Arrays.fill(datum[j], -1);
                }
                for (int j = 0; j < maxHeight; j++) {
                    if (i + j < data.size()) {
                        for (int k = 0; k < maxWidth; k++) {
                            if (k < data.get(i + j).length()) {
                                if (data.get(i + j).charAt(k) != ' ') {
                                    datum[j][k] = 1;
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                }
                trainingData.add(datum);
                i = i + maxHeight + 1;
                if (i + maxHeight < data.size() && !data.get(i + maxHeight).matches("\\A\\s*\\z")) {
                    i++;
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return trainingData;
    }

    public ArrayList<double[][]> parseTestingData(String path) {
        ArrayList<double[][]> testingData = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            ArrayList<String> data = new ArrayList<>();
            int height = 0;
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else {
                    data.add(line);
                }
            }
            for (int i = 0; i < data.size();) {
                double[][] datum = new double[maxHeight][maxWidth];
                for (int j = 0; j < datum.length; j++) {
                    Arrays.fill(datum[j], -1);
                }
                for (int j = 0; j < maxHeight; j++) {
                    if (i + j < data.size()) {
                        for (int k = 0; k < maxWidth; k++) {
                            if (k < data.get(i + j).length()) {
                                if (data.get(i + j).charAt(k) != ' ') {
                                    datum[j][k] = 1;
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                }
                testingData.add(datum);
                i = i + maxHeight + 1;
                if (i + maxHeight < data.size() && !data.get(i + maxHeight).matches("\\A\\s*\\z")) {
                    i++;
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return testingData;
    }

    public ArrayList<double[][]> addNoise(ArrayList<double[][]> originalData, double probability) {
        ArrayList<double[][]> noiseData = new ArrayList<>();
        for (double[][] oldData: originalData) {
            double[][] data = new double[oldData.length][oldData[0].length];
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    if (Math.random() <= probability) {
                        data[i][j] = oldData[i][j] * -1;
                    } else {
                        data[i][j] = oldData[i][j];
                    }
                }
            }
            noiseData.add(data);
        }
        return noiseData;
    }
}
