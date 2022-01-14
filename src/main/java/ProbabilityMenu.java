import javax.swing.*;
import java.awt.event.ActionEvent;

public class ProbabilityMenu implements View, ViewManager {
    private JPanel root;
    private JButton menuButton;
    private JButton permutationCalculatorButton;
    private JButton combinationCalculatorButton;
    private JButton binomialProbabilityButton;
    private JButton normalProbabilityButton;
    private JButton poissonProbabilityButton;
    private JButton button7;

    private final SuperCalcApp parent;

    public ProbabilityMenu(SuperCalcApp parent) {
        this.parent = parent;

        menuButton.addActionListener(this::returnToMenu);

        permutationCalculatorButton.addActionListener(this::buttonPressed);
        combinationCalculatorButton.addActionListener(this::buttonPressed);
        binomialProbabilityButton.addActionListener(this::buttonPressed);
        normalProbabilityButton.addActionListener(this::buttonPressed);
        poissonProbabilityButton.addActionListener(this::buttonPressed);
    }

    @Override
    public void buttonPressed(ActionEvent event) {
        if (event.getSource() == permutationCalculatorButton) {
            parent.setPanel("permutationCalculator");
        } else if (event.getSource() == combinationCalculatorButton) {
            parent.setPanel("combinationCalculator");
        } else if (event.getSource() == binomialProbabilityButton) {
            parent.setPanel("binomialProbabilityCalculator");
        } else if (event.getSource() == normalProbabilityButton) {
            parent.setPanel("normalProbabilityCalculator");
        } else if (event.getSource() == poissonProbabilityButton) {
            parent.setPanel("poissonProbabilityCalculator");
        }
    }

    @Override
    public void returnToMenu(ActionEvent event) {
        parent.setPanel("menu");
    }

    @Override
    public JPanel getRoot() {
        return root;
    }
}
