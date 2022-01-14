package Probability;

import App.SuperCalcApp;
import Interfaces.View;
import org.apache.commons.math3.distribution.PoissonDistribution;

import javax.swing.*;
import java.awt.event.ActionEvent;

import InputVerifier.DoubleInputVerifier;

public class PoissonProbabilityCalculator implements View {
    private JPanel root;
    private JButton menuButton;
    private JSpinner randomVariableSpinner;
    private JTextField rateOfSuccessTextField;
    private JButton evaluateButton;
    private JLabel lessThanLabel;
    private JLabel lessThanOrEqualLabel;
    private JLabel equalLabel;
    private JLabel greaterThanLabel;
    private JLabel greaterThanOrEqualLabel;

    private final SuperCalcApp parent;

    public PoissonProbabilityCalculator(SuperCalcApp parent) {
        this.parent = parent;

        menuButton.addActionListener(this::returnToMenu);
        evaluateButton.addActionListener(this::evaluate);

        randomVariableSpinner.setModel(new SpinnerNumberModel(0, 0, Long.MAX_VALUE, 1));

        rateOfSuccessTextField.setInputVerifier(new DoubleInputVerifier());
    }

    private void evaluate(ActionEvent event) {
        int randomVariable = ((Double) randomVariableSpinner.getValue()).intValue();
        double rateOfSuccess = Double.parseDouble(rateOfSuccessTextField.getText());

        PoissonDistribution poissonDistribution = new PoissonDistribution(rateOfSuccess);

        double equal = poissonDistribution.probability(randomVariable);

        double lessThan = poissonDistribution.cumulativeProbability(randomVariable - 1);
        double lessThanOrEqual = poissonDistribution.cumulativeProbability(randomVariable);
        double greaterThanOrEqual = 1 - lessThan;
        double greaterThan = 1 - lessThanOrEqual;

        lessThanLabel.setText("P(X < x) = " + lessThan);
        lessThanOrEqualLabel.setText("P(X <= x) = " + lessThanOrEqual);
        equalLabel.setText("P(X = x) = " + equal);
        greaterThanOrEqualLabel.setText("P(X >= x) = " + greaterThanOrEqual);
        greaterThanLabel.setText("P(X > x) = " + greaterThan);
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
