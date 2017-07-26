package edu.cmu.cs.mvelezce.tool.instrumentation.java;

import edu.cmu.cs.mvelezce.*;
import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.ProgramAnalysis;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by miguelvelez on 4/30/17.
 */
public class InstrumenterTest {
    // TODO check that the regions are correct since we might have, at this point, java line numbers instead of bytecodeindex

    @Test
    public void testElevatorSimple() throws IOException, ParseException, InterruptedException {
        String programName = "elevator-simple";
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/elevator/out/production/elevator/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/elevator/";

        // Program arguments
        String[] args = new String[0];

//        String[] args = new String[1];
//        args[0] = "-saveres";

//        String[] args = new String[2];
//        args[0] = "-delres";
//        args[1] = "-saveres";

        Map<JavaRegion, Set<Set<String>>> decisionsToOptions = ProgramAnalysis.analyze(programName, args);

        Instrumenter.instrument(srcDirectory, classDirectory, decisionsToOptions.keySet());
    }

    @Test
    public void testElevator() throws IOException, ParseException, InterruptedException {
        String programName = "elevator";
        String originalSrcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/original/elevator/";
        String originalClassDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/original/elevator/out/production/elevator/";
        String instrumentSrcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/elevator/";
        String instrumentClassDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/elevator/out/production/elevator/";

        // Format return statements with method calls
        Formatter.format(originalSrcDirectory, originalClassDirectory, instrumentSrcDirectory, instrumentClassDirectory);


        // Program arguments
        String[] args = new String[0];

//        String[] args = new String[1];
//        args[0] = "-saveres";

//        String[] args = new String[2];
//        args[0] = "-delres";
//        args[1] = "-saveres";

        Map<JavaRegion, Set<Set<String>>> decisionsToOptions = ProgramAnalysis.analyze(programName, args);

        Instrumenter.instrument(instrumentSrcDirectory, instrumentClassDirectory, decisionsToOptions.keySet());
    }

    @Test
    public void testSleep30() throws IOException, ParseException, InterruptedException {
        String programName = "sleep30";
        String originalSrcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/original/dummy/";
        String originalClassDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/original/dummy/out/production/dummy/";
        String instrumentSrcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";
        String instrumentClassDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";

        // Format return statements with method calls
        Formatter.format(originalSrcDirectory, originalClassDirectory, instrumentSrcDirectory, instrumentClassDirectory);

        // Program arguments
        String[] args = new String[0];

//        String[] args = new String[1];
//        args[0] = "-saveres";

//        String[] args = new String[2];
//        args[0] = "-delres";
//        args[1] = "-saveres";

        Map<JavaRegion, Set<Set<String>>> decisionsToOptions = ProgramAnalysis.analyze(programName, args);

        Instrumenter.instrument(instrumentSrcDirectory, instrumentClassDirectory, decisionsToOptions.keySet());
    }

    @Test
    public void testGPL() throws IOException, ParseException, InterruptedException {
        String programName = "gpl";
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/gpl/out/production/gpl/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/gpl/";

        // Program arguments
        String[] args = new String[0];

//        String[] args = new String[1];
//        args[0] = "-saveres";

//        String[] args = new String[2];
//        args[0] = "-delres";
//        args[1] = "-saveres";

        Map<JavaRegion, Set<Set<String>>> decisionsToOptions = ProgramAnalysis.analyze(programName, args);

        Instrumenter.instrument(srcDirectory, classDirectory, decisionsToOptions.keySet());
    }

    @Test
    public void testSleep1() throws IOException, ParseException, InterruptedException {
        String programName = "Sleep1";
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Program arguments
        String[] args = new String[0];

//        String[] args = new String[1];
//        args[0] = "-saveres";

//        String[] args = new String[2];
//        args[0] = "-delres";
//        args[1] = "-saveres";

        Map<JavaRegion, Set<Set<String>>> decisionsToOptions = ProgramAnalysis.analyze(programName, args);

        Instrumenter.instrument(srcDirectory, classDirectory, decisionsToOptions.keySet());
    }

