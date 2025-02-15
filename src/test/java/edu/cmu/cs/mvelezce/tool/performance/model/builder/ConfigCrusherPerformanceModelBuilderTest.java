package edu.cmu.cs.mvelezce.tool.performance.model.builder;

import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import edu.cmu.cs.mvelezce.tool.analysis.region.Region;
import edu.cmu.cs.mvelezce.tool.analysis.taint.Analysis;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.DefaultStaticAnalysis;
import edu.cmu.cs.mvelezce.tool.execute.java.ConfigCrusherExecutor;
import edu.cmu.cs.mvelezce.tool.execute.java.Executor;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.runningexample.RunningExampleMain;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.BaseRegionInstrumenter;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.ConfigCrusherTimerRegionInstrumenter;
import edu.cmu.cs.mvelezce.tool.performance.entry.PerformanceEntryStatistic;
import edu.cmu.cs.mvelezce.tool.performance.model.PerformanceModel;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public class ConfigCrusherPerformanceModelBuilderTest {

  @Test
  public void runningExample() throws Exception {
    String programName = RunningExampleMain.PROGRAM_NAME;

    // Program arguments
    String[] args = new String[0];

    BaseRegionInstrumenter instrumenter =
        new ConfigCrusherTimerRegionInstrumenter(programName);
    instrumenter.instrument(args);
    Map<JavaRegion, Set<Set<String>>> javaRegionsToOptionSet =
        instrumenter.getRegionsToOptionSet();

    Analysis analysis = new DefaultStaticAnalysis();
    Map<Region, Set<Set<String>>> regionsToOptionSet =
        analysis.transform(javaRegionsToOptionSet);

    Executor executor = new ConfigCrusherExecutor(programName);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);

    args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PerformanceModelBuilder builder = new ConfigCrusherPerformanceModelBuilder(
        programName, measuredPerformance, regionsToOptionSet);
    builder.createModel(args);
  }

  @Test
  public void runningExample1() throws Exception {
    String programName = RunningExampleMain.PROGRAM_NAME;

    // Program arguments
    String[] args = new String[0];

    PerformanceModelBuilder builder =
        new ConfigCrusherPerformanceModelBuilder(programName);
    builder.createModel(args);
  }

  @Test
  public void prevayler() throws Exception {
    String programName = "prevayler";

    // Program arguments
    String[] args = new String[0];

    BaseRegionInstrumenter instrumenter =
        new ConfigCrusherTimerRegionInstrumenter(programName);
    instrumenter.instrument(args);
    Map<JavaRegion, Set<Set<String>>> javaRegionsToOptionSet =
        instrumenter.getRegionsToOptionSet();

    Analysis analysis = new DefaultStaticAnalysis();
    Map<Region, Set<Set<String>>> regionsToOptionSet =
        analysis.transform(javaRegionsToOptionSet);

    Executor executor = new ConfigCrusherExecutor(programName);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);

    args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PerformanceModelBuilder builder = new ConfigCrusherPerformanceModelBuilder(
        programName, measuredPerformance, regionsToOptionSet);
    PerformanceModel model = builder.createModel(args);
    model.toString();
  }

  @Test
  public void colorCounter() throws Exception {
    String programName = "pngtasticColorCounter";

    // Program arguments
    String[] args = new String[0];

    BaseRegionInstrumenter instrumenter =
        new ConfigCrusherTimerRegionInstrumenter(programName);
    instrumenter.instrument(args);
    Map<JavaRegion, Set<Set<String>>> javaRegionsToOptionSet =
        instrumenter.getRegionsToOptionSet();

    Analysis analysis = new DefaultStaticAnalysis();
    Map<Region, Set<Set<String>>> regionsToOptionSet =
        analysis.transform(javaRegionsToOptionSet);

    Executor executor = new ConfigCrusherExecutor(programName);
    Set<PerformanceEntryStatistic> measuredPerformance = executor.execute(args);

    args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    PerformanceModelBuilder builder = new ConfigCrusherPerformanceModelBuilder(
        programName, measuredPerformance, regionsToOptionSet);
    builder.createModel(args);
  }
}