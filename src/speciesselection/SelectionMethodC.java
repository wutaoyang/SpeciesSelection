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
public class SelectionMethodC {

    /**
     * @param args the command line arguments The first argument is the name for
     * the input file The second argument is the name for the output file The
     * third argument is for the number of species sets for each size The fourth
     * argument for the minimal size of species in a species set The fifth
     * argument for the maximal size of species in a species set
     * @param normSens
     * @param sensType
     * @throws java.io.FileNotFoundException
     */
    public static void exec(String[] args, boolean normSens, boolean sensType)
            throws FileNotFoundException, InterruptedException, SpecSelException {

        //test
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        System.out.println("Method C");    

        //the list to record the indicators that has been processed.
//        ArrayList<Integer> indicatorCreated = new ArrayList<>();

        String fileName = "example.txt"; //default input data           

        int argStart = 0; //the argument starting point except for the first 
        //two or three (depending on the submethod) 

        if (args.length < 2) {
            System.out.println("This method need additional arguments");
            //to exit
        }

        double areaThreshold;
        areaThreshold = Double.parseDouble(args[0]);

        System.out.println("The area Threshold is " + areaThreshold);

        int option = Integer.parseInt(args[1]);

        if (option < 0 || option > 3) {
            System.out.println("The option should be 0, 1, 2 or 3");
            //to exit
        }

        int specThreshold = 0;
        double sdThreshold = 0;

        //remove by number of sensitivity    
        argStart = 2; //default value

        if (option == 1) {
            argStart = 3;
            specThreshold = Integer.parseInt(args[2]);
        }

        if (option == 2) {
            argStart = 3;
            sdThreshold = Double.parseDouble(args[2]);
        }

        if (option == 3) {
            argStart = 4;
            specThreshold = Integer.parseInt(args[2]);
            sdThreshold = Double.parseDouble(args[3]);
        }

        if (args.length > argStart + 0) {
            fileName = args[argStart + 0];

        }

        String outFileName = fileName + "_result.txt";  //default file for output

        if (args.length > argStart + 1) {
            outFileName = args[argStart + 1];
        }

        System.out.println("The dataset is taken from file " + fileName);

        //construct the bipartite graph between species and indicators.
        SpecRTGraph sig;
        //read the graph from the file   
        sig = ReadFile.graphConstr(fileName, "Areas");

        //the list to record the species removed
        ArrayList<Species> speciesRemoved;
        //remove species using the threshold      
        speciesRemoved = sig.remSpecByAreas(areaThreshold);

        //test whether there exists an isolated traits
        if (sig.noIsolatedResourceType() != true) {
            System.out.println("There exist isolated traits");
            PrintStream outPut = new PrintStream(new File(outFileName));
            outPut.println("The dataset:" + fileName);
            outPut.println("Warning: Removing species leads to method fails!");
            return;
        }

        ArrayList<ResourceType> remTraits = new ArrayList<>();

        //processing the options
        if (option == 1) {
            remTraits = sig.remRTByNum(specThreshold);
        }

        if (option == 2) {
            remTraits = sig.remRTBySD(sdThreshold);
        }

        if (option == 3) {
            remTraits = sig.remRTByBoth(specThreshold, sdThreshold);
        }

        //test whether there exists an isolated species
        if (sig.isolatedSpecies() == true) {
            System.out.println("Warning: There exist isolated species");
        }

        //dealing with the sensType
        sig.setSensType(sensType);

        //Dealing with the normalization
        if (normSens == true) {
            sig.normalizeSensitivities();
        }

        //Output the results to a file
        PrintStream outPut = new PrintStream(new File(outFileName));
        outPut.println("The dataset:" + fileName);
        outPut.println("The dataset contains " + sig.numSpecies() + " species and "
                + sig.numResourceTypes() + " resource types");

        //output the removed species
        outPut.println("The following species are removed");
        for (Species sp : speciesRemoved) {
            outPut.print(sp + ",");
        }
        outPut.println();

        //output the remove traits
        outPut.println("The following traits are removed");

        ArrayList<Integer> remIDs = new ArrayList<>();
        for (ResourceType rt : remTraits) {
            remIDs.add(rt.getID());
        }

        Collections.sort(remIDs);

        for (Integer i : remIDs) {
            outPut.print(i + ",");
        }
        outPut.println();
        int startSize = 2;
        //int endSize=1;
        int endSize = sig.numSpecies() - 1;

        int numOfSpeciesSet = 11; //the number of species sets for 
        //each size, 11 is the default size;

        if (args.length > 2 + argStart) {
            numOfSpeciesSet = Integer.parseInt(args[2 + argStart]) + 1;
        }

        if (args.length > 3 + argStart) {
            startSize = Integer.parseInt(args[3 + argStart]);
            endSize = startSize;
        }

        if (args.length > 4 + argStart) {
            endSize = Integer.parseInt(args[4 + argStart]);
        }

        MinSpecSetFamily mssf = sig.getMinDomSpecSets();

        for (int i = startSize; i <= endSize; i++) {
            //  System.out.println("In the main loop "+i);
            SpecSetFamily consFamily = sig.getDomSpecSets(i, numOfSpeciesSet, mssf);
            outPut.println("For " + i + "  species");
            outPut.println(consFamily);
        }

        System.out.println("The output is stored in " + outFileName);

    }

}
