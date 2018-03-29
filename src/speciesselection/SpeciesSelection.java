/*
 * Species Selection
 * An object, niche-based approach to species selection
 * proposed by Simon Butler etc in 'Methods in Ecology and Evolution' 2012
 * 
 * The main method for command-line interface
 */
package speciesselection;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import preprocessing.ResultSet;

/**
 *
 * @author taoyang wu (taoyang.wu@uea.ac.uk) March 2014 Further developed by
 * Stephen Whiddett (s.whiddett@uea.ac.uk) Jan-May 2018
 */
public class SpeciesSelection implements Runnable {

    private PropertyChangeSupport pcs;
    private String fileName, option;
    private boolean allResults, finished, success;
    private ArrayList<Double> result;
    private SpecRTGraph specRTGraph;
    private int truncateThreshold;
    private int specThresholdM;
    private double sdThresholdX, areaOrPrecisionY;

    private final String theDataset = "The dataset:";
    private final String theDatasetContains = "The dataset contains ";
    
    /**
     * @param args the command line arguments The first argument is the name for
     * the input file The second argument is the name for the output file The
     * third argument is for the number of species sets for each size The fourth
     * argument for the minimal size of species in a species set The fifth
     * argument for the maximal size of species in a species set
     * @throws java.io.FileNotFoundException
     * @throws java.lang.InterruptedException
     * @throws speciesselection.SpecSelException
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException, SpecSelException {
        SpeciesSelection speciesSelection = new SpeciesSelection();
        long start = System.nanoTime();
        printIntro();
        speciesSelection.specSel(args[0], true);
        System.out.println("Process took " + ((System.nanoTime() - start) / 1000000.0) + "ms");
    }
    
    /**
     * prints intro information to the console stating project name and author and version info
     */
    private static void printIntro()
    {
        System.out.println("The Indicator Species Selection Project.");
        //System.out.println("By Taoyang Wu, Version 0.2, March 2014");
        //System.out.println("By Taoyang Wu, Version 1.0, February 2016");
        System.out.println("By Taoyang Wu & S.Whiddett, Version 2.0, March 2018");
    }

    /**
     * Default constructor
     */
    public SpeciesSelection() {
        this.finished = false;
        this.success = false;
    }

    /**
     * Alternative Constructor
     * @param fileName - name of file containing the dataset
     * @param allResults - boolean indicating whether all or truncated results requested
     * @param truncateThreshold - number of consecutive min mean sensitivity increases before output is truncated
     * @param option - specifies process type option selected by user
     * @param specThresholdM - value of m specified by user
     * @param sdThresholdX - value of x specified by user
     * @param areaOrPrecisionY - value of y specified by user
     */
    public SpeciesSelection(String fileName, boolean allResults, int truncateThreshold, String option, int specThresholdM, double sdThresholdX, double areaOrPrecisionY) {
        this.pcs = new PropertyChangeSupport(this);
        this.fileName = fileName;
        this.allResults = allResults;
        this.finished = false;
        this.success = false;
        this.truncateThreshold = truncateThreshold;
        this.option = option;
        this.specThresholdM = specThresholdM;
        this.sdThresholdX = sdThresholdX;
        this.areaOrPrecisionY = areaOrPrecisionY;
    }

