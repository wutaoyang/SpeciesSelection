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

    public FileList() {
        files = new TreeSet<>(
                new Comparator<File>() {
            // compare integers at the end of the filename
            // assumes filenames are identical apart from the number at the end of the filename string
            // e.g. filename0.txt, filename1.txt, filename10.txt, filename2.txt
            // simple comparison of strings returns 10 as before 2 but we want numeric order
            @Override
            public int compare(File file1, File file2) {
                String name1 = file1.getName().substring(0, file1.getName().lastIndexOf("."));
                String name2 = file2.getName().substring(0, file2.getName().lastIndexOf("."));
                Integer int1 = getLastInt(name1);
                Integer int2 = getLastInt(name2);
                if (int1 >= 0 && int2 >= 0) {
                    return int1.compareTo(int2);
                }
                return name1.compareTo(name2);
            }
        });
    }

    // returns integer value at the end of a string
    public static int getLastInt(String line) {
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

    public void add(File file) {
        files.add(file);
    }

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

    // allow iteration over the FileList object, specifically the list of files
    @Override
    public Iterator<File> iterator() {
        return files.iterator();
    }

}
