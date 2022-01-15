package UnitConversion;

import App.SuperCalcApp;
import Interfaces.View;
import com.digidemic.unitof.UnitOf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class UnitConversion implements View {
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
        put("weightMassConversion", new String[] {"Carats", "Milligrams", "Centigrams", "Decigrams", "Grams", "Dekagrams", "Hectograms", "Kilograms", "Metric Tonnes", "Ounces", "Pounds", "Stone"});
        put("temperatureConversion", new String[] {"Celsius", "Fahrenheit", "Kelvin"});
        put("energyConversion", new String[] {"Electron Volts", "Joules", "Kilojoules", "Thermal Calories", "Calories", "Foot-Pounds", "British Thermal Units"});
        put("areaConversion", new String[] {"Square Millimeters", "Square Centimeters", "Square Meters", "Hectares", "Square Kilometers", "Square Inches", "Square Feet", "Square Yards", "Acres", "Square Miles"});
        put("speedConversion", new String[] {"Centimeters per Second", "Meters per Second", "Kilometers per Hour", "Feet per Second", "Miles per Hour", "Knots", "Mach"});
    }};

    private JPanel root;
    private JButton menuButton;
    private JComboBox<String> unitAComboBox;
    private JComboBox<String> unitBComboBox;
    private JTextField aTextField;
    private JTextField bTextField;
    private JLabel titleLabel;
    private JButton convertButton;

    private final SuperCalcApp parent;

    private final String type;

    public UnitConversion(SuperCalcApp parent, String type) {
        this.parent = parent;
        this.type = type;

        titleLabel.setText(titles.get(type) + " Converter");

        menuButton.addActionListener(this::returnToMenu);
        convertButton.addActionListener(this::convert);

        ComboBoxModel<String> modelA = new DefaultComboBoxModel<>(options.get(type));
        ComboBoxModel<String> modelB = new DefaultComboBoxModel<>(options.get(type));

        unitAComboBox.setModel(modelA);
        unitBComboBox.setModel(modelB);
    }

    private void convert(ActionEvent event) {
        Double start = null;
        String startUnit = null, endUnit = null;

        if (!aTextField.getText().isEmpty()) {
            start = Double.parseDouble(aTextField.getText());
            startUnit = (String) unitAComboBox.getSelectedItem();
            endUnit = (String) unitBComboBox.getSelectedItem();
        } else if (!bTextField.getText().isEmpty()) {
            start = Double.parseDouble(bTextField.getText());
            endUnit = (String) unitAComboBox.getSelectedItem();
            startUnit = (String) unitBComboBox.getSelectedItem();
        }

        if (start == null || startUnit == null || endUnit == null) {
            return;
        }

        double result = switch (type) {
            case "volumeConversion" -> convertVolumes(start, startUnit, endUnit);
            case "lengthConversion" -> convertLength(start, startUnit, endUnit);
            case "weightMassConversion" -> convertWeightMass(start, startUnit, endUnit);
            case "temperatureConversion" -> convertTemperature(start, startUnit, endUnit);
            case "energyConversion" -> convertEnergy(start, startUnit, endUnit);
            case "areaConversion" -> convertArea(start, startUnit, endUnit);
            case "speedConversion" -> convertSpeed(start, startUnit, endUnit);
            default -> -1;
        };

        if (!aTextField.getText().isEmpty()) {
            bTextField.setText(Double.toString(result));
        } else {
            aTextField.setText(Double.toString(result));
        }
    }

    private double convertVolumes(double start, String startUnit, String endUnit) {
        UnitOf.Volume volume = switch (startUnit) {
            case "Milliliters", "Cubic Centimeters" -> new UnitOf.Volume().fromMilliliters(start);
            case "Liters" -> new UnitOf.Volume().fromLiters(start);
            case "Cubic Meters" -> new UnitOf.Volume().fromCubicMeters(start);
            case "Teaspoons (US)" -> new UnitOf.Volume().fromTeaspoonsUS(start);
            case "Tablespoons (US)" -> new UnitOf.Volume().fromTablespoonsUS(start);
            case "Fluid Ounces (US)" -> new UnitOf.Volume().fromFluidOuncesUS(start);
            case "Cups (US)" -> new UnitOf.Volume().fromCupsUS(start);
            case "Pints (US)" -> new UnitOf.Volume().fromPintsUS(start);
            case "Quarts (US)" -> new UnitOf.Volume().fromQuartsUS(start);
            case "Gallons (US)" -> new UnitOf.Volume().fromGallonsUS(start);
            case "Cubic Inches" -> new UnitOf.Volume().fromCubicInches(start);
            case "Cubic Feet" -> new UnitOf.Volume().fromCubicFeet(start);
            case "Cubic Yards" -> new UnitOf.Volume().fromCubicYards(start);
            case "Teaspoons (UK)" -> new UnitOf.Volume().fromTeaspoonsUK(start);
            case "Tablespoons (UK)" -> new UnitOf.Volume().fromTablespoonsUK(start);
            case "Fluid Ounces (UK)" -> new UnitOf.Volume().fromFluidOuncesUK(start);
            case "Pints (UK)" -> new UnitOf.Volume().fromPintsUK(start);
            case "Quarts (UK)" -> new UnitOf.Volume().fromQuartsUK(start);
            case "Gallons (UK)" -> new UnitOf.Volume().fromGallonsUK(start);
            default -> null;
        };

        if (volume == null) {
            return -1;
        }

        return switch (endUnit) {
            case "Milliliters", "Cubic Centimeters" -> volume.toMilliliters();
            case "Liters" -> volume.toLiters();
            case "Cubic Meters" -> volume.toCubicMeters();
            case "Teaspoons (US)" -> volume.toTeaspoonsUS();
            case "Tablespoons (US)" -> volume.toTablespoonsUS();
            case "Fluid Ounces (US)" -> volume.toFluidOuncesUS();
            case "Cups (US)" -> volume.toCupsUS();
            case "Pints (US)" -> volume.toPintsUS();
            case "Quarts (US)" -> volume.toQuartsUS();
            case "Gallons (US)" -> volume.toGallonsUS();
            case "Cubic Inches" -> volume.toCubicInches();
            case "Cubic Feet" -> volume.toCubicFeet();
            case "Cubic Yards" -> volume.toCubicYards();
            case "Teaspoons (UK)" -> volume.toTeaspoonsUK();
            case "Tablespoons (UK)" -> volume.toTablespoonsUK();
            case "Fluid Ounces (UK)" -> volume.toFluidOuncesUK();
            case "Pints (UK)" -> volume.toPintsUK();
            case "Quarts (UK)" -> volume.toQuartsUK();
            case "Gallons (UK)" -> volume.toGallonsUK();
            default -> -1;
        };
    }

    private double convertLength(double start, String startUnit, String endUnit) {
        UnitOf.Length length = switch (startUnit) {
            case "Nanometers" -> new UnitOf.Length().fromNanometers(start);
            case "Microns" -> new UnitOf.Length().fromMicrons(start);
            case "Millimeters" -> new UnitOf.Length().fromMillimeters(start);
            case "Centimeters" -> new UnitOf.Length().fromCentimeters(start);
            case "Meters" -> new UnitOf.Length().fromMeters(start);
            case "Kilometers" -> new UnitOf.Length().fromKilometers(start);
            case "Inches" -> new UnitOf.Length().fromInches(start);
            case "Feet" -> new UnitOf.Length().fromFeet(start);
            case "Yards" -> new UnitOf.Length().fromYards(start);
            case "Miles" -> new UnitOf.Length().fromMiles(start);
            case "Nautical Miles" -> new UnitOf.Length().fromNauticalMilesUSCustomary(start);
            default -> null;
        };

        if (length == null) {
            return -1;
        }

        return switch (endUnit) {
            case "Nanometers" -> length.toNanometers();
            case "Microns" -> length.toMicrons();
            case "Millimeters" -> length.toMillimeters();
            case "Centimeters" -> length.toCentimeters();
            case "Meters" -> length.toMeters();
            case "Kilometers" -> length.toKilometers();
            case "Inches" -> length.toInches();
            case "Feet" -> length.toFeet();
            case "Yards" -> length.toYards();
            case "Miles" -> length.toMiles();
            case "Nautical Miles" -> length.toNauticalMilesUSCustomary();
            default -> -1;
        };
    }

    private double convertWeightMass(double start, String startUnit, String endUnit) {
        UnitOf.Mass mass = switch (startUnit) {
            case "Carats" -> new UnitOf.Mass().fromCarats(start);
            case "Milligrams" -> new UnitOf.Mass().fromMilligrams(start);
            case "Centigrams" -> new UnitOf.Mass().fromCentigrams(start);
            case "Decigrams" -> new UnitOf.Mass().fromDecigrams(start);
            case "Grams" -> new UnitOf.Mass().fromGrams(start);
            case "Dekagrams" -> new UnitOf.Mass().fromDekagrams(start);
            case "Hectograms" -> new UnitOf.Mass().fromHectograms(start);
            case "Kilograms" -> new UnitOf.Mass().fromKilograms(start);
            case "Metric Tonnes" -> new UnitOf.Mass().fromTonsMetric(start);
            case "Ounces" -> new UnitOf.Mass().fromOuncesUS(start);
            case "Pounds" -> new UnitOf.Mass().fromPounds(start);
            case "Stone" -> new UnitOf.Mass().fromStonesUK(start);
            default -> null;
        };

        if (mass == null) {
            return -1;
        }

        return switch (endUnit) {
            case "Carats" -> mass.toCarats();
            case "Milligrams" -> mass.toMilligrams();
            case "Centigrams" -> mass.toCentigrams();
            case "Decigrams" -> mass.toDecigrams();
            case "Grams" -> mass.toGrams();
            case "Dekagrams" -> mass.toDekagrams();
            case "Hectograms" -> mass.toHectograms();
            case "Kilograms" -> mass.toKilograms();
            case "Metric Tonnes" -> mass.toTonsMetric();
            case "Ounces" -> mass.toOuncesUS();
            case "Pounds" -> mass.toPounds();
            case "Stone" -> mass.toStonesUK();
            default -> -1;
        };
    }

    private double convertTemperature(double start, String startUnit, String endUnit) {
        UnitOf.Temperature temperature = switch (startUnit) {
            case "Celsius" -> new UnitOf.Temperature().fromCelsius(start);
            case "Fahrenheit" -> new UnitOf.Temperature().fromFahrenheit(start);
            case "Kelvin" -> new UnitOf.Temperature().fromKelvin(start);
            default -> null;
        };

        if (temperature == null) {
            return -1;
        }

        return switch (endUnit) {
            case "Celsius" -> temperature.toCelsius();
            case "Fahrenheit" -> temperature.toFahrenheit();
            case "Kelvin" -> temperature.toKelvin();
            default -> -1;
        };
    }

    private double convertEnergy(double start, String startUnit, String endUnit) {
        UnitOf.Energy energy = switch (startUnit) {
            case "Electron Volts" -> new UnitOf.Energy().fromElectronVolts(start);
            case "Joules" -> new UnitOf.Energy().fromJoules(start);
            case "Kilojoules" -> new UnitOf.Energy().fromKilojoules(start);
            case "Thermal Calories" -> new UnitOf.Energy().fromCaloriesThermochemical(start);
            case "Calories" -> new UnitOf.Energy().fromCaloriesNutritional(start);
            case "Foot-Pounds" -> new UnitOf.Energy().fromFootPounds(start);
            case "British Thermal Units" -> new UnitOf.Energy().fromBTUsInternationalStandard(start);
            default -> null;
        };

        if (energy == null) {
            return -1;
        }

        return switch (endUnit) {
            case "Electron Volts" -> energy.toElectronVolts();
            case "Joules" -> energy.toJoules();
            case "Kilojoules" -> energy.toKilojoules();
            case "Thermal Calories" -> energy.toCaloriesThermochemical();
            case "Calories" -> energy.toCaloriesNutritional();
            case "Foot-Pounds" -> energy.toFootPounds();
            case "British Thermal Units" -> energy.toBTUsInternationalStandard();
            default -> -1;
        };
    }

    private double convertArea(double start, String startUnit, String endUnit) {
        UnitOf.Area area = switch (startUnit) {
            case "Square Millimeters" -> new UnitOf.Area().fromSquareMillimeters(start);
            case "Square Centimeters" -> new UnitOf.Area().fromSquareCentimeters(start);
            case "Square Meters" -> new UnitOf.Area().fromSquareMeters(start);
            case "Hectares" -> new UnitOf.Area().fromHectares(start);
            case "Square Kilometers" -> new UnitOf.Area().fromSquareKilometers(start);
            case "Square Inches" -> new UnitOf.Area().fromSquareInches(start);
            case "Square Feet" -> new UnitOf.Area().fromSquareFeet(start);
            case "Square Yards" -> new UnitOf.Area().fromSquareYards(start);
            case "Acres" -> new UnitOf.Area().fromAcres(start);
            case "Square Miles" -> new UnitOf.Area().fromSquareMiles(start);
            default -> null;
        };

        if (area == null) {
            return -1;
        }

        return switch (endUnit) {
            case "Square Millimeters" -> area.toSquareMillimeters();
            case "Square Centimeters" -> area.toSquareCentimeters();
            case "Square Meters" -> area.toSquareMeters();
            case "Hectares" -> area.toHectares();
            case "Square Kilometers" -> area.toSquareKilometers();
            case "Square Inches" -> area.toSquareInches();
            case "Square Feet" -> area.toSquareFeet();
            case "Square Yards" -> area.toSquareYards();
            case "Acres" -> area.toAcres();
            case "Square Miles" -> area.toSquareMiles();
            default -> -1;
        };
    }

    private double convertSpeed(double start, String startUnit, String endUnit) {
        UnitOf.Speed speed = switch (startUnit) {
            case "Centimeters per Second" -> new UnitOf.Speed().fromCentimetersPerSecond(start);
            case "Meters per Second" -> new UnitOf.Speed().fromMetersPerSecond(start);
            case "Kilometers per Hour" -> new UnitOf.Speed().fromKilometersPerHour(start);
            case "Feet per Second" -> new UnitOf.Speed().fromFeetPerSecond(start);
            case "Miles per Hour" -> new UnitOf.Speed().fromMilesPerHour(start);
            case "Knots" -> new UnitOf.Speed().fromKnots(start);
            case "Mach" -> new UnitOf.Speed().fromMach(start);
            default -> null;
        };

        if (speed == null) {
            return -1;
        }

        return switch (endUnit) {
            case "Centimeters per Second" -> speed.toCentimetersPerSecond();
            case "Meters per Second" -> speed.toMetersPerSecond();
            case "Kilometers per Hour" -> speed.toKilometersPerHour();
            case "Feet per Second" -> speed.toFeetPerSecond();
            case "Miles per Hour" -> speed.toMilesPerHour();
            case "Knots" -> speed.toKnots();
            case "Mach" -> speed.toMach();
            default -> -1;
        };
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