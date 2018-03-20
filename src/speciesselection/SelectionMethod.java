package speciesselection;

import java.io.FileNotFoundException;

/**
 *
 * @author mre16utu
 */
public interface SelectionMethod {
    
   
    public void exec(String fileName, boolean normSens, boolean sensType, int specThresholdM, double sdThresholdX, double areaThresholdY)
            throws FileNotFoundException, SpecSelException, InterruptedException;


    public SpecRTGraph getSpecRTGraph();
    
    public String getDetails();
    

}