    @Test
    public void testSleep3() throws IOException, ParseException, InterruptedException {
        String programName = "Sleep3";
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Program arguments
        String[] args = new String[0];

//        String[] args = new String[1];
//        args[0] = "-saveres";

//        String[] args = new String[2];
//        args[0] = "-delres";
//        args[1] = "-saveres";

        Map<JavaRegion, Set<Set<String>>> decisionsToOptions = ProgramAnalysis.analyze(programName, args);

        Instrumenter.instrument(srcDirectory, classDirectory, decisionsToOptions.keySet());
    }

    @Test
    public void testSleep17() throws IOException, ParseException, InterruptedException {
        String programName = "Sleep17";
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Program arguments
        String[] args = new String[0];

//        String[] args = new String[1];
//        args[0] = "-saveres";

//        String[] args = new String[2];
//        args[0] = "-delres";
//        args[1] = "-saveres";

        Map<JavaRegion, Set<Set<String>>> decisionsToOptions = ProgramAnalysis.analyze(programName, args);

        Instrumenter.instrument(srcDirectory, classDirectory, decisionsToOptions.keySet());
    }

    @Test
    public void testInstrumentPipeline1() throws IOException, InterruptedException {
        // Program directory
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Set<JavaRegion> regions = new HashSet<>();
        JavaRegion region = new JavaRegion("d9e007bd-0c4a-43b9-ac70-4404378b02ee", Sleep1.PACKAGE, Sleep1.CLASS, Sleep1.MAIN_METHOD, 20);
        regions.add(region);

        Instrumenter.instrument(srcDirectory, classDirectory, regions);
    }

    @Test
    public void testInstrumentPipeline13() throws IOException, InterruptedException {
        // Program directory
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Set<JavaRegion> regions = new HashSet<>();
        JavaRegion region = new JavaRegion("37486f0a-e662-4a18-a7e7-a88ca76d9d2a", Sleep13.PACKAGE, Sleep13.CLASS, Sleep13.MAIN_METHOD, 18);
        regions.add(region);

        Instrumenter.instrument(srcDirectory, classDirectory, regions);
    }

    @Test
    public void testInstrumentPipeline2() throws IOException, InterruptedException {
        // Program directory
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Set<JavaRegion> regions = new HashSet<>();
        JavaRegion region = new JavaRegion("820767c8-a7ee-431b-93b0-d422b44119b8", Sleep2.PACKAGE, Sleep2.CLASS, Sleep2.MAIN_METHOD, 20);
        regions.add(region);

        region = new JavaRegion("d1c659ba-b32e-474f-ba07-9ff1b0e93e3d", Sleep2.PACKAGE, Sleep2.CLASS, Sleep2.METHOD_1, 16);
        regions.add(region);

        Instrumenter.instrument(srcDirectory, classDirectory, regions);
    }

    @Test
    public void testInstrumentPipeline3() throws IOException, InterruptedException {
        // Program directory
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Set<JavaRegion> regions = new HashSet<>();
        JavaRegion region = new JavaRegion("515dad72-acd8-4078-afd1-243d8a0a8159", Sleep3.PACKAGE, Sleep3.CLASS, Sleep3.MAIN_METHOD, 28);
        regions.add(region);

        region = new JavaRegion("e175b797-442f-4af3-ae7b-df17f690cbb5", Sleep3.PACKAGE, Sleep3.CLASS, Sleep3.MAIN_METHOD, 45);
        regions.add(region);

        region = new JavaRegion("49ccd1a0-4f72-4704-afed-da8c8fc07628", Sleep3.PACKAGE, Sleep3.CLASS, Sleep3.METHOD_1, 16);
        regions.add(region);

        region = new JavaRegion("35d3ea7b-b151-406b-8c40-8402f2aaf2d7", Sleep3.PACKAGE, Sleep3.CLASS, Sleep3.METHOD_2, 16);
        regions.add(region);

        Instrumenter.instrument(srcDirectory, classDirectory, regions);
    }

    @Test
    public void testInstrumentPipeline4() throws IOException, InterruptedException {
        // Program directory
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Set<JavaRegion> regions = new HashSet<>();
        JavaRegion region = new JavaRegion("869447bc-1159-43aa-8cd6-76a941928dec", Sleep4.PACKAGE, Sleep4.CLASS, Sleep4.MAIN_METHOD, 20);
        regions.add(region);

        Instrumenter.instrument(srcDirectory, classDirectory, regions);
    }

