package edu.cmu.cs.mvelezce.evaluation;

import edu.cmu.cs.mvelezce.evaluation.approaches.bruteforce.execute.BruteForceExecutor;
import edu.cmu.cs.mvelezce.evaluation.approaches.featurewise.Featurewise;
import edu.cmu.cs.mvelezce.evaluation.approaches.pairwise.Pairwise;
import edu.cmu.cs.mvelezce.tool.Helper;
import edu.cmu.cs.mvelezce.tool.compression.Compression;
import edu.cmu.cs.mvelezce.tool.compression.simple.SimpleCompression;
import edu.cmu.cs.mvelezce.tool.execute.java.ConfigCrusherExecutor;
import edu.cmu.cs.mvelezce.tool.execute.java.Executor;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.BaseAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.density.DensityAdapter;
import edu.cmu.cs.mvelezce.tool.performance.entry.PerformanceEntryStatistic;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Option.Builder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * <h1> Run Evaluation </h1>
 */
public class Run {

  private static Options buildOptions() {
    Options options = new Options();
    options.addOption("p", "program", true, "program name");
    options.addOption("d", "dir", true, "class directory");
    options.addOption("e", "entry", true, "entry point");
    return options;
  }
  /**
   *
   * @param args
   * @throws IOException
   * @throws InterruptedException
   */
  public static void main(String[] args)
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
      CommandLine commandLine = parser.parse(options, args);
      programName = commandLine.getOptionValue("p");
      classDirectory = commandLine.getOptionValue("d");
      entryPoint = commandLine.getOptionValue("e");
    } catch (ParseException exception) {
      System.out.print("Parse error: ");
      System.out.println(exception.getMessage());
      throw exception;
    }

    // Program arguments
    args = new String[0];

    Compression compression = new SimpleCompression(programName);
    Set<Set<String>> ccConfigs = compression.compressConfigurations(args);

    List<String> options = DensityAdapter.getDensityOptions();
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

  /**
   *
   * @param name
   * @param configurations
   * @throws IOException
   * @throws InterruptedException
   */
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