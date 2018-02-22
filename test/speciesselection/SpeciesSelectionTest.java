package speciesselection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mre16utu
 */
public class SpeciesSelectionTest {

    public SpeciesSelectionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of specSel method, of class SpeciesSelection.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testSpecSel() throws Exception {
        System.out.println("Testing SpeciesSelection.specSel()");
        //first test
        String results = getResults("Forest_D_ALL.txt");
        assertTrue(results.contains(forestDAllResult));
        // second test
        results = getResults("Forest2.txt");
        assertTrue(results.contains(forest2Result));
    }

    private static String getResults(String fileName) throws FileNotFoundException, InterruptedException {
        String[] args = {fileName};
        SpeciesSelection ss = new SpeciesSelection();
        ss.specSel(args, false);
        // Read in results file
        String outFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_result.txt";
        File f = new File(outFileName);
        Scanner input = new Scanner(f);
        String results = "";
        while (input.hasNext()) {
            results += input.nextLine() + "\n";
        }
        return results;
    }

    private static final String forest2Result
            = "The dataset:Forest2.txt\n"
            + "The dataset contains 46 species and 37 resource types\n"
            + "For 2  species\n"
            + "[]\n"
            + "For 3  species\n"
            + "[\n"
            + "54:41.33:[8, 39, 42]]\n"
            + "For 4  species\n"
            + "[\n"
            + "54:25.25:[9, 11, 32, 39]\n"
            + "54:25.50:[9, 25, 32, 39]\n"
            + "54:25.50:[9, 11, 34, 39]\n"
            + "54:25.75:[9, 14, 34, 39]\n"
            + "54:25.75:[9, 25, 34, 39]\n"
            + "54:26.00:[2, 8, 23, 39]\n"
            + "54:26.00:[8, 11, 32, 39]\n"
            + "54:26.00:[8, 11, 39, 46]\n"
            + "54:26.00:[9, 13, 32, 39]\n"
            + "54:26.00:[9, 26, 32, 39]]\n"
            + "For 5  species\n"
            + "[\n"
            + "54:22.40:[9, 11, 23, 32, 39]\n"
            + "54:22.60:[9, 14, 23, 32, 39]\n"
            + "54:22.60:[9, 23, 25, 32, 39]\n"
            + "54:22.60:[9, 11, 23, 34, 39]\n"
            + "54:22.80:[9, 11, 17, 32, 39]\n"
            + "54:22.80:[9, 11, 19, 32, 39]\n"
            + "54:22.80:[9, 11, 21, 32, 39]\n"
            + "54:22.80:[9, 11, 22, 32, 39]\n"
            + "54:22.80:[9, 11, 29, 32, 39]\n"
            + "54:22.80:[9, 14, 23, 34, 39]]\n"
            + "For 6  species\n"
            + "[\n"
            + "54:20.83:[9, 11, 17, 23, 32, 39]\n"
            + "54:20.83:[9, 11, 19, 23, 32, 39]\n"
            + "54:20.83:[9, 11, 21, 23, 32, 39]\n"
            + "54:20.83:[9, 11, 22, 23, 32, 39]\n"
            + "54:20.83:[9, 11, 23, 29, 32, 39]\n"
            + "54:21.00:[9, 11, 14, 23, 32, 39]\n"
            + "54:21.00:[9, 14, 17, 23, 32, 39]\n"
            + "54:21.00:[9, 14, 19, 23, 32, 39]\n"
            + "54:21.00:[9, 14, 21, 23, 32, 39]\n"
            + "54:21.00:[9, 14, 22, 23, 32, 39]]\n"
            + "For 7  species\n"
            + "[\n"
            + "54:19.71:[9, 11, 17, 19, 23, 32, 39]\n"
            + "54:19.71:[9, 11, 17, 21, 23, 32, 39]\n"
            + "54:19.71:[9, 11, 19, 21, 23, 32, 39]\n"
            + "54:19.71:[9, 11, 17, 22, 23, 32, 39]\n"
            + "54:19.71:[9, 11, 19, 22, 23, 32, 39]\n"
            + "54:19.71:[9, 11, 21, 22, 23, 32, 39]\n"
            + "54:19.71:[9, 11, 17, 23, 29, 32, 39]\n"
            + "54:19.71:[9, 11, 19, 23, 29, 32, 39]\n"
            + "54:19.71:[9, 11, 21, 23, 29, 32, 39]\n"
            + "54:19.71:[9, 11, 22, 23, 29, 32, 39]]\n"
            + "For 8  species\n"
            + "[\n"
            + "54:18.88:[9, 11, 17, 19, 21, 23, 32, 39]\n"
            + "54:18.88:[9, 11, 17, 19, 22, 23, 32, 39]\n"
            + "54:18.88:[9, 11, 17, 21, 22, 23, 32, 39]\n"
            + "54:18.88:[9, 11, 19, 21, 22, 23, 32, 39]\n"
            + "54:18.88:[9, 11, 17, 19, 23, 29, 32, 39]\n"
            + "54:18.88:[9, 11, 17, 21, 23, 29, 32, 39]\n"
            + "54:18.88:[9, 11, 19, 21, 23, 29, 32, 39]\n"
            + "54:18.88:[9, 11, 17, 22, 23, 29, 32, 39]\n"
            + "54:18.88:[9, 11, 19, 22, 23, 29, 32, 39]\n"
            + "54:18.88:[9, 11, 21, 22, 23, 29, 32, 39]]\n"
            + "For 9  species\n"
            + "[\n"
            + "54:18.22:[9, 11, 17, 19, 21, 22, 23, 32, 39]\n"
            + "54:18.22:[9, 11, 17, 19, 21, 23, 29, 32, 39]\n"
            + "54:18.22:[9, 11, 17, 19, 22, 23, 29, 32, 39]\n"
            + "54:18.22:[9, 11, 17, 21, 22, 23, 29, 32, 39]\n"
            + "54:18.22:[9, 11, 19, 21, 22, 23, 29, 32, 39]\n"
            + "54:18.33:[9, 11, 14, 17, 19, 21, 23, 32, 39]\n"
            + "54:18.33:[9, 11, 14, 17, 19, 22, 23, 32, 39]\n"
            + "54:18.33:[9, 11, 14, 17, 21, 22, 23, 32, 39]\n"
            + "54:18.33:[9, 11, 14, 19, 21, 22, 23, 32, 39]\n"
            + "54:18.33:[9, 14, 17, 19, 21, 22, 23, 32, 39]]\n"
            + "For 10  species\n"
            + "[\n"
            + "54:17.70:[9, 11, 17, 19, 21, 22, 23, 29, 32, 39]\n"
            + "54:17.80:[9, 11, 14, 17, 19, 21, 22, 23, 32, 39]\n"
            + "54:17.80:[9, 11, 14, 17, 19, 21, 23, 29, 32, 39]\n"
            + "54:17.80:[9, 11, 14, 17, 19, 22, 23, 29, 32, 39]\n"
            + "54:17.80:[9, 11, 14, 17, 21, 22, 23, 29, 32, 39]\n"
            + "54:17.80:[9, 11, 14, 19, 21, 22, 23, 29, 32, 39]\n"
            + "54:17.80:[9, 14, 17, 19, 21, 22, 23, 29, 32, 39]\n"
            + "54:17.80:[9, 11, 17, 19, 21, 22, 23, 24, 32, 39]\n"
            + "54:17.80:[9, 11, 17, 19, 21, 23, 24, 29, 32, 39]\n"
            + "54:17.80:[9, 11, 17, 19, 22, 23, 24, 29, 32, 39]]\n"
            + "For 11  species\n"
            + "[\n"
            + "54:17.36:[9, 11, 14, 17, 19, 21, 22, 23, 29, 32, 39]\n"
            + "54:17.36:[9, 11, 17, 19, 21, 22, 23, 24, 29, 32, 39]\n"
            + "54:17.36:[9, 11, 17, 19, 21, 22, 23, 25, 29, 32, 39]\n"
            + "54:17.45:[9, 11, 14, 17, 19, 21, 22, 23, 24, 32, 39]\n"
            + "54:17.45:[9, 11, 14, 17, 19, 21, 23, 24, 29, 32, 39]\n"
            + "54:17.45:[9, 11, 14, 17, 19, 22, 23, 24, 29, 32, 39]\n"
            + "54:17.45:[9, 11, 14, 17, 21, 22, 23, 24, 29, 32, 39]\n"
            + "54:17.45:[9, 11, 14, 19, 21, 22, 23, 24, 29, 32, 39]\n"
            + "54:17.45:[9, 14, 17, 19, 21, 22, 23, 24, 29, 32, 39]\n"
            + "54:17.45:[9, 11, 14, 17, 19, 21, 22, 23, 25, 32, 39]]\n"
            + "For 12  species\n"
            + "[\n"
            + "54:17.08:[9, 11, 14, 17, 19, 21, 22, 23, 24, 29, 32, 39]\n"
            + "54:17.08:[9, 11, 14, 17, 19, 21, 22, 23, 25, 29, 32, 39]\n"
            + "54:17.08:[9, 11, 17, 19, 21, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:17.17:[9, 11, 14, 17, 19, 21, 22, 23, 24, 25, 32, 39]\n"
            + "54:17.17:[9, 11, 14, 17, 19, 21, 23, 24, 25, 29, 32, 39]\n"
            + "54:17.17:[9, 11, 14, 17, 19, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:17.17:[9, 11, 14, 17, 21, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:17.17:[9, 11, 14, 19, 21, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:17.17:[9, 14, 17, 19, 21, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:17.17:[9, 11, 14, 17, 18, 19, 21, 22, 23, 29, 32, 39]]\n"
            + "For 13  species\n"
            + "[\n"
            + "54:16.85:[9, 11, 14, 17, 19, 21, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:16.92:[9, 11, 14, 17, 18, 19, 21, 22, 23, 24, 29, 32, 39]\n"
            + "54:16.92:[9, 11, 14, 17, 18, 19, 21, 22, 23, 25, 29, 32, 39]\n"
            + "54:16.92:[9, 11, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:16.92:[9, 11, 14, 17, 19, 21, 22, 23, 24, 25, 29, 34, 39]\n"
            + "54:17.00:[9, 11, 14, 17, 18, 19, 21, 22, 23, 24, 25, 32, 39]\n"
            + "54:17.00:[9, 11, 14, 17, 18, 19, 21, 23, 24, 25, 29, 32, 39]\n"
            + "54:17.00:[9, 11, 14, 17, 18, 19, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:17.00:[9, 11, 14, 17, 18, 21, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:17.00:[9, 11, 14, 18, 19, 21, 22, 23, 24, 25, 29, 32, 39]]\n"
            + "For 14  species\n"
            + "[\n"
            + "54:16.71:[9, 11, 14, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:16.79:[9, 11, 13, 14, 17, 19, 21, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:16.79:[9, 11, 14, 17, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39]\n"
            + "54:16.79:[9, 11, 14, 17, 18, 19, 21, 22, 23, 24, 25, 29, 34, 39]\n"
            + "54:16.86:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 29, 32, 39]\n"
            + "54:16.86:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 25, 29, 32, 39]\n"
            + "54:16.86:[9, 11, 14, 17, 18, 19, 21, 22, 23, 24, 26, 29, 32, 39]\n"
            + "54:16.86:[9, 11, 14, 17, 18, 19, 21, 22, 23, 25, 26, 29, 32, 39]\n"
            + "54:16.86:[9, 11, 14, 17, 19, 21, 22, 23, 24, 25, 29, 32, 39, 41]\n"
            + "54:16.86:[9, 11, 13, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 39]]\n"
            + "For 15  species\n"
            + "[\n"
            + "54:16.67:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:16.67:[9, 11, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39]\n"
            + "54:16.73:[9, 11, 13, 14, 17, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39]\n"
            + "54:16.73:[9, 11, 14, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 39, 41]\n"
            + "54:16.73:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 29, 34, 39]\n"
            + "54:16.73:[9, 11, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 34, 39]\n"
            + "54:16.80:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 26, 29, 32, 39]\n"
            + "54:16.80:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 25, 26, 29, 32, 39]\n"
            + "54:16.80:[9, 11, 13, 14, 17, 19, 21, 22, 23, 24, 25, 29, 32, 39, 41]\n"
            + "54:16.80:[9, 11, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 39]]\n"
            + "For 16  species\n"
            + "[\n"
            + "54:16.63:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39]\n"
            + "54:16.69:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 39, 41]\n"
            + "54:16.69:[9, 11, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 41]\n"
            + "54:16.69:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 34, 39]\n"
            + "54:16.75:[9, 11, 13, 14, 17, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 41]\n"
            + "54:16.75:[9, 11, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 39]\n"
            + "54:16.75:[9, 11, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39]\n"
            + "54:16.75:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 39, 40]\n"
            + "54:16.75:[9, 11, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 40]\n"
            + "54:16.75:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 34, 39]]\n"
            + "For 17  species\n"
            + "[\n"
            + "54:16.65:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 41]\n"
            + "54:16.65:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 46]\n"
            + "54:16.71:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 39, 41, 46]\n"
            + "54:16.71:[9, 11, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 41, 46]\n"
            + "54:16.71:[9, 11, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39]\n"
            + "54:16.71:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 40]\n"
            + "54:16.71:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 34, 39]\n"
            + "54:16.71:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 34, 39, 41]\n"
            + "54:16.76:[9, 11, 13, 14, 17, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 41, 46]\n"
            + "54:16.76:[2, 9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39]]\n"
            + "For 18  species\n"
            + "[\n"
            + "54:16.67:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 41, 46]\n"
            + "54:16.72:[9, 11, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 41]\n"
            + "54:16.72:[9, 11, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 46]\n"
            + "54:16.72:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 40, 41]\n"
            + "54:16.72:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 40, 46]\n"
            + "54:16.72:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 34, 39, 41]\n"
            + "54:16.72:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 34, 39, 46]\n"
            + "54:16.72:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 34, 39, 41, 46]\n"
            + "54:16.78:[9, 11, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 39, 41, 46]\n"
            + "54:16.78:[9, 11, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 41, 46]]\n"
            + "For 19  species\n"
            + "[\n"
            + "54:16.74:[9, 11, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 41, 46]\n"
            + "54:16.74:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 34, 39, 41, 46]\n"
            + "54:16.74:[9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 40, 41, 46]\n"
            + "54:16.79:[9, 11, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 34, 39, 41]\n"
            + "54:16.79:[9, 11, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 34, 39, 46]\n"
            + "54:16.79:[2, 9, 11, 13, 14, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 41, 46]\n"
            + "54:16.79:[9, 11, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 40, 41]\n"
            + "54:16.79:[9, 11, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 32, 39, 40, 46]\n"
            + "54:16.79:[9, 11, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 29, 34, 39, 41, 46]\n"
            + "54:16.84:[9, 11, 13, 14, 16, 17, 18, 19, 21, 22, 23, 24, 25, 29, 32, 34, 39, 41, 46]]\n";