    @Test
    public void testInstrumentPipeline7() throws IOException, InterruptedException {
        // Program directory
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Set<JavaRegion> regions = new HashSet<>();
        JavaRegion region = new JavaRegion("6b5f3599-b7be-48dd-a1e2-403b66e065c2", Sleep7.PACKAGE, Sleep7.CLASS, Sleep7.MAIN_METHOD, 20);
        regions.add(region);

        region = new JavaRegion("95df7be1-6bd8-4675-bbfd-1c0bdcc170d8", Sleep7.PACKAGE, Sleep7.CLASS, Sleep7.MAIN_METHOD, 41);
        regions.add(region);

        Instrumenter.instrument(srcDirectory, classDirectory, regions);
    }

    @Test
    public void testInstrumentPipeline8() throws IOException, InterruptedException {
        // Program directory
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Set<JavaRegion> regions = new HashSet<>();
        JavaRegion region = new JavaRegion("62a843e8-fda7-4296-9cb0-06f2ab7e922a", Sleep8.PACKAGE, Sleep8.CLASS, Sleep8.MAIN_METHOD, 20);
        regions.add(region);

        region = new JavaRegion("55be5222-878e-480e-9b4c-19ee048e7d68", Sleep8.PACKAGE, Sleep8.CLASS, Sleep8.MAIN_METHOD, 42);
        regions.add(region);

        Instrumenter.instrument(srcDirectory, classDirectory, regions);
    }

    @Test
    public void testInstrumentPipeline9() throws IOException, InterruptedException {
        // Program directory
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Set<JavaRegion> regions = new HashSet<>();
        JavaRegion region = new JavaRegion("23cc14a5-0f8b-4647-9a9d-31124de65660", Sleep9.PACKAGE, Sleep9.CLASS, Sleep9.MAIN_METHOD, 24);
        regions.add(region);

        region = new JavaRegion("093455fe-df64-4f1f-9bcf-2c879c97ae1c", Sleep9.PACKAGE, Sleep9.CLASS, Sleep9.MAIN_METHOD, 41);
        regions.add(region);

        Instrumenter.instrument(srcDirectory, classDirectory, regions);
    }

    @Test
    public void testInstrumentPipeline10() throws IOException, InterruptedException {
        // Program directory
        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
        String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";

        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Set<JavaRegion> regions = new HashSet<>();
        JavaRegion region = new JavaRegion("8991b0ff-a0a9-4967-8acd-ba823cf700f4", Sleep10.PACKAGE, Sleep10.CLASS, Sleep10.MAIN_METHOD, 28);
        regions.add(region);

        region = new JavaRegion("44f7b4e3-3af8-46c1-b9d0-1a93736950c7", Sleep10.PACKAGE, Sleep10.CLASS, Sleep10.MAIN_METHOD, 42);
        regions.add(region);

        region = new JavaRegion("96c809ce-733d-4647-8d10-d1d587144c8b", Sleep10.PACKAGE, Sleep10.CLASS, Sleep10.METHOD_1, 16);
        regions.add(region);

        region = new JavaRegion("e80dd113-203e-477b-91bc-0c4889da64d2", Sleep10.PACKAGE, Sleep10.CLASS, Sleep10.METHOD_2, 16);
        regions.add(region);

        Instrumenter.instrument(srcDirectory, classDirectory, regions);
    }

//    @Test
//    public void testInstrumentPipeline99() throws IOException, InterruptedException {
//        // Program directory
//        String classDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";
//      String srcDirectory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/";
//
//        // Java Region
//        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
//        Set<JavaRegion> regions = new HashSet<>();
//        JavaRegion region = new JavaRegion("8991b0ff-a0a9-4967-8acs-ba823cf700f4", LocalVariableNameCheck.class.getPackage().getName(), LocalVariableNameCheck.class.getSimpleName(), "mustCheckName", 4);
//        regions.add(region);
//
//        Instrumenter.instrument(srcDirectory, classDirectory, regions);
//    }
}