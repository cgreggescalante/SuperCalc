package App;

import Calculator.Calculator;
import Probability.*;

import javax.swing.*;
import java.awt.*;

public class SuperCalcApp extends JFrame {
    private JPanel root;

    public SuperCalcApp() {
        Calculator calculator = new Calculator(this);

        ProbabilityMenu probabilityMenu = new ProbabilityMenu(this);
        PermutationCalculator permutationCalculator = new PermutationCalculator(this);
        CombinationCalculator combinationCalculator = new CombinationCalculator(this);
        BinomialProbabilityCalculator binomialProbabilityCalculator = new BinomialProbabilityCalculator(this);
        NormalProbabilityCalculator normalProbabilityCalculator = new NormalProbabilityCalculator(this);
        PoissonProbabilityCalculator poissonProbabilityCalculator = new PoissonProbabilityCalculator(this);

        root.add(calculator.getRoot(), "calculator");

        root.add(probabilityMenu.getRoot(), "probability");
        root.add(permutationCalculator.getRoot(), "permutationCalculator");
        root.add(combinationCalculator.getRoot(), "combinationCalculator");
        root.add(binomialProbabilityCalculator.getRoot(), "binomialProbabilityCalculator");
        root.add(normalProbabilityCalculator.getRoot(), "normalProbabilityCalculator");
        root.add(poissonProbabilityCalculator.getRoot(), "poissonProbabilityCalculator");

        SuperCalcMenu menu = new SuperCalcMenu(this);
        root.add(menu.getRoot(), "menu");

        setPanel("menu");

        add(root);

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void setPanel(String panelName) {
        CardLayout layout = (CardLayout) root.getLayout();

        layout.show(root, panelName);
    }

    public static void main(String[] args) {
        new SuperCalcApp();
    }
}
