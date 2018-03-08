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
    //Here each species is associated with a list of resource types (RT), and
    //each RT is associated with a list of species.
    protected ArrayList<Species> specNode;
    protected ArrayList<ResourceType> rtNode;

    SpecRTGraph() {
        specNode = new ArrayList<>();
        rtNode = new ArrayList<>();
    }

    public void addSpecNode(Species inSpecies) {
        if (specNode.contains(inSpecies) == false) {
            specNode.add(inSpecies);
        }
    }

    public int numSpecies() {
        return specNode.size();
    }
    
    public List<Integer> speciesIDs()
    {
        List<Integer> iDs = new ArrayList<>();
        for(Species species : specNode)
        {
            iDs.add(species.getName());
        }
        return iDs;
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

    public SpecSetFamily getDomSpecSets(int setSize, int targetNum, MinSpecSetFamily mssf) throws SpecSelException {
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
     * MOST PROCESSING HAPPENS HERE
     *
     * @return The collection of 'irreducible' dominated sets
     * @throws java.lang.InterruptedException
     */
    public MinSpecSetFamily getMinDomSpecSetsParallel() throws InterruptedException {
        ResourceType minRT = this.getFirstEssentialResourceType();
        if (minRT == null || minRT.noSpecies()) {
            System.out.println("There is something wrong with the "
                    + "minimal rticator, such as adjacent to no species. ");
        }

        MinSpecSetFamily result = minRT.specList.parallelStream()
                .flatMap(spec -> {
                    try {
                        return getMinimalConstSpecTreeRootedAt(spec).getLeaves().stream();
                    } catch (InterruptedException e) {
                        System.out.println("Returning null from SpecRTGraph.getMinDomSpecSets()");
                        return null;// TODO: DOES NOT RETURN NULL FOR ENTIRE METHOD
                    }
                })
                .map(leaf -> new SpecSet(leaf.getAncestors()))
                .collect(MinSpecSetFamily::new, MinSpecSetFamily::addSpecSet, MinSpecSetFamily::addMSSF);
        return result;
    }

    /**
     * Method superceded by parallel stream approach
     *
     * @return The collection of 'irreducible' dominated sets
     * @throws java.lang.InterruptedException
     */
    public MinSpecSetFamily getMinDomSpecSets() throws InterruptedException {
        MinSpecSetFamily result = new MinSpecSetFamily();
        ResourceType minRT = this.getFirstEssentialResourceType();
        if (minRT == null || minRT.noSpecies()) {
            System.out.println("There is something wrong with the "
                    + "minimal rticator, such as adjacent to no species. ");
        }
        for (Species spec : minRT.specList) {
            ArrayList<SpecTreeNode> leafList = this.getMinimalConstSpecTreeRootedAt(spec).getLeaves();
            for (SpecTreeNode leaf : leafList) {
                result.addSpecSet(new SpecSet(leaf.getAncestors()));
            }
        }
        return result;
    }

    public SpecTree getMinSpecTree() throws InterruptedException {
        ArrayList<ResourceType> essRT = this.getEssentialResourceTypes();
        Species root = essRT.get(0).getFirstSpecies();
        return this.getMinimalConstSpecTreeRootedAt(root);
    }

    /**
     *
     * @param rootSpec A species
     * @return A minimal constitute species tree with root specified by
     * rootSpec; null if rootSpec is not a species
     * @throws java.lang.InterruptedException
     */
    public SpecTree getMinimalConstSpecTreeRootedAt(Species rootSpec) throws InterruptedException {
        if (!this.containsSpecies(rootSpec)) {
            return null;
        }
        ArrayList<ResourceType> essRT = this.getEssentialResourceTypes();
        SpecTreeNode rootNode = new SpecTreeNode(rootSpec);
        SpecTree returnTree = new SpecTree(rootNode);

        SpecTreeNode curNode = getFirstNonCompleteLeaf(returnTree, essRT);// SPW - was being called twice - as condition of while loop and inside while loop
        while (curNode != null) {
            ResourceType curRT = getFirstNonDominatedRT(curNode.getAncestors(), essRT);
            ArrayList<Species> specList = curRT.specList;
            for (Species spec : specList) {
                SpecTreeNode tmpNode = new SpecTreeNode(spec);
                curNode.addChild(tmpNode);
            }
            curNode = getFirstNonCompleteLeaf(returnTree, essRT);
        }
        return returnTree;
    }

    private SpecTreeNode getFirstNonCompleteLeaf(SpecTree st, ArrayList<ResourceType> rtList) throws InterruptedException {
        ArrayList<SpecTreeNode> leafList = st.getLeaves();
        for (SpecTreeNode stn : leafList) {
            if (getFirstNonDominatedRT(stn.getAncestors(), rtList) != null) {
                return stn;
            }
        }
        return null;
    }

    /**
     * @param specList A collection of species node
     * @param rtList A collection of ResourceType node
     * @return The first RT that is not adjacent to any of the species in
     * specList, null if no such RT exists
     */
    private ResourceType getFirstNonDominatedRT(ArrayList<Species> specList, ArrayList<ResourceType> rtList) {
        for (ResourceType rt : rtList) {
            boolean dominated = false;
            for (Species spec : specList) {
                if (rt.adjacentTo(spec)) {
                    dominated = true;
                }
            }
            if (dominated == false) {
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
     * @return Return a set of essential rticators, when an rticator is
     * essential if it is not covered by other rticators
     */
    public ArrayList<ResourceType> getEssentialResourceTypes() {
        //sorting the rticator list first
        Collections.sort(rtNode);// sorts resources in order of fewest species requiring resource
        ArrayList<ResourceType> essRT = new ArrayList<>();
        int numRTs = numResourceTypes();

        for (int i = 0; i < numRTs; i++) {
            ResourceType currentRT = rtNode.get(i);
            boolean essFlag = true; //false rtications no essential

            for (int j = i + 1; j < numRTs; j++) {
                if (currentRT.isCoveredBy(rtNode.get(j))) {
                    essFlag = false;
                    break;
                }
            }
            for (ResourceType tmpRT : essRT) {
                if (currentRT.isCoveredBy(tmpRT)) {
                    essFlag = false;
                    break;
                }
            }
            if (essFlag) {
                essRT.add(currentRT);
            }
        }
        System.out.println("essRT: " + essRT);
        return essRT;
    }

    public ArrayList<Species> getOneMinimalConstituteSpecies() {
        ArrayList<Species> conSpecList = new ArrayList<>();
        ArrayList<ResourceType> essRTList = getEssentialResourceTypes();

        while (essRTList.size() != 0) {
            ResourceType tmpRT = essRTList.get(0); //get the first essRTList
            Species tmpSpec = tmpRT.getFirstSpecies();
            conSpecList.add(tmpSpec);
            removeDomination(essRTList, tmpSpec);
        } //while
        return conSpecList;
    }

    /**
     * @param rtList a list of rticators
     * @param spec a species After calling the method, the rticators in the list
     * which is 'adjacent' to the species are removed
     */
    private void removeDomination(ArrayList<ResourceType> rtList, Species spec) {
        rtList.removeAll(spec.rtList);
    }

    @Override
    public String toString() {
        StringBuilder outStr = new StringBuilder("Species-ResourceType graph:\n ");
        for (Species spec : specNode) {
            for (ResourceType rt : rtNode) {
                if (rt.adjacentTo(spec)) {
                    outStr.append("(")
                            .append(spec.getName())
                            .append(",")
                            .append(rt.getID())
                            .append(") ");
                }
            }
        }
        return outStr.toString();
    }
}
