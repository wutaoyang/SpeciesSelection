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
    
    private final List<Double>  xList;
    private final List<Double>  yList;
    private final List<Double>  margins;
    private final List<String>  fileNames;
    private final List<Integer> speciesNos;
    private final List<Long>    times;
    private final PropertyChangeSupport pcs;
    public static final int MIN_POINTS_TO_FIT_CURVE = 3;

    public PlotPoints() {
        xList      = new ArrayList<>();
        yList      = new ArrayList<>();
        margins    = new ArrayList<>();
        fileNames  = new ArrayList<>();
        speciesNos = new ArrayList<>();
        times      = new ArrayList<>();
        pcs        = new PropertyChangeSupport(this);
    }
    
    public ArrayList<Integer> getSpeciesByMargin(int expDivergence)
    {
        double margin = (100 + expDivergence)/100.0;
        ArrayList<Integer> species = new ArrayList<>();
        for (int i = 0; i < margins.size(); i++)
        {
            double currMargin = margins.get(i);
            if(currMargin > margin)
            {
                species.add(speciesNos.get(i));
            }
        }
        return species;
    }

    // adds a new plot point and fires a property changed event
    public void addPoint(double x, double y, String fileName, int speciesNo, long time) {
        xList.add(x);
        yList.add(y);
        fileNames.add(fileName);
        speciesNos.add(speciesNo);
        times.add(time);
        setMargin();
        pcs.firePropertyChange("size", null, this.size());
    }

    // Calculates the margin of error (multiplication factor) for the last added
    // point in comparison to a fitted exponential trendline
    private void setMargin() {
        if (this.size() > MIN_POINTS_TO_FIT_CURVE) {
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

    // allow listener to be added to a PlotPoints object so that property 
    // changes can be used to fire additional actions
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

    // converts List<Double> to double[]
    private double[] doubleListToArray(List<Double> list) {
        double[] target = new double[list.size()];
        for (int i = 0; i < target.length; i++) {
            target[i] = list.get(i);
        }
        return target;
    }
    
    // Converts a number of seconds to a consistently formatted string of the 
    // form HH:MM:SS.ss
    private String toTimeString(double seconds) {
        int hours   =  (int) seconds / 3600;
        int minutes = ((int) seconds % 3600) / 60;
        seconds     = seconds % 60;
        DecimalFormat df1 = new DecimalFormat("00");
        DecimalFormat df2 = new DecimalFormat("00.00");
        String time       = df1.format(hours) + ":"
                          + df1.format(minutes) + ":"
                          + df2.format(seconds);
        return time;
    }
    
    // Creates padding based on length of data file name
    private String padding(int spacing)
    {
        if(!fileNames.isEmpty())
        {
            spacing += fileNames.get(0).length();
        }
        spacing = Math.max(spacing, 1);//avoid spacing less than 1
        String padding = "";
        for(int i = 0; i < spacing; i++)
        {
            padding += " ";
        }
        return padding;
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
                + padding(-5)
                + "sNo"
                + "       Time";
        for (int i = 0; i < xList.size(); i++) {
            str += "\n" 
                    + String.format("%3s", df0.format(xList.get(i))) + "," 
                    + String.format("%8s", df0.format(yList.get(i))) + "," 
                    + String.format("%7s", df2.format(margins.get(i))) + ",    " 
                    + fileNames.get(i)+ ",   " 
                    + speciesNos.get(i)+ ",   " 
                    + toTimeString(times.get(i)/1000.0);
        }
        return str;
    }
}
