package trendlines;

/**
 *
 * @author https://stackoverflow.com/questions/17592139/trend-lines-regression-curve-fitting-java-library
 */
public class LogTrendLine extends OLSTrendLine {

    protected double[] xVector(double x) {
        return new double[]{1, Math.log(x)};
    }

    protected boolean logY() {
        return false;
    }
}
