package edu.cmu.cs.mvelezce.tool.analysis.taint.java.groundtruth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.cs.mvelezce.tool.Options;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;

/**
 * Analyses what value each subtrace has.
 */
public class SubtracesValueAnalysis {

  private final String programName;
  private final Map<Set<String>, List<String>> configsToTraces;
  private final List<String> alignedTrace;

  public SubtracesValueAnalysis(String programName) {
    this.programName = programName;
    this.configsToTraces = new HashMap<>();
    this.alignedTrace = new ArrayList<>();
  }

  public SubtracesValueAnalysis(String programName, Map<Set<String>, List<String>> configsToTraces,
      List<String> alignedTrace) {
    this.programName = programName;
    this.configsToTraces = configsToTraces;
    this.alignedTrace = alignedTrace;
  }

  private Set<ConfigLabelValueInfo> analyze() {
    Set<ConfigLabelValueInfo> configLabelValues = new HashSet<>();

    for (Map.Entry<Set<String>, List<String>> entry : this.configsToTraces.entrySet()) {
      Set<String> config = entry.getKey();
      List<String> trace = entry.getValue();

      Map<String, String> labelsToValues = this.getLabelsToValues(trace);

      ConfigLabelValueInfo configLabelValue = new ConfigLabelValueInfo(config, labelsToValues);
      configLabelValues.add(configLabelValue);
    }

    return configLabelValues;
  }

  private Map<String, String> getLabelsToValues(List<String> trace) {
    Map<String, String> labelsToValues = new HashMap<>();

    for (String label : this.alignedTrace) {
      labelsToValues.put(label, "");
    }

    Map<String, Integer> traceElementsToIndexes = this.getTraceElementsToIndexes(trace);

    for (String label : this.alignedTrace) {
      int index = traceElementsToIndexes.getOrDefault(label, -1);

      if (index < 0 || index == (trace.size() - 1)) {
        continue;
      }

      String element = trace.get(index + 1);

      if (!element.startsWith(SubtracesLogger.LABEL)) {
        labelsToValues.put(label, element);
      }

    }

    return labelsToValues;

  }

  private Map<String, Integer> getTraceElementsToIndexes(List<String> trace) {
    Map<String, Integer> traceElementsToIndexes = new HashMap<>();

    for (int i = 0; i < trace.size(); i++) {
      traceElementsToIndexes.put(trace.get(i), i);
    }

    return traceElementsToIndexes;
  }

  Set<ConfigLabelValueInfo> analyze(String[] args) throws IOException {
    Options.getCommandLine(args);

    String outputFile = this.outputDir();
    File file = new File(outputFile);

    Options.checkIfDeleteResult(file);

    if (file.exists()) {
      Collection<File> files = FileUtils.listFiles(file, null, true);

      if (files.size() != 1) {
        throw new RuntimeException(
            "We expected to find 1 file in the directory, but that is not the case "
                + outputFile);
      }

      return this.readFromFile(files.iterator().next());
    }

    Set<ConfigLabelValueInfo> configLabelValues = this.analyze();

    if (Options.checkIfSave()) {
      this.writeToFile(configLabelValues);
    }

    return configLabelValues;
  }

  private Set<ConfigLabelValueInfo> readFromFile(File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();

    return mapper.readValue(file, new TypeReference<Set<ConfigLabelValueInfo>>() {
    });
  }

  private void writeToFile(Set<ConfigLabelValueInfo> configLabelValues) throws IOException {
    String outputFile = this.outputDir() + "/" + this.programName + Options.DOT_JSON;
    File file = new File(outputFile);
    file.getParentFile().mkdirs();

    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(file, configLabelValues);
  }

  private String outputDir() {
    return Options.DIRECTORY + "/analysis/spec/subtracesvalues/java/programs/" + this.programName;
  }

}
