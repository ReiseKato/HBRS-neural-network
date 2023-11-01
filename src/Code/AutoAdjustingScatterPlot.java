package Code;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AutoAdjustingScatterPlot extends JPanel {

    private ArrayList<Double> xData;
    private ArrayList<Double> yData;
    private int padding = 20;

    public AutoAdjustingScatterPlot(ArrayList<Double> xData, ArrayList<Double> yData) {
        this.xData = xData;
        this.yData = yData;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        double xMin = calculateMin(xData);
        double xMax = calculateMax(xData);
        double yMin = calculateMin(yData);
        double yMax = calculateMax(yData);

        double xScale = (width - 2 * padding) / (xMax - xMin);
        double yScale = (height - 2 * padding) / (yMax - yMin);

        g.setColor(Color.LIGHT_GRAY);
        for (int i = 1; i <= 10; i++) {
            int xGrid = (int) (padding + i * (width - 2 * padding) / 10.0);
            g.drawLine(xGrid, padding, xGrid, height - padding);
            int yGrid = (int) (padding + i * (height - 2 * padding) / 10.0);
            g.drawLine(padding, yGrid, width - padding, yGrid);
        }

        g.setColor(Color.BLACK);
        g.drawLine(padding, height - padding, width, height - padding);
        g.drawLine(padding, padding, padding, height - padding);

        g.setColor(Color.BLUE);
        for (int i = 0; i < xData.size(); i++) {
            int x = (int) (padding + (xData.get(i) - xMin) * xScale);
            int y = (int) (height - padding - (yData.get(i) - yMin) * yScale);
            g.fillOval(x - 3, y - 3, 6, 6);
        }

        // X-Achse beschriften
        for (int i = 0; i <= 10; i++) {
            int xLabel = (int) (padding + i * (width - 2 * padding) / 10.0);
            int xValue = (int) (xMin + i * (xMax - xMin) / 10);
            g.drawString(Integer.toString(xValue), xLabel - 5, height - 5);
        }

        // Y-Achse beschriften
        for (int i = 0; i <= 10; i++) {
            int yLabel = (int) (padding + i * (height - 2 * padding) / 10.0);
            int yValue = (int) (yMax - i * (yMax - yMin) / 10);
            g.drawString(Integer.toString(yValue), 5, yLabel + 5);
        }
    }

    private double calculateMax(ArrayList<Double> data) {
        double max = Double.MIN_VALUE;
        for (Double value : data) {
            max = Math.max(max, value);
        }
        return max;
    }

    private double calculateMin(ArrayList<Double> data) {
        double min = Double.MAX_VALUE;
        for (Double value : data) {
            min = Math.min(min, value);
        }
        return min;
    }

    public static void createScatterPlot(ArrayList<Double> xData, ArrayList<Double> yData) {
        JFrame frame = new JFrame("Auto-Adjusting Scatter Plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AutoAdjustingScatterPlot plot = new AutoAdjustingScatterPlot(xData, yData);
        frame.add(plot);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ArrayList<Double> xData = new ArrayList<>();
        ArrayList<Double> yData = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            xData.add((double) i);
            yData.add((double) (i * i));
        }

        createScatterPlot(xData, yData);
    }
}
