package edu.cmu.cs.mvelezce.tool.analysis.taint.java;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StaticAnalysisConfig {
  @JsonProperty("TaintAnalysisOutputDir") private String taintAnalysisOutputDir;

  public String getTaintAnalysisOutputDir() { return taintAnalysisOutputDir; }
}