/*
 * Indicator Species Selection
 * 
 * The tree structure used to compute all possible 'irreducible' species sets
 * that is, those species sets covering all resource types whils any proper subset does not
 */
package speciesselection;

import java.util.*;

/**
 *
 * @author taoyang wu @UEA (taoyang.wu@uea.ac.uk)
 */
public class SpecTree {
    
    private SpecTreeNode root;
    
    public SpecTree(){
        root=null;
    }
    
    public SpecTree(SpecTreeNode root){
        this.root=root;
    }
    
    public SpecTreeNode getRoot(){
        return root;
    }
    
    
    public int size(){
        return root.numNodesBelow();
    }
    
    public ArrayList<SpecTreeNode> getLeaves(){
        return root.getLeavesBelow();
    }
    
     
    public String toString(){
      StringBuilder outStr=new StringBuilder("Spec tree: \n");
      outStr.append(this.getRoot());
      
        
         
      return outStr.toString();
    }   
    
}
