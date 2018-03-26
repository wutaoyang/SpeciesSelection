package speciesselection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;

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
    
    // return 5th column header - should be Areas or Precision
    public static String getFileType(String fileName) throws FileNotFoundException
    {
        File f = new File(fileName);
        Scanner scanner = new Scanner(f);
        String headerLine = scanner.nextLine();
        scanner = new Scanner(headerLine);
        String str = "";
        for(int i = 0; i < 5; i++)
        {
            str = scanner.next();
        }
        return str;
    }
    
    public static SpecRTGraph graphConstr(String fileName, String fileTypes) throws FileNotFoundException {
        try {
            System.out.println("The dataset is taken from file " + fileName);
            SpecRTGraph specRTGraph = new SpecRTGraph();
            File f = new File(fileName);
            Scanner input = new Scanner(f);
            String headerLine = input.nextLine();
            int columnCount = validateHeader(headerLine, fileTypes);
            if(!(columnCount > 0))
            {
                return null;
            }
            
            while (input.hasNext()) {
                String text = input.nextLine();
                if(!processLine(text, specRTGraph, columnCount, fileTypes))
                {
                    return null;
                }
            }
            input.close();
            return specRTGraph;
        } catch (FileNotFoundException e) {
            System.out.println("Error in SpeciesSelection.processFile()");
            return null;
        }
    }
    
    // count number of columns in the input data file and check the header names are valid
    private static int validateHeader(String headerLine, String fileTypes)
    {
        int count = 0;
        Scanner scanner = new Scanner(headerLine);
        while(scanner.hasNext())
        {
            count++;
            String str = scanner.next();
            if(count == 1)
            {
                if(!str.equals("Species"))
                {
                    throwInfoBox(count, "Species");
                    return -1;
                }
            }
            else if(count == 2)
            {
                if(!str.equals("Reliance"))
                {
                    throwInfoBox(count, "Reliance");
                    return -1;
                }
            }
            else if(count == 3)
            {
                if(!str.equals("Resources"))
                {
                    throwInfoBox(count, "Resources");
                    return -1;
                }
            }
            else if(count == 4)
            {
                if(!str.equals("Sensitivity"))
                {
                    throwInfoBox(count, "Sensitivity");
                    return -1;
                }
            }
            else if(count == 5 && (!fileTypes.equals(Constants.NONE)))
            {
                if(!(str.equals(Constants.AREAS) || str.equals(Constants.PRECISION)))
                {
                    String error = "Column " + count + " header is not '" + Constants.AREAS + "' or '" + Constants.PRECISION +"'";
                    infoBox(error);
                    System.err.println(error);
                    return -1;
                }
            }
            else if(str.charAt(0) != 'R')
            {
                String error = "Resource title at column " + count + " does not begin with 'R'";
                infoBox(error);
                System.err.println(error);
                return -1;
            }
        }
        return count;
    }
    
    private static void throwInfoBox(int count, String header)
    {
        String error = "Column " + count + " header is not '" + header + "'";
        infoBox(error);
        System.err.println(error);
    }
    
    public static void infoBox(String infoMessage)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + "ReadFile Error", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    
    //process the given String for a line of the data file
    private static boolean processLine(String text, SpecRTGraph sig, int columnCount, String fileType){
        Scanner data=new Scanner(text);
        //create a new species
        int spName = data.nextInt();
        Species inSpec=new Species(spName);
        
        double spReliance=data.nextDouble();
        int spResources=data.nextInt();
        double spSensitivity=data.nextDouble();
        inSpec.setReliance(spReliance);
        inSpec.setSensitivity(spSensitivity);
        
        int countColumns = 4;// 4 columns read so far
        
       //Datasets including "Areas"
        if(fileType.equals(Constants.AREAS)){
            double spAreas=data.nextDouble();
            inSpec.setAreas(spAreas);
            countColumns++;
        }
        
        //Datasets including "Precision"
        if(fileType.equals(Constants.PRECISION)){
            double spPrecision=data.nextDouble();
            inSpec.setPrecision(spPrecision);
            countColumns++;
        }
        
        //the indication information
        int index=0;
        
        //test variable
        
        while(data.hasNextInt()){
            index++;
            int indValue=data.nextInt();
            
            // if value is not 0 or 1 there is a problem
            if(indValue != 0 && indValue != 1)
            {
                String error = "Indicator value of " + indValue + " found in " + text + ". Must be 0 or 1.";
                System.err.println(error);
                infoBox(error);
                return false;
            }
            
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
        
        countColumns += index;
        // check number of columns in data row matches that in header row
        if(countColumns != columnCount)
        {
            String error = "Error after column " + countColumns + ". Value is not an integer or column count in\n" + text.replaceAll("\t", " ") + "\ndoes not match count of " + columnCount + " in header.";
            System.err.println(error);
            infoBox(error);
            return false;
        }
        return true;
      //   System.out.println(inSpec);
        
    } //process a line
    
    
}