    private static final String forestDAllResult
            = "The dataset:Forest_D_ALL.txt\n"
            + "The dataset contains 18 species and 26 resource types\n"
            + "For 2  species\n"
            + "[]\n"
            + "For 3  species\n"
            + "[]\n"
            + "For 4  species\n"
            + "[\n"
            + "36:17.50:[3, 4, 12, 18]\n"
            + "36:17.50:[3, 4, 13, 18]\n"
            + "36:17.50:[3, 12, 14, 18]\n"
            + "36:17.50:[3, 13, 14, 18]\n"
            + "36:17.50:[3, 12, 16, 18]\n"
            + "36:17.50:[3, 13, 16, 18]\n"
            + "36:17.75:[3, 5, 12, 18]\n"
            + "36:17.75:[3, 5, 13, 18]\n"
            + "36:19.25:[3, 4, 7, 18]\n"
            + "36:19.25:[3, 7, 14, 18]]\n"
            + "For 5  species\n"
            + "[\n"
            + "36:15.40:[3, 4, 10, 12, 18]\n"
            + "36:15.40:[3, 4, 10, 13, 18]\n"
            + "36:15.40:[3, 10, 12, 14, 18]\n"
            + "36:15.40:[3, 10, 13, 14, 18]\n"
            + "36:15.40:[3, 10, 12, 16, 18]\n"
            + "36:15.40:[3, 10, 13, 16, 18]\n"
            + "36:15.60:[3, 5, 10, 12, 18]\n"
            + "36:15.60:[3, 5, 10, 13, 18]\n"
            + "36:15.80:[3, 4, 9, 12, 18]\n"
            + "36:15.80:[3, 4, 12, 13, 18]]\n"
            + "For 6  species\n"
            + "[\n"
            + "36:14.33:[3, 4, 9, 10, 12, 18]\n"
            + "36:14.33:[3, 4, 10, 12, 13, 18]\n"
            + "36:14.33:[3, 4, 9, 10, 13, 18]\n"
            + "36:14.33:[3, 9, 10, 12, 14, 18]\n"
            + "36:14.33:[3, 10, 12, 13, 14, 18]\n"
            + "36:14.33:[3, 9, 10, 13, 14, 18]\n"
            + "36:14.33:[3, 9, 10, 12, 16, 18]\n"
            + "36:14.33:[3, 10, 12, 13, 16, 18]\n"
            + "36:14.33:[3, 9, 10, 13, 16, 18]\n"
            + "36:14.50:[3, 4, 10, 11, 12, 18]]\n"
            + "For 7  species\n"
            + "[\n"
            + "36:13.57:[3, 4, 9, 10, 12, 13, 18]\n"
            + "36:13.57:[3, 9, 10, 12, 13, 14, 18]\n"
            + "36:13.57:[3, 9, 10, 12, 13, 16, 18]\n"
            + "36:13.71:[3, 4, 9, 10, 11, 12, 18]\n"
            + "36:13.71:[3, 4, 10, 11, 12, 13, 18]\n"
            + "36:13.71:[3, 4, 9, 10, 11, 13, 18]\n"
            + "36:13.71:[3, 9, 10, 11, 12, 14, 18]\n"
            + "36:13.71:[3, 10, 11, 12, 13, 14, 18]\n"
            + "36:13.71:[3, 9, 10, 11, 13, 14, 18]\n"
            + "36:13.71:[3, 9, 10, 11, 12, 16, 18]]\n"
            + "For 8  species\n"
            + "[\n"
            + "36:13.13:[3, 4, 9, 10, 11, 12, 13, 18]\n"
            + "36:13.13:[3, 9, 10, 11, 12, 13, 14, 18]\n"
            + "36:13.13:[3, 9, 10, 11, 12, 13, 16, 18]\n"
            + "36:13.25:[3, 4, 9, 10, 12, 13, 15, 18]\n"
            + "36:13.25:[3, 9, 10, 12, 13, 14, 15, 18]\n"
            + "36:13.25:[3, 9, 10, 12, 13, 15, 16, 18]\n"
            + "36:13.25:[3, 5, 9, 10, 11, 12, 13, 18]\n"
            + "36:13.38:[3, 4, 9, 10, 11, 12, 15, 18]\n"
            + "36:13.38:[3, 4, 10, 11, 12, 13, 15, 18]\n"
            + "36:13.38:[3, 4, 9, 10, 11, 13, 15, 18]]\n"
            + "For 9  species\n"
            + "[\n"
            + "36:12.89:[3, 4, 9, 10, 11, 12, 13, 15, 18]\n"
            + "36:12.89:[3, 9, 10, 11, 12, 13, 14, 15, 18]\n"
            + "36:12.89:[3, 9, 10, 11, 12, 13, 15, 16, 18]\n"
            + "36:13.00:[3, 5, 9, 10, 11, 12, 13, 15, 18]\n"
            + "36:13.22:[3, 4, 9, 10, 11, 12, 13, 14, 18]\n"
            + "36:13.22:[3, 4, 9, 10, 11, 12, 13, 16, 18]\n"
            + "36:13.22:[3, 9, 10, 11, 12, 13, 14, 16, 18]\n"
            + "36:13.33:[3, 4, 9, 10, 12, 13, 14, 15, 18]\n"
            + "36:13.33:[3, 4, 9, 10, 12, 13, 15, 16, 18]\n"
            + "36:13.33:[3, 9, 10, 12, 13, 14, 15, 16, 18]]\n"
            + "For 10  species\n"
            + "[\n"
            + "36:13.00:[3, 4, 9, 10, 11, 12, 13, 14, 15, 18]\n"
            + "36:13.00:[3, 4, 9, 10, 11, 12, 13, 15, 16, 18]\n"
            + "36:13.00:[3, 9, 10, 11, 12, 13, 14, 15, 16, 18]\n"
            + "36:13.10:[3, 4, 5, 9, 10, 11, 12, 13, 15, 18]\n"
            + "36:13.10:[3, 5, 9, 10, 11, 12, 13, 14, 15, 18]\n"
            + "36:13.10:[3, 5, 9, 10, 11, 12, 13, 15, 16, 18]\n"
            + "36:13.20:[3, 4, 7, 9, 10, 11, 12, 13, 15, 18]\n"
            + "36:13.20:[3, 7, 9, 10, 11, 12, 13, 14, 15, 18]\n"
            + "36:13.20:[3, 7, 9, 10, 11, 12, 13, 15, 16, 18]\n"
            + "36:13.30:[3, 4, 9, 10, 11, 12, 13, 14, 16, 18]]\n"
            + "For 11  species\n"
            + "[\n"
            + "36:13.09:[3, 4, 9, 10, 11, 12, 13, 14, 15, 16, 18]\n"
            + "36:13.18:[3, 4, 5, 9, 10, 11, 12, 13, 14, 15, 18]\n"
            + "36:13.18:[3, 4, 5, 9, 10, 11, 12, 13, 15, 16, 18]\n"
            + "36:13.18:[3, 5, 9, 10, 11, 12, 13, 14, 15, 16, 18]\n"
            + "36:13.27:[3, 4, 7, 9, 10, 11, 12, 13, 14, 15, 18]\n"
            + "36:13.27:[3, 4, 7, 9, 10, 11, 12, 13, 15, 16, 18]\n"
            + "36:13.27:[3, 7, 9, 10, 11, 12, 13, 14, 15, 16, 18]\n"
            + "36:13.36:[3, 4, 5, 7, 9, 10, 11, 12, 13, 15, 18]\n"
            + "36:13.36:[3, 5, 7, 9, 10, 11, 12, 13, 14, 15, 18]\n"
            + "36:13.36:[3, 5, 7, 9, 10, 11, 12, 13, 15, 16, 18]]\n"
            + "For 12  species\n"
            + "[\n"
            + "36:13.25:[3, 4, 5, 9, 10, 11, 12, 13, 14, 15, 16, 18]\n"
            + "36:13.33:[3, 4, 7, 9, 10, 11, 12, 13, 14, 15, 16, 18]\n"
            + "36:13.42:[3, 4, 5, 7, 9, 10, 11, 12, 13, 14, 15, 18]\n"
            + "36:13.42:[3, 4, 5, 7, 9, 10, 11, 12, 13, 15, 16, 18]\n"
            + "36:13.42:[3, 5, 7, 9, 10, 11, 12, 13, 14, 15, 16, 18]\n"
            + "36:13.50:[3, 4, 6, 9, 10, 11, 12, 13, 14, 15, 16, 18]\n"
            + "36:13.50:[3, 4, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18]\n"
            + "36:13.58:[3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 18]\n"
            + "36:13.58:[3, 4, 5, 6, 9, 10, 11, 12, 13, 15, 16, 18]\n"
            + "36:13.58:[3, 5, 6, 9, 10, 11, 12, 13, 14, 15, 16, 18]]\n";
}
