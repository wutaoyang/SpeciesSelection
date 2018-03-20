/*
 * Indicator Species Selection
 * 
 * The class is designed to generating indicator set using the method
 * published on PLOS one
 */
package speciesselection;

import java.io.*;

/**
 *
 * @author taoyang
 */
public class SelectionMethodA implements SelectionMethod {
    
    private SpecRTGraph specRTGraph;
    
    public SelectionMethodA(String fileName) throws FileNotFoundException, SpecSelException, InterruptedException
    {
        exec(fileName, false, false, 0, 0, 0);
    }
    
    
//    public void exec(String fileName) throws FileNotFoundException, SpecSelException, InterruptedException {
//        exec(fileName, false, false, 0, 0);
//    }
    
    /**
     * @param fileName
     * @param normSens false
     * @param sensType false
     * @param specThreshold 0 as unused
     * @param sdThreshold  0 as unused
     * @param areaThresholdY 0 as unused
     * @throws java.io.FileNotFoundException
     * @throws speciesselection.SpecSelException
     * @throws java.lang.InterruptedException
     */
    @Override
    public void exec(String fileName, boolean normSens, boolean sensType, int specThreshold, double sdThreshold, double areaThresholdY) throws FileNotFoundException, SpecSelException, InterruptedException {

        System.out.println("Method A");

        //read the graph from the file   
        specRTGraph = ReadFile.graphConstr(fileName, "None");

    } //end exec


    @Override
    public SpecRTGraph getSpecRTGraph() {
        return specRTGraph;
    }

    @Override
    public String getDetails() {
        return "";
    }

}
