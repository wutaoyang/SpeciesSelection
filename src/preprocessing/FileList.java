package preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * List of files
 * @author mre16utu
 */
public class FileList implements Iterable<File>{
    List<File> files;
    
    public FileList()
    {
        files = new ArrayList<>();
    }
    
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
        for (File file : files) {
            String fileName = file.getName();
            str += fileName + "\n";
        }
        return str.substring(0, str.lastIndexOf("\n"));
    }

    @Override
    public Iterator<File> iterator() {
        return files.iterator();
    }
    
}
