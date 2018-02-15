package preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import speciesselection.Species;

/**
 *
 * @author mre16utu
 */
public class ProblemSpecies //implements Iterable<Species> 
{
    ArrayList<Species> probSpecies;
    File dataSet;
    int initialSpeciesPct, expMarginPct;

    public ProblemSpecies(File file, int initialSpeciesPct, int expMarginPct) {
        this.dataSet = file;
        this.initialSpeciesPct = initialSpeciesPct;
        this.expMarginPct = expMarginPct;
        this.probSpecies = new ArrayList<>();
        findProblemSpecies();
    }

    private void findProblemSpecies() {
        
        
        
    }

//    @Override
//    public Iterator<Species> iterator() {
//        return probSpecies.iterator();
//    }

//    public int size() {
//        return probSpecies.size();
//    }

    @Override
    public String toString() {

        if (probSpecies.isEmpty()) {
            return "No Problem Species";
        }
        String str = "";
        // Loop over species
        for (int i = 0; i < probSpecies.size(); i++) {
            str += probSpecies.get(i).getName();
            if (i < probSpecies.size() - 1)
            {
                str += ", ";
            }
        }
        return str;
    }

}
