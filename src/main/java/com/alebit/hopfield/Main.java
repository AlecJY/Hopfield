package com.alebit.hopfield;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;

import javax.swing.*;

/**
 * Created by Alec on 2016/12/10.
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        InputForm inputForm = new InputForm();
        inputForm.setVisible(true);
    }
}
