/*
 * Species Selection
 * 
 * Represents the collection of 'irreducible' dominating species sets, that is, 
 * those species sets covering all resources types while any proper subset does 
 * not
 */
package speciesselection;

import java.util.*;

/**
 *
 * @author taoyang
 */
public class MinSpecSetFamily {

    ArrayList<SpecSet> ssf;

    public MinSpecSetFamily() {
        ssf = new ArrayList<>();
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
    
    public void addMSSF(MinSpecSetFamily other)
    {
        Set<SpecSet> ssfSet = new HashSet<>(ssf);
        ssfSet.addAll(other.ssf);
        ssf = new ArrayList<>(ssfSet);
    }
    
    public boolean addSpecSet(SpecSet inSet) {
        //check no set in the family is a subset of inSet
        for (SpecSet spSet : ssf) {
            if (spSet.isSubSetOf(inSet)) {
                return false;
            }
        }

        //remove the sets in the family that are a superset of inSet
        ArrayList<SpecSet> tmpList = new ArrayList<>();
        for (SpecSet spSet : ssf) {
            if (spSet.isSuperSetof(inSet)) {
                tmpList.add(spSet);
            }
        }
        ssf.removeAll(tmpList);
        ssf.add(inSet);
        return true;
    }

    @Override
    public String toString() {
        Collections.sort(ssf);
        return ssf.toString();
    }
}
