package edu.cmu.cs.mvelezce.tool.compression;

import edu.cmu.cs.mvelezce.tool.Helper;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by mvelezce on 4/28/17.
 */
public class SimpleTest {

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

    public static void checktOptionsPermuatationsToGetConfigurationsToExecute(Set<Set<String>> relevantOptionsSet) {
        Collection<List<Set<String>>> permutations = CollectionUtils.permutations(relevantOptionsSet);

        for(List<Set<String>> permutation : permutations) {
//            System.out.println("\nPermutation: " + permutation);
            Set<Set<String>> results = Simple.getConfigurationsToExecute(relevantOptionsSet);
            SimpleTest.checkConfigurationIsStatisfied(new HashSet<>(permutation), results);
        }
    }

    public static void checkConfigurationIsStatisfied(Set<Set<String>> relevantOptionsSet, Set<Set<String>> results) {
//            System.out.println(results);
        for(Set<String> relevantOptions : relevantOptionsSet) {
            Set<Set<String>> powerSet = Helper.getConfigurations(relevantOptions);

            for (Set<String> configuration : powerSet) {
//                    System.out.println("Want configuration: " + configuration + " from: " + relevantOptionsConvenient);
                boolean hasConfiguration = false;

                for (Set<String> result : results) {
                    if (SimpleTest.matches(result, configuration, relevantOptions)) {
                        hasConfiguration = true;
                        break;
                    }
                }

                Assert.assertTrue(hasConfiguration);
            }
        }
    }

    public static boolean matches(Set<String> result, Set<String> configuration, Set<String> relevantOptions) {
        Set<String> valueOfResultInRelevantOption = new HashSet<>(relevantOptions);
        valueOfResultInRelevantOption.retainAll(result);
        return valueOfResultInRelevantOption.equals(configuration);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline1() throws IOException {
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("AB, AC, AD, BE");

        // Program
        String program = "test1";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline2() throws IOException {
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("ABC, BCD");

        // Program
        String program = "test2";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline3() throws IOException {
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("AB, BCD");

        // Program
        String program = "test3";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline4() throws IOException {
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("AB, BC");

        // Program
        String program = "test4";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline5() throws IOException {
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("AB, CDE");

        // Program
        String program = "test5";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline6() throws IOException {
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("AB, AC, BC");

        // Program
        String program = "test6";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline7() throws IOException {
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("AB, AC, AD, BC, BD");

        // Program
        String program = "test7";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline8() throws IOException {
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("AB, AC, AD, BC, CD");

        // Program
        String program = "test8";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline9() throws IOException {
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("ABC, CD, BD");

        // Program
        String program = "test9";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline10() throws IOException{
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("ABC, CD");

        // Program
        String program = "test10";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline11() throws IOException{
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("ABC, DEF");

        // Program
        String program = "test11";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline12() throws IOException{
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("");

        // Program
        String program = "test12";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline13() throws IOException {
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("ABCD, ADXY, ABDX");

        // Program
        String program = "test13";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void testGetConfigurationsToExecutePipeline14() throws IOException{
        // Program arguments
        String[] args = new String[2];
        args[0] = "-delres";
        args[1] = "-saveres";

        // Options
        Set<Set<String>> relevantOptionsSet = SimpleTest.getOptionsSet("AB, AC, AD, BC, CD, BD");

        // Program
        String program = "test14";
        Set<Set<String>> outputSave = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        args = new String[0];
        Set<Set<String>> outputRead = Simple.getConfigurationsToExecute(program, args, relevantOptionsSet);

        Assert.assertEquals(outputSave, outputRead);
    }

    @Test
    public void filterOptions1() {
        Set<Set<String>> set = SimpleTest.getOptionsSet("AB, AC");
        Assert.assertEquals(set, Simple.filterOptions(set));
    }

    @Test
    public void filterOptions2() {
        Set<Set<String>> set = SimpleTest.getOptionsSet("ABC, ACD");
        Assert.assertEquals(set, Simple.filterOptions(set));
    }

    @Test
    public void filterOptions3() {
        Set<Set<String>> set = SimpleTest.getOptionsSet("AB, ABC");
        Set<Set<String>> result = SimpleTest.getOptionsSet("ABC");
        Assert.assertEquals(result, Simple.filterOptions(set));
    }

    @Test
    public void filterOptions4() {
        Set<Set<String>> set = SimpleTest.getOptionsSet("AB, ABC, BCD, BC, DEF");
        Set<Set<String>> result = SimpleTest.getOptionsSet("ABC, BCD, DEF");
        Assert.assertEquals(result, Simple.filterOptions(set));
    }

}