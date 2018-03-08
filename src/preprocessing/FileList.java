package preprocessing;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * List of files
 * @author mre16utu
 */
public class FileList implements Iterable<File>{
    Set<File> files;
    
    public FileList()
    {
        files = new TreeSet<>();
    }
    
    /**
     * Physically deletes all files from the file system and clears the list of files
     */
    public void delete()
    {
        for(File file : files)
        {
            file.delete();
        }
        files.removeAll(files);
    }
    
    public void add(File file)
    {
        files.add(file);
    }
    
    public int size()
    {
        return files.size();
    }
    
    @Override
    public String toString() {
        String str = "";
        if(files.isEmpty())
        {
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
