package edu.cmu.cs.mvelezce.tool;

import edu.cmu.cs.mvelezce.tool.SystemConfig;
import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import edu.cmu.cs.mvelezce.tool.analysis.region.Region;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.StaticAnalysis;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.taintflow.TaintFlowAnalysis;
import edu.cmu.cs.mvelezce.tool.compression.BaseCompression;
import edu.cmu.cs.mvelezce.tool.compression.Compression;
import edu.cmu.cs.mvelezce.tool.compression.simple.SimpleCompression;
import edu.cmu.cs.mvelezce.tool.execute.java.ConfigCrusherExecutor;
import edu.cmu.cs.mvelezce.tool.execute.java.Executor;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.CompileInstrumenter;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.ConfigCrusherTimerRegionInstrumenter;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.Formatter;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.Instrumenter;
import edu.cmu.cs.mvelezce.tool.performance.entry.PerformanceEntryStatistic;
import edu.cmu.cs.mvelezce.tool.performance.model.PerformanceModel;
import edu.cmu.cs.mvelezce.tool.performance.model.builder.ConfigCrusherPerformanceModelBuilder;
import edu.cmu.cs.mvelezce.tool.performance.model.builder.PerformanceModelBuilder;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public class ConfigCrusher {

  final static Logger log = Logger.getLogger(ConfigCrusher.class.getName());

  private String programName;
  private String srcDir;
  private String classDir;
  private String entry;
  private final SystemConfig systemConfig;

  // public ConfigCrusher(String programName, String classDir, String entry) {
  //   this(programName, "", classDir, entry);
  // }

  public ConfigCrusher(String programName, String srcDir, String classDir,
                       String entry, SystemConfig systemConfig) {
    this.programName = programName;
    this.srcDir = srcDir;
    this.classDir = classDir;
    this.entry = entry;
    this.systemConfig = systemConfig;
  }

  public PerformanceModel run(String[] args)
      throws IOException, NoSuchMethodException, IllegalAccessException,
             InvocationTargetException, InterruptedException {

    log.info("ConfigCrush begins to run.");

    StaticAnalysis analysis = new TaintFlowAnalysis(
        this.programName, this.systemConfig.staticAnalysisConfig);
    Map<JavaRegion, Set<Set<String>>> javaRegionsToOptionSet =
        analysis.analyze(args);

    Set<Set<String>> options =
        BaseCompression.expandOptions(javaRegionsToOptionSet.values());
    Compression compressor = new SimpleCompression(this.programName, options);
    Set<Set<String>> configurations = compressor.compressConfigurations(args);
    System.out.println("Configurations to sample: " + configurations.size());

    Instrumenter instrumenter = new ConfigCrusherTimerRegionInstrumenter(
        this.programName, this.entry, this.classDir, javaRegionsToOptionSet);
    instrumenter.instrument(args);

    Executor executor = new ConfigCrusherExecutor(
        this.programName, this.entry, this.classDir, configurations);
    Set<PerformanceEntryStatistic> performanceEntries = executor.execute(args);

    Map<Region, Set<Set<String>>> regionsToOptionSet =
        analysis.transform(javaRegionsToOptionSet);
    PerformanceModelBuilder builder = new ConfigCrusherPerformanceModelBuilder(
        this.programName, performanceEntries, regionsToOptionSet);
    PerformanceModel performanceModel = builder.createModel(args);

    return performanceModel;
  }

  public void compile() throws IOException, InterruptedException {
    Instrumenter compiler = new CompileInstrumenter(this.srcDir, this.classDir);
    compiler.compileFromSource();
  }

  public void format() throws InvocationTargetException, NoSuchMethodException,
                              IllegalAccessException, IOException,
                              InterruptedException {
    String[] args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    Instrumenter compiler = new Formatter(this.srcDir, this.classDir);
    compiler.instrument(args);
  }
}
