/*
 * Species Selection
 * 
 * A bipartite graph representing the incidenc structure between species 
 * and resouce types (RTs)
 * Contains most of the important methods of the project
 */
package speciesselection;

import java.util.*;

/**
 *
 * @author taoyang wu @UEA (taoyang.wu@uea.ac.uk)
 */
public class SpecRTGraph {

    //The bipartite graph is represented by two 'adjacent' lists
    //Here each species is associated with a list of resorce types (RT), and
    //each RT is associated with a list of species.
    protected ArrayList<Species> specNode;
    protected ArrayList<ResourceType> rtNode;

    SpecRTGraph() {
        specNode = new ArrayList<>();
        rtNode   = new ArrayList<>();
    }

    public void addSpecNode(Species inSpecies) {
        if (specNode.contains(inSpecies) == false) {
            specNode.add(inSpecies);
        }
    }

    public int numSpecies() {
        return specNode.size();
    }

    public int numResourceTypes() {
        return rtNode.size();
    }

    public void addRTNode(ResourceType newRT) {
        if (rtNode.contains(newRT) == false) {
            rtNode.add(newRT);
        }
    }

    public boolean containsSpecies(Species spec) {
        return specNode.contains(spec);
    }

    public boolean containsResourceType(ResourceType rt) {
        return rtNode.contains(rt);
    }

    public boolean noIsolatedResourceType() {
        for (ResourceType rt : rtNode) {
            if (rt.numSpecies() == 0) {
                System.out.println("There is an isolated resource type.");
                return false;
            }
        }
        return true;
    }

    public ResourceType getResourceTypeByID(int rtValue) {

        for (ResourceType tmpRT : rtNode) {
            if (tmpRT.getID() == rtValue) {
                return tmpRT;
            }
        }

        return null;
    }

    public void addEdge(Species specEnd, ResourceType rtEnd) {
        addSpecNode(specEnd);
        addRTNode(rtEnd);

        specEnd.addResourceType(rtEnd);
        rtEnd.addSpecies(specEnd);
    }

    public SpecSetFamily getDomSpecSets(int setSize, int targetNum, MinSpecSetFamily mssf) {
        mssf.sortBySensitivity();
        Collections.sort(rtNode);

        SpecSetFamily result = new SpecSetFamily();
        result.setNumSetsLimit(targetNum);

        int maxRTex = mssf.size();
        for (int i = 0; i < maxRTex; i++) {
            SpecSet tmpSet = mssf.get(i);
            if (tmpSet.size() <= setSize) {
                SpecSetFamily tmpFamily = tmpSet.extendToFamily(specNode, setSize, targetNum);
                result.addSpecSet(tmpFamily);
            }
        }
        return result;
    }

    /**
     *
     * @param setSize The number of species in the species set
     * @param targetNum The number of species sets to be computed
     * @return A collection of species sets
     */
    public SpecSetFamily getDomSpecSets(int setSize, int targetNum) {
        MinSpecSetFamily mssf = this.getMinDomSpecSets();
        return getDomSpecSets(setSize, targetNum, mssf);
    }

    /**
     *
     * @return The collection of 'irreducible' dominated sets
     */
    public MinSpecSetFamily getMinDomSpecSets() {
        MinSpecSetFamily result = new MinSpecSetFamily();
        ResourceType minRT = this.getFirstEssentialResourceType();

        if (minRT == null || minRT.noSpecies()) {
            System.out.println("There is something wrong with the "
                    + "minimal rticator, such as adjacent to no species. ");
        }

        for (Species spec : minRT.specList) {
            SpecTree minTree = this.getMinimalConstSpecTreeRootedAt(spec);
            ArrayList<SpecTreeNode> leafList = minTree.getLeaves();

            for (SpecTreeNode leaf : leafList) {
                ArrayList<Species> sp = leaf.getAncestors();
                SpecSet tmpSet = new SpecSet(sp);
                result.addSpecSet(tmpSet);
            }
        }
        return result;
    }

    public SpecTree getMinSpecTree() {
        ArrayList<ResourceType> essRT = this.getEssentialResourceTypes();
        Species root = essRT.get(0).getFirstSpecies();

        return this.getMinimalConstSpecTreeRootedAt(root);

    }

    private boolean isDominated(
            ArrayList<Species> specList, ArrayList<ResourceType> rtList) {
        return (getFirstNonDominatedRT(specList, rtList) == null) ? true : false;
    }

    /**
     *
     * @param rootSpec A species
     * @return A minimal constitute species tree with root specified by
     * rootSpec; null if rootSpec is not a species
     */
    public SpecTree getMinimalConstSpecTreeRootedAt(Species rootSpec) {
        if (!this.containsSpecies(rootSpec)) {
            return null;
        }

        ArrayList<ResourceType> essRT = this.getEssentialResourceTypes();

        SpecTreeNode rootNode = new SpecTreeNode(rootSpec);

        SpecTree returnTree = new SpecTree(rootNode);

        while (getFirstNonCompleteLeaf(returnTree, essRT) != null) {

            SpecTreeNode curNode = getFirstNonCompleteLeaf(returnTree, essRT);
            ResourceType curRT = getFirstNonDominatedRT(curNode.getAncestors(), essRT);

            ArrayList<Species> specList = curRT.specList;
            for (Species spec : specList) {
                SpecTreeNode tmpNode = new SpecTreeNode(spec);
                curNode.addChild(tmpNode);
            }
        }

        return returnTree;

    }

