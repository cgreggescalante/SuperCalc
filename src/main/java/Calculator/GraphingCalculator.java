package Calculator;

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

public class GraphingCalculator extends JFrame {

    private JTextField equationTextField;
    private JButton displayButton;
    private JPanel graphPanel;
    private JPanel root;

    private JFreeChart chart;

    public GraphingCalculator() {
        displayButton.addActionListener(this::updateGraph);
        XYDataset ds = createDataset();
        chart = ChartFactory.createXYLineChart("Test Chart",
                "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                false);

        ChartPanel panel = new ChartPanel(chart);

        graphPanel.removeAll();
        graphPanel.setLayout(new BorderLayout());
        graphPanel.add(panel, BorderLayout.NORTH);
        graphPanel.updateUI();
        add(root);

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void updateGraph(ActionEvent event) {
        XYDataset ds = createDataset();

        chart = ChartFactory.createXYLineChart("Test Chart",
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

        DefaultXYDataset ds = new DefaultXYDataset();

        double[][] data = new double[2][1000];

        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 0d);

        for (int x = 0; x < 1000; x++) {
            data[0][x] = x / 100d;
            variables.put("x", x / 100d);
            data[1][x] = expression.evaluate(variables);
        }

        ds.addSeries("series1", data);

        return ds;
    }

    public static void main(String[] args) {
        new GraphingCalculator();
    }
}