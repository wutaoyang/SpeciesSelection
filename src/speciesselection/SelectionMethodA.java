/*
 * Indicator Species Selection
 * 
 * The class is designed to generating indicator set using the method
 * published on PLOS one
 */
package speciesselection;

import java.io.*;
import java.util.*;

/**
 *
 * @author taoyang
 */
public class SelectionMethodA {

    /**
     * @param args the command line arguments The first argument is the name for
     * the input file The second argument is the name for the output file The
     * third argument is for the number of species sets for each size The fourth
     * argument for the minimal size of species in a species set The fifth
     * argument for the maximal size of species in a species set
     * @throws java.io.FileNotFoundException
     */
    public static void exec(String[] args) throws FileNotFoundException, SpecSelException, InterruptedException {

        System.out.println("Method A");

        //the list to record the indicators that has been processed.
//        ArrayList<Integer> indicatorCreated = new ArrayList<>();

        String fileName = "example.txt"; //default input data

//        System.out.println(fileName);

        if (args.length > 0) {
            fileName = args[0];
        }

        String outFileName = fileName + "_result.txt";  //default file for output

        if (args.length > 1) {
            outFileName = args[1];
        }

        System.out.println("The dataset is taken from file " + fileName);

        //read the graph from the file   
        SpecRTGraph sig = ReadFile.graphConstr(fileName, "None");

        //Output the results to a file
        PrintStream outPut = new PrintStream(new File(outFileName));
        outPut.println("The dataset:" + fileName);
        outPut.println("The dataset contains " + sig.numSpecies() + " species and "
                + sig.numResourceTypes() + " resource types");

        int startSize = 2;
        //int endSize=1;
        int endSize = sig.numSpecies() - 1;

        int numOfSpeciesSet = 11; //the number of species sets for 
        //each size, 11 is the default size;

        if (args.length > 2) {
            numOfSpeciesSet = Integer.parseInt(args[2]) + 1;
        }

        if (args.length > 3) {
            startSize = Integer.parseInt(args[3]);
            endSize = startSize;
        }

        if (args.length > 4) {
            endSize = Integer.parseInt(args[4]);
        }

        MinSpecSetFamily mssf = sig.getMinDomSpecSets();

        for (int i = startSize; i <= endSize; i++) {
            System.out.println("In the main loop " + i);
            SpecSetFamily consFamily = sig.getDomSpecSets(i, numOfSpeciesSet, mssf);
            outPut.println("For " + i + "  species");
            outPut.println(consFamily);
        }

        System.out.println("The output is stored in " + outFileName);

    } //end exec

}
