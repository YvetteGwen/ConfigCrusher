package edu.cmu.cs.mvelezce.analysis.pipeline;

import edu.cmu.cs.mvelezce.analysis.Helper;
import edu.cmu.cs.mvelezce.analysis.performance.PerformanceEntry;
import edu.cmu.cs.mvelezce.analysis.performance.PerformanceModel;
import edu.cmu.cs.mvelezce.analysis.pipeline.sleep.SleepPipeline;
import edu.cmu.cs.mvelezce.analysis.pipeline.sleep.SleepRegion;
import edu.cmu.cs.mvelezce.analysis.taint.Region;
import edu.cmu.cs.mvelezce.sleep.ast.expression.ExpressionConstantInt;
import edu.cmu.cs.mvelezce.sleep.ast.statement.Statement;
import edu.cmu.cs.mvelezce.sleep.ast.statement.StatementSleep;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by mvelezce on 4/10/17.
 */
public class PipelineTest {

    public static Set<Set<String>> getOptionsSet(String string) {
        Set<Set<String>> result = new HashSet<>();
        String[] allOptions = string.split(",");

        for(String options : allOptions) {
            Set<String> newOption = new HashSet<>();
            options = options.trim();

            for(int i = 0; i < options.length(); i++) {
                newOption.add(options.charAt(i) + "");
            }

            result.add(newOption);
        }

        return result;
    }

    public static void getConfigurationsToExecute(Set<Set<String>> relevantOptionsSet) {
        Collection<List<Set<String>>> permutations = CollectionUtils.permutations(relevantOptionsSet);

        for(List<Set<String>> permutation : permutations) {
//            System.out.println("\nPermutation: " + permutation);
            Set<Set<String>> permutationAsSet = new HashSet<>(permutation);
            Set<Set<String>> results = Pipeline.getConfigurationsToExecute(permutationAsSet);
//            System.out.println(results);

            for(Set<String> relevantOptions : relevantOptionsSet) {
                Set<Set<String>> powerSet = Helper.getConfigurations(relevantOptions);

                for (Set<String> configuration : powerSet) {
//                    System.out.println("Want configuration: " + configuration + " from: " + relevantOptionsConvenient);
                    boolean hasConfiguration = false;

                    for (Set<String> result : results) {
                        if (PipelineTest.matches(result, configuration, relevantOptions)) {
                            hasConfiguration = true;
                            break;
                        }
                    }

                    Assert.assertTrue(hasConfiguration);
                }
            }
        }
    }

    public static boolean matches(Set<String> result, Set<String> configuration, Set<String> relevantOptions) {
        Set<String> hold = new HashSet<>(relevantOptions);
        hold.retainAll(result);
        return hold.equals(configuration);
    }

    @Test
    public void testGetConfigurationsToExecute1() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("AB, AC, AD, BE");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void testGetConfigurationsToExecute2() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("ABC, BCD");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void testGetConfigurationsToExecute3() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("AB, BCD");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void testGetConfigurationsToExecute4() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("AB, BC");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void testGetConfigurationsToExecute5() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("AB, CDE");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void testGetConfigurationsToExecute6() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("AB, AC, BC");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void testGetConfigurationsToExecute7() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("AB, AC, AD, BC, BD");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void testGetConfigurationsToExecute8() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("AB, AC, AD, BC, CD");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test // TODO ERROR because I need to get the other 4 configs from ABC
    public void testGetConfigurationsToExecute9() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("ABC, CD, BD");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void testGetConfigurationsToExecute10() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("ABC, CD");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void testGetConfigurationsToExecute11() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("ABC, DEF");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void testGetConfigurationsToExecute12() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void testGetConfigurationsToExecute13() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("ABCD, ADXY, ABDX");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void testGetConfigurationsToExecute14() {
        Set<Set<String>> relevantOptionsSet = PipelineTest.getOptionsSet("AB, AC, AD, BC, CD, BD");
        PipelineTest.getConfigurationsToExecute(relevantOptionsSet);
    }

    @Test
    public void filterOptions1() throws Exception {
        Set<Set<String>> set = PipelineTest.getOptionsSet("AB, AC");
        Assert.assertEquals(set, Pipeline.filterOptions(set));
    }

    @Test
    public void filterOptions2() throws Exception {
        Set<Set<String>> set = PipelineTest.getOptionsSet("ABC, ACD");
        Assert.assertEquals(set, Pipeline.filterOptions(set));
    }

    @Test
    public void filterOptions3() throws Exception {
        Set<Set<String>> set = PipelineTest.getOptionsSet("AB, ABC");
        Set<Set<String>> result = PipelineTest.getOptionsSet("ABC");
        Assert.assertEquals(result, Pipeline.filterOptions(set));
    }

    @Test
    public void filterOptions4() throws Exception {
        Set<Set<String>> set = PipelineTest.getOptionsSet("AB, ABC, BCD, BC, DEF");
        Set<Set<String>> result = PipelineTest.getOptionsSet("ABC, BCD, DEF");
        Assert.assertEquals(result, Pipeline.filterOptions(set));
    }

    @Test
    public void testCreatePerformanceModel1() throws Exception {
        // Map<Region, Set<String>> regionsToOptions
        Map<Region, Set<String>> regionsToOptions = new HashMap<>();
        Set<String> relevantOptions = new HashSet<>();
        relevantOptions.add("A");
        int duration1 = 3;
        Statement timedStatement1 = new StatementSleep(new ExpressionConstantInt(duration1));
        Region region1 = new SleepRegion(timedStatement1);
        regionsToOptions.put(region1.clone(), relevantOptions);

        relevantOptions = new HashSet<>();
        relevantOptions.add("B");
        int duration2 = 1;
        Statement timedStatement2 = new StatementSleep(new ExpressionConstantInt(duration2));
        Region region2 = new SleepRegion(timedStatement2);
        regionsToOptions.put(region2.clone(), relevantOptions);

        // Set<PerformanceEntry> measuredPerformance
        Set<PerformanceEntry> measuredPerformance = new HashSet<>();
        Set<String> configuration = new HashSet<>();
        Set<Region> regions = new HashSet<>();
        region1.startTime(0);
        region1.endTime(0);
        region2.startTime(0);
        region2.endTime(0);
        regions.add(region1.clone());
        regions.add(region2.clone());
        PerformanceEntry performanceEntry = new PerformanceEntry(configuration, regions);
        measuredPerformance.add(performanceEntry);

        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("B");
        regions = new HashSet<>();
        region1.startTime(0);
        region1.endTime(duration1);
        region2.startTime(0);
        region2.endTime(duration2);
        regions.add(region1.clone());
        regions.add(region2.clone());
        performanceEntry = new PerformanceEntry(configuration, regions);
        measuredPerformance.add(performanceEntry);

        // Performance model
        PerformanceModel performanceModel = SleepPipeline.createPerformanceModel(measuredPerformance, regionsToOptions);

        int performance = 0;
        configuration = new HashSet<>();
        Assert.assertEquals(performance, performanceModel.evaluate(configuration));

        performance = 4;
        configuration = new HashSet<>();
        configuration.add("A");
        configuration.add("B");
        Assert.assertEquals(performance, performanceModel.evaluate(configuration));

        performance = 3;
        configuration = new HashSet<>();
        configuration.add("A");
        Assert.assertEquals(performance, performanceModel.evaluate(configuration));

        performance = 1;
        configuration = new HashSet<>();
        configuration.add("B");
        Assert.assertEquals(performance, performanceModel.evaluate(configuration));
    }

    // TODO test create a performance mondel with a base time

}