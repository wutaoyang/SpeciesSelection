package preprocessing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import speciesselection.Species;
import speciesselection.SpeciesSelection;
import trendlines.ExpTrendLine;
import trendlines.TrendLine;

/**
 * Identifies list of species that cause faster than exponential growth in
 * solving time for a species selection dataset
 *
 * @author mre16utu
 */
public class ProblemSpecies {

    ArrayList<Integer> probSpecies;
    boolean insufficient;

    public ProblemSpecies(File file, int initialNoSpecies, int expMarginPct) throws FileNotFoundException {
        this.insufficient = false;
        this.probSpecies = new ArrayList<>();
        findProblemSpecies(file, initialNoSpecies, expMarginPct);
    }

    private void findProblemSpecies(File file, int initialNoSpecies, int expMarginPct) throws FileNotFoundException {
        List<String> fileAsList = readFileToList(file);
        System.out.println(listAsString(fileAsList));

        int numSpecies = fileAsList.size();
//        int initialSubsetSize = Math.max(1, numSpecies * initialNoSpecies / 100);
        String fileName = file.getName();
        String fileExt = fileName.substring(fileName.lastIndexOf("."));
        fileName = fileName.substring(0, fileName.lastIndexOf("."));

        int minNoSpeciesToFitExpCurve = 3;
        if (numSpecies - initialNoSpecies > minNoSpeciesToFitExpCurve) {
            PlotPoints points = new PlotPoints();

            for (int i = initialNoSpecies; i < numSpecies; i++) {
                String tempFileName = fileName + "_" + i + fileExt;
                System.out.println(tempFileName);
                PrintWriter out = new PrintWriter(tempFileName);
                for (int j = 0; j <= i; j++) {
                    if(!probSpecies.contains(j))
                    {
                        String str = fileAsList.get(j);
//                      System.out.println("str: " + str);
                        out.println(str);
                    }
                }
                out.flush();
                out.close();

                String[] args = {tempFileName};
                SpeciesSelection specSel = new SpeciesSelection(args, true);
                long startTime = System.nanoTime();
                specSel.run();
                long totalTimeMs = (System.nanoTime() - startTime) / 1000000;
                System.out.println("File: " + tempFileName + ", took: " + totalTimeMs + "ms");

                // add new point to plot points list
                points.addPoint(i - probSpecies.size(), totalTimeMs);
                
                //once enough points, check if the last point added is within limits
                if (points.size() > minNoSpeciesToFitExpCurve) {
                    if (!withinExpMargin(points, expMarginPct)) {
                        System.err.println("Problem Species: " + i);
                        probSpecies.add(i);
                    }
                }

            }
            System.out.println("Points:\n" + points);
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
        double errMargin = t.predict(xArr[xArr.length - 1])/yArr[yArr.length - 1];
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

}
