/*
 * Species Selection
 * 
 * Represents a column in the resource requirement matrix. Note that the use of indicator in the algorithm is
 * different than that in the paper. That is, in the paper an indicator set refers to a set of species
 * while here indicators mean
 */
package speciesselection;

import java.util.*;

/**
 *
 * @author taoyang wu @UEA (taoyang.wu@uea.ac.uk)
 */
public class ResourceType implements Comparable<ResourceType> {

    protected int id;  // The position of the column in the matrix
    protected ArrayList<Species> specList;

    public ResourceType() {
        specList = new ArrayList<>();
    }

    public ResourceType(int inID) {
        id = inID;
        specList = new ArrayList<>();
    }

    public boolean noSpecies() {
        return specList.isEmpty();
    }

    public void setID(int inID) {
        id = inID;
    }

    public int getID() {
        return id;
    }

    public int numSpecies() {
        return specList.size();
    }

    public void addSpecies(Species inSpec) {
        if (specList.contains(inSpec) == false) {
            specList.add(inSpec);
        }
    }

    public boolean adjacentTo(Species inSpec) {
        return specList.contains(inSpec);
    }

    public Species getFirstSpecies() {
        return specList.get(0);
    }

    /**
     *
     * @param otherRT A second resource type to be compared
     * @return The number of species associated with the current resource type
     * minus that of otherRT.
     */
    @Override
    public int compareTo(ResourceType otherRT) {
        return numSpecies() - otherRT.numSpecies();
    }

    /**
     *
     * @param otherRT A second resource type to be compared
     * @return True if the current resource type is redundant w.r.t otherRT,
     * that is, all species associated with otherRT are also associated with the
     * current one.
     */
    public boolean isCoveredBy(ResourceType otherRT) {
        return specList.containsAll(otherRT.specList);
    }

    @Override
    public String toString() {
        return "R" + id;
    }
}
