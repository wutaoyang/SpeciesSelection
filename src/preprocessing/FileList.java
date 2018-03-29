package preprocessing;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * List of files
 *
 * @author mre16utu
 */
public class FileList implements Iterable<File> {

    Set<File> files;

    /**
     * FileList constructor
     * Sets up a comparator to compare Files by file name where integers at the 
     * end of the filename allow sorting by the int value rather than string
     */
    public FileList() {
        files = new TreeSet<>(
                new Comparator<File>() {
            // comparator to sort files by filename 
            // if filename ends with an integer, files are sorted by the integer value
            // otherwise they are sorted by the string
            // e.g. filename0.txt, filename1.txt, filename10.txt, filename2.txt
            // simple comparison of strings returns 10 as before 2 but we want numeric order
            @Override
            public int compare(File file1, File file2) {
                String name1 = file1.getName().substring(0, file1.getName().lastIndexOf("."));
                String name2 = file2.getName().substring(0, file2.getName().lastIndexOf("."));
                Integer int1 = getLastInt(name1);
                Integer int2 = getLastInt(name2);
                if (int1 >= 0 && int2 >= 0) {
                    // check that filenames match apart from the integer at the end
                    String name1a = name1.substring(0, name1.lastIndexOf(int1.toString()));
                    String name2a = name2.substring(0, name2.lastIndexOf(int2.toString()));
                    if(name1a.equals(name2a))
                    {
                        // return comparison by last integer
                        return int1.compareTo(int2);
                    }
                }
                // return standard string comparison
                return name1.compareTo(name2);
            }
        });
    }

    /**
     * returns integer value at the end of a string
     * Used in the comparator created to order Files
     * @param line
     * @return 
     */
    private static int getLastInt(String line) {
        int offset = line.length();
        for (int i = line.length() - 1; i >= 0; i--) {
            char c = line.charAt(i);
            if (Character.isDigit(c)) {
                offset--;
            } else {
                if (offset == line.length()) {
                    // No int at the end
                    return -1;
                }
                return Integer.parseInt(line.substring(offset));
            }
        }
        return Integer.parseInt(line.substring(offset));
    }

    /**
     * Physically deletes all files from the file system and clears the list of
     * files
     */
    public void delete() {
        for (File file : files) {
            file.delete();
        }
        files.removeAll(files);
    }

    /**
     * Adds a file to the filelist
     * @param file 
     */
    public void add(File file) {
        files.add(file);
    }

    /**
     * returns number of files in the filelist
     * @return 
     */
    public int size() {
        return files.size();
    }

    @Override
    public String toString() {
        String str = "";
        if (files.isEmpty()) {
            return str;
        }
        for (File file : files) {
            String fileName = file.getName();
            str += fileName + "\n";
        }
        return str.substring(0, str.lastIndexOf("\n"));
    }

    /**
     * allow iteration over the FileList object, specifically the list of files
     * @return 
     */
    @Override
    public Iterator<File> iterator() {
        return files.iterator();
    }
}
