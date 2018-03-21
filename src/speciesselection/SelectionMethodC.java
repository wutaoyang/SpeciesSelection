/*
 * Indicator Species Selection
 * 
 * The class is designed to generating indicator set with 
 * area threshold to exclude some species
 * (more todo)
 * Precision is partially implemented but has not been finished since the 
 * methodology is the same as for areas. It should still work so long as the 
 * input file contains a single column of either area or precision
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
    private ArrayList<ResourceType> remTraits;//the list to record the RT's removed
    private ArrayList<Species> speciesRemoved;//the list to record the species removed
    private boolean isolatedRTFlag;

    public SelectionMethodC(String fileName, boolean normSens, boolean sensType, int specThreshold, double sdThreshold, double areaThresholdY)
            throws FileNotFoundException, SpecSelException, InterruptedException {
        remTraits = new ArrayList<>();
        speciesRemoved = new ArrayList<>();
        isolatedRTFlag = true;
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

        System.out.println("Method C");
        System.out.println("The area Threshold is " + areaThresholdY);

        int option = getOption(specThresholdM, sdThresholdX);

        //construct the bipartite graph between species and indicators.
        specRTGraph = ReadFile.graphConstr(fileName, Constants.AREAS);// TODO THERE SHOULD BE OPTION FOR PRECISIONS BUT THE METHOD IS THE SAME SO IT HAS NOT BEEN FULLY IMPLEMENTED

        if (null != specRTGraph) {
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
        }

    }

    private String testIsolatedRT() {
        String str = "";
        if (isolatedRTFlag != true) {
            str += "There exist isolated ResourceTypes\n"
                    + "Warning: Removing species leads to method fails!\n";
        }
        return str;
    }

    private String testIsolatedSpecies() {
        //test whether there exists an isolated species
        if (specRTGraph.isolatedSpecies() == true) {
            return "There exist isolated species\n";
        }
        return "";
    }

    private String getRemovedSpecies() {
        String str = "";
        //output the removed species
        str += "The following species are removed";
        for (Species sp : speciesRemoved) {
            str += sp + ",";
        }
        str += "\n";
        return str;
    }

    private String getRemovedTraits() {
        String str = "";
        // get the removed traits
        str += "The following traits are removed:\n";
        ArrayList<Integer> remIDs = new ArrayList<>();
        for (ResourceType rt : remTraits) {
            remIDs.add(rt.getID());
        }
        Collections.sort(remIDs);
        for (Integer i : remIDs) {
            str += i + ",";
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

        if (specThreshold > 0 && sdThreshold > 0) {
            return 3;
        }
        if (sdThreshold > 0) {
            return 2;
        }
        if (specThreshold > 0) {
            return 1;
        }
        return 0;
    }
}
