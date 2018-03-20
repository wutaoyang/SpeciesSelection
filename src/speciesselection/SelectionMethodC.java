/*
 * Indicator Species Selection
 * 
 * The class is designed to generating indicator set with 
 * area threshold to exclude some species
 * (more todo)
 */
package speciesselection;

import java.io.*;
import java.util.*;

/**
 *
 * @author taoyang
 */
public class SelectionMethodC implements SelectionMethod {

    private SpecRTGraph specRTGraph;
    private ArrayList<ResourceType> remTraits ;//the list to record the RT's removed
    private ArrayList<Species> speciesRemoved;//the list to record the species removed
    private boolean isolatedRTFlag;
    
    public SelectionMethodC(String fileName, boolean normSens, boolean sensType, int specThreshold, double sdThreshold, double areaThresholdY) 
            throws FileNotFoundException, SpecSelException, InterruptedException
    {
        exec(fileName, normSens, sensType, specThreshold, sdThreshold, areaThresholdY);
    }
    
    /**
     * @param fileName
     * @param normSens
     * @param sensType
     * @param specThresholdM
     * @param sdThresholdX
     * @param areaThresholdY
     * @throws java.io.FileNotFoundException
     * @throws java.lang.InterruptedException
     * @throws speciesselection.SpecSelException
     */
    @Override
    public void exec(String fileName, boolean normSens, boolean sensType, int specThresholdM, double sdThresholdX, double areaThresholdY)
            throws FileNotFoundException, InterruptedException, SpecSelException {

        //test
//        for (int i = 0; i < args.length; i++) {
//            System.out.println(args[i]);
//        }

        System.out.println("Method C");    

        //the list to record the indicators that has been processed.
//        ArrayList<Integer> indicatorCreated = new ArrayList<>();

//        String fileName = "example.txt"; //default input data           

//        int argStart = 0; //the argument starting point except for the first 
//        //two or three (depending on the submethod) 
//
//        if (args.length < 2) {
//            System.out.println("This method need additional arguments");
//            //to exit
//        }

//        double areaThreshold;
//        areaThreshold = Double.parseDouble(args[0]);

        System.out.println("The area Threshold is " + areaThresholdY);

        int option = getOption(specThresholdM, sdThresholdX);

//        if (option < 0 || option > 3) {
//            System.out.println("The option should be 0, 1, 2 or 3");
//            //to exit
//        }

//        int specThreshold = 0;
//        double sdThreshold = 0;

        //remove by number of sensitivity    
//        argStart = 2; //default value
//
//        if (option == 1) {
//            argStart = 3;
//            specThreshold = Integer.parseInt(args[2]);
//        }
//
//        if (option == 2) {
//            argStart = 3;
//            sdThreshold = Double.parseDouble(args[2]);
//        }
//
//        if (option == 3) {
//            argStart = 4;
//            specThreshold = Integer.parseInt(args[2]);
//            sdThreshold = Double.parseDouble(args[3]);
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
        specRTGraph = ReadFile.graphConstr(fileName, "Areas");// TODO THERE SHOULD BE OPTION FOR PRECISIONS BUT THE METHOD IS THE SAME SO IT HAS NOT BEEN FULLY IMPLEMENTED

        
        //remove species using the threshold      
        speciesRemoved = specRTGraph.remSpecByAreas(areaThresholdY);

        //test whether there exists an isolated RT
        isolatedRTFlag = specRTGraph.noIsolatedResourceType();
        

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


        //dealing with the sensType
        specRTGraph.setSensType(sensType);

        //Dealing with the normalization
        if (normSens == true) {
            specRTGraph.normalizeSensitivities();
        }

        //Output the results to a file
//        PrintStream outPut = new PrintStream(new File(outFileName));
//        outPut.println("The dataset:" + fileName);
//        outPut.println("The dataset contains " + specRTGraph.numSpecies() + " species and "
//                + specRTGraph.numResourceTypes() + " resource types");

        
        
//        outPut.println();
//        int startSize = 2;
//        int endSize = specRTGraph.numSpecies() - 1;
//
//        int numOfSpeciesSet = 11; //the number of species sets for 
//        //each size, 11 is the default size;
//
//        if (args.length > 2 + argStart) {
//            numOfSpeciesSet = Integer.parseInt(args[2 + argStart]) + 1;
//        }
//
//        if (args.length > 3 + argStart) {
//            startSize = Integer.parseInt(args[3 + argStart]);
//            endSize = startSize;
//        }
//
//        if (args.length > 4 + argStart) {
//            endSize = Integer.parseInt(args[4 + argStart]);
//        }
//
//        MinSpecSetFamily mssf = specRTGraph.getMinDomSpecSets();
//
//        for (int i = startSize; i <= endSize; i++) {
//            SpecSetFamily consFamily = specRTGraph.getDomSpecSets(i, numOfSpeciesSet, mssf);
//            outPut.println("For " + i + "  species");
//            outPut.println(consFamily);
//        }
//        System.out.println("The output is stored in " + outFileName);
    }

    private String testIsolatedRT()
    {
        String str = "";
        if (isolatedRTFlag != true) {
            str += "There exist isolated ResourceTypes\n" +
                   "Warning: Removing species leads to method fails!\n";
        }
        return str;
    }
    
    private String testIsolatedSpecies()
    {
        //test whether there exists an isolated species
        if (specRTGraph.isolatedSpecies() == true) {
            return "There exist isolated species\n";
        }
        return "";
    }
    
    private String getRemovedSpecies()
    {
        String str = "";
        //output the removed species
        str += "The following species are removed";
        for (Species sp : speciesRemoved) {
            str += sp + ",";
        }
        str += "\n";
        return str;
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
    public SpecRTGraph getSpecRTGraph() {
        return specRTGraph;
    }

    @Override
    public String getDetails() {
        return testIsolatedRT() + testIsolatedSpecies() + getRemovedSpecies() + getRemovedTraits();
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
}
