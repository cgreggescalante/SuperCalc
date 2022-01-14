package Calculator;

import App.SuperCalcApp;
import Interfaces.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class Calculator implements View {
    private JTextField expressionTextField;

    private JPanel root;
    private JButton evaluateButton;
    private JTable variableTable;
    private JButton loadButton;
    private JLabel resultField;
    private JButton menuButton;

    private final VariableTableModel variableTableModel;

    private final SuperCalcApp parent;

    private Expression expression;

    public Calculator(SuperCalcApp parent) {
        this.parent = parent;

        evaluateButton.addActionListener(this::evaluateButtonPressed);
        loadButton.addActionListener(this::loadButtonPressed);

        variableTableModel = new VariableTableModel(0, 0);

        String[] header = new String[] {"Variable", "Value"};
        variableTableModel.setColumnIdentifiers(header);
        variableTable.setModel(variableTableModel);
        variableTable.setVisible(false);
        menuButton.addActionListener(this::returnToMenu);
    }

    private void loadButtonPressed(ActionEvent event) {
        expression = new Expression(expressionTextField.getText());

        Set<String> variables = expression.getVariables();

        variableTable.setVisible(variables.size() > 0);

        while (variableTableModel.getRowCount() > 0) {
            variableTableModel.removeRow(0);
        }

        for (String variable : variables) {
            Vector<Object> row = new Vector<>();
            row.add(variable);
            row.add(0);
            variableTableModel.addRow(row);
        }
    }

    private void evaluateButtonPressed(ActionEvent event) {
        Map<String, Double> variables = new HashMap<>();

        for (int i = 0; i < variableTableModel.getRowCount(); i++) {
            variables.put((String) variableTableModel.getValueAt(i, 0), Double.valueOf(String.valueOf(variableTableModel.getValueAt(i, 1))));
        }

        double result = expression.evaluate(variables);
        resultField.setText(Double.toString(result));
    }

    @Override
    public JPanel getRoot() {
        return root;
    }

    public void returnToMenu(ActionEvent event) {
        parent.setPanel("menu");
    }
}
