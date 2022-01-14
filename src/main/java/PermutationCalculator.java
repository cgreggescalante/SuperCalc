import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.showMessageDialog;
import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficient;

public class PermutationCalculator implements View {
    private JPanel root;

    private JSpinner nSpinner;
    private JSpinner rSpinner;

    private JButton evaluateButton;

    private JButton menuButton;

    private JLabel resultLabel;

    private final SuperCalcApp parent;

    public PermutationCalculator(SuperCalcApp parent) {
        this.parent = parent;

        menuButton.addActionListener(this::returnToMenu);
        evaluateButton.addActionListener(this::evaluate);

        nSpinner.setModel(new SpinnerNumberModel(0, 0, Long.MAX_VALUE, 1));
        rSpinner.setModel(new SpinnerNumberModel(0, 0, Long.MAX_VALUE, 1));
    }

    private void evaluate(ActionEvent event) {
        int n = (int) nSpinner.getValue();
        int k = (int) rSpinner.getValue();

        try {
            long result = binomialCoefficient(n, k);

            resultLabel.setText("Answer : " + result);
        } catch (MathArithmeticException e) {
            showMessageDialog(null, "Result exceeds Java max long value: 9,223,372,036,854,775,807");
        } catch (NotPositiveException e) {
            showMessageDialog(null, "n must be positive.");
        } catch (NumberIsTooLargeException e) {
            showMessageDialog(null, "k must be equal to or less than n");
        }
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
