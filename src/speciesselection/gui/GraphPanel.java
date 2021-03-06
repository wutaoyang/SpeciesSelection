package speciesselection.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author
 * https://stackoverflow.com/questions/8693342/drawing-a-simple-line-graph-in-java
 * Modified by Stephen Whiddett 1/2/2018
 */
public class GraphPanel extends JPanel {

    private int width = 800;
    private int heigth = 400;
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private List<Double> scores;
    private String yLabel;
    private String xLabel;
    private int offset;

    public GraphPanel(List<Double> scores) {
        this.scores = scores;
        this.xLabel = "";
        this.yLabel = "";
        this.offset = 0;
    }

    public GraphPanel(List<Double> scores, String xLabel, String yLabel) {
        this.scores = scores;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.offset = getOffset(scores);
        System.out.println("Scores: " + scores);
    }

    // Default constructor for testing with dummy data
    public GraphPanel() {
        List<Double> scoreList = new ArrayList<>();
        scoreList.add(1.0);
        scoreList.add(2.0);
        scoreList.add(7.0);
        scoreList.add(4.0);
        scoreList.add(5.0);
        this.scores = scoreList;
        this.xLabel = "";
        this.yLabel = "";
    }

    // get position of first non zero score
    private int getOffset(List<Double> scores) {
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) != 0) {
                return i;
            }
        }
        return 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            double maxScore = getMaxScore();
            double minScore = getMinScore();
            double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1 - offset);
            double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxScore - minScore);

            List<Point> graphPoints = new ArrayList<>();
            for (int i = offset; i < scores.size(); i++) {
                int x1 = (int) ((i - offset) * xScale + padding + labelPadding);
                int y1 = (int) ((maxScore - scores.get(i)) * yScale + padding);
                graphPoints.add(new Point(x1, y1));
            }

            // draw white background
            g2.setColor(Color.WHITE);
            g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
            g2.setColor(Color.BLACK);

            // create hatch marks and grid lines for y axis.
            for (int i = 0; i < numberYDivisions + 1; i++) {
                int x0 = padding + labelPadding;
                int x1 = pointWidth + padding + labelPadding;
                int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
                int y1 = y0;

                // Label y-axis
                if (i == numberYDivisions) {
                    g2.drawString(yLabel, x0 - 30, y0 - 15);
                }

                if (scores.size() > 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                    g2.setColor(Color.BLACK);

                    // label y-axis data points
                    String yLabel = ((int) ((minScore + (maxScore - minScore) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(yLabel);
                    g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }

            // and for x axis
            for (int i = 0; i < scores.size(); i++) {
                if (scores.size() > 1) {
                    int x0 = (i - offset) * (getWidth() - padding * 2 - labelPadding) / (scores.size() - offset - 1) + padding + labelPadding;
                    int x1 = x0;
                    int y0 = getHeight() - padding - labelPadding;
                    int y1 = y0 - pointWidth;

                    // Label x-axis
                    if (i == scores.size() - 1) {
                        g2.drawString(xLabel, x1 - 60, y0 + 40);
                    }

                    if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
                        if (i >= offset)// start x-axis at first plotable point (>0)
                        {
                            g2.setColor(gridColor);
                            g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                            g2.setColor(Color.BLACK);
                            String xLabel = i + "";
                            FontMetrics metrics = g2.getFontMetrics();
                            int labelWidth = metrics.stringWidth(xLabel);
                            g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                        }
                    }
                    g2.drawLine(x0, y0, x1, y1);
                }
            }

            // create x and y axes 
            g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
            g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

            Stroke oldStroke = g2.getStroke();
            g2.setColor(lineColor);
            g2.setStroke(GRAPH_STROKE);
            for (int i = 0; i < graphPoints.size() - 1; i++) {
                int x1 = graphPoints.get(i).x;
                int y1 = graphPoints.get(i).y;
                int x2 = graphPoints.get(i + 1).x;
                int y2 = graphPoints.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }

            g2.setStroke(oldStroke);
            g2.setColor(pointColor);
            for (int i = 0; i < graphPoints.size(); i++) {
                int x = graphPoints.get(i).x - pointWidth / 2;
                int y = graphPoints.get(i).y - pointWidth / 2;
                int ovalW = pointWidth;
                int ovalH = pointWidth;
                g2.fillOval(x, y, ovalW, ovalH);
            }
        } catch (Exception e) {
            System.out.println("Exception in GraphPanel.paintComponent(): " + e);
        }
    }

    private double getMinScore() {
        boolean initialZeros = true;
        double minScore = Double.MAX_VALUE;
        for (Double score : scores) {
            if(initialZeros && score !=0)
            {
                initialZeros = false;
            }
            if (!initialZeros)//ignore initial points at zero
            {
                minScore = Math.min(minScore, score);
            }

        }
        return minScore;
    }

    private double getMaxScore() {
        boolean initialZeros = true;
        double maxScore = -Double.MAX_VALUE;
        for (Double score : scores) {
            if(initialZeros && score !=0)
            {
                initialZeros = false;
            }
            if (!initialZeros)//ignore initial points at zero
            {
                maxScore = Math.max(maxScore, score);
            }
        }
        return maxScore;
    }

    public void setScores(List<Double> scores) {
        this.scores = scores;
        invalidate();
        this.repaint();
    }

    public List<Double> getScores() {
        return scores;
    }

    private static void createAndShowGui() {
        List<Double> scores = new ArrayList<>();
        Random random = new Random();
        int maxDataPoints = 40;
        int maxScore = 10;
        for (int i = 0; i < maxDataPoints; i++) {
            scores.add((double) random.nextDouble() * maxScore);
        }
        GraphPanel mainPanel = new GraphPanel(scores);
        mainPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGui();
            }
        });
    }
}
