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

        double equal = binomialCoefficient(n, x) * FastMath.pow(probability, x) * FastMath.pow(1 - probability, n - x);
        double lessThan = 0;
        for (int i = 0; i < x; i++) {
            lessThan += binomialCoefficient(n, i) * FastMath.pow(probability, i) * FastMath.pow(1 - probability, n - i);
        }
        double lessThanOrEqual = lessThan + equal;
        double greaterThan = 0;
        for (int i = x + 1; i <= n; i++) {
            greaterThan += binomialCoefficient(n, i) * FastMath.pow(probability, i) * FastMath.pow(1 - probability, n - i);
        }
        double greaterThanOrEqual = greaterThan + equal;

        equalLabel.setText("P(X = x) = " + equal);
        lessThanLabel.setText("P(X < x) = " + lessThan);
        lessThanOrEqualLabel.setText("P(X <= x) = " + lessThanOrEqual);
        greaterThanLabel.setText("P(X > x) = " + greaterThan);
        greaterThanOrEqualLabel.setText("P(X >= x) = " + greaterThanOrEqual);
    }

    @Override
    public JPanel getRoot() {
        return root;
    }
}
