package Probability;

import App.SuperCalcApp;
import Interfaces.View;
import org.apache.commons.math3.distribution.NormalDistribution;

import javax.swing.*;
import java.awt.event.ActionEvent;

import InputVerifier.DoubleOrEmptyInputVerifier;

import static javax.swing.JOptionPane.showMessageDialog;

public class NormalProbabilityCalculator implements View {
    private JPanel root;

    private JButton menuButton;
    private JButton evaluateButton;

    private JTextField zTextField;
    private JTextField meanTextField;
    private JTextField stdTextField;
    private JTextField lessThanTextField;
    private JTextField greaterThanTextField;

    private final SuperCalcApp parent;

    public NormalProbabilityCalculator(SuperCalcApp parent) {
        this.parent = parent;

        menuButton.addActionListener(this::returnToMenu);
        evaluateButton.addActionListener(this::evaluate);

        zTextField.setInputVerifier(new DoubleOrEmptyInputVerifier());
        meanTextField.setInputVerifier(new DoubleOrEmptyInputVerifier());
        stdTextField.setInputVerifier(new DoubleOrEmptyInputVerifier());
        lessThanTextField.setInputVerifier(new DoubleOrEmptyInputVerifier());
        greaterThanTextField.setInputVerifier(new DoubleOrEmptyInputVerifier());
    }

    public void evaluate(ActionEvent event) {
        Double x = null, mean = null, std = null, lessThan = null, greaterThan = null;
        try {
            x = Double.parseDouble(zTextField.getText());
        } catch (NumberFormatException ignored) {}
        try {
            mean = Double.parseDouble(meanTextField.getText());
        } catch (NumberFormatException ignored) {}
        try {
            std = Double.parseDouble(stdTextField.getText());
        } catch (NumberFormatException ignored) {}
        try {
            lessThan = Double.parseDouble(lessThanTextField.getText());
        } catch (NumberFormatException ignored) {}
        try {
            greaterThan = Double.parseDouble(greaterThanTextField.getText());
        } catch (NumberFormatException ignored) {}

        if (lessThan == null && greaterThan != null) {
            lessThan = 1 - greaterThan;
        } else if (lessThan != null && greaterThan == null) {
            greaterThan = 1 - lessThan;
        }

        if (x != null && mean != null && std != null) {
            NormalDistribution distribution = new NormalDistribution(mean, std);
            lessThan = distribution.cumulativeProbability(x);
            greaterThan = 1 - lessThan;
        } else if (mean != null && std != null && lessThan != null) {
            x = new NormalDistribution(mean, std).inverseCumulativeProbability(lessThan);
        } else {
            showMessageDialog(null, "More inputs required.");
            return;
        }
//        TODO: Add STD from mean and probability and expected
//        TODO Add mean from STD and probability and expected
        zTextField.setText(Double.toString(x));
        meanTextField.setText(Double.toString(mean));
        stdTextField.setText(Double.toString(std));
        lessThanTextField.setText(Double.toString(lessThan));
        greaterThanTextField.setText(Double.toString(greaterThan));
    }

    @Override
    public void returnToMenu(ActionEvent event) {
        parent.setPanel("probability");
    }

    @Override
    public JPanel getRoot() {
        return root;
    }
}
