package com.alebit.hopfield;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alec on 2016/12/10.
 */
public class InputForm extends JFrame {
    private JButton calculateButton;
    private JTextField trainingTextField;
    private JTextField testingTextField;
    private JButton trainingBrowseButton;
    private JButton testingBrowseButton;
    private JPanel mainPanel;

    public InputForm() {
        setTitle("Hopfield Network");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getRootPane().setDefaultButton(calculateButton);
        add(mainPanel);
        pack();

        trainingBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    trainingTextField.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });
        testingBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    testingTextField.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileParser fileParser = new FileParser();
                fileParser.parseTrainingData(trainingTextField.getText());
            }
        });
    }
}
