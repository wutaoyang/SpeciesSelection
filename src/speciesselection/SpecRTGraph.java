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
        rtNode   = new ArrayList<>();
    }

    //Find the mean sensitivity across the species
    public double getMeanSensitivity() {
        int n = this.numSpecies();
        double result = 0.0;

        if (n == 0) {
            System.err.println("No Species found when getting MeanSensitivity");
            return result;
        }

        for (Species sp : specNode) {
            result += sp.getSensitivity();
        }
//        System.out.println("Mean: " + (result / n));
        return (result / n);
    }

    //Return the standard deviation of the sensitivities
    public double getStandardDeviation() {
        int n = this.numSpecies();
        if (n <= 1) {
            System.err.println("1 or fewer Species found when getting SD");
            return 0.0;
        }

        double mean = this.getMeanSensitivity();
        double var = 0.0;
        for (Species sp : specNode) {
            var += Math.pow(sp.getSensitivity() - mean, 2);
            // System.out.println(sp.getName()+":"+var);
        }

        var = var / (n - 1);  //Using the adjusted one
//        System.out.println("SD: " + Math.sqrt(var));
        return (Math.sqrt(var));
    }

    //this is to get a deep copy of the graph
    //all species and RTtypes are deep copied
    //they are related to the origin ones only by 
    //the IDs and speciesName
    public SpecRTGraph getADeepCopy() {
        SpecRTGraph sig = new SpecRTGraph();
        for (Species sp : specNode) {
            Species inSpec = new Species(sp.getName());

            inSpec.setReliance(sp.getReliance());
            inSpec.setSensitivity(sp.sensitivity); //set the sensitivity score
            inSpec.setAreas(sp.getAreas()); //set the areas
            inSpec.setPrecision(sp.getPrecision());
            inSpec.setSensType(sp.getSensType());

            ArrayList<Integer> rtIDs = sp.getRTIDs();
            for (Integer e : rtIDs) {
                ResourceType tmpInd = sig.getResourceTypeByID(e);
                if (tmpInd != null) {
                    sig.addEdge(inSpec, tmpInd);
                } else {
                    ResourceType newInd = new ResourceType(e);
                    sig.addEdge(inSpec, newInd);
                }
            } //end Ids   
        } //end species
        return sig;
    }

    //  update or set sensitivities using the default method
    // public void setSensitivities(){
    //   for(Species sp: specNode){
    //    sp.setSensitivity();
    // }
    //}
    //set sensType 
    public void setSensType(boolean sensType) {
        for (Species sp : specNode) {
            sp.setSensType(sensType);
        }
    }

    //Normalized sensitivities using the community average
    public void normalizeSensitivities() {
        double mean = this.getMeanSensitivity();
        double sd = this.getStandardDeviation();
        System.out.println("Mean: " + mean + ", SD: " + sd);

        if (sd == 0) {
            System.err.println("The normalized process for sensitivity has failed ");
        }

        for (Species sp : specNode) {
            double tmp = sp.getSensitivity();
            tmp = (tmp - mean) / sd;

            System.out.println("Species " + sp.getName() + ", Sens " + tmp);
            sp.setSensitivity(tmp);
            sp.setSensType(true);//set the sensType to be imposed
        }
    }

    public void addSpecNode(Species inSpecies) {
        if (specNode.contains(inSpecies) == false) {
            specNode.add(inSpecies);
        }
    }

    public int numSpecies() {
        return specNode.size();
    }

    public List<Integer> speciesIDs() {
        List<Integer> iDs = new ArrayList<>();
        for (Species species : specNode) {
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

    //return true if there exists isolatedSpecies 
    public boolean isolatedSpecies() {
        for (Species sp : specNode) {
            if (sp.numResourceTypes() == 0) {
                // System.out.println("Warning: There is an isolated resource type.");
                return true;
            }
        }
        return false;
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

    public void remEdge(Species outSP, ResourceType outRT) {

        outSP.remRT(outRT);
        outRT.remSpecies(outSP);

    }

    public void remRTNode(ResourceType outRT) {
        //get the outRT from each species
        for (Species sp : specNode) {
            sp.remRT(outRT);
        }
        //remove outRF from the rtNode list      
        rtNode.remove(outRT);
    }

    //Remove all RTs for which the num of 
    //adjacent species is strictly less than the input threshold
    //The output is the set of RTs removed 
    public ArrayList<ResourceType> remRTByNum(int threshold) {

        //construct the set of RT nodes to be removed
        ArrayList<ResourceType> outRTList = new ArrayList<ResourceType>();

        for (ResourceType rt : rtNode) {
            // System.out.println("Consider RT with ID "+rt.getID());
            if (rt.numSpecies() < threshold) {
                //  System.out.println("removing RT with ID "+rt.getID());
                outRTList.add(rt);
            }
        }

        for (ResourceType rt : outRTList) {
            //  System.out.println("We remove traits "+rt.getID());
            this.remRTNode(rt);
        }

        return outRTList;

    }

    //Remove all RTs for which the average sensitivity is  
    //a certain standard devitation (specified by threshold) strictlylarger than 
    //the average
    //The output is the set of RTs removed 
    public ArrayList<ResourceType> remRTBySD(double threshold) {

        //construct the set of RT nodes to be removed
        ArrayList<ResourceType> outRTList = new ArrayList<ResourceType>();

        double mean = this.getMeanSensitivity();
        double sd = this.getStandardDeviation();
        // System.out.println("The target is "+(mean+threshold*sd));

        for (ResourceType rt : rtNode) {
            double rtMean = rt.meanSensitivit();
            // System.out.println("Consider RT with ID "+rt.getID()+","+rtMean);

            if (rtMean > mean + threshold * sd) {
                //   System.out.println("Removing RT with ID "+rt.getID());
                outRTList.add(rt);
            }
        }

        for (ResourceType rt : outRTList) {
            this.remRTNode(rt);
        }

        return outRTList;

    }

    //Remove all RTs for which 
    //(1) the average sensitivity is a certain standard devitation (specified by numSD) strictlylarger than 
    //the average, and 
    //(2)  the num of adjacent species is strictly less than numSpecies
    //The output is the set of RTs removed 
    public ArrayList<ResourceType> remRTByBoth(int numSpecies, double numSD) {

        //construct the set of RT nodes to be removed
        ArrayList<ResourceType> outRTList = new ArrayList<>();

        double mean = this.getMeanSensitivity();
        double sd = this.getStandardDeviation();

        for (ResourceType rt : rtNode) {
            //System.out.println("Consider RT with ID "+rt.getID());
            double rtMean = rt.meanSensitivit();
            if (rtMean > mean + numSD * sd && rt.numSpecies() < numSpecies) {
                //   System.out.println("Removing RT with ID "+rt.getID());
                outRTList.add(rt);
            }
        }

        for (ResourceType rt : outRTList) {
            this.remRTNode(rt);
        }

        return outRTList;

    }

    public void remSpecies(Species outSP) {

        //remove the SP from each root type
        for (ResourceType rt : rtNode) {
            rt.remSpecies(outSP);
        }

        //remove outSP from the species list      
        specNode.remove(outSP);

    }

    //Remove all Species whose areas is below a threshold
    //The output is the set of Species removed 
    public ArrayList<Species> remSpecByAreas(double threshold) {

        //construct the set of RT nodes to be removed
        ArrayList<Species> outSpecList = new ArrayList<>();

        for (Species sp : specNode) {
            // System.out.println("Consider RT with ID "+rt.getID());
            if (sp.getAreas() < threshold) {
                // System.out.println("removing Species "+sp);
                outSpecList.add(sp);
            }
        }

        for (Species sp : outSpecList) {
            // System.out.println("We remove Species "+sp.getName());
            this.remSpecies(sp);
        }

        return outSpecList;

    } //end function

    //Remove all Species whose precision is below a threshold
    //The output is the set of Species removed 
    public ArrayList<Species> remSpecByPrecision(double threshold) {

        //construct the set of RT nodes to be removed
        ArrayList<Species> outSpecList = new ArrayList<>();

        for (Species sp : specNode) {
            // System.out.println("Consider RT with ID "+rt.getID());
            if (sp.getPrecision() > threshold) {
                //  System.out.println("removing RT with ID "+rt.getID());
                outSpecList.add(sp);
            }
        }

        for (Species sp : outSpecList) {
            //  System.out.println("We remove Species "+sp.getName());
            this.remSpecies(sp);
        }

        return outSpecList;

    } //end function

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
     *
     * @param setSize The number of species in the species set
     * @param targetNum The number of species sets to be computed
     * @return A collection of species sets
     * @throws java.lang.InterruptedException
     * @throws speciesselection.SpecSelException
     */
    public SpecSetFamily getDomSpecSets(int setSize, int targetNum) throws InterruptedException, SpecSelException {
        MinSpecSetFamily mssf = this.getMinDomSpecSets();
        return getDomSpecSets(setSize, targetNum, mssf);

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

    private boolean isDominated(
            ArrayList<Species> specList, ArrayList<ResourceType> rtList) {
        return (getFirstNonDominatedRT(specList, rtList) == null);
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
//        System.out.println("essRT: " + essRT);
        return essRT;
    }

    public ArrayList<Species> getOneMinimalConstituteSpecies() {
        ArrayList<Species> conSpecList = new ArrayList<>();
        ArrayList<ResourceType> essRTList = getEssentialResourceTypes();

        while (!essRTList.isEmpty()) {
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

    //unit test
    public static void main(String[] args) {

        System.out.println("This is a unit test");

        //initialize three species
        Species AA = new Species(1);
        Species BB = new Species(2);
        Species CC = new Species(3);

        AA.setReliance(3);
        BB.setReliance(1);
        CC.setReliance(2);

        //initialize six RTs
        ResourceType R1 = new ResourceType(1);
        ResourceType R2 = new ResourceType(2);
        ResourceType R3 = new ResourceType(3);
        ResourceType R4 = new ResourceType(4);
        ResourceType R5 = new ResourceType(5);
        ResourceType R6 = new ResourceType(6);

        //Note: resolving conflict IDs?
        //Build the first graph
        SpecRTGraph fst = new SpecRTGraph();

        fst.addEdge(AA, R1);
        fst.addEdge(AA, R2);
        fst.addEdge(AA, R3);
        fst.addEdge(AA, R4);
        fst.addEdge(AA, R5);
        fst.addEdge(AA, R6);
        fst.addEdge(BB, R1);
        fst.addEdge(BB, R5);
        fst.addEdge(CC, R3);
        fst.addEdge(CC, R4);
        fst.addEdge(CC, R6);

        System.out.println(fst);
        //fst.setSensitivities();

        System.out.println(AA);
        System.out.println(BB);
        System.out.println(CC);

        System.out.println(fst.getMeanSensitivity());
        System.out.print(fst.getStandardDeviation());

        /*
        //Deep copy a second graph
        System.out.println("Second");
        SpecRTGraph snd=fst.getADeepCopy();
        System.out.println(snd);

        snd.remRTByNum(2);
        //System.out.println(fst);
        System.out.println(snd);
        * */
 /*
         //Deep copy a thrid graph
        System.out.println("Thrid");
        SpecRTGraph thd=fst.getADeepCopy();
        thd.setSensitivities();
        System.out.println(thd);

        thd.remRTBySD(0.4);
        //System.out.println(fst);
        System.out.println(thd);
        * */
        //Deep copy a fourth graph
        System.out.println("Four");
        SpecRTGraph four = fst.getADeepCopy();
        System.out.println(four);

        // four.setSensitivities();
        four.remRTByBoth(2, 1);
        //System.out.println(fst);
        System.out.println(four);

        /*
        //modified the first graph
        fst.addEdge(AA, R4);
        System.out.println(fst);
        System.out.println(snd);

        //modified the second graph
        snd.remEdge(AA, R1);
        snd.remEdge(CC, R3);
        System.out.println(fst);
        System.out.println(snd);

        //modified the second graph
        snd.remRTNode(snd.getResourceTypeByID(1));
        System.out.println(fst);
        System.out.println(snd);
        * */
    } //end unit test
}
