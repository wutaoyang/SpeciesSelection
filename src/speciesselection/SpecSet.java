/*
 * Species Selection
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

    public SpecSet(Set<Species> spSet) {
        this.spSet = new HashSet<>(spSet);
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

    public ArrayList<Species> sortForPrint() {
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

    public boolean isSubSetOf(SpecSet other) {
        return (other.spSet).containsAll(spSet);
    }

    public boolean isSuperSetof(SpecSet other) {
        return spSet.containsAll(other.spSet);
    }

    public double getSumSensitivity() {
        double result = 0;
        for (Species sp : spSet) {
            result += sp.getSensitivity();
        }
        return result;
    }

    public double getMeanSensitivity() {
        if (spSet.isEmpty()) {
            return 0;
        }
        return this.getSumSensitivity() / spSet.size();
    }

    public double getMaxSensitivity() {
        if (spSet.isEmpty()) {
            System.out.print("The species set contains no elements!");
        }
        double result = 0;
        for (Species sp : spSet) {
//            double sensitivity = sp.getSensitivity();
//            if (result < sensitivity) {
//                result = sensitivity;
//            }
            result = Math.max(result, sp.getSensitivity());
        }
        return result;
    }

    public SpecSetFamily extendToFamily(ArrayList<Species> inSpecList, int targetSetSize, int targetFamilyNum) throws SpecSelException {
        SpecSetFamily result = new SpecSetFamily();

        //find the elements that are not in the current set
        ArrayList<Species> tmpSpecList = new ArrayList<>(inSpecList);
        tmpSpecList.removeAll(spSet);
        Collections.sort(tmpSpecList);

        int extNum = targetSetSize - size();
        if (extNum == 0) {
            result.addSpecSet(this);
            return result;
        }

        //**********************************************************************
        // Attempt to avoid maxNum > tmpSpecList.size() by Stephen W - NOT SURE IF THIS BREAKS ANYTHING
        // Suspect this is OK because targetFamilyNum is hard coded with a default value of 11 in SpeciesSelection class
        if(targetFamilyNum > tmpSpecList.size())
        {
            targetFamilyNum = tmpSpecList.size();
            System.err.println("targetFamilyNum was changed - SPW not sure if "
                    + "this breaks the algorithm but if avoids exceptions:0(");// 27/03/2018
        }
        //**********************************************************************

        //get the combinatorical size and subset indicator
        CombSize rangSize = new CombSize(extNum, targetFamilyNum);
        int maxNum = rangSize.getSize();

        if (maxNum > tmpSpecList.size()) {
            String str = "Exception in SpecSet.extendToFamily: Something is wrong about tmpSpecList\n"
                       + "This may be due to too few Species in the dataset";
            System.out.println(str);
            ReadFile.infoBox(str, "SpecSet error");
            throw new SpecSelException(str);
        }

        CombSubSet rangSets = new CombSubSet(extNum, maxNum);
        ArrayList<ArrayList<Integer>> indexSets = rangSets.getSubSets();

        SpecSet tmpSet;
        for (ArrayList<Integer> curList : indexSets) {
            tmpSet = new SpecSet(spSet);
            for (Integer index : curList) {
                tmpSet.addSpecies(tmpSpecList.get(index));
            }
            result.addSpecSet(tmpSet);
        }
        return result;
    }

    /**
     *
     * @param inSpecList A set of species to be added into the current family
     * @param targetSize The number of members in the family aimed to get
     * @return true if the current family has been extended to the target size
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
    public int compareTo(SpecSet other){
        double curSen=this.getMaxSensitivity();
        double otherSen=other.getMaxSensitivity();
        if(curSen<otherSen)
            return -1;
        if(curSen>otherSen)
            return 1;
        
        double curMean=this.getMeanSensitivity();
        double otherMean=other.getMeanSensitivity();
     
        if(curMean<otherMean) return -1;
        if(curMean==otherMean) return 0;
        
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        SpecSet other = (SpecSet) obj;
        return this.isEqualTo(other);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.spSet);
        return hash;
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
