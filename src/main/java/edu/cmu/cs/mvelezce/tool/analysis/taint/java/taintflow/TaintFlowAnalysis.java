package edu.cmu.cs.mvelezce.tool.analysis.taint.java.taintflow;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.BaseStaticAnalysis;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.StaticAnalysisConfig;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.BaseAdapter;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.apache.log4j.Logger;

public class TaintFlowAnalysis extends BaseStaticAnalysis {

  final static Logger log = Logger.getLogger(TaintFlowAnalysis.class.getName());

  // private static final String TAINTFLOW_OUTPUT_DIR =
  // BaseAdapter.USER_HOME +
  // "/Documents/Programming/Java/Projects/taintflow/src/main/resources/output";

  public TaintFlowAnalysis(String programName,
                           StaticAnalysisConfig staticAnalysisConfig) {
    super(programName, staticAnalysisConfig);
  }

  @Override
  public Map<JavaRegion, Set<Set<String>>> analyze() throws IOException {

    log.info("TaintFlowAnalysis begins.");

    List<ControlFlowResult> results = this.readTaintFlowResults();
    Map<JavaRegion, Set<Set<String>>> regionsToOptionsSet = new HashMap<>();

    for (ControlFlowResult result : results) {
      JavaRegion region = new JavaRegion(
          result.getPackageName(), result.getClassName(),
          result.getMethodSignature(), result.getBytecodeIndex());

      // TODO with the current implementation of taintflow, we only have 1 set
      // of options
      Set<Set<String>> optionsSet = new HashSet<>();
      optionsSet.add(result.getOptions());

      regionsToOptionsSet.put(region, optionsSet);
    }

    return regionsToOptionsSet;
  }

  private List<ControlFlowResult> readTaintFlowResults() throws IOException {
    ObjectMapper mapper = new ObjectMapper();

    /*TaintFlowAnalysis.TAINTFLOW_OUTPUT_DIR*/
    File inputFile =
        new File(this.staticAnalysisConfig.getTaintAnalysisOutputDir() + "/" +
                 this.getProgramName() + "/" + this.getProgramName() + ".json");

    log.debug("inputFile = " + inputFile);

    List<ControlFlowResult> results = mapper.readValue(
        inputFile, new TypeReference<List<ControlFlowResult>>() {});

    return results;
  }
}
