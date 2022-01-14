import org.apache.commons.math3.util.FastMath;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficient;

public class BinomialProbabilityCalculator implements View {
    private JPanel root;
    private JButton menuButton;
    private JTextField probabilityField;
    private JSpinner trialsSpinner;
    private JSpinner successesSpinner;
    private JButton evaluateButton;
    private JLabel equalLabel;
    private JLabel lessThanLabel;
    private JLabel lessThanOrEqualLabel;
    private JLabel greaterThanLabel;
    private JLabel greaterThanOrEqualLabel;

    SuperCalcApp parent;

    public BinomialProbabilityCalculator(SuperCalcApp parent) {
        this.parent = parent;

        menuButton.addActionListener(this::returnToMenu);
        evaluateButton.addActionListener(this::evaluate);

        probabilityField.setInputVerifier(new DoubleInputVerifier());

        trialsSpinner.setModel(new SpinnerNumberModel(1, 1, Long.MAX_VALUE, 1));
        successesSpinner.setModel(new SpinnerNumberModel(0, 0, Long.MAX_VALUE, 1));
    }

    private void returnToMenu(ActionEvent event) {
        parent.setPanel("probability");
    }

    private void evaluate(ActionEvent event) {
        double probability = Double.parseDouble(probabilityField.getText());
        int n = (int) (double) trialsSpinner.getValue();
        int x = (int) (double) successesSpinner.getValue();

        double result = binomialCoefficient(n, x) * FastMath.pow(probability, x) * FastMath.pow(1 - probability, n - x);

        equalLabel.setText("P(X = x) = " + result);
    }

    @Override
    public JPanel getRoot() {
        return root;
    }
}
