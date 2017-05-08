package edu.cmu.cs.mvelezce.tool.analysis.taint.java;

import edu.cmu.cs.mvelezce.java.programs.Sleep1;
import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by mvelezce on 4/28/17.
 */
public class ProgramAnalysisTest {

    @Test
    public void testAnalysePipeline1() throws Exception {
        // TODO call Lotrack
        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Map<JavaRegion, Set<String>> relevantRegionToOptions = new HashMap<>();
        Set<String> relevantOptions = new HashSet<>();
        relevantOptions.add("A");
        JavaRegion region = new JavaRegion("ecb89258-9de1-416f-955c-194d09e9b249", Sleep1.PACKAGE, Sleep1.CLASS, Sleep1.MAIN_METHOD, 20, -1);
        relevantRegionToOptions.put(region, relevantOptions);
        // TODO call Lotrack

        // Program files
        List<String> programFiles = new ArrayList<>();
        programFiles.add(Sleep1.FILENAME);

        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        Map<JavaRegion, Set<String>>  outputSave = ProgramAnalysis.analyse(Sleep1.CLASS, args, Sleep1.FILENAME, programFiles, relevantRegionToOptions);

        args = new String[0];
        Map<JavaRegion, Set<String>>  outputRead = ProgramAnalysis.analyse(Sleep1.CLASS, args, Sleep1.FILENAME, programFiles, relevantRegionToOptions);

        for(Map.Entry<JavaRegion, Set<String>> save : outputSave.entrySet()) {
            boolean oneIsEqual = false;
            for(Map.Entry<JavaRegion, Set<String>> read : outputRead.entrySet()) {
                JavaRegion saveRegion = save.getKey();
                Set<String> saveOptions = save.getValue();
                JavaRegion readRegion = read.getKey();
                Set<String> readOptions = read.getValue();

                if(saveRegion.getRegionID().equals(readRegion.getRegionID()) && saveRegion.getStartBytecodeIndex() == readRegion.getStartBytecodeIndex()
                        && saveRegion.getEndBytecodeIndex() == readRegion.getEndBytecodeIndex()
                        && saveRegion.getRegionPackage().equals(readRegion.getRegionPackage())
                        && saveRegion.getRegionClass().equals(readRegion.getRegionClass())
                        && saveRegion.getRegionMethod().equals(readRegion.getRegionMethod())
                        && saveOptions.equals(readOptions)) {
                    oneIsEqual = true;
                    break;
                }
            }

            Assert.assertTrue(oneIsEqual);
        }
    }

}