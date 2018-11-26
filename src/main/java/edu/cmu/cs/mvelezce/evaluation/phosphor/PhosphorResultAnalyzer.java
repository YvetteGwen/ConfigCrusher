package edu.cmu.cs.mvelezce.evaluation.phosphor;

import com.bpodgursky.jbool_expressions.Expression;
import edu.cmu.cs.mvelezce.tool.Helper;
import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.DynamicAnalysis;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.BFPhosphorAnalysis;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.ExecVarCtx;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.SinkData;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.groundtruth.BranchCoverageAnalysis;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.groundtruth.Context;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.groundtruth.DecisionBranchCountTable;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.groundtruth.DecisionInfo;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.groundtruth.DynamicAnalysisSpecification;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PhosphorResultAnalyzer {

//  private static final String OUTPUT_DIR = Options.DIRECTORY + "/evaluation/phosphor/programs";

  private final String programName;
  private final List<String> options;

  PhosphorResultAnalyzer(String programName, List<String> options) {
    this.programName = programName;
    this.options = options;
  }

  void analyze() throws IOException, InterruptedException {
    Map<JavaRegion, DecisionInfo> specRes = this.readSpecResults();
    Map<JavaRegion, SinkData> phosphorRes = this.readPhosphorResults();

    Set<JavaRegion> specRegionsAnalyzed = this.compareSpecToPhosphor(specRes, phosphorRes);
    this.checkMissingBFRegions(phosphorRes.keySet(), specRegionsAnalyzed);
  }

  private void checkMissingBFRegions(Set<JavaRegion> phosphorRegions,
      Set<JavaRegion> specRegionsAnalyzed) {
    for (JavaRegion phosphorRegion : phosphorRegions) {
      if (!specRegionsAnalyzed.contains(phosphorRegion)) {
        System.out.println(phosphorRegion);
        System.out.println("Region not found in spec");
        throw new UnsupportedOperationException("Add code to show the information from the spec");
      }

    }
  }

  private Set<JavaRegion> compareSpecToPhosphor(Map<JavaRegion, DecisionInfo> specRes,
      Map<JavaRegion, SinkData> phosphorRes) {
    Set<JavaRegion> specAnalyzedRegions = new HashSet<>();

    for (Map.Entry<JavaRegion, DecisionInfo> specEntry : specRes.entrySet()) {
      JavaRegion specRegion = specEntry.getKey();
      specAnalyzedRegions.add(specRegion);

      SinkData phosphorSinkData = phosphorRes.get(specRegion);
      StringBuilder errors = new StringBuilder();

      if (phosphorSinkData == null) {
        errors.append("Region not found in bf");
      }
      else {
        errors.append(this.compareRegions(specEntry.getValue(), phosphorSinkData));
      }

      if (errors.length() > 0) {
        System.out.println("Region: " + specRegion);
        System.out.println(errors);
        System.out.println();
      }
    }

    return specAnalyzedRegions;
  }

  private String compareRegions(DecisionInfo specDecisionInfo, SinkData phosphorSinkData) {
    Map<List<String>, Context> tracesToContexts = specDecisionInfo.getStackTracesToContexts();
    Map<ExecVarCtx, Set<Set<String>>> data = phosphorSinkData.getData();

    StringBuilder errors = new StringBuilder();
    String ctxErrors = this.compareCtxs(tracesToContexts.values(), data.keySet());
    errors.append(ctxErrors);
    String optionsErrors = this.compareOptions(specDecisionInfo, data);
    errors.append(optionsErrors);

    return errors.toString();
  }

  private String compareOptions(DecisionInfo specInfo,
      Map<ExecVarCtx, Set<Set<String>>> phosphorData) {
    Map<Expression<String>, Set<Set<String>>> specCtxToOptions = this.getSpecCtxToOptions(specInfo);
    Map<Expression<String>, Set<Set<String>>> bfCtxToOptions = this
        .getPhosphorCtxToOptions(phosphorData);

    StringBuilder errors = new StringBuilder();

    if (!specCtxToOptions.equals(bfCtxToOptions)) {
      errors.append(this.compareConfigs(specCtxToOptions, bfCtxToOptions));
    }

    return errors.toString();
  }

  private String compareConfigs(Map<Expression<String>, Set<Set<String>>> specCtxToOptions,
      Map<Expression<String>, Set<Set<String>>> bfCtxToOptions) {
    Set<Map<String, Boolean>> specConfigs = this.getConfigs(specCtxToOptions);
    Set<Map<String, Boolean>> phosphorConfigs = this.getConfigs(bfCtxToOptions);

    StringBuilder errors = new StringBuilder();

    if (specConfigs.equals(phosphorConfigs)) {
      return errors.toString();
    }

    errors.append("WARNING: The options are different");
    errors.append("\n");
    errors.append("Spec: ");
    errors.append(specCtxToOptions);
    errors.append("\n");
    errors.append("Bf: ");
    errors.append(bfCtxToOptions);
    errors.append("\n");

    Set<Map<String, Boolean>> missing = new HashSet<>();

    for (Map<String, Boolean> specConfig : specConfigs) {
      if (!phosphorConfigs.contains(specConfig)) {
        missing.add(specConfig);
      }
    }

    if (!missing.isEmpty()) {
      errors.append("Spec will sample the following ");
      errors.append(missing.size());
      errors.append(" configurations, but phosphor will not");
      errors.append("\n");
      errors.append(missing);
      errors.append("\n");
    }

    missing = new HashSet<>();

    for (Map<String, Boolean> phosphorConfig : phosphorConfigs) {
      if (!specConfigs.contains(phosphorConfig)) {
        missing.add(phosphorConfig);
      }
    }

    if (!missing.isEmpty()) {
      errors.append("Phosphor will sample the following ");
      errors.append(missing.size());
      errors.append(" configurations, but spec will not");
      errors.append("\n");
      errors.append(missing);
      errors.append("\n");
    }

    return errors.toString();
  }

  private Set<Map<String, Boolean>> getConfigs(
      Map<Expression<String>, Set<Set<String>>> ctxToOptions) {
    Set<Map<String, Boolean>> configs = new HashSet<>();

    for (Map.Entry<Expression<String>, Set<Set<String>>> entry : ctxToOptions.entrySet()) {
      Expression<String> expr = entry.getKey();
      Map<String, Boolean> ctxConfig = this.getCtxConfig(expr);

      Set<Map<String, Boolean>> optionsConfigs = this.getOptionsConfigs(entry.getValue());

      for (Map<String, Boolean> optionsConfig : optionsConfigs) {
        Map<String, Boolean> config = new HashMap<>();
        config.putAll(optionsConfig);
        config.putAll(ctxConfig);

        configs.add(config);
      }

    }

    return configs;
  }

  private Set<Map<String, Boolean>> getOptionsConfigs(Set<Set<String>> optionsSets) {
    Set<Map<String, Boolean>> configs = new HashSet<>();

    for (Set<String> options : optionsSets) {
      Set<Set<String>> allConfigs = Helper.getConfigurations(options);

      for (Set<String> configSet : allConfigs) {
        Map<String, Boolean> config = new HashMap<>();

        for (String option : this.options) {
          config.put(option, configSet.contains(option));
        }

        configs.add(config);
      }
    }

    return configs;
  }

  private Map<String, Boolean> getCtxConfig(Expression<String> expr) {
    Map<String, Boolean> config = new HashMap<>();
    String ctx = expr.toLexicographicString();

    if (ctx.equals("true")) {
      return config;
    }

    if (ctx.contains("|")) {
      throw new RuntimeException("The ctx " + ctx + " has OR");
    }

    String[] options = ctx.split("&");

    for (String option : options) {
      int optionLength = option.length();
      if (optionLength < 1 || optionLength > 2) {
        throw new RuntimeException("The option " + option + " does not have the expected format");
      }

      config.put(String.valueOf(option.charAt(option.length() - 1)), !option.contains("!"));
    }

    return config;
  }

  private Map<Expression<String>, Set<Set<String>>> getSpecCtxToOptions(DecisionInfo specInfo) {
    Map<Expression<String>, Set<Set<String>>> cnfCtxsToOptions = new HashMap<>();

    Map<List<String>, Context> tracesToCtxs = specInfo.getStackTracesToContexts();
    Map<List<String>, DecisionBranchCountTable> tracesToTables = specInfo
        .getStackTracesToDecisionBranchTables();

    for (Context ctx : tracesToCtxs.values()) {
      Expression<String> cnf = DecisionInfo.toCNF(ctx, this.options);
      Set<Set<String>> optionsSet = new HashSet<>();
      cnfCtxsToOptions.put(cnf, optionsSet);
    }

    // TODO test that there is a different trace, but the same context, which then leads to two entries with the same context
    for (Map.Entry<List<String>, Context> entry : tracesToCtxs.entrySet()) {
      Context ctx = entry.getValue();
      Expression<String> cnf = DecisionInfo.toCNF(ctx, this.options);
      Set<Set<String>> optionsSet = cnfCtxsToOptions.get(cnf);

      DecisionBranchCountTable table = tracesToTables.get(entry.getKey());
      Set<String> options = DynamicAnalysisSpecification.getMinimalSetOfOptions(table);
      optionsSet.add(options);
    }

    return cnfCtxsToOptions;
  }

  private Map<Expression<String>, Set<Set<String>>> getPhosphorCtxToOptions(
      Map<ExecVarCtx, Set<Set<String>>> phosphorData) {
    Map<Expression<String>, Set<Set<String>>> cnfCtxsToOptions = new HashMap<>();

    for (Map.Entry<ExecVarCtx, Set<Set<String>>> entry : phosphorData.entrySet()) {
      Expression<String> cnf = entry.getKey().toCNF();
      cnfCtxsToOptions.put(cnf, entry.getValue());
    }

    return cnfCtxsToOptions;
  }

  private String compareCtxs(Collection<Context> specCtxs, Set<ExecVarCtx> phosphorCtxs) {
    Set<Expression<String>> specCNFCtxs = this.getSpecCNFCtxs(specCtxs);
    Set<Expression<String>> bfCNFCtxs = this.getBFCNFCtxs(phosphorCtxs);

    StringBuilder errors = new StringBuilder();

    if (!specCNFCtxs.equals(bfCNFCtxs)) {
      errors.append("WARNING: contexts are different");
      errors.append("\n");
      errors.append("Spec: ");
      errors.append(specCNFCtxs);
      errors.append("\n");
      errors.append("BF: ");
      errors.append(bfCNFCtxs);
      errors.append("\n");
    }

    return errors.toString();
  }

  private Set<Expression<String>> getBFCNFCtxs(Set<ExecVarCtx> bfCtxs) {
    Set<Expression<String>> bfCNFCtxs = new HashSet<>();

    for (ExecVarCtx bfCtx : bfCtxs) {
      Expression<String> bfCNF = bfCtx.toCNF();
      bfCNFCtxs.add(bfCNF);
    }

    return bfCNFCtxs;
  }


  private Set<Expression<String>> getSpecCNFCtxs(Collection<Context> specCtxs) {
    Set<Expression<String>> specCNFCtxs = new HashSet<>();

    for (Context specCtx : specCtxs) {
      Expression<String> specCNF = DecisionInfo.toCNF(specCtx, this.options);
      specCNFCtxs.add(specCNF);
    }

    return specCNFCtxs;
  }

  private Map<JavaRegion, DecisionInfo> readSpecResults()
      throws IOException, InterruptedException {
    DynamicAnalysis<DecisionInfo> analysis = new BranchCoverageAnalysis(this.programName);
    String[] args = new String[0];
    return analysis.analyze(args);
  }

  private Map<JavaRegion, SinkData> readPhosphorResults()
      throws IOException, InterruptedException {
    DynamicAnalysis<SinkData> analysis = new BFPhosphorAnalysis(this.programName);
    String[] args = new String[0];
    return analysis.analyze(args);
  }

}