    @Override
    public void run() {
        long start = System.nanoTime();
        printIntro();
        try {
            result = specSel(fileName, allResults, truncateThreshold, option, specThresholdM, sdThresholdX, areaOrPrecisionY);
            success = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpeciesSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            System.out.println("Thread interrupted");
            pcs.firePropertyChange("interrupted", null, null);
            Logger.getLogger(SpeciesSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SpecSelException ex){
            pcs.firePropertyChange("errorOccurred", null, null);
            Logger.getLogger(SpeciesSelection.class.getName()).log(Level.SEVERE, null, ex);
        }
        finished = true;
        pcs.firePropertyChange("finished", null, finished);
        System.out.println("Process took " + ((System.nanoTime() - start) / 1000000.0) + "ms");
    }

    public boolean isFinished() {
        return finished;
    }
    
    public boolean isSuccess() {
        return success;
    }

    public ArrayList<Double> getResult() {
        return result;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    /**
     * Method for use with Problem Species identification
     * @return
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws SpecSelException 
     */
    public MinSpecSetFamily getMssf() 
            throws InterruptedException, FileNotFoundException, SpecSelException {
        specRTGraph = ReadFile.graphConstr(fileName, Constants.NONE);
        if(null == specRTGraph)
        {
            throw new SpecSelException("Exception in SpeciesSelection.getMSSF() "
                    + "- specRTGraph is null");
        }
        return specRTGraph.getMinDomSpecSets();
    }
    
    /**
     * Default specSel method outputs truncated results
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
     * @throws java.lang.InterruptedException
     * @throws speciesselection.SpecSelException
     */
    public ArrayList<Double> specSel(String fileName) throws FileNotFoundException, InterruptedException, SpecSelException {
        this.fileName = fileName;
        return specSel(fileName, false, 3, "A", 0, 0, 0);
    }
    
    /**
     * Basic specSel method allowing choice of allResults/truncation
     * @param fileName
     * @param allResults
     * @return
     * @throws FileNotFoundException
     * @throws InterruptedException
     * @throws SpecSelException 
     */
    public ArrayList<Double> specSel(String fileName, boolean allResults) throws FileNotFoundException, InterruptedException, SpecSelException {
        return specSel(fileName, allResults, 3, "A", 0, 0, 0);
    }
    
    /**
     * Advanced specSel method will full choice of options
     * @param fileName
     * @param allResults set true for full output, otherwise output will be
     * produced up until truncateThreshold consecutive meanSensitivity increases
     * @param truncateThreshold
     * @param option
     * @param specThresholdM
     * @param sdThresholdX
     * @param areaOrPrecisionY
     * @return
     * @throws FileNotFoundException
     * @throws java.lang.InterruptedException
     * @throws speciesselection.SpecSelException
     */
    public ArrayList<Double> specSel(String fileName, boolean allResults, int truncateThreshold, String option, int specThresholdM, double sdThresholdX, double areaOrPrecisionY) 
            throws FileNotFoundException, InterruptedException, SpecSelException {
        //construct the bipartite graph between species and indicators.
        SelectionMethod selMeth = selectionMethod(fileName, option, specThresholdM, sdThresholdX, areaOrPrecisionY);
        specRTGraph = selMeth.getSpecRTGraph();
        if(null == specRTGraph){
            throw new SpecSelException("specRTGraph is null: incorrect input data format likely");
        }
        long startMssf = System.nanoTime();
        MinSpecSetFamily mssf = specRTGraph.getMinDomSpecSets();
        System.out.println("MSSF: " + mssf.size());
        System.out.println("MSSF time: " + ((System.nanoTime() - startMssf) / 1000000.0));
        return outputResults(mssf, fileName, truncateThreshold, selMeth.getDetails());
    }
    
    /**
     * returns appropriate selection method according to option and thresholds specified
     * @param fileName
     * @param option
     * @param specThreshold
     * @param sdThreshold
     * @param areaOrPrecisionY
     * @return
     * @throws FileNotFoundException
     * @throws SpecSelException
     * @throws InterruptedException 
     */
    private SelectionMethod selectionMethod(String fileName, String option, 
            int specThreshold, double sdThreshold, double areaOrPrecisionY) 
            throws FileNotFoundException, SpecSelException, InterruptedException
    {
        switch (option) 
        {
            case "A":
                return new SelectionMethodA(fileName);
            case "B":
                return new SelectionMethodB(fileName, false, false, specThreshold, sdThreshold);
            case "BN":
                return new SelectionMethodB(fileName, true, false, specThreshold, sdThreshold);
            case "BF":
                return new SelectionMethodB(fileName, false, true, specThreshold, sdThreshold);
            case "BFN":
                return new SelectionMethodB(fileName, true, true, specThreshold, sdThreshold);
            case "C":
                return new SelectionMethodC(fileName, false, false, specThreshold, sdThreshold, areaOrPrecisionY);
            case "CF":
                return new SelectionMethodC(fileName, false, true, specThreshold, sdThreshold, areaOrPrecisionY);
            case "CN":
                return new SelectionMethodC(fileName, true, false, specThreshold, sdThreshold, areaOrPrecisionY);
            case "CFN":
                return new SelectionMethodC(fileName, true, false, specThreshold, sdThreshold, areaOrPrecisionY);
            default:
                return null;
        }
    }
    
    /**
     * returns default results file name
     * @return 
     */
    public String getResultsFileName()
    {
        return getResultsFileName(fileName);
    }
    
    /**
     * returns fileName appended with _result.txt
     * @param fileName
     * @return 
     */
    public static String getResultsFileName(String fileName)
    {
        return fileName.substring(0, fileName.lastIndexOf(".")) + "_result.txt";
    }

    /**
     * Outputs the Species Selection results to file with all results or 
     * truncation depending upon allResults value set
     * @param mssf
     * @param fileName
     * @param truncateThreshold - number of rises in min mean sensitivity when truncating results output
     * @param details
     * @return
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws SpecSelException 
     */
    public ArrayList<Double> outputResults(MinSpecSetFamily mssf, 
            String fileName, int truncateThreshold, String details)
            throws InterruptedException, FileNotFoundException, SpecSelException {
        String outFileName = getResultsFileName();
        //Output the results to a file
        PrintStream outPut = new PrintStream(new File(outFileName));
        outPut.println(theDataset + fileName);
        outPut.println(theDatasetContains + specRTGraph.numSpecies() + " species and "
                + specRTGraph.numResourceTypes() + " resource types");
        outPut.print(details);
        
        int startSize = 2;
        int endSize = specRTGraph.numSpecies() - 1;

        //the number of species sets for each size, 11 is the default size;
        int numOfSpeciesSet = 11;

        mssf.sortBySensitivity();
        Collections.sort(specRTGraph.rtNode);
        SpecSetFamily consSpecSetFamily;
        int count = 0;
        double prevMeanSens = 10000;// large initial value greater than any sensitivity
        int i = startSize;
        ArrayList<Double> minSensitivities = new ArrayList<>();
        // there are never results for 0 or 1 size arrays so add zeros for those
        minSensitivities.add(0.0);
        minSensitivities.add(0.0);
        while (i <= endSize && (count < truncateThreshold || allResults)) {
            if (Thread.currentThread().isInterrupted()) {
                outPut.close();
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
        // return list of minimum mean sensitivity for each set size
        return minSensitivities;
    }

    /**
     * gets the list of species IDs
     * @return 
     */
    public List<Integer> speciesIDs()
    {
        return specRTGraph.speciesIDs();
    }

    /**
     * reads the associated results file and returns it as a list of ResultSets
     * @return 
     */
    public List<ResultSet> readResultsFile() {
        try 
        {
            File file = new File(getResultsFileName());
            Scanner scanner = new Scanner(file);
            String lineOne = scanner.nextLine();// e.g. The dataset:Forest1.txt
            String lineTwo = scanner.nextLine();// e.g. The dataset contains 40 species and 37 resource types
            // check that the file contents are consistent with a results file
            if (!(lineOne.contains(theDataset) && lineTwo.contains(theDatasetContains))) {
                System.err.println("readResultsFile returned false");
                return null;
            }

            //create list in which to return all result sets for this SpeciesSelection
            List<ResultSet> results = new ArrayList<>();
            
            // process all lines of the results file
            ResultSet resultSet = null;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains("For ")) {
                    if (null != resultSet) {
                        // System.out.println("RS str: " + resultSet.toString());
                    }
                    Scanner scan = new Scanner(line.substring(4));
                    int setSize = scan.nextInt();
                    resultSet = new ResultSet(setSize);
                    results.add(resultSet);
                    // System.out.println("Set size: " + resultSet.getSetSize());
                }
                if (Character.isDigit(line.charAt(0))) {
                    resultSet.add(line);
                }
            }
            scanner.close();
            return results;
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(SpeciesSelection.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error in SpeciesSelection.readResultsFile()");
            return null;
        }
    }
}
