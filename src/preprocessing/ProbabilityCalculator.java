package preprocessing;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import speciesselection.SpeciesSelection;
import speciesselection.gui.SpecSelGUI;

/**
 * Class to calculate probabilities of species appearing in an optimal species selection result
 * @author mre16utu
 */
public class ProbabilityCalculator {

    private final DecimalFormat df = new DecimalFormat("0.##");
    private final SpecSelGUI specSelGUI;
    private final HashMap<Integer, HashMap<Integer, Double>> probabilities;
    private final Set<Integer> speciesSet;
    private final List<String> fileAsList;// The complete dataset converted to list
    private static String currentProbsFileName;
    
    public ProbabilityCalculator(SpecSelGUI specSelGUI, List<String> fileAsList)
    {
        this.specSelGUI = specSelGUI;
        this.probabilities = new HashMap<>();
        this.speciesSet = new TreeSet<>();
        this.fileAsList = fileAsList;
        this.currentProbsFileName = "probabilities.txt";
    }
    
    
    /**
     * Calculates the probability that a species will appear in an optimal 
     * solution, for each solution set size, when that species is included in the subset 
     * @param specSelList
     * @return 
     * @throws FileNotFoundException 
     */
    public boolean calcProbs(List<SpeciesSelection> specSelList) throws FileNotFoundException
    {
        //      setSize          speciesID, count
        HashMap<Integer, HashMap<Integer, Integer>> optimalOccurence = new HashMap<>();
        // optimalOccurence is used to keep count of number of times each species 
        // was included in optimal solution for each set size
        
        //      setSize          speciesID, count
        HashMap<Integer, HashMap<Integer, Integer>> speciesSetSizeUsage = new HashMap<>();
        // speciesSetSizeUsage is used such that for each species and each solution 
        // set size, we keep count of number of times the species was included in 
        // a subset when a solution was found for the set size
        
        for(SpeciesSelection specSel : specSelList)
        {
            List<Integer> iDs = specSel.speciesIDs();
            List<ResultSet> results = specSel.readResultsFile();
            
            // Keep count of number of times each species was included in optimal solution for each set size
            for(ResultSet resultSet : results)
            {
                int setSize = resultSet.getSetSize();
                //get optimal occurrance map for given set size
                HashMap<Integer, Integer> tempMap;
                if(optimalOccurence.containsKey(setSize))
                {
                    tempMap = optimalOccurence.get(setSize);
                }
                else
                {
                    tempMap = new HashMap<>();
                    optimalOccurence.put(setSize, tempMap);
                }
                
                // get the set of species that existed in optimal solutions 
                Set<Integer> intSet = resultSet.getSetInts();
                for(Integer speciesID : intSet)
                {
                    if(!tempMap.containsKey(speciesID))
                    {
                        tempMap.put(speciesID, 1);
                    }
                    else
                    {
                        tempMap.put(speciesID, tempMap.get(speciesID) + 1);
                    }
                }
                
                // For each species and each solution set size, keep count of number
                // of times the species was included in a subset and a solution was 
                // found for the set size
                if(!resultSet.isEmpty()) // only count the species usage if a result was found for the set size
                {
                    //get optimal occurrance map for given set size
                    HashMap<Integer, Integer> tempMap2;
                    if(speciesSetSizeUsage.containsKey(setSize))
                    {
                        tempMap2 = speciesSetSizeUsage.get(setSize);
                    }
                    else
                    {
                        tempMap2 = new HashMap<>();
                        speciesSetSizeUsage.put(setSize, tempMap2);
                    }

                    // get the set of species that existed dataset 
                    for(Integer speciesID : iDs)
                    {
                        if(!tempMap2.containsKey(speciesID))
                        {
                            tempMap2.put(speciesID, 1);
                        }
                        else
                        {
                            tempMap2.put(speciesID, tempMap2.get(speciesID) + 1);
                        }
                    }
                }
            }
        }
        
        // calculate the probabilities for each species in each set size and
        // Check if probabilities results file all ready exists
        JCheckBox jCheckBoxOverwriteProbs = specSelGUI.getCheckBoxOverwriteProbs();
        File file = new File(currentProbsFileName);
        if(!jCheckBoxOverwriteProbs.isSelected() && FileUtils.fileExists(file))
        {
            String newFileName = specSelGUI.getTextFilePath("SAVE");
            // ensure file ends with .txt
            String suffix = ".txt";
            if(!newFileName.endsWith(suffix))
            {
                newFileName += suffix;
            }
            
            if (specSelGUI.getFileChooserResult() == JFileChooser.APPROVE_OPTION) {
                currentProbsFileName = newFileName;
                file = new File(currentProbsFileName);
                jCheckBoxOverwriteProbs.setText("Overwrite " + currentProbsFileName + "?");
            }
            else
            {
                // dont save probs if user cancelled save dialog
                return false;
            }
        }
        
        // If user did not cancel, save the probabilities
        outputResults(file, optimalOccurence, speciesSetSizeUsage);
        return true;
    }
    
