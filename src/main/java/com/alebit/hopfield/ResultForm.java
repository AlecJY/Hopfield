package com.alebit.hopfield;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Alec on 2016/12/11.
 */
public class ResultForm {
    private JPanel resultPanel;
    private JTextPane trainingPane;
    private JTextPane testingPane;
    private JTextPane resultPane;

    public ResultForm(ArrayList<double[][]> trainingData, ArrayList<double[][]> testingData, ArrayList<double[][]> resultData) {
        trainingPane.setContentType("text/html");
        trainingPane.setText(dataString(trainingData));
        testingPane.setContentType("text/html");
        testingPane.setText(dataString(testingData));
        resultPane.setContentType("text/html");
        resultPane.setText(dataString(resultData));
    }

    public JPanel getPanel() {
        return resultPanel;
    }

    private String dataString(ArrayList<double[][]> dataSet) {
        StringBuilder s = new StringBuilder("<html><p><font size=\"5\">");
        for (double[][] data: dataSet) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    if (data[i][j] == 1) {
                        s.append("█");
                    } else {
                        s.append("▒");
                    }
                }
                s.append("<br/>");
            }
            s.append("<br/>");
        }
        s.append("</font></p></html>");
        return s.toString();
    }
}
