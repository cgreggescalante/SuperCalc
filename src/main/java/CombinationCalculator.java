import javax.swing.*;
import java.awt.event.ActionEvent;

public class CombinationCalculator implements View {
    private JPanel root;

    private JSpinner nSpinner;
    private JSpinner rSpinner;

    private JButton evaluateButton;

    private JButton menuButton;

    private JLabel resultLabel;

    private final SuperCalcApp parent;

    public CombinationCalculator(SuperCalcApp parent) {
        this.parent = parent;

        menuButton.addActionListener(this::returnToMenu);
        evaluateButton.addActionListener(this::evaluate);

        nSpinner.setModel(new SpinnerNumberModel(0, 0, Long.MAX_VALUE, 1));
        rSpinner.setModel(new SpinnerNumberModel(0, 0, Long.MAX_VALUE, 1));
    }

    private void evaluate(ActionEvent event) {
        int n = (int) nSpinner.getValue();
        int k = (int) rSpinner.getValue();

        long[] row = new long[200];
        row[0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = i; j > 0; j--) {
                row[j] += row[j - 1];
                if (i == n && j == k) {
                    resultLabel.setText("Answer : " + row[k]);
                    return;
                }
            }
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
