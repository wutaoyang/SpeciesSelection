/*
 * Indicator Species Selection
 * 
 * The class is designed to generating indicatoir set using the method
 * alling trait exclusion
 */
package speciesselection;

import java.io.*;
import java.util.*;

/**
 *
 * @author taoyang
 */
public class SelectionMethodB implements SelectionMethod {
    
    private ArrayList<ResourceType> remTraits ;
    private SpecRTGraph specRTGraph;

    public SelectionMethodB(String fileName, boolean normSens, boolean sensType, int specThreshold, double sdThreshold) 
            throws FileNotFoundException, SpecSelException, InterruptedException
    {
        exec(fileName, normSens, sensType, specThreshold, sdThreshold, 0);
    }
    
    
    /**
     * @param fileName
     * @param normSens
     * @param specThresholdM
     * @param sdThresholdX
     * @param sensType 
     * @param areaThresholdY  0 as unused
     * @throws java.io.FileNotFoundException
     * @throws speciesselection.SpecSelException
     * @throws java.lang.InterruptedException
     */
    @Override
    public void exec(String fileName, boolean normSens, boolean sensType, int specThresholdM, double sdThresholdX, double areaThresholdY)
            throws FileNotFoundException, SpecSelException, InterruptedException {

        System.out.println("Method B"); 

        //the list to record the indicators that has been processed.
//        ArrayList<Integer> indicatorCreated = new ArrayList<>();

//        String fileName = "example.txt"; //default input data

        //  System.out.println(fileName);   
//        int argStart = 0; //the argument starting point except for the first 
//        //two or three (depending on the submethod) 
//
//        if (args.length < 0) {// SPW: < 0 NOT POSSIBLE???
//            System.out.println("This method need additional arguments");
//            //to exit
//        }

        int option = getOption(specThresholdM, sdThresholdX);

//        if (option < 0 || option > 3) {
//            System.out.println("The option should be 0, 1, 2 or 3");
//            //to exit
//        }

//        int specThreshold = 0;
//        double sdThreshold = 0;
//
//        //remove by number of sensitivity  
//        //option 0 for the basic case
//        if (option == 0) {
//            argStart = 1;
//        }
//
//        if (option == 1) {
//            argStart = 2;
//            specThreshold = Integer.parseInt(args[1]);
//        }
//
//        if (option == 2) {
//            argStart = 2;
//            sdThreshold = Double.parseDouble(args[1]);
//        }
//
//        if (option == 3) {
//            argStart = 3;
//            specThreshold = Integer.parseInt(args[1]);
//            sdThreshold = Double.parseDouble(args[2]);
//        }
//
//        if (args.length > argStart + 0) {
//            fileName = args[argStart + 0];
//        }
//
//        String outFileName = fileName + "_result.txt";  //default file for output
//
//        if (args.length > argStart + 1) {
//            outFileName = args[argStart + 1];
//        }
//
//        System.out.println("The dataset is taken from file " + fileName);

        //construct the bipartite graph between species and indicators.
        specRTGraph = ReadFile.graphConstr(fileName, "None");

        remTraits = new ArrayList<>();

        //processing the options
        if (option == 1) {
            remTraits = specRTGraph.remRTByNum(specThresholdM);
        }

        if (option == 2) {
            remTraits = specRTGraph.remRTBySD(sdThresholdX);
        }

        if (option == 3) {
            remTraits = specRTGraph.remRTByBoth(specThresholdM, sdThresholdX);
        }

        

        //test whether there exists an isolated traits
//        if (sig.noIsolatedResourceType() == false) {
//            System.out.println("There exist isolated traits");
//            PrintStream outPut = new PrintStream(new File(outFileName));
//            outPut.println("The dataset:" + fileName);
//            outPut.println("Warning: Removing resource types leads to method fails!");
//            return null;
//            // System.out.println("There exists isolated species");
//        }

        //dealing with the sensType
        specRTGraph.setSensType(sensType);

        //Dealing with the normalization
        if (normSens == true) {
            specRTGraph.normalizeSensitivities();
        }

    } //end exec
    
    private String testIsolatedSpecies()
    {
        //test whether there exists an isolated species
        if (specRTGraph.isolatedSpecies() == true) {
            return "There exist isolated species\n";
        }
        return "";
    }
    
    private String testForIsolatedTraits()
    {
        //test whether there exists an isolated traits
        if (specRTGraph.noIsolatedResourceType() == false) {
            return "There exist isolated traits\n" +
                   "Warning: Removing resource types leads to method fails!\n";
        }
        return "";
    }
    
    private String getRemovedTraits()
    {
        String str = "";
        // get the removed traits
        str += "The following traits are removed:\n";
        ArrayList<Integer> remIDs = new ArrayList<>();
        for (ResourceType rt : remTraits) {
            remIDs.add(rt.getID());
        }
        Collections.sort(remIDs);
        for (Integer i : remIDs) {
            str+= i + ",";
        }
        str += "\n";
        return str;
    }
    
    @Override
    public String getDetails()
    {
        return testIsolatedSpecies() 
             + testForIsolatedTraits() 
             + getRemovedTraits();
    }
    
    

    private int getOption(int specThreshold, double sdThreshold) {
        
        if(specThreshold > 0 && sdThreshold > 0)
        {
            return 3;
        }
        if(sdThreshold > 0)
        {
            return 2;
        }
        if(specThreshold > 0)
        {
            return 1;
        }
        return 0;        
    }

    @Override
    public SpecRTGraph getSpecRTGraph() {
        return specRTGraph;
    }

}
