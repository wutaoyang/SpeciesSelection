package preprocessing;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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

    private final List<PointRecord> pointRecords;
    private final PropertyChangeSupport pcs;
    public static final int MIN_POINTS_TO_FIT_CURVE = 3;

    /**
     * Constructor
     */
    public PlotPoints() {
        pointRecords = new ArrayList<>();
        pcs          = new PropertyChangeSupport(this);
    }
    
    /**
     * returns list of species numbers from point records margin greater than 
     * margin calculated with specified expDivergence
     * @param expDivergence
     * @return 
     */
    public ArrayList<Integer> getSpeciesByMargin(int expDivergence)
    {
        double margin = (100 + expDivergence)/100.0;
        ArrayList<Integer> species = new ArrayList<>();
        for (int i = 0; i < pointRecords.size(); i++)
        {
            PointRecord record = pointRecords.get(i);
            double currMargin = record.getMargin();
            if(currMargin > margin)
            {
                species.add(record.getSpeciesNo());
            }
        }
        return species;
    }

    /**
     * adds a new plot point and fires a property changed event
     * @param x - number of species in the dataset
     * @param y - minSpecSetFamily size
     * @param fileName
     * @param speciesNo
     * @param time 
     */
    public void addPoint(double x, double y, String fileName, int speciesNo, long time) {
        PointRecord pointRecord = new PointRecord(x, y, calcMargin(), fileName, speciesNo, time);
        pointRecords.add(pointRecord);
        pcs.firePropertyChange("size", null, this.size());
    }

    /**
     * Calculates the margin of error (multiplication factor) for the last added
     * point in comparison to a fitted exponential trendline
     * @return 
     */
    private double calcMargin() {
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
            return errMargin;
        } else {
            return 0.0;
        }
    }

    /**
     * allow listener to be added to a PlotPoints object so that property 
     * changes can be used to fire additional actions
     * @param listener 
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * allows removal of listener
     * @param listener 
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
    
    /**
     * returns number of point records
     * @return 
     */
    public int size() {
        return pointRecords.size();
    }
    
    /**
     * returns the margin from the last point record added
     * @return 
     */
    public double getLastMargin() {
        return pointRecords.get(pointRecords.size() - 1).getMargin();
    }

    /**
     * returns array of x values
     * @return 
     */
    public double[] getXArr() {
        List<Double> xList = new ArrayList<>();
        for(PointRecord point : pointRecords)
        {
            xList.add(point.getX());
        }
        return doubleListToArray(xList);
    }

    /**
     * returns array of y values
     * @return 
     */
    public double[] getYArr() {
        List<Double> yList = new ArrayList<>();
        for(PointRecord point : pointRecords)
        {
            yList.add(point.getY());
        }
        return doubleListToArray(yList);
    }

    /**
     * converts List<Double> to double[]
     * @param list
     * @return 
     */
    private double[] doubleListToArray(List<Double> list) {
        double[] target = new double[list.size()];
        for (int i = 0; i < target.length; i++) {
            target[i] = list.get(i);
        }
        return target;
    }
    
    /**
     * Creates padding based on length of data file name
     * @param spacing
     * @return 
     */
    private String padding(int spacing)
    {
        if(!pointRecords.isEmpty())
        {
            spacing += pointRecords.get(0).getFileName().length();
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
        String str = "Plot Points:\n "
                + " X"
                + "          Y"
                + "      Margin"
                + "       DataFile"
                + padding(-5)
                + "sNo"
                + "       Time";
        for (int i = 0; i < pointRecords.size(); i++) {
            str += "\n" + pointRecords.get(i);
        }
        return str;
    }
}
