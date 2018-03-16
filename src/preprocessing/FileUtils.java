package preprocessing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * File related utilities
 * @author mre16utu
 */
public class FileUtils {
    
    public static boolean fileExists(File file)
    {
        if(file.exists() && !file.isDirectory())
        {
            return true;
        }
        return false;
    }
    
    // Reads a file line by line and adds each line to an ArrayList of Strings
    public static List<String> readFileToList(File file) throws FileNotFoundException {
        String line;
        List<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            list.add(line);
        }
        scanner.close();
        return list;
    }

    // returns filename string with file extension removed
    public static String removeExt(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}
