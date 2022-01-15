package Calculator;

import App.SuperCalcApp;
import Interfaces.View;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class GraphingCalculator implements View {
    private static final int FUNCTION_RESOLUTION = 100;

    private JButton displayButton;
    private JPanel graphPanel;
    private JPanel root;
    private JButton menuButton;
    private JTextField xMinTextField;
    private JTextField xMaxTextField;
    private JTextField yMaxTextField;
    private JTextField yMinTextField;

    private JPanel equationPanel;
    private JButton addEquationButton;
    private JPanel boundsPanel;
    private JTable expressionTable;

    private JFreeChart chart;

    private final SuperCalcApp parent;
    private final ExpressionTableModel expressionTableModel;

    public GraphingCalculator(SuperCalcApp parent) {
        this.parent = parent;

        displayButton.addActionListener(this::updateGraph);
        menuButton.addActionListener(this::returnToMenu);
        addEquationButton.addActionListener(this::addEquation);

        expressionTableModel = new ExpressionTableModel(0, 0);
        expressionTable.setModel(expressionTableModel);

        expressionTableModel.setColumnIdentifiers(new String[] {"a", "b"});

        addEquation(null);

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

    private void addEquation(ActionEvent event) {
        Vector<Object> row = new Vector<>();
        row.add("f(x) = ");
        row.add("x");

        expressionTableModel.addRow(row);
    }

    private void updateGraph(ActionEvent event) {
        XYDataset ds = createDataset();

        chart = ChartFactory.createXYLineChart("",
                "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                false);

        double y_min = Double.parseDouble(yMinTextField.getText());
        double y_max = Double.parseDouble(yMaxTextField.getText());

        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setRange(y_min, y_max);

        ChartPanel panel = new ChartPanel(chart);

        graphPanel.removeAll();
        graphPanel.setLayout(new BorderLayout());
        graphPanel.add(panel, BorderLayout.NORTH);
        graphPanel.updateUI();
    }

    private XYDataset createDataset() {
        DefaultXYDataset ds = new DefaultXYDataset();

        int count = 1;
        for (int i = 0; i < expressionTableModel.getRowCount(); i++) {
            Expression expression = new Expression((String) expressionTableModel.getValueAt(i, 1));

            int x_min = Integer.parseInt(xMinTextField.getText());
            int x_max = Integer.parseInt(xMaxTextField.getText());

            int points = (x_max - x_min) * FUNCTION_RESOLUTION + 1;


            double[][] data = new double[2][points];

            Map<String, Double> variables = new HashMap<>();

            for (int x = 0; x < points; x++) {
                data[0][x] = ((double) x) / FUNCTION_RESOLUTION + x_min;
                variables.put("x", ((double) x) / FUNCTION_RESOLUTION + x_min);
                data[1][x] = expression.evaluate(variables);
            }

            ds.addSeries(count + " : " + expression, data);
            count++;
        }

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