package UnitConversion;

import App.SuperCalcApp;
import Interfaces.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class UnitConverter implements View {
    private static final Map<String, String> titles = new HashMap<>() {{
        put("volumeConversion", "Volume");
        put("lengthConversion", "Length");
        put("weightMassConversion", "Weight and Mass");
        put("temperatureConversion", "Temperature");
        put("energyConversion", "Energy");
        put("areaConversion", "Area");
        put("speedConversion", "Speed");
    }};

    private static final Map<String, String[]> options = new HashMap<>() {{
        put("volumeConversion", new String[] {"Milliliters", "Cubic Centimeters", "Liters", "Cubic Meters", "Teaspoons (US)", "Tablespoons (US)", "Fluid Ounces (US)", "Cups (US)", "Pints (US)", "Quarts (US)", "Gallons (US)", "Cubic Inches", "Cubic Feet", "Cubic Yards", "Teaspoons (UK)", "Tablespoons (UK)", "Fluid Ounces (UK)", "Pints (UK)", "Quarts (UK)", "Gallons (UK)"});
        put("lengthConversion", new String[] {"Nanometers", "Microns", "Millimeters", "Centimeters", "Meters", "Kilometers", "Inches", "Feet", "Yards", "Miles", "Nautical Miles"});
        put("weightMassConversion", new String[] {"Carats", "Milligrams", "Centigrams", "Decigrams", "Grams", "Dekagrams", "Hectograms", "Kilograms", "Metric Tonnes", "Ounces", "Pounds", "Stone", "Short Tons (UK)", "Long Tons (UK)"});
        put("temperatureConversion", new String[] {"Celsius", "Fahrenheit", "Kelvin"});
        put("energyConversion", new String[] {"Electron Volts", "Joules", "Kilojoules", "Thermal Calories", "Calories", "Foot-Pounds", "British Thermal Units"});
        put("areaConversion", new String[] {"Square Millimeters", "Square Centimeters", "Square Meters", "Hectares", "Square Kilometers", "Square Inches", "Square Feet", "Square Yards", "Acres", "Square Miles"});
        put("speedConversion", new String[] {"Centimeters per Second", "Meters per Second", "Kilometers per Hour", "Feet per Second", "Miles per Hour", "Knots", "Mach"});
    }};

    private JPanel root;
    private JButton menuButton;
    private JComboBox unitAComboBox;
    private JComboBox unitBComboBox;
    private JTextField aTextField;
    private JTextField bTextField;
    private JLabel titleLabel;

    private final SuperCalcApp parent;

    public UnitConverter(SuperCalcApp parent, String type) {
        this.parent = parent;

        titleLabel.setText(titles.get(type) + " Converter");

        menuButton.addActionListener(this::returnToMenu);

        ComboBoxModel<String> model = new DefaultComboBoxModel<>(options.get(type));

        unitAComboBox.setModel(model);
        unitBComboBox.setModel(model);
    }

    @Override
    public JPanel getRoot() {
        return root;
    }

    @Override
    public void returnToMenu(ActionEvent event) {
        parent.setPanel("unitConversion");
    }
}
