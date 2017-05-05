package edu.cmu.cs.mvelezce.tool.execute.java;

import edu.cmu.cs.mvelezce.java.programs.Sleep1;
import edu.cmu.cs.mvelezce.java.programs.Sleep2;
import edu.cmu.cs.mvelezce.java.programs.Sleep3;
import edu.cmu.cs.mvelezce.tool.Helper;
import edu.cmu.cs.mvelezce.tool.compression.SimpleTest;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.Instrumenter;
import edu.cmu.cs.mvelezce.tool.performance.PerformanceEntry;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by miguelvelez on 4/30/17.
 */
public class ExecuterTest {

    public static void checkExecutionTimes(Set<PerformanceEntry> expectedPerformances, Set<PerformanceEntry> actualPerformances) {
        for(PerformanceEntry expected : expectedPerformances) {
            for(PerformanceEntry actual : actualPerformances) {
//                for(Region expectedRegion : expected.getRegions()) {
//                    for(Region actualRegion : actual.getRegions()) {
//                        if(expected.getConfiguration().equals(actual.getConfiguration()) && expectedRegion.equals(actualRegion)) {
//                            System.out.println("Configuration: " + actual.getConfiguration());
//                            System.out.println("Expected: " + expectedRegion.getExecutionTime()/1000.0);
//                            System.out.println("Actual: " + actualRegion.getSecondsExecutionTime());
//                            Assert.assertEquals(actualRegion.getSecondsExecutionTime(), expectedRegion.getExecutionTime()/1000.0, JavaPipelineTest.TIMING_ERROR);
//                        }
//                    }
//                }
            }

            System.out.println();
        }
    }

    @Test
    public void testMeasureConfigurationPerformancePipeline1() throws Exception {
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Configurations
        Set<Set<String>> optionsSet = SimpleTest.getOptionsSet("A");
        Set<Set<String>> configurationsToExecute = Helper.getConfigurations(optionsSet.iterator().next());

        // Execute
        Set<PerformanceEntry> outputSave = Executer.measureConfigurationPerformance(Sleep1.CLASS, args, Sleep1.FILENAME, Instrumenter.DIRECTORY + "/" + Sleep1.CLASS, configurationsToExecute);

        args = new String[0];
        Set<PerformanceEntry> outputRead = Executer.measureConfigurationPerformance(Sleep1.CLASS, args, Sleep1.FILENAME, Instrumenter.DIRECTORY + "/" + Sleep1.CLASS, configurationsToExecute);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testMeasureConfigurationPerformancePipeline2() throws Exception {
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Configurations
        Set<Set<String>> optionsSet = SimpleTest.getOptionsSet("A");
        Set<Set<String>> configurationsToExecute = Helper.getConfigurations(optionsSet.iterator().next());

        // Execute
        Set<PerformanceEntry> outputSave = Executer.measureConfigurationPerformance(Sleep2.CLASS, args, Sleep2.FILENAME, Instrumenter.DIRECTORY + "/" + Sleep2.CLASS, configurationsToExecute);

        args = new String[0];
        Set<PerformanceEntry> outputRead = Executer.measureConfigurationPerformance(Sleep2.CLASS, args, Sleep2.FILENAME, Instrumenter.DIRECTORY + "/" + Sleep2.CLASS, configurationsToExecute);

        Assert.assertEquals(outputSave, outputRead);
    }

        @Test
    public void testMeasureConfigurationPerformance4() throws Exception {
        // Configurations
        Set<Set<String>> optionsSet = SimpleTest.getOptionsSet("AB");
        Set<Set<String>> configurationsToExecute = Helper.getConfigurations(optionsSet.iterator().next());

        // Execute
        Set<PerformanceEntry> results = Executer.measureConfigurationPerformance(Sleep1.CLASS, Sleep3.FILENAME, Instrumenter.DIRECTORY + "/" + Sleep3.CLASS, configurationsToExecute);

//        // Java Region
//        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
//        Set<JavaRegion> regions = new HashSet<>();
//        JavaRegion region1 = new JavaRegion(Sleep3.PACKAGE, Sleep3.CLASS, Sleep3.MAIN_METHOD, 31, 36);
//        regions.add(region1);
//
//        JavaRegion region2 = new JavaRegion(Sleep3.PACKAGE, Sleep3.CLASS, Sleep3.MAIN_METHOD, 48, 53);
//        regions.add(region2);
//
//        JavaRegion region3 = new JavaRegion(Sleep3.PACKAGE, Sleep3.CLASS, Sleep3.METHOD_1, 19, 20);
//        regions.add(region3);
//
//        JavaRegion region4 = new JavaRegion(Sleep3.PACKAGE, Sleep3.CLASS, Sleep3.METHOD_2, 19, 20);
//        regions.add(region4);
//
//        // Set of performance entries
//        Set<PerformanceEntry> measuredPerformance = new HashSet<>();
//
//        // Empty configuration
//        Set<String> configuration = new HashSet<>();
//        Regions.getProgram().startTime(0);
//        Regions.getProgram().endTime(300);
//        PerformanceEntry performanceEntry = new PerformanceEntry(configuration, Regions.getRegions(), Regions.getProgram());
//        measuredPerformance.add(performanceEntry);
//
//        // Configuration A
//        configuration = new HashSet<>();
//        configuration.add("A");
//        Regions.resetRegions();
//        Regions.getRegion(region1).startTime(0);
//        Regions.getRegion(region1).endTime(1500);
//        Regions.getRegion(region3).startTime(0);
//        Regions.getRegion(region3).endTime(600);
//        Regions.getProgram().startTime(0);
//        Regions.getProgram().endTime(1800);
//        performanceEntry = new PerformanceEntry(configuration, Regions.getRegions(), Regions.getProgram());
//        measuredPerformance.add(performanceEntry);
//
//        // Configuration B
//        configuration = new HashSet<>();
//        configuration.add("B");
//        Regions.resetRegions();
//        Regions.getRegion(region2).startTime(0);
//        Regions.getRegion(region2).endTime(1700);
//        Regions.getRegion(region4).startTime(0);
//        Regions.getRegion(region4).endTime(600);
//        Regions.getProgram().startTime(0);
//        Regions.getProgram().endTime(1900);
//        performanceEntry = new PerformanceEntry(configuration, Regions.getRegions(), Regions.getProgram());
//        measuredPerformance.add(performanceEntry);
//
//        // Configuration AB
//        configuration = new HashSet<>();
//        configuration.add("A");
//        configuration.add("B");
//        Regions.resetRegions();
//        Regions.getRegion(region1).startTime(0);
//        Regions.getRegion(region1).endTime(1500);
//        Regions.getRegion(region2).startTime(0);
//        Regions.getRegion(region2).endTime(1700);
//        Regions.getRegion(region3).startTime(0);
//        Regions.getRegion(region3).endTime(600);
//        Regions.getRegion(region4).startTime(0);
//        Regions.getRegion(region4).endTime(600);
//        Regions.getProgram().startTime(0);
//        Regions.getProgram().endTime(3500);
//        performanceEntry = new PerformanceEntry(configuration, Regions.getRegions(), Regions.getProgram());
//        measuredPerformance.add(performanceEntry);
//
//        // Assert
//        Assert.assertEquals(measuredPerformance, results);
//        ExecuterTest.checkExecutionTimes(measuredPerformance, results);
    }

}