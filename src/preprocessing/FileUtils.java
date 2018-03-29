package preprocessing;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * File related utilities
 *
 * @author mre16utu
 */
public class FileUtils {

    /**
     * returns whether the file exists and is not a directory
     *
     * @param file
     * @return
     */
    public static boolean fileExists(File file) {
        if (file.exists() && !file.isDirectory()) {
            return true;
        }
        return false;
    }

    /**
     * Reads a file line by line and adds each line to an ArrayList of Strings
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
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

    /**
     * returns filename string with file extension removed
     *
     * @param fileName
     * @return
     */
    public static String removeExt(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * Opens a file in the default program of the host PC
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String openFile(String fileName) throws IOException {
        File f = new File(fileName);
        if (f.exists() && !f.isDirectory()) {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(f);
                return "";
            } else {
                String str = "Cannot open file. This PC does not support Desktop.";
                JOptionPane.showMessageDialog(null, str, "InfoBox: " + "Open File Error", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(str);
                return str;
            }
        } else {
            String str = "File " + fileName + "does not exist";
            System.out.println(str);
            return str;
        }
    }
}
