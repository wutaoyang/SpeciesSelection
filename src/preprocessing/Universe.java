package preprocessing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class representing the 'universe' of a Species Selection data set. 
 * If the species cover all resources then the universe is all ones. 
 * Any resources not cover will be zeros.
 * 
 * @author mre16utu
 */
public final class Universe {
    
    List<Integer> universe;
    
    /**
     * Creates a universe from a list of strings as found in a species selection 
     * dataset. The list of strings should exclude the header line. Each string 
     * should begin with 4 int values and be followed by a series of ones and 
     * zeros
     * 
     * @param list 
     */
    public Universe (List<String> list)
    {
        universe = new ArrayList<>();
        boolean firstRow = true;
        for(String str: list)
        {
            processLine(str, firstRow);
            firstRow = false;
        }
    }
    
    /**
     * Processes a line during calculation of a universe
     * @param text
     * @param firstRow 
     */
    public void processLine(String text, boolean firstRow) {

        Scanner data = new Scanner(text);
        int spName = data.nextInt();
        int spReliance = data.nextInt();
        int spResources = data.nextInt();
        int spSensitivity = data.nextInt();

        // for each resource, set its index in the universe to 1 if it is covered
        int index = 0;
        while (data.hasNextInt()) {
            int value = data.nextInt();
            if(firstRow)
            {
                universe.add(value);
            }
            else 
            {
                universe.set(index, Math.max(universe.get(index), value));
            }
            index++;
        }
    }
    
    @Override
    public String toString()
    {
        String str = "";
        for(Integer i : universe)
        {
            str += i;
        }
        return str;
    }
    
    /**
     * returns whether 2 universes are equal
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj)
    {
        Universe other = (Universe) obj;
        if(this.universe.size() != other.universe.size())
        {
            return false;
        }
        
        for(int i = 0; i < universe.size(); i++)
        {
            if(this.universe.get(i).intValue() != other.universe.get(i).intValue())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.universe);
        return hash;
    }

    /**
     * Test method
     * @param args 
     */
    public static void main(String[] args) {
        
        List<String> list = new ArrayList<>();
        list.add("1	3	14	42	0	1	1	0	0	0	0	1	0	0	1	1	1	0	0	1	1	1	1	0	0	0	1	1	1	1	0	0");
        list.add("2	3	10	30	0	1	0	0	0	0	0	0	0	0	1	1	1	1	1	0	0	0	0	0	0	0	1	1	1	1	0	0");
        list.add("3	1	11	11	1	1	0	0	0	1	1	0	0	0	1	0	1	0	0	1	0	1	0	0	0	1	0	1	0	0	0	1");
        list.add("4	1	14	14	0	1	0	0	0	0	1	0	0	0	1	1	1	1	0	1	1	1	1	0	1	0	0	1	1	0	0	1");
        
        Universe uni = new Universe(list);
        System.out.println(uni.toString());
    }
}
