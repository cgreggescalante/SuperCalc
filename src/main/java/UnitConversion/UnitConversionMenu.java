package UnitConversion;

import App.SuperCalcApp;
import Interfaces.View;
import Interfaces.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UnitConversionMenu implements View, ViewManager {
    private JButton volumeButton;
    private JButton menuButton;
    private JButton lengthButton;
    private JButton weightMassButton;
    private JButton temperatureButton;
    private JButton energyButton;
    private JButton areaButton;
    private JButton speedButton;
    private JPanel root;

    private final SuperCalcApp parent;

    public UnitConversionMenu(SuperCalcApp parent) {
        this.parent = parent;

        menuButton.addActionListener(this::returnToMenu);

        volumeButton.addActionListener(this::buttonPressed);
        lengthButton.addActionListener(this::buttonPressed);
        weightMassButton.addActionListener(this::buttonPressed);
        temperatureButton.addActionListener(this::buttonPressed);
        energyButton.addActionListener(this::buttonPressed);
        areaButton.addActionListener(this::buttonPressed);
        speedButton.addActionListener(this::buttonPressed);
    }

    @Override
    public void buttonPressed(ActionEvent event) {
        String target = switch (((JButton) event.getSource()).getText()) {
            case "Volume" -> "volumeConversion";
            case "Length" -> "lengthConversion";
            case "Weight and Mass" -> "weightMassConversion";
            case "Temperature" -> "temperatureConversion";
            case "Energy" -> "energyConversion";
            case "Area" -> "areaConversion";
            case "Speed" -> "speedConversion";
            default -> null;
        };

        if (target != null) {
            parent.setPanel(target);
        }
    }

    @Override
    public JPanel getRoot() {
        return root;
    }

    @Override
    public void returnToMenu(ActionEvent event) {
        parent.setPanel("menu");
    }
}
