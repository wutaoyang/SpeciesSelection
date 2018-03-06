package preprocessing;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import speciesselection.MinSpecSetFamily;
import speciesselection.SpeciesSelection;

/**
 * Identifies list of species that cause faster than exponential growth in
 * solving time for a species selection dataset
 *
 * @author mre16utu
 */
public class ProblemSpecies implements Runnable {

    private PropertyChangeSupport pcs;
    private ArrayList<Integer> probSpecies;
    private boolean insufficient, finished;
    private File file;
    private FileList fileList;// list of files created by this class
    private int initialNoSpecies, expMarginPct, minPoints;
    private PlotPoints points;
    private Options option;

    public ProblemSpecies(File file, int initialNoSpecies, int expMarginPct, Options option) throws FileNotFoundException {
        this.pcs  = new PropertyChangeSupport(this);
        this.file = file;
        this.initialNoSpecies = initialNoSpecies;
        this.expMarginPct = expMarginPct;
        this.option = option;
        this.insufficient = false;
        this.finished = false;
        this.points = new PlotPoints();
        this.probSpecies = new ArrayList<>();
        this.fileList = new FileList();
    }
    
    public void deleteFiles()
    {
        fileList.delete();
    }
    
    public boolean isFinished() {
        return finished;
    }
    
    public ArrayList<Integer> getProblemSpecies()
    {
        return probSpecies;
    }
    
    public PlotPoints getPoints()
    {
        return points;
    }
    
    private void addProbSpecies(int i)
    {
        probSpecies.add(i);
        pcs.firePropertyChange("size", null, probSpecies.size());
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    private void findProblemSpecies(File file, int initialNoSpecies, int expMarginPct) throws FileNotFoundException, InterruptedException {
        long startPsTime = System.currentTimeMillis();
        List<String> fileAsList = readFileToList(file);
//        System.out.println(listAsString(fileAsList));

        int numSpecies = fileAsList.size();
        String fileName = file.getName();
        String fileExt = fileName.substring(fileName.lastIndexOf("."));
        fileName = fileName.substring(0, fileName.lastIndexOf("."));

        if (numSpecies - initialNoSpecies > PlotPoints.minPointsToFitCurve) {

            for (int i = initialNoSpecies; i < numSpecies; i++) {
                String tempFileName = fileName + "_" + i + fileExt;
                System.out.println(tempFileName);
                PrintWriter out = new PrintWriter(tempFileName);
                String str = "";
                int currentSpecies = -1;
                for (int j = 0; j <= i; j++) {
                    // get jth line from dataset
                    str = fileAsList.get(j);
                    // get species number from the jth line 
                    currentSpecies = Integer.parseInt(str.replaceFirst(".*?(\\d+).*", "$1"));
//                    System.out.println("currentSpecies: " + currentSpecies + ", i: " + i);
                    
                    //check if species is problem species or not                    
                    if(!probSpecies.contains(currentSpecies))
                    {
                        //add species to latest file
                        out.println(str);
                    }
                }
                out.flush();
                out.close();
                fileList.add(new File(tempFileName));
                

                String[] args = {tempFileName};
                SpeciesSelection specSel = new SpeciesSelection(args, false);// run with truncated results to reduce processing time (allResults = false)
                long startTime = System.nanoTime();
                MinSpecSetFamily mssf = specSel.getMssf();
                int mssfSize = mssf.size();
                long totalTimeMs = (System.nanoTime() - startTime) / 1000000;
                System.out.println("File: " + tempFileName + ", took: " + totalTimeMs + "ms");

                // produce output if requested
                if(option == Options.ALL || (i == numSpecies - 1 && option == Options.FINAL))
                {
                    specSel.outputResults(mssf, tempFileName);
                    fileList.add(new File(specSel.getResultsFileName()));
                }
                                
                // add new point to plot points list
                points.addPoint(i - probSpecies.size(), mssfSize, tempFileName, totalTimeMs);
                
                
                               
                // once enough initial points are generated to fit an exponential 
                // curve, begin checking if the last point added is within limits
                if (points.size() > PlotPoints.minPointsToFitCurve) {
                    if (!withinExpMargin(expMarginPct)) {
                        System.err.println("Problem Species: " + currentSpecies);
                        this.addProbSpecies(currentSpecies);
                    }
                }
            }
            System.out.println("Points:\n" + points);
            System.out.println("Problem Species Time Taken: " + (System.currentTimeMillis() - startPsTime)/3600000.0 + " hours");
        } else {
            System.out.println("Insufficient Species");
            insufficient = true;
        }
    }
    
    // checks if last point in the arrays is within the acceptable margin of an exponential fitted curve
    private boolean withinExpMargin(int expMarginPct) {
        return points.getLastMargin() < (1 + (expMarginPct / 100.0));
    }

    public static List<String> readFileToList(File file) throws FileNotFoundException {
        String line;
        List<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            list.add(line);
        }
        scanner.close();
        return list;
    }

    public String listAsString(List<String> list) {
        String str = "";
        for (String string : list) {
            str += string + "\n";
        }
        return str;
    }

    @Override
    public String toString() {
        if (insufficient) {
            return "Insufficient Species";
        }
        if (probSpecies.isEmpty()) {
            return "No Problem Species";
        }
        String str = "";
        // Loop over species
        for (int i = 0; i < probSpecies.size(); i++) {
            str += probSpecies.get(i);
            if (i < probSpecies.size() - 1) {
                str += ", ";
            }
        }
        return str;
    }

    @Override
    public void run() {
        try 
        {
            findProblemSpecies(file, initialNoSpecies, expMarginPct);
        } 
        catch (FileNotFoundException | InterruptedException ex) {
            Logger.getLogger(ProblemSpecies.class.getName()).log(Level.SEVERE, null, ex);
        }
        finished = true;
    }

}
