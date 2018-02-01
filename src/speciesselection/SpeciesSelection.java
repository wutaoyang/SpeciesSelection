/*
 * Species Selection
 * An object, niche-based approach to species selection
 * proposed by Simon Butler etc in 'Methods in Ecology and Evolution' 2012
 * 
 * The main method for command-line interface
 */
package speciesselection;

import java.io.*;
import java.util.*;

/**
 *
 * @author taoyang wu (taoyang.wu@uea.ac.uk)
 */
public class SpeciesSelection {

    /**
     * @param args the command line arguments The first argument is the name for
     * the input file The second argument is the name for the output file The
     * third argument is for the number of species sets for each size The fourth
     * argument for the minimal size of species in a species set The fifth
     * argument for the maximal size of species in a species set
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("The Indicator Species Selection Project.");
        System.out.println("By Taoyang Wu, Version 0.2, March 2014");
        specSel(args);
    }

    public static void specSel(String[] args) throws FileNotFoundException {
        //construct the bipartite graph between species and indicators.
        SpecRTGraph sig = new SpecRTGraph();

        //the list to record the indicators that has been processed.
        ArrayList<Integer> indicatorCreated = new ArrayList<>();

        String fileName = "Forest_D_ALL.txt"; //default input data
        int numWordsInName = 1; //the number of words contains in the speices name

        if (args.length > 0) {
            fileName = args[0];
        }

        String outFileName = fileName + "_result.txt";  //default file for output

        if (args.length > 1) {
            outFileName = args[1];
        }

        System.out.println("The dataset is taken from file " + fileName);

        File f = new File(fileName);
        Scanner input = new Scanner(f);
        String titleInformation = input.nextLine();
        while (input.hasNext()) {
            String text = input.nextLine();
            processLine(text, sig, numWordsInName);
        }

        //Output the results to a file
        PrintStream outPut = new PrintStream(new File(outFileName));
        outPut.println("The dataset:" + fileName);
        outPut.println("The dataset contains " + sig.numSpecies() + " species and "
                + sig.numResourceTypes() + " resource types");

        int startSize = 2;
        int endSize = sig.numSpecies() - 1;

        int numOfSpeciesSet = 11; //the number of species sets for each size, 11 is the default size;

        if (args.length > 2) {
            numOfSpeciesSet = Integer.parseInt(args[2]) + 1;

            if (args.length > 3) {
                startSize = Integer.parseInt(args[3]);
                endSize = startSize;

                if (args.length > 4) {
                    endSize = Integer.parseInt(args[4]);
                }
            }
        }

        MinSpecSetFamily mssf = sig.getMinDomSpecSets();

        for (int i = startSize; i <= endSize; i++) {
            // System.out.println("In the main loop "+i);
            SpecSetFamily consSpecSetFamily = sig.getDomSpecSets(i, numOfSpeciesSet, mssf);
            outPut.println("For " + i + "  species");
            outPut.println(consSpecSetFamily);
        }
        System.out.println("The output is stored in " + outFileName);
    }

    //process the given String for a line of the data file
    public static void processLine(String text, SpecRTGraph sig, int numWordsInName) {
        Scanner data = new Scanner(text);
        //creat a new species
        int spName = data.nextInt();
        Species inSpec = new Species(spName);

        int spReliance = data.nextInt();
        int spResources = data.nextInt();
        int spSensitivity = data.nextInt();
        inSpec.setReliance(spReliance);

        //the indication information
        int index = 0;

        //test variable
        while (data.hasNextInt()) {
            index++;
            int indValue = data.nextInt();
            if (indValue == 1) {
                //first to check whether an indicator with the given id exists
                ResourceType tmpInd = sig.getResourceTypeByID(index);

                if (tmpInd != null) {
                    sig.addEdge(inSpec, tmpInd);
                } else {
                    //System.out.println("no such indicator "+index);
                    ResourceType newInd = new ResourceType(index);
                    sig.addEdge(inSpec, newInd);
                }
            }
        }
    } //process a line

}
