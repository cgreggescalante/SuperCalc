package App;

import Calculator.Calculator;
import Interfaces.View;
import Probability.*;
import UnitConversion.UnitConversionMenu;
import UnitConversion.UnitConverter;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SuperCalcApp extends JFrame {
    private static final Map<String, Class<?>> views = new HashMap<>() {{
        put("menu", SuperCalcMenu.class);
        put("calculator", Calculator.class);
        put("probability", ProbabilityMenu.class);
        put("permutationCalculator", PermutationCalculator.class);
        put("combinationCalculator", CombinationCalculator.class);
        put("binomialProbabilityCalculator", BinomialProbabilityCalculator.class);
        put("normalProbabilityCalculator", NormalProbabilityCalculator.class);
        put("poissonProbabilityCalculator", PoissonProbabilityCalculator.class);
        put("unitConversion", UnitConversionMenu.class);
        put("volumeConversion", UnitConverter.class);
        put("lengthConversion", UnitConverter.class);
        put("weightMassConversion", UnitConverter.class);
        put("temperatureConversion", UnitConverter.class);
        put("energyConversion", UnitConverter.class);
        put("areaConversion", UnitConverter.class);
        put("speedConversion", UnitConverter.class);
    }};

    private JPanel root;

    public SuperCalcApp() {
        Calculator calculator = new Calculator(this);

        ProbabilityMenu probabilityMenu = new ProbabilityMenu(this);
        PermutationCalculator permutationCalculator = new PermutationCalculator(this);
        CombinationCalculator combinationCalculator = new CombinationCalculator(this);
        BinomialProbabilityCalculator binomialProbabilityCalculator = new BinomialProbabilityCalculator(this);
        NormalProbabilityCalculator normalProbabilityCalculator = new NormalProbabilityCalculator(this);
        PoissonProbabilityCalculator poissonProbabilityCalculator = new PoissonProbabilityCalculator(this);

        UnitConversionMenu unitConversionMenu = new UnitConversionMenu(this);

        root.add(calculator.getRoot(), "calculator");

        root.add(probabilityMenu.getRoot(), "probability");
        root.add(permutationCalculator.getRoot(), "permutationCalculator");
        root.add(combinationCalculator.getRoot(), "combinationCalculator");
        root.add(binomialProbabilityCalculator.getRoot(), "binomialProbabilityCalculator");
        root.add(normalProbabilityCalculator.getRoot(), "normalProbabilityCalculator");
        root.add(poissonProbabilityCalculator.getRoot(), "poissonProbabilityCalculator");

        root.add(unitConversionMenu.getRoot(), "unitConversion");

        SuperCalcMenu menu = new SuperCalcMenu(this);
        root.add(menu.getRoot(), "menu");

        setPanel("menu");

        add(root);

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private Component getVisibleCard() {
        for (Component c : root.getComponents()) {
            if (c.isVisible()) {
                return c;
            }
        }

        return null;
    }

    public void setPanel(String panelName) {
        CardLayout layout = (CardLayout) root.getLayout();

        Component currentCard = getVisibleCard();

        layout.show(root, panelName);

        if (getVisibleCard() == currentCard) {
            Class<?> clazz = views.get(panelName);
            View view = null;
            try {
                Constructor<?> constructor = clazz.getConstructor(SuperCalcApp.class);
                view = (View) constructor.newInstance(this);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignored) {}
            try {
                Constructor<?> constructor = clazz.getConstructor(SuperCalcApp.class, String.class);
                view = (View) constructor.newInstance(this, panelName);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ignored) {}

            if (view != null) {
                root.add(view.getRoot(), panelName);
                layout.show(root, panelName);
            }
        }
    }

    public static void main(String[] args) {
        new SuperCalcApp();
    }
}
