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
    private boolean isolatedTraitsFlag;

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

        int option = getOption(specThresholdM, sdThresholdX);

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
        isolatedTraitsFlag = specRTGraph.noIsolatedResourceType();

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
        if (!isolatedTraitsFlag) {
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
