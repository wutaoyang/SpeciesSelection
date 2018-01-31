/*
 * Indicator Species Selection
 * 
 * Represents a node in class SpecTree
 */
package speciesselection;

import java.util.*;

/**
 *
 * @author taoyang wu @UEA (taoyang.wu@uea.ac.uk)
 */
public class SpecTreeNode {
    
    protected Species spec;
    
    protected SpecTreeNode parent;
    
    protected ArrayList<SpecTreeNode> chList;
    
    public SpecTreeNode(){
        chList=new ArrayList<SpecTreeNode>();
        spec=null;
        parent=null;
    }
    
    public SpecTreeNode(Species spec){
        this();
        this.spec=spec;
    }
    
    public void setSpecies(Species spec){
        this.spec=spec;
    }
    
    public Species getSpecies(){
        return spec;
    }
    
    public void setParent(SpecTreeNode parent){
        this.parent=parent;
    }
    
    public SpecTreeNode getParent(){
        return parent;
    }
    
    
    
    public void addChild(SpecTreeNode chSpec){
        
        chSpec.setParent(this);
        
        if(!chList.contains(chSpec))
            chList.add(chSpec);
    }
    
    public void removed(){
        if(parent!=null){
            parent.chList.remove(this);
            parent=null;            
        }
        spec=null;        
        chList=null;      
    }
    
     
    
    public boolean hasChild(SpecTreeNode chSpec){
        return chList.contains(chSpec);
    }
    
    
      
    public boolean isLeaf(){
        return (chList.isEmpty())?true:false;
    }
    
    public boolean isRoot(){
        return (parent==null)?true:false;
    }
    
    public int getLevel(){
       int level=1;
       if(parent!=null){
           level+=parent.getLevel();
       }
       return level;
    }
    
    public ArrayList<Species> getAncestors(){
        ArrayList<Species> result=new ArrayList<Species>();
        result.add(spec);
        if(parent!=null){
            result.addAll(parent.getAncestors());
        }        
        return result;        
    }
    
    /**
     * 
     * @return the collection of leave nodes below the current one
     */
    
    public ArrayList<SpecTreeNode> getLeavesBelow(){
        ArrayList<SpecTreeNode> result=new ArrayList<SpecTreeNode>();
        if(isLeaf()){   
            result.add(this);
        }
        for(SpecTreeNode stn: chList){
            result.addAll(stn.getLeavesBelow());
        }
        return result;
    }
    
    /**
     * @param spec A species
     * @return true if the species is contained in an ancestor of 
     * the node (including itself), false otherwise. 
     */
    
    
    public boolean isAncestor(Species spec){
        if(getSpecies()==spec) return true;
        if(isRoot()) return false;
        
        return(parent.isAncestor(spec));        
        
    }
    
    public int numNodesBelow(){
        if(this==null) return 0;
        int result=1;
        for(SpecTreeNode stn:chList){
            result+=stn.numNodesBelow();
        }
        return result;
    }
    
    public String toString(){
        StringBuilder outStr=new StringBuilder();
           
        if(isRoot()&&isLeaf()){
            outStr.append("(").append(spec.getName()+")");
            return outStr.toString();
        }
        
        if(isLeaf()) {
            outStr.append(spec.getName()+"~"+this.getLevel()+",");            
        } 
        else{
            outStr.append("(").append(spec.getName()+":"+this.getLevel());
            outStr.append("(");
            for(SpecTreeNode sp : chList){
            outStr.append(sp).append("|");            
        }
            outStr.append(")");
            outStr.append(")");
        }
            
         
         return outStr.toString();
    }
    
    
}
