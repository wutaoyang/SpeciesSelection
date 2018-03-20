package speciesselection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author taoyang
 */
public class ReadFile {
    
//    public static SpecRTGraph graphConstr(String fileName, String fileTypes)
//       throws FileNotFoundException{
//        //construct the bipartite graph between species and indicators.
//        SpecRTGraph sig=new SpecRTGraph();
//        
//        File f =new File(fileName);
//        Scanner input=new Scanner(f);
//        String titleInformation=input.nextLine();
//        while(input.hasNext()){
//            String text=input.nextLine();
//          //  System.out.println(text);
//            processLine(text,sig,fileTypes);
//        }
//        
//        return sig;
//    }
    
    public static SpecRTGraph graphConstr(String fileName, String fileTypes) throws FileNotFoundException {
        try {
            System.out.println("The dataset is taken from file " + fileName);
            SpecRTGraph specRTGraph = new SpecRTGraph();
            File f = new File(fileName);
            Scanner input = new Scanner(f);
            String titleInformation = input.nextLine();
            while (input.hasNext()) {
                String text = input.nextLine();
                processLine(text, specRTGraph, fileTypes);
            }
            input.close();
            return specRTGraph;
        } catch (FileNotFoundException e) {
            System.out.println("Error in SpeciesSelection.processFile()");
            return null;
        }
    }
    
    //process the given String for a line of the data file
    private static void processLine(String text, SpecRTGraph sig,String fileType){
        Scanner data=new Scanner(text);
        //create a new species
        int spName = data.nextInt();
        Species inSpec=new Species(spName);
        
        
        int spReliance=data.nextInt();
        int spResources=data.nextInt();
        double spSensitivity=data.nextDouble();
        inSpec.setReliance(spReliance);
        inSpec.setSensitivity(spSensitivity);
        
       //Datasets including "Areas"
        if(fileType.equals("Areas")){
        double spAreas=data.nextDouble();
        inSpec.setAreas(spAreas);
        }
        
        //Datasets including "Precision"
        if(fileType.equals("Precision")){
        double spPrecision=data.nextDouble();
        inSpec.setPrecision(spPrecision);
        }
        
        //the indication inforamtion
        int index=0;
        
        //test variable
        
        while(data.hasNextInt()){
            index++;
            int indValue=data.nextInt();
            if(indValue==1){
                //first to check whether an indicator with the given id exists
              //System.out.println("The index is "+index+".");
                ResourceType tmpInd=sig.getResourceTypeByID(index);
                      
                                
                if(tmpInd!=null){                   
                    sig.addEdge(inSpec,tmpInd);
                }
                
                else{
                     //System.out.println("no such indicator "+index);
                    ResourceType newInd=new ResourceType(index);                 
                    sig.addEdge(inSpec,newInd);
                }                
            }
        }         
        
      //   System.out.println(inSpec);
        
    } //process a line
    
    
}