    private SpecTreeNode getFirstNonCompleteLeaf(
            SpecTree st, ArrayList<ResourceType> rtList) {

        ArrayList<SpecTreeNode> leafList = st.getLeaves();

        for (SpecTreeNode stn : leafList) {
            if (getFirstNonDominatedRT(stn.getAncestors(), rtList) != null) {
                return stn;
            }
        }

        return null;

    }

    /**
     *
     * @param specList A collection of species node
     * @param rtList A collection of ResourceType node
     * @return The first RT that is not adjacent to any of the species in
     * specList, null if no such RT exists
     */
    private ResourceType getFirstNonDominatedRT(
            ArrayList<Species> specList, ArrayList<ResourceType> rtList) {

        //  System.out.println("getFirstNonDOminatedRT");
        // System.out.println("The species set is "+specList);
        // System.out.println("The rticator set is "+rtList);
        for (ResourceType rt : rtList) {
            boolean dominated = false;
            for (Species spec : specList) {
                if (rt.adjacentTo(spec)) {
                    dominated = true;

                }
            }
            if (dominated == false) {
                // System.out.println("The return rticator is "+rt);
                return rt;
            }
        }
        return null;
    }

    private ResourceType getFirstEssentialResourceType() {
        ArrayList<ResourceType> rtList = this.getEssentialResourceTypes();
        if (rtList.isEmpty()) {
            return null;
        }

        return rtList.get(0);
    }

    /**
     *
     * @return Return a set of essential rticators, when an rticator is
     * essential if it is not covered by other rticators
     */
    public ArrayList<ResourceType> getEssentialResourceTypes() {

        //sorting the rticator list first
        Collections.sort(rtNode);

        ArrayList<ResourceType> essRT;
        essRT = new ArrayList<ResourceType>();

        int rtSize = numResourceTypes();

        for (int i = 0; i < rtSize; i++) {
            ResourceType curRT = rtNode.get(i);
            boolean essFlag = true; //false rtications no essential

            for (int j = i + 1; j < rtSize; j++) {
                if (curRT.isCoveredBy(rtNode.get(j))) {
                    essFlag = false;
                    break;
                }
            } //end for

            for (ResourceType tmpRT : essRT) {
                if (curRT.isCoveredBy(tmpRT)) {
                    essFlag = false;
                    break;
                }
            }

            if (essFlag == true) {
                essRT.add(curRT);
            }
        }

        // System.out.println(essRT);
        return essRT;
    }

    public ArrayList<Species> getOneMinimalConstituteSpecies() {
        ArrayList<Species> conSpecList = new ArrayList<Species>();
        ArrayList<ResourceType> essRTList = new ArrayList<ResourceType>();

        essRTList = getEssentialResourceTypes();

        while (essRTList.size() != 0) {
            ResourceType tmpRT = essRTList.get(0); //get the first essRTList
            Species tmpSpec = tmpRT.getFirstSpecies();
            conSpecList.add(tmpSpec);
            removeDomination(essRTList, tmpSpec);
        } //while

        // System.out.println(conSpecList);
        return conSpecList;
    }

    /**
     *
     * @param rtList a list of rticators
     * @param spec a species After calling the method, the rticators in the list
     * which is 'adjacent' to the species are removed
     */
    private void removeDomination(ArrayList<ResourceType> rtList, Species spec) {
        rtList.removeAll(spec.rtList);
    }

    //for test
    /*
    
    public void testBelow(){
        ArrayList<Species> redCandidate=new ArrayList<Species>();
        for(Species spec: specNode){
            for(Species other:specNode){
                if(spec!=other){
                    if(spec.isBelow(other)){
                        System.out.println(spec.getName()+"is below "+other.getName());
                        if(redCandidate.contains(spec)==false)
                            redCandidate.add(spec);
                    }
                }
            }
        }
        
      //  System.out.println("The number of redundance candidates are"+redCandidate.size());
    }
    * */
    //for test
    /*
    public void rticatorCoverTest(){
        
        ArrayList<ResourceType> redRT=new ArrayList<ResourceType>();
        
        for(ResourceType rt: rtNode){
            for(ResourceType other:rtNode){
                if( (rt!=other) && rt.isCoveredBy(other) ){
                    redRT.add(rt);
                    System.out.println(rt.getID()+" is covered by "+other.getID());
                    break;
                }   
                    
            }
        }
        
        System.out.println(redRT);
        System.out.println("The number of redundacne rticators is "+redRT.size());
    }
    * */
    public String toString() {
        StringBuilder outStr = new StringBuilder("Species-ResourceType graph: \n ");

        for (Species spec : specNode) {
            //outStr.append(spec);
            for (ResourceType rt : rtNode) {
                if (rt.adjacentTo(spec)) {
                    outStr.append("(").append(spec.getName());
                    outStr.append(",").append(rt.getID()).append(") ");
                }
            }
        }

        return outStr.toString();
    }
}
