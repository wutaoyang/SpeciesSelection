package trendlines;

import java.util.Random;

/**
 *
 * @author mre16utu
 */
public class TestTrendLines {

    public static void main(String[] args) {
//        testPolyTrendLine();
        testExpTrendLine();
        
    }
    
    private static void testExpTrendLine() {
        TrendLine t = new ExpTrendLine();
        Random rand = new Random();
        double[] x = new double[5];
        double[] y = new double[x.length];
        
        x[0] = 30;
        x[1] = 35;
        x[2] = 40;
        x[3] = 45;
        x[4] = 50;
        y[0] = 1195;
        y[1] = 5869;
        y[2] = 11469;
        y[3] = 18602;
        y[4] = 30098;
        
        
        t.setValues(y, x);
        System.out.println(t.predict(51));
    }
    
    private static void testPolyTrendLine()
    {
        TrendLine t = new PolyTrendLine(2);
        Random rand = new Random();
        double[] x = new double[1000 * 1000];
        double[] err = new double[x.length];
        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            x[i] = 1000 * rand.nextDouble();
        }
        for (int i = 0; i < x.length; i++) {
            err[i] = 100 * rand.nextGaussian();
        }
        for (int i = 0; i < x.length; i++) {
            y[i] = x[i] * x[i] + err[i];
        } // quadratic model
        t.setValues(y, x);
        System.out.println(t.predict(12)); // when x=12, y should be... , eg 143.61380202745192
    }

    

}