    private void outputResults(File file, 
            HashMap<Integer, HashMap<Integer, Integer>> optimalOccurence, 
            HashMap<Integer, HashMap<Integer, Integer>> speciesSetSizeUsage) 
            throws FileNotFoundException
    {
        
        // Output the results to a file
        PrintStream outPut = new PrintStream(file);
        outPut.println("Source dataset: " + specSelGUI.getFileName());
        outPut.println("Probabilities calculated from " + specSelGUI.getNoSubsets() + " subsets of size " + specSelGUI.getSubsetSize() + "\n");
        //DecimalFormat df = new DecimalFormat("0.##"); 
        Set<Integer> optOccKeys = optimalOccurence.keySet();
        int setSizeWithTruncation = Collections.max(optOccKeys) - specSelGUI.getTruncateThreshold() + 1;// set size where min mean sensitivity must be increasing
                
        for(Integer setSize : optOccKeys)
        {
            outPut.println("Probs for set size: " + setSize);
            String speciesStr = "Species: ";
            String probStr    = "Probs  : ";

            HashMap<Integer, Integer> tempOptimalOccurence = optimalOccurence.get(setSize);
            HashMap<Integer, Integer> tempSpeciesSetSizeUsage = speciesSetSizeUsage.get(setSize);
            List<Integer> keys = new ArrayList<>(tempOptimalOccurence.keySet());
            Collections.sort(keys);
            HashMap<Integer, Double> map = new HashMap<>();
            for(Integer speciesID : keys)
            {
                speciesStr += String.format("%1$5s", speciesID);
                double prob = tempOptimalOccurence.get(speciesID)/(double)(tempSpeciesSetSizeUsage.get(speciesID));
                probStr += String.format("%1$5s", df.format(prob));
                
                //populate probabilities map
                map.put(speciesID, prob);
                // store set of all species up until setsize where min mean sensitivity is definately increasing
                if(prob > specSelGUI.getProbsThreshold() && (setSize < setSizeWithTruncation || !specSelGUI.getIsTruncated()))
                {
                    speciesSet.add(speciesID);
                }
            }
            probabilities.put(setSize, map);

            outPut.println(speciesStr);
            outPut.println(probStr);
        }
        outPut.println("There are " + speciesSet.size() + " species with probabilities greater than " + specSelGUI.getProbsThreshold() + "\n");
        outPut.println(speciesSet);
        outPut.close();
        
        System.out.println(toString());// TODO Comment out this line when no longer debugging
    }

    // creates dataset containing only the species with a probability of appearing in an optimal specsel solution
    public void generateSubDataset(String fileName) throws FileNotFoundException
    {
        PrintStream outPut = new PrintStream(new File(fileName));
        outPut.println(fileAsList.get(0));
        for(int i = 1; i < fileAsList.size(); i++)
        {
            String line = fileAsList.get(i);
            int speciesNo = getFirstInt(line);
            if(speciesSet.contains(speciesNo))
            {
                outPut.println(line);
            }
        }
        outPut.close();
    }
    
    // return int at the beginning of a string
    private int getFirstInt(String str)
    {
        Scanner scanner = new Scanner(str);
        return scanner.nextInt();        
    }
    
    public String getCurrentProbsFileName()
    {
        return currentProbsFileName;
    }
       
    public static String viewResults() {
        try
        {
            File f = new File(currentProbsFileName);
            if(f.exists() && !f.isDirectory()) { 
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().edit(f);
                    return "";
                } 
                else 
                {
                    String str = "This PC does not support file edit";
                    System.out.println(str);
                    return str;
                }
            }
            else
            {
                String str = "File does not exist";
                System.out.println(str);
                return str;
            }
        }
        catch(IOException e)
        {
            String str = "Error attempting to open " + currentProbsFileName + ": " + e;
            System.out.println(str);
            return str;
        }
    }
    
    @Override
    public String toString()
    {
        String str = "";
        List<Integer> setSizeKeys = new ArrayList<>(probabilities.keySet());
        Collections.sort(setSizeKeys);
        for(Integer setSize : setSizeKeys)
        {
            str += "Probs for set size: " + setSize + "\n";
            String speciesStr = "Species: ";
            String probStr    = "Probs  : ";
            
            HashMap<Integer, Double> map = probabilities.get(setSize);
            List<Integer> speciesIDKeys = new ArrayList<>(map.keySet());
            Collections.sort(speciesIDKeys);
            for(Integer speciesID : speciesIDKeys)
            {
                speciesStr += String.format("%1$5s", speciesID);
                probStr += String.format("%1$5s", df.format(map.get(speciesID)));
            }
            str += speciesStr + "\n";
            str += probStr + "\n";
        }
        str += "There are " + speciesSet.size() + " species with probabilities greater than " + specSelGUI.getProbsThreshold() + "\n" + speciesSet;
        return str;
    }
    
}
