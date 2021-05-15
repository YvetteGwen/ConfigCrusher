package edu.cmu.cs.mvelezce.evaluation;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.cs.mvelezce.evaluation.approaches.bruteforce.execute.BruteForceExecutor;
import edu.cmu.cs.mvelezce.evaluation.approaches.featurewise.Featurewise;
import edu.cmu.cs.mvelezce.evaluation.approaches.pairwise.Pairwise;
import edu.cmu.cs.mvelezce.tool.ConfigCrusher;
import edu.cmu.cs.mvelezce.tool.Helper;
import edu.cmu.cs.mvelezce.tool.SystemConfig;
import edu.cmu.cs.mvelezce.tool.compression.Compression;
import edu.cmu.cs.mvelezce.tool.compression.simple.SimpleCompression;
import edu.cmu.cs.mvelezce.tool.execute.java.ConfigCrusherExecutor;
import edu.cmu.cs.mvelezce.tool.execute.java.Executor;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.BaseAdapter;
// import edu.cmu.cs.mvelezce.tool.execute.java.adapter.density.DensityAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.runningexample.RunningExampleAdapter;
import edu.cmu.cs.mvelezce.tool.performance.entry.PerformanceEntryStatistic;
import edu.cmu.cs.mvelezce.tool.performance.model.PerformanceModel;
import edu.cmu.cs.mvelezce.tool.utility.Serialize;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Option.Builder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

/**
 * <h1> Run Evaluation </h1>
 */
public class Run {

  final static Logger log = Logger.getLogger(Run.class.getName());

  private static Options buildOptions() {
    Options options = new Options();
    options.addOption("program", true, "program name");
    options.addOption("srcDir", true, "source code directory");
    options.addOption("classDir", true, "class directory");
    options.addOption("entry", true, "entry point");
    options.addOption("config", true, "system configuration file(json)");
    return options;
  }

  public static void main(String[] args)
      throws ParseException, RuntimeException, Exception {
    String programName, srcDir = "", classDir, entryPoint, configFilePath;
    Options options = buildOptions();
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd;
    try {
      cmd = parser.parse(options, args);

    } catch (ParseException exception) {
      System.out.print("Parse error: " + exception.getMessage());
      throw exception;
    }

    if (cmd.hasOption("program")) {
      programName = cmd.getOptionValue("program");
    } else {
      throw new RuntimeException("Option '-program' is necessary.");
    }
    if (cmd.hasOption("srcDir")) {
      srcDir = cmd.getOptionValue("srcDir");
    }
    if (cmd.hasOption("classDir")) {
      classDir = cmd.getOptionValue("classDir");
    } else {
      throw new RuntimeException("Option '-classDir' is necessary");
    }
    if (cmd.hasOption("entry")) {
      entryPoint = cmd.getOptionValue("entry");
    } else {
      throw new RuntimeException("Option '-entry' is necessary.");
    }

    SystemConfig systemConfig;
    if (cmd.hasOption("config")) {
      configFilePath = cmd.getOptionValue("config");
      byte[] encoded = Files.readAllBytes(Paths.get(configFilePath));
      String content = new String(encoded, Charset.forName("UTF-8"));
      ObjectMapper mapper = new ObjectMapper();
      systemConfig = mapper.readValue(content, SystemConfig.class);
    } else {
      throw new RuntimeException("Option '-config' is necessary.");
    }

    log.info("Start to run.");

    // args = new String[0];
    args = new String[1];
    args[0] = "-saveres";

    ConfigCrusher configCrusher = new ConfigCrusher(
        programName, srcDir, classDir, entryPoint, systemConfig);

    try {
      PerformanceModel performanceModel = configCrusher.run(args);
    } catch (Exception exception) {
      System.out.println("Error: " + exception.getMessage());
      throw exception;
    }
  }

  public static void main_pre(String[] args)
      throws IOException, InterruptedException, ParseException {

    /* commented out by gwen
    String programName = "density";
    //        String classDirectory = BaseAdapter.USER_HOME +
    //
    "/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/density/target/classes";
    String classDirectory =
        BaseAdapter.USER_HOME +
        "/Documents/Programs/ConfigDependency/performance-mapper-evaluation/instrumented/density/target/classes";
    String entryPoint = "at.favre.tools.dconvert.Main";
    */

    // String programName = "running-example";
    // String classDString =
    //     BaseAdapter.USER_HOME +
    //     "/Documents/Programs/ConfigDependency/performance-mapper-evaluation/original/running-example/target/classes";
    // String entryPoint = "edu.cmu.cs.mvelezce.Example";

    String programName, classDirectory, entryPoint;

    try {
      Options options = buildOptions();
      CommandLineParser parser = new DefaultParser();
      CommandLine cmd = parser.parse(options, args);
      programName = cmd.getOptionValue("p");
      classDirectory = cmd.getOptionValue("d");
      entryPoint = cmd.getOptionValue("e");
    } catch (ParseException exception) {
      System.out.print("Parse error: ");
      System.out.println(exception.getMessage());
      throw exception;
    }

    // Program arguments
    args = new String[0];

    // args = new String[2];
    // args[0] = "-delres";
    // args[1] = "-saveres";

    Compression compression = new SimpleCompression(programName);
    Set<Set<String>> ccConfigs = compression.compressConfigurations(args);

    log.debug("ccConfigs = " + Serialize.serializeConfs(ccConfigs));

    List<String> options = RunningExampleAdapter.getRunningExampleOptions();
    Set<Set<String>> fwConfigs =
        Featurewise.getFeaturewiseConfigurations(options);
    Set<Set<String>> pwConfigs = Pairwise.getPairwiseConfigurations(options);

    Set<Set<String>> configs = Helper.mergeConfigs(ccConfigs, fwConfigs);
    configs = Helper.mergeConfigs(configs, pwConfigs);

    Set<Set<String>> randomConfigs =
        Helper.getRandomConfigs(options, 1000, configs);

    configs = Helper.mergeConfigs(randomConfigs, configs);

    System.out.println("Configurations: " + configs.size());

    // args = new String[3];
    // args[0] = "-delres";
    // args[1] = "-saveres";
    // args[2] = "-i1";

    // Executor executor = new ConfigCrusherExecutor(
    //     programName, entryPoint, classDirectory, configurations);
    // Set<PerformanceEntryStatistic> measuredPerformance =
    // executor.execute(args); measuredPerformance.size();
  }

  public static void
  removeSampledConfigurations(String name, Set<Set<String>> configurations)
      throws IOException, InterruptedException {
    // arguments
    String[] args = new String[0];

    Executor executor = new BruteForceExecutor(name);
    Set<PerformanceEntryStatistic> performanceEntries = executor.execute(args);

    for (PerformanceEntryStatistic entry : performanceEntries) {
      configurations.remove(entry.getConfiguration());
    }
  }
}