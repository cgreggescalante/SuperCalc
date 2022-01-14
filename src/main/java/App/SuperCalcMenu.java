package App;

import Interfaces.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static java.lang.System.exit;

public class SuperCalcMenu implements View {
    private JPanel root;
    private JButton calculatorButton;
    private JButton probabilityButton;

    SuperCalcApp parent;

    public SuperCalcMenu(SuperCalcApp parent) {
        this.parent = parent;

        calculatorButton.addActionListener(this::buttonPressed);
        probabilityButton.addActionListener(this::buttonPressed);
    }

    private void buttonPressed(ActionEvent event) {
        if (event.getSource() == calculatorButton) {
            parent.setPanel("calculator");
        } else if (event.getSource() == probabilityButton) {
            parent.setPanel("probability");
        }
    }

    public void returnToMenu(ActionEvent event) {
        exit(0);
    }

    @Override
    public JPanel getRoot() {
        return root;
    }
}
