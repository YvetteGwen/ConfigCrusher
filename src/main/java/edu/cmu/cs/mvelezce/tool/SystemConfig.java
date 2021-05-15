package edu.cmu.cs.mvelezce.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.StaticAnalysisConfig;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.nio.charset.Charset;
// import java.nio.charset.StandardCharsets;
// import java.util.HashMap;
// import java.util.Iterator;
// import java.util.Map;
// import java.util.stream.Stream;

public class SystemConfig {

  @JsonProperty("StaticAnalysis")
  public StaticAnalysisConfig staticAnalysisConfig;
}