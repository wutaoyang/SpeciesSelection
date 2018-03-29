package preprocessing;

import java.util.Set;
import java.util.TreeSet;

/**
 * Class to provide statistics related to optimal species selection solutions
 * for a given set size
 *
 * @author mre16utu
 */
public class ResultSet {

    private final int setSize;
    private double maxSensitivity;
    private double meanSensitivity;
    private final Set<Integer> setInts;// set of all ints that appear in an optimal solution

    /**
     * Constructor
     * @param setSize 
     */
    public ResultSet(int setSize) {
        this.setSize = setSize;
        this.maxSensitivity = -1;
        this.meanSensitivity = -1;
        this.setInts = new TreeSet<>();
    }
    
    /**
     * returns the number of species in the result sets being analysed in 
     * this ResultSet
     * @return 
     */
    public int getSetSize()
    {
        return setSize;
    }
    
    /**
     * returns setInts - the species numbers in optimal results sets
     * @return 
     */
    public Set<Integer> getSetInts()
    {
        return setInts;
    }
    
    /**
     * returns whether setInts is empty
     * @return 
     */
    public boolean isEmpty()
    {
        return setInts.isEmpty();
    }
    
    /**
     * Takes in a Species selection result set string of the format 54:41.33:[24, 72, 77]]
     * Extracts the species numbers from the string and adds them to setInts so 
     * long as the solution is optimal
     * @param line 
     */
    public void add(String line)
    {
        try {
            String[] parts = line.split(":");
            double firstDouble = Double.parseDouble(parts[0]);
            double secondDouble = Double.parseDouble(parts[1]);
            if (maxSensitivity == -1) {
                maxSensitivity = firstDouble;
                meanSensitivity = secondDouble;
            }

            // if line contains an optimal solution process the line
            if (maxSensitivity == firstDouble && meanSensitivity == secondDouble) {
                String set = parts[2].replaceAll("[\\[\\]\\,]", "");
                String[] strInts = set.split(" ");

                // check set size is as expected and throw error if it doesn't
                if (strInts.length != setSize) {
                    throw new Exception("Error: Set size mismatch");
                }

                for (int i = 0; i < setSize; i++) {
                    setInts.add(Integer.parseInt(strInts[i]));
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * returns whether an integer is in an optimal solution
     * @param i
     * @return 
     */
    public boolean isInOptimalSolution(int i) {
        return setInts.contains(i);
    }
    
    @Override
    public String toString()
    {
        if(setInts.isEmpty())
        {
            return "empty";
        }
        return setInts.toString();
    }
}