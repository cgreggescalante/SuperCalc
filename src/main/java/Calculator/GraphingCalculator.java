package Calculator;

import App.SuperCalcApp;
import Interfaces.View;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class GraphingCalculator implements View {
    private static final int FUNCTION_RESOLUTION = 100;

    private JTextField equationTextField;
    private JButton displayButton;
    private JPanel graphPanel;
    private JPanel root;
    private JButton menuButton;
    private JTextField xMinTextField;
    private JTextField xMaxTextField;
    private JTextField yMaxTextField;
    private JTextField yMinTextField;

    private JFreeChart chart;

    private final SuperCalcApp parent;

    public GraphingCalculator(SuperCalcApp parent) {
        this.parent = parent;

        displayButton.addActionListener(this::updateGraph);
        menuButton.addActionListener(this::returnToMenu);

        XYDataset ds = createDataset();
        chart = ChartFactory.createXYLineChart("",
                "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                false);

        ChartPanel panel = new ChartPanel(chart);

        graphPanel.removeAll();
        graphPanel.setLayout(new BorderLayout());
        graphPanel.add(panel, BorderLayout.NORTH);
        graphPanel.updateUI();
    }

    private void updateGraph(ActionEvent event) {
        XYDataset ds = createDataset();

        chart = ChartFactory.createXYLineChart("",
                "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                false);

        ChartPanel panel = new ChartPanel(chart);

        graphPanel.removeAll();
        graphPanel.setLayout(new BorderLayout());
        graphPanel.add(panel, BorderLayout.NORTH);
        graphPanel.updateUI();
    }

    private XYDataset createDataset() {
        Expression expression = new Expression(equationTextField.getText());

        int x_min = Integer.parseInt(xMinTextField.getText());
        int x_max = Integer.parseInt(xMaxTextField.getText());

        int points = (x_max - x_min) * FUNCTION_RESOLUTION + 1;
        DefaultXYDataset ds = new DefaultXYDataset();

        double[][] data = new double[2][points];

        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 0d);

        for (int x = 0; x < points; x++) {
            data[0][x] = ((double) x) / FUNCTION_RESOLUTION + x_min;
            variables.put("x", ((double) x) / FUNCTION_RESOLUTION + x_min);
            data[1][x] = expression.evaluate(variables);
        }

        ds.addSeries("series1", data);

        return ds;
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