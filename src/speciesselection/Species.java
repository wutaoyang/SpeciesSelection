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
public class Species implements Comparable<Species>{
    
   protected String speciesName;    
   protected  int reliance;  
    
   protected ArrayList<ResourceType> rtList;
    
    public Species(){
        rtList=new ArrayList<ResourceType>();
    }
    
    public Species(String name){
        speciesName=name;
        rtList=new ArrayList<ResourceType>();
    }
    
    
    public int getSensitivity(){       
        
        //to check reliance and sensitivity; todo
        if(reliance<0){
            System.out.println("The species has non-positive reliance");
        }        
        
        return reliance*rtList.size();
    }
    
    
    /**
     * 
     * @param otherSpec  Another species
     * @return the sensitivity of the current minus that of the otherSpec.
     */
    
    public int compareTo(Species otherSpec){
        return this.getSensitivity()-otherSpec.getSensitivity();
    }
    
    public String getName(){
        return speciesName;
    }
    
    public int getReliance(){
        return reliance;
    }
    
    public void setReliance(int inRel){
        reliance=inRel;
    }
    
    public void addResourceType(ResourceType newInd){
        if(rtList.contains(newInd)==false)
        rtList.add(newInd);
    }
    
    
    //return true iff the rticator in this object is used by other 
    //
    public boolean isBelow(Species other){
        return other.rtList.containsAll(rtList);
    }
    
    
    
    public String toString(){
        
         StringBuilder specStr=new StringBuilder();//"Species: ");
         specStr.append(speciesName);//.append("\n");

         return specStr.toString();
        
    }      
    
    
    
}
