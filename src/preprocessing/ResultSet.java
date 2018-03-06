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
//    private List<int[]> setList;
    private Set<Integer> setInts;// set of all ints that appear in an optimal solution

    public ResultSet(int setSize) {
        this.setSize = setSize;
        this.maxSensitivity = -1;
        this.meanSensitivity = -1;
//        this.setList = new ArrayList<>();
        this.setInts = new TreeSet<>();
    }
    
    public int getSetSize()
    {
        return setSize;
    }
    
    public Set<Integer> getSetInts()
    {
        return setInts;
    }

    public void add(String line)// format 54:41.33:[24, 72, 77]]
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

                int[] ints = new int[setSize];
                for (int i = 0; i < setSize; i++) {
                    ints[i] = Integer.parseInt(strInts[i]);
                    setInts.add(Integer.parseInt(strInts[i]));
                }

//                setList.add(ints);
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }

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