/*
 * Indicator Species Selection
 * 
 * Represent a collection of species sets in which the number of species sets in 
 * the collection is bounded
 */
package speciesselection;

import java.util.*;

/**
 *
 * @author taoyang
 */
public class SpecSetFamily {
    private int numSetsLimit;
    protected ArrayList<SpecSet> ssf;

    public SpecSetFamily() {
        numSetsLimit = -1; //-1 denotes no limit
        ssf = new ArrayList<>();
    }

    public int getNumSetsLimit() {
        return numSetsLimit;
    }

    public void setNumSetsLimit(int numSetsLimit) {
        this.numSetsLimit = numSetsLimit;
    }

    public SpecSet getMaxSpecSetBySensitivity() {
        Collections.sort(ssf);
        return ssf.get(ssf.size() - 1);
    }

    public int size() {
        return ssf.size();
    }

    public SpecSet get(int index) {
        return ssf.get(index);
    }

    public void sortBySensitivity() {
        Collections.sort(ssf);
    }

    public void addSpecSet(SpecSetFamily inSSFamily) {
        for (SpecSet spSet : inSSFamily.ssf) {
            addSpecSet(spSet);
        }
    }

    public boolean addSpecSet(SpecSet inSS) {
        //check no set in the family is a subset of inSet
        for (SpecSet spSet : ssf) {
            if (spSet.isEqualTo(inSS)) {
                return false;
            }
        }
        ssf.add(inSS);

        //when no limit or the limit of size has not reached
        if (numSetsLimit < 0 || ssf.size() < numSetsLimit) {

            return true;
        }

        //when the limit reached, removed the one with largest sensitivity
        sortBySensitivity();
        int lastEleIndex = ssf.size() - 1;
        if (lastEleIndex >= 0) {
            ssf.remove(lastEleIndex);
        }
        return true;
    }

    @Override
    public String toString() {
        Collections.sort(ssf);
        // Sort species into numeric order for consistent printing
        String outStr = "[";
        for (SpecSet ss : ssf) {
            outStr += "\n" + ss.getMaxSensitivity()
                    + ":" + String.format("%.02f", ss.getMeanSensitivity())
                    + ":" + ss.sortForPrint();
        }
        outStr += "]";
        return outStr;
    }
}
