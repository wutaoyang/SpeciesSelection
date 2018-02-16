package preprocessing;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mre16utu
 */
public class PlotPoints {

    List<Double> xList;
    List<Double> yList;

    public PlotPoints() {
        xList = new ArrayList<>();
        yList = new ArrayList<>();
    }
    
    public void addPoint(double x, double y)
    {
        xList.add(x);
        yList.add(y);
    }

    public int size()
    {
        return xList.size();
    }
    
    public double[] getXArr()
    {
        return doubleListToArray(xList);
    }
    
    public double[] getYArr()
    {
        return doubleListToArray(yList);
    }
    
    private double[] doubleListToArray(List<Double> list) {
        double[] target = new double[list.size()];
        for (int i = 0; i < target.length; i++) {
            target[i] = list.get(i);
        }
        return target;
    }
    
    @Override
    public String toString()
    {
        return "MyPoints";
    }

}
