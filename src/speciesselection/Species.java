/*
 * ResourceType Species Selection
 * 
 * Represent a species, which is comparable according to their
 * sensitivities
 */
package speciesselection;

import java.util.*;

/**
 *
 * @author taoyang wu @UEA (taoyang.wu@uea.ac.uk)
 */
public class Species implements Comparable<Species> {

    protected int speciesName;
    protected int reliance;
    protected boolean sensType;   //true if the sensitivity is imposed
    protected double sensitivity;  //a new field added in v1.0
                                 //default sensitivity is -1
    protected double areas; //a new field
    protected double precision; //a new field

    protected ArrayList<ResourceType> rtList;

    public Species() {
        rtList = new ArrayList<>();
        sensitivity = -1;
        areas = -1;
        precision = -1;
        sensType = false;
    }

    public Species(int name) {
        speciesName = name;
        rtList = new ArrayList<>();
        sensitivity = -1;
        areas = -1;
        precision = -1;
        sensType = false;
    }

    public void setAreas(double inAreas) {
        this.areas = inAreas;
    }

    public int numResourceTypes() {
        return rtList.size();
    }

    public boolean getSensType() {
        return sensType;
    }

    public void setSensType(boolean sensType) {
        this.sensType = sensType;
    }

    public double getAreas() {
        return areas;
    }

    public void setPrecision(double inPrecision) {
        this.precision = inPrecision;
    }

    public double getPrecision() {
        return precision;
    }

    public void remRT(ResourceType rt) {
        rtList.remove(rt);
    }

    //return the list of IDs for the Resource Type
    public ArrayList<Integer> getRTIDs() {
        ArrayList<Integer> result = new ArrayList<Integer>();

        for (ResourceType rt : rtList) {
            Integer tmp = new Integer(rt.getID());
            result.add(tmp);
        }

        return result;
    }

    public double getSensitivity() {

        //imposed sensType
        if (sensType == true) {
            return sensitivity;
        }
        //reliance*resource
        return reliance * rtList.size();
    }

    /**
     * @param otherSpec Another species
     * @return the sensitivity of the current minus that of the otherSpec.
     */
    @Override
    public int compareTo(Species otherSpec) {
        double tmp = this.getSensitivity() - otherSpec.getSensitivity();
        if (tmp > 0) {
            return 1;
        }
        if (tmp == 0) {
            return 0;
        }
        return -1;
    }

    public Integer getName() {
        return speciesName;
    }

    public int getReliance() {
        return reliance;
    }

    public void setReliance(int inRel) {
        reliance = inRel;
    }

    public void setSensitivity(double inSen) {
        sensitivity = inSen;
    }

    //Set senstivity using the default method
    public void setSensitivity() {
        sensitivity = reliance * rtList.size();
    }

    public void addResourceType(ResourceType newInd) {
        if (rtList.contains(newInd) == false) {
            rtList.add(newInd);
        }
    }

    //return true if the rticator in this object is used by other 
    public boolean isBelow(Species other) {
        return other.rtList.containsAll(rtList);
    }

    @Override
    public String toString() {
        return "" + speciesName;
    }
}
