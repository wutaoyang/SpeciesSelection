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
 *
 * @author mre16utu
 */
public class SubSetGenerator implements Comparator<String>{
    
    public FileList generateSubsets(File file, int subsetSize, int noSubsets) throws FileNotFoundException
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
        for(int i = 0; i < noSubsets; i++)
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
            while(!tempUniverse.equals(universe));
            
            Collections.sort(subset, this);
            // add the header back to top of the list
            subset.add(0, header);
            // Write out file for subset
            String fileName = file.getName();
            String subsetFileName = subsetFileName(fileName, i);
            fileNames.add(new File(subsetFileName));
            writeSubsetToFile(subsetFileName, subset);
        }
        return fileNames;
    }
    
    private String subsetFileName(String fileName, int i)
    {
        return fileName.substring(0, fileName.lastIndexOf(".")) + "_subset" + i + ".txt";
    }
    
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
        
    private Universe getUniverse(List<String> fileAsList)
    {
        return new Universe(fileAsList);
    }
    
    @Override
    public int compare(String str1, String str2) {
        Scanner scanner1 = new Scanner(str1);
        Integer no1 = scanner1.nextInt();
        Scanner scanner2 = new Scanner(str2);
        Integer no2 = scanner2.nextInt();
        return no1.compareTo(no2);
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("Forest1.txt");
        SubSetGenerator pc = new SubSetGenerator();
        pc.generateSubsets(file, 50, 10);
    }

}
