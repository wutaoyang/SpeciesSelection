package preprocessing;

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
import trendlines.ExpTrendLine;
import trendlines.TrendLine;

/**
 * Identifies list of species that cause faster than exponential growth in
 * solving time for a species selection dataset
 *
 * @author mre16utu
 */
public class ProblemSpecies implements Runnable {

    ArrayList<Integer> probSpecies;
    boolean insufficient, finished, hasChanged;
    File file;
    int initialNoSpecies, expMarginPct, minPoints;
    PlotPoints points;

    public ProblemSpecies(File file, int initialNoSpecies, int expMarginPct) throws FileNotFoundException {
        this.file = file;
        this.initialNoSpecies = initialNoSpecies;
        this.expMarginPct = expMarginPct;        
        this.insufficient = false;
        this.finished = false;
        this.points = new PlotPoints();
        this.probSpecies = new ArrayList<>();
        this.minPoints = 3; // minimum number of points required before we start fitting an exponential curve to the data
        
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

    private void findProblemSpecies(File file, int initialNoSpecies, int expMarginPct, int minNoSpeciesToFitExpCurve) throws FileNotFoundException, InterruptedException {
        long startPsTime = System.currentTimeMillis();
        List<String> fileAsList = readFileToList(file);
        System.out.println(listAsString(fileAsList));

        int numSpecies = fileAsList.size();
        String fileName = file.getName();
        String fileExt = fileName.substring(fileName.lastIndexOf("."));
        fileName = fileName.substring(0, fileName.lastIndexOf("."));

        if (numSpecies - initialNoSpecies > minNoSpeciesToFitExpCurve) {

            for (int i = initialNoSpecies; i < numSpecies; i++) {
                String tempFileName = fileName + "_" + i + fileExt;
                System.out.println(tempFileName);
                PrintWriter out = new PrintWriter(tempFileName);
                for (int j = 0; j <= i; j++) {
                    if(!probSpecies.contains(j))
                    {
                        String str = fileAsList.get(j);
                        out.println(str);
                    }
                }
                out.flush();
                out.close();

                String[] args = {tempFileName};
                SpeciesSelection specSel = new SpeciesSelection(args, false);// run with truncated results to reduce processing time (allResults = false)
                long startTime = System.nanoTime();
                MinSpecSetFamily mssf = specSel.getMssf();
                int mssfSize = mssf.size();
                long totalTimeMs = (System.nanoTime() - startTime) / 1000000;
                System.out.println("File: " + tempFileName + ", took: " + totalTimeMs + "ms");

                // produce output for final run
                if(i == numSpecies-1)
                {
                    specSel.outputResults(mssf, tempFileName);
                }
                
                // add new point to plot points list
                points.addPoint(i - probSpecies.size(), mssfSize);
                
                // once enough initial points are generated to fit an exponential 
                // curve, begin checking if the last point added is within limits
                if (points.size() > minNoSpeciesToFitExpCurve) {
                    if (!withinExpMargin(points, expMarginPct)) {
                        System.err.println("Problem Species: " + i); // TODO get i from species ID uncase the species not in order or some missing
                        probSpecies.add(i);
                        hasChanged = true;
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
    private boolean withinExpMargin(PlotPoints points, int expMarginPct) {
        double[] xArr = points.getXArr();
        double[] yArr = points.getYArr();
        TrendLine t = new ExpTrendLine();
        t.setValues(yArr, xArr);
        double xLast = xArr[xArr.length - 1];
        double yLast = yArr[yArr.length - 1];
        // error factor = measurement / prediction
        double errMargin = yLast/t.predict(xLast);
        System.err.println("Margin: " + errMargin);
        return errMargin < (1 + (expMarginPct / 100.0));
    }

    private List<String> readFileToList(File file) throws FileNotFoundException {
        String line;
        List<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            list.add(line);
        }
        scanner.close();
//        list.remove(0);//remove titles line
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
            findProblemSpecies(file, initialNoSpecies, expMarginPct, minPoints);
        } 
        catch (FileNotFoundException | InterruptedException ex) {
            Logger.getLogger(ProblemSpecies.class.getName()).log(Level.SEVERE, null, ex);
        }
        finished = true;
    }

    public boolean hasChanged() {
        if(hasChanged)
        {
            hasChanged=false;
            return true;
        }
        return false;
    }

}
