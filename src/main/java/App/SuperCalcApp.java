package App;

import Calculator.Calculator;
import Calculator.GraphingCalculator;
import Interfaces.View;
import Probability.*;
import UnitConversion.UnitConversionMenu;
import UnitConversion.UnitConversion;

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
        put("graphingCalculator", GraphingCalculator.class);
        put("probability", ProbabilityMenu.class);
        put("permutationCalculator", PermutationCalculator.class);
        put("combinationCalculator", CombinationCalculator.class);
        put("binomialProbabilityCalculator", BinomialProbabilityCalculator.class);
        put("normalProbabilityCalculator", NormalProbabilityCalculator.class);
        put("poissonProbabilityCalculator", PoissonProbabilityCalculator.class);
        put("unitConversion", UnitConversionMenu.class);
        put("volumeConversion", UnitConversion.class);
        put("lengthConversion", UnitConversion.class);
        put("weightMassConversion", UnitConversion.class);
        put("temperatureConversion", UnitConversion.class);
        put("energyConversion", UnitConversion.class);
        put("areaConversion", UnitConversion.class);
        put("speedConversion", UnitConversion.class);
    }};

    private JPanel root;

    public SuperCalcApp() {
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
