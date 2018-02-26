package preprocessing;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import trendlines.ExpTrendLine;
import trendlines.TrendLine;

/**
 * Class representing a set of x,y coordinates for a 2D plot with support to
 * listen for changes to the size of the list
 *
 * @author mre16utu
 */
public class PlotPoints {

    private PropertyChangeSupport pcs;
    private List<Double> xList;
    private List<Double> yList;
    private List<Double> margins;
    private List<String> fileNames;
    private List<Long>   times;
    public static final int minPointsToFitCurve = 3;

    public PlotPoints() {
        xList     = new ArrayList<>();
        yList     = new ArrayList<>();
        margins   = new ArrayList<>();
        fileNames = new ArrayList<>();
        times     = new ArrayList<>();
        pcs       = new PropertyChangeSupport(this);
    }

    public void addPoint(double x, double y, String fileName, long time) {
        xList.add(x);
        yList.add(y);
        fileNames.add(fileName);
        times.add(time);
        setMargin();
        pcs.firePropertyChange("size", null, this.size());
    }

    // Calculates the margin of error (multiplication factor) for the last added
    // point in comparison to a fitted exponential trendline
    private void setMargin() {
        if (this.size() > minPointsToFitCurve) {
            double[] xArr = this.getXArr();
            double[] yArr = this.getYArr();
            TrendLine t = new ExpTrendLine();
            t.setValues(yArr, xArr);
            double xLast = xArr[xArr.length - 1];
            double yLast = yArr[yArr.length - 1];
            // error factor = measurement / prediction
            double errMargin = yLast / t.predict(xLast);
            System.err.println("Margin: " + errMargin);
            margins.add(errMargin);
        } else {
            margins.add(0.0);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    public int size() {
        return xList.size();
    }
    
    public double getLastMargin() {
        return margins.get(margins.size() - 1);
    }

    public double[] getXArr() {
        return doubleListToArray(xList);
    }

    public double[] getYArr() {
        return doubleListToArray(yList);
    }

    private double[] doubleListToArray(List<Double> list) {
        double[] target = new double[list.size()];
        for (int i = 0; i < target.length; i++) {
            target[i] = list.get(i);
        }
        return target;
    }
    
    private String toTimeString(double seconds) {
        int hours = (int) seconds / 3600;
        int minutes = ((int) seconds % 3600) / 60;
        seconds = seconds % 60;
        DecimalFormat df1 = new DecimalFormat("00");
        DecimalFormat df2 = new DecimalFormat("00.00");
        String time = df1.format(hours) + ":"
                + df1.format(minutes) + ":"
                + df2.format(seconds);
        return time;
    }

    @Override
    public String toString() {
        DecimalFormat df0 = new DecimalFormat("#.##");
        DecimalFormat df2 = new DecimalFormat("0.000");
        String str = "Plot Points:\n "
                + " X"
                + "          Y"
                + "      Margin"
                + "       DataFile"
                + "               Time";
        for (int i = 0; i < xList.size(); i++) {
            str += "\n" 
                    + String.format("%3s", df0.format(xList.get(i))) + "," 
                    + String.format("%8s", df0.format(yList.get(i))) + "," 
                    + String.format("%7s", df2.format(margins.get(i))) + ",    " 
                    + fileNames.get(i)+ ",   " 
                    + toTimeString(times.get(i)/1000.0);
        }
        return str;
    }

}
