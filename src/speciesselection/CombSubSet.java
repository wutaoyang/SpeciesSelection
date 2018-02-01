/*
 * Indicator Species Selection
 * 
 * This class represents the collection of sets of integer indices to be checked,
 * which is defined as the collection of sets with cardinality $k$ from the poweset of 
 * ${0,1,....,n-1}. Here $k$ is specified by cardSize and $n$ by maxNumber.
 * 
 * For instance, if $k=2$ and $n=4$, then the collection is
 * {{[0, 1}, {0, 2}, {1, 2}, {0, 3}, {1, 3}, {2, 3}}  
 * 
 */
package speciesselection;

import java.util.*;

/**
 *
 * @author taoyang wu @UEA (taoyang.wu@uea.ac.uk)
 */
public class CombSubSet {
    
    private final int maxNumber;
    private final int cardSize;
   
    //store all subsets with given cardSize and 
    //all elements bounded above by maxNumber (not included) 
    //and below by zero (included)
    //
    protected ArrayList<ArrayList<Integer>> subSets;
    
    public CombSubSet(int cardSize, int maxNumber){
        
        //to throw exceptions
        if(cardSize>maxNumber)
            System.out.println("Something about the permuation is wrong");
        
        this.cardSize=cardSize;
        this.maxNumber=maxNumber;
        subSets=new ArrayList<>();
        initialize();
    }
    
     public ArrayList<ArrayList<Integer>> getSubSets(){
         return subSets;
     }
    
    
    private void initialize(){
      
        //case 1
         if(cardSize==maxNumber){
            ArrayList<Integer> tmpSet=new ArrayList<>();
            for(int i=0;i<maxNumber;i++){
                Integer tmp=new Integer(i);
                        tmpSet.add(tmp);
            }
            subSets.add(tmpSet); 
            return;
        }
        
        
         //case 2
        if(cardSize==1){
               for(int i =0; i<maxNumber;i++){
                Integer tmp=new Integer(i);
                ArrayList<Integer> tmpSet=new ArrayList<Integer>();
                tmpSet.add(tmp); 
                subSets.add(tmpSet);
            }
            return;
     
        }
        
        //the case for 1<cardSize<maxNumber 
           for(int i =cardSize;i<=maxNumber;i++){
                CombSubSet tmpSetFamily=new CombSubSet(cardSize-1,i-1);
                ArrayList<ArrayList<Integer>> tmpSets=tmpSetFamily.getSubSets();
                for(ArrayList<Integer> intList:tmpSets){
                    intList.add(i-1);
                    subSets.add(intList);
                }                    
            }                
    }
    
    
    
    public String toString(){
        StringBuilder outStr=new StringBuilder();
             outStr.append(subSets);
             return outStr.toString();
    }
    
    
    //test functions
  public static void main (String[] args)
 {
     CombSubSet test = new CombSubSet(2,5);
     System.out.println(test);
 }
    
}
