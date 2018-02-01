/*
 *Species Selection
 * 
 * Represents a potential indicatior, i.e., a set of species
 */
package speciesselection;

import java.util.*;

/**
 *
 * @author taoyang wu @UEA (taoyang.wu@uea.ac.uk)
 */
public class SpecSet implements Comparable<SpecSet> {

    private Set<Species> spSet;

    public SpecSet() {
        spSet = new HashSet<>();
    }

    public SpecSet(ArrayList<Species> specList) {
        spSet = new HashSet<>();
        for (Species spec : specList) {
            addSpecies(spec);
        }
    }

    public boolean contains(Species spec) {
        return spSet.contains(spec);
    }

    public void addSpecies(Species spec) {
        spSet.add(spec);
    }

    public void addSpecies(Set<Species> inSpecSet) {
        for (Species inSpec : inSpecSet) {
            spSet.add(inSpec);
        }
    }
    
    public ArrayList<Species> sortForPrint()
    {
        ArrayList<Species> spList = new ArrayList<>(spSet);
        Collections.sort(spList, (a, b) -> ((Species) a).getName().compareTo(((Species) b).getName()));
        return spList;
    }

    public int size() {
        return spSet.size();
    }

    public boolean isEqualTo(SpecSet other) {
        if (this.size() == other.size() && spSet.containsAll(other.spSet)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSuberSetOf(SpecSet other) {
        return (other.spSet).containsAll(spSet);
    }

    public boolean isSuperSetof(SpecSet other) {
        return spSet.containsAll(other.spSet);
    }

    public int getSumSensitivity() {
        int result = 0;

        for (Species sp : spSet) {
            result += sp.getSensitivity();
        }
        return result;
    }

    public double getMeanSensitivity() {
        if (spSet.isEmpty()) {
            return 0;
        }
        return this.getSumSensitivity() * 1.0 / spSet.size();
    }

    public int getMaxSensitivity() {
        if (spSet.isEmpty()) {
            System.out.print("The species set contains no elements!");
        }
        int result = 0;
        for (Species sp : spSet) {
            if (result < sp.getSensitivity()) {
                result = sp.getSensitivity();
            }
        }
        return result;
    }

    public SpecSetFamily extendToFamily(ArrayList<Species> inSpecList, int targetSetSize, int targetFamilyNum) {
        SpecSetFamily result = new SpecSetFamily();

        //find the elements that are not in the current set
        ArrayList<Species> tmpSpecList = new ArrayList<>();
        for (Species sp : inSpecList) {
            if (!contains(sp)) {
                tmpSpecList.add(sp);
            }
        }

        Collections.sort(tmpSpecList);
        int extNum = targetSetSize - size();
        if (extNum == 0) {
            result.addSpecSet(this);
            return result;
        }

        //get the combinatorical size and subset indicator
        CombSize rangSize = new CombSize(extNum, targetFamilyNum);
        int maxNum = rangSize.getSize();

        if (maxNum > tmpSpecList.size()) {
            System.out.println("Something is wrong about tmpSpecList");
        }

        CombSubSet rangSets = new CombSubSet(extNum, maxNum);
        ArrayList<ArrayList<Integer>> indexSets = rangSets.getSubSets();

        for (ArrayList<Integer> curList : indexSets) {
            SpecSet tmpSet = new SpecSet();
            tmpSet.addSpecies(spSet);
            for (Integer index : curList) {
                Species tmpSpecies = tmpSpecList.get(index.intValue());
                tmpSet.addSpecies(tmpSpecies);
            }
            result.addSpecSet(tmpSet);
        }
        return result;
    }

    /**
     *
     * @param inSpecList A set of species to be added into the current family
     * @param targetSize The number of members in the family aimed to get
     * @return true iff the current family has been extended to the target size
     */
    public boolean increaseToSize(ArrayList<Species> inSpecList, int targetSize) {
        if (size() > targetSize) {
            return false;
        }
        if (size() == targetSize) {
            return true;
        }
        for (Species spec : inSpecList) {
            this.addSpecies(spec);
            if (this.size() == targetSize) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(SpecSet other) {
        int curSen = this.getMaxSensitivity();
        int otherSen = other.getMaxSensitivity();
        if (curSen != otherSen) {
            return curSen - otherSen;
        }

        double curMean = this.getMeanSensitivity();
        double otherMean = other.getMeanSensitivity();

        if (curMean < otherMean) {
            return -1;
        }
        if (curMean == otherMean) {
            return 0;
        }
        return 1;
    }

    @Override
    public String toString() {
        StringBuilder outStr = new StringBuilder();
        outStr.append("\r\n");
        outStr.append(this.getMaxSensitivity()).append(":");
        outStr.append(String.format("%.02f", this.getMeanSensitivity())).append(":");
        outStr.append(spSet);
        return outStr.toString();
    }
}
