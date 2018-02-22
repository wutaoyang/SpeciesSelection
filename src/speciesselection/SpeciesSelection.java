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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author taoyang wu (taoyang.wu@uea.ac.uk) March 2014 Further developed by
 * Stephen Whiddett (s.whiddett@uea.ac.uk) Jan-May 2018
 */
public class SpeciesSelection implements Runnable {

    String[] args;
    boolean allResults;
    ArrayList<Double> result;
    boolean finished;
    SpecRTGraph specRTGraph;

    /**
     * @param args the command line arguments The first argument is the name for
     * the input file The second argument is the name for the output file The
     * third argument is for the number of species sets for each size The fourth
     * argument for the minimal size of species in a species set The fifth
     * argument for the maximal size of species in a species set
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        SpeciesSelection speciesSelection = new SpeciesSelection();
        long start = System.nanoTime();
        System.out.println("The Indicator Species Selection Project.");
        System.out.println("By Taoyang Wu, Version 0.2, March 2014");
        speciesSelection.specSel(args, true);
        System.out.println("Process took " + ((System.nanoTime() - start) / 1000000.0) + "ms");
    }

    public SpeciesSelection() {
        this.finished = false;
    }

    public SpeciesSelection(String[] args, boolean allResults) {
        this.args = args;
        this.allResults = allResults;
        this.finished = false;
    }

    @Override
    public void run() {
        long start = System.nanoTime();
        System.out.println("The Indicator Species Selection Project.");
        System.out.println("By Taoyang Wu, Version 0.2, March 2014");
        try {
            result = specSel(args, allResults);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpeciesSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            System.out.println("Thread interrupted");
            Logger.getLogger(SpeciesSelection.class.getName()).log(Level.SEVERE, null, ex);
        }
        finished = true;
        System.out.println("Process took " + ((System.nanoTime() - start) / 1000000.0) + "ms");
    }

    public boolean isFinished() {
        return finished;
    }

    public ArrayList<Double> getResult() {
        return result;
    }

    /**
     * Default specSel method outputs truncated results
     *
     * @param args
     * @return
     * @throws FileNotFoundException
     * @throws java.lang.InterruptedException
     */
    public ArrayList<Double> specSel(String[] args) throws FileNotFoundException, InterruptedException {
        return specSel(args, false);
    }

    private static void processFile(String fileName, SpecRTGraph specRTGraph) throws FileNotFoundException {
        try {
            System.out.println("The dataset is taken from file " + fileName);
            File f = new File(fileName);
            Scanner input = new Scanner(f);
            String titleInformation = input.nextLine();
            while (input.hasNext()) {
                String text = input.nextLine();
                processLine(text, specRTGraph);
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error in SpeciesSelection.processFile()");
        }

    }

    // Method for use with Problem Species identification
    public MinSpecSetFamily getMssf() throws InterruptedException, FileNotFoundException {
        String fileName = this.args[0];
        specRTGraph = new SpecRTGraph();
        processFile(fileName, specRTGraph);
        return specRTGraph.getMinDomSpecSets();
    }

    /**
     *
     * @param args
     * @param allResults set true for full output, otherwise output will be
     * produced up until 3 consecutive meanSensitivity increases
     * @return
     * @throws FileNotFoundException
     */
    public ArrayList<Double> specSel(String[] args, boolean allResults) throws FileNotFoundException, InterruptedException {
        //construct the bipartite graph between species and indicators.
        specRTGraph = new SpecRTGraph();

        String fileName = "Forest1.txt"; //default input data
        if (args.length > 0) {
            fileName = args[0];
        }

        processFile(fileName, specRTGraph);

        long startMssf = System.nanoTime();
        MinSpecSetFamily mssf = specRTGraph.getMinDomSpecSets();
        System.out.println("MSSF: " + mssf.size());
        System.out.println("MSSF time: " + ((System.nanoTime() - startMssf) / 1000000.0));

        return outputResults(mssf, fileName);

    }

    public ArrayList<Double> outputResults(MinSpecSetFamily mssf, String fileName) throws InterruptedException, FileNotFoundException {
        String outFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_result.txt";  //default file for output
        if (args.length > 1) {
            outFileName = args[1];
        }
        //Output the results to a file
        PrintStream outPut = new PrintStream(new File(outFileName));
        outPut.println("The dataset:" + fileName);
        outPut.println("The dataset contains " + specRTGraph.numSpecies() + " species and "
                + specRTGraph.numResourceTypes() + " resource types");

        int startSize = 2;
        int endSize = specRTGraph.numSpecies() - 1;

        //the number of species sets for each size, 11 is the default size;
        int numOfSpeciesSet = 11;

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

        mssf.sortBySensitivity();
        Collections.sort(specRTGraph.rtNode);
        SpecSetFamily consSpecSetFamily;
        int count = 0;
        double prevMeanSens = 10000;// large initial value greater than any sensitivity
        int i = startSize;
        ArrayList<Double> minSensitivities = new ArrayList<>();
        minSensitivities.add(0.0);
        minSensitivities.add(0.0);
        while (i <= endSize && (count < 3 || allResults)) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();// throw if cancel requested
            }

            consSpecSetFamily = specRTGraph.getDomSpecSets(i, numOfSpeciesSet, mssf);
            if (consSpecSetFamily.size() > 0)//check for increasing mean sensitivity
            {
                SpecSet firstSSF = consSpecSetFamily.get(0);
                double meanSens = firstSSF.getMeanSensitivity();
                minSensitivities.add(meanSens);
                if (meanSens > prevMeanSens) {
                    count++;
                } else {
                    count = 0;
                }
                prevMeanSens = meanSens;
            } else {
                minSensitivities.add(0.0);
            }
            outPut.println("For " + i + "  species");
            outPut.println(consSpecSetFamily);
            i++;
        }
        outPut.close();
        System.out.println("The output is stored in " + outFileName);
        return minSensitivities;
    }

    //process the given String for a line of the data file
    public static void processLine(String text, SpecRTGraph sig) {
//        System.out.println("text: " + text);
        Scanner data = new Scanner(text);
        //create a new species
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
                    ResourceType newInd = new ResourceType(index);
                    sig.addEdge(inSpec, newInd);
                }
            }
        }
    }

}
