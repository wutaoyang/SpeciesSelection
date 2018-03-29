package preprocessing;

import java.text.DecimalFormat;

/**
 * Class to record details of testing the processing time of a species selection
 * dataset
 *
 * @author mre16utu
 */
public class PointRecord {

    private final double numSpeciesX;
    private final double mssfSizeY;
    private final double margin;
    private final String fileName;
    private final int    speciesNo;
    private final long   time;

    /**
     * Constructor
     * @param numSpeciesX - number of species in the dataset
     * @param mssfSizeY - MinSpecSelFamily size calculated by SpeciesSelection algorithm
     * @param margin - multiple of expected mssf size based upon fitted exponential curve
     * @param fileName - name of file containing the dataset
     * @param speciesNo - last species in dataset
     * @param time - processing time in milliseconds
     */
    public PointRecord(double numSpeciesX, double mssfSizeY, double margin, String fileName, int speciesNo, long time) {
        this.numSpeciesX = numSpeciesX;
        this.mssfSizeY = mssfSizeY;
        this.margin = margin;
        this.fileName = fileName;
        this.speciesNo = speciesNo;
        this.time = time;
    }

    /**
     * returns number of species in dataset - X value for plotting
     * @return 
     */
    public double getX() {
        return numSpeciesX;
    }

    /**
     * returns MinSpecSetFamily size - Y value for plotting
     * @return 
     */
    public double getY() {
        return mssfSizeY;
    }

    /**
     * returns margin - multiple of predicted mssf size by exponential curve fitting
     * @return 
     */
    public double getMargin() {
        return margin;
    }

    /**
     * name of file containing dataset used
     * @return 
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * last species in the dataset
     * @return 
     */
    public int getSpeciesNo() {
        return speciesNo;
    }

    /**
     * time in milliseconds taken to process dataset
     * @return 
     */
    public long getTime() {
        return time;
    }
    
    /**
     * Converts a number of seconds to a consistently formatted string of the
     * form HH:MM:SS.ss
     * @param seconds
     * @return 
     */
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

    @Override
    public String toString() {
        DecimalFormat df0 = new DecimalFormat("#.##");
        DecimalFormat df2 = new DecimalFormat("0.000");
        String str = "";
        str += String.format("%3s", df0.format(numSpeciesX)) + ","
             + String.format("%8s", df0.format(mssfSizeY)) + ","
             + String.format("%7s", df2.format(margin)) + ",    "
             + fileName + ",   "
             + speciesNo + ",   "
             + toTimeString(time / 1000.0);
        return str;
    }
}
