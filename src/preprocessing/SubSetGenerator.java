package preprocessing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Class used to generate subsets of a species selection dataset
 * @author mre16utu
 */
public class SubSetGenerator implements Comparator<String>{
    
    /**
     * create subset containing only the species NOT in the provided arraylist
     * @param file
     * @param speciesNos
     * @return
     * @throws FileNotFoundException 
     */
    public String createSubset(File file, ArrayList<Integer> speciesNos) throws FileNotFoundException
    {
        // read file adding each line to a list
        List<String> fileAsList = FileUtils.readFileToList(file);
        List<String> subset = new ArrayList<>();
        
        subset.add(fileAsList.get(0));
        for(int i = 1; i < fileAsList.size(); i++)
        {
            String line = fileAsList.get(i);
            if(!speciesNos.contains(getFirstInt(line)))
            {
                subset.add(line);
            }
        }
        
        String subsetFileName = FileUtils.removeExt(file.getName()) + "_Subset.txt";
        writeSubsetToFile(subsetFileName, subset);
        return subsetFileName;
    }
    
    /**
     * Gets the first int from a string
     * @param line
     * @return 
     */
    private int getFirstInt(String line)
    {
        Scanner scanner = new Scanner(line);
        return scanner.nextInt();
    }
    
    /**
     * Generates requested subsets and returns a list of the fileNames where the 
     * subsets have been saved to
     * @param file
     * @param subsetSize
     * @param noSubsets
     * @param maxSeconds
     * @return
     * @throws FileNotFoundException 
     */
    public FileList generateSubsets(File file, int subsetSize, int noSubsets, int maxSeconds) throws FileNotFoundException
    {
        FileList fileNames = new FileList();
        // read file adding each line to a list
        List<String> fileAsList = FileUtils.readFileToList(file);
                
        // Store header text and remove it from the list
        String header = fileAsList.get(0);
        fileAsList.remove(0);

        // Get the full datasets universe
        Universe universe = getUniverse(fileAsList);
        
        // generate random subsets that cover all resources
        Random rand = new Random();
        
        long startTimeMs = System.currentTimeMillis();
        int i = 0;
        while(i < noSubsets && checkTime(startTimeMs, maxSeconds))
        {
            Universe tempUniverse;
            List<String> subset = new ArrayList<>();
            do {
                // shuffle full list
                Collections.shuffle(fileAsList);
                // select first n rows from shuffled list
                subset = new ArrayList<>(fileAsList.subList(0,subsetSize));
                // get its universe
                tempUniverse = getUniverse(subset);
            }
            while(!tempUniverse.equals(universe) && checkTime(startTimeMs, maxSeconds));
            
            // create subset file and add to list unless the max time is exceeded
            if(checkTime(startTimeMs, maxSeconds))
            {
                Collections.sort(subset, this);
                // add the header back to top of the list
                subset.add(0, header);
                // Write out file for subset
                String fileName = file.getName();
                String subsetFileName = subsetFileName(fileName, i);
                fileNames.add(new File(subsetFileName));
                writeSubsetToFile(subsetFileName, subset);
            }
            i++;
        }
        return fileNames;
    }
    
    /**
     * returns whether the maxSeconds has been exceeded since the start time
     * @param startTimeMs
     * @param maxSeconds
     * @return 
     */
    private boolean checkTime(long startTimeMs, int maxSeconds)
    {
        return (System.currentTimeMillis() - startTimeMs) < (maxSeconds * 1000);
    }
    
    /**
     * returns a name for a subset file by appending _subset and integer i to 
     * the passed in fileName
     * @param fileName
     * @param i
     * @return 
     */
    private String subsetFileName(String fileName, int i)
    {
        return fileName.substring(0, fileName.lastIndexOf(".")) + "_subset" + i + ".txt";
    }
    
    /**
     * Writes the subset out to a file with the specified fileName
     * @param fileName
     * @param subset
     * @throws FileNotFoundException 
     */
    private void writeSubsetToFile(String fileName, List<String> subset) throws FileNotFoundException
    {
        //Output the results to a file
        PrintStream outPut = new PrintStream(new File(fileName));
        for(String str : subset)
        {
            outPut.println(str);
        }
        outPut.flush();
        outPut.close();
    }
    
    /**
     * Gets the universe of a dataset - the universe is the set covered by the dataset
     * @param fileAsList
     * @return 
     */
    private Universe getUniverse(List<String> fileAsList)
    {
        return new Universe(fileAsList);
    }
    
    /**
     * Comparator that compares strings by the first integer in the strings
     * @param str1
     * @param str2
     * @return 
     */
    @Override
    public int compare(String str1, String str2) {
        Scanner scanner1 = new Scanner(str1);
        Integer no1 = scanner1.nextInt();
        Scanner scanner2 = new Scanner(str2);
        Integer no2 = scanner2.nextInt();
        return no1.compareTo(no2);
    }
    
    /**
     * Test method
     * @param args
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("Forest1.txt");
        SubSetGenerator pc = new SubSetGenerator();
        pc.generateSubsets(file, 50, 10, 60);
    }
}
