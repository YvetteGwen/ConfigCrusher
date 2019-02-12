package edu.cmu.cs.mvelezce.tool.compression.dynamic.phosphor;

import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.BFPhosphorAnalysis;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.PhosphorAnalysis;
import edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.phosphor.SinkData;
import edu.cmu.cs.mvelezce.tool.compression.Compression;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.dynamicrunningexample.DynamicRunningExampleAdapter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class PhosphorCompressionTest {

  @Test
  public void RunningExample() throws IOException, InterruptedException {
    String programName = DynamicRunningExampleAdapter.PROGRAM_NAME;
    Set<String> options = new HashSet<>(DynamicRunningExampleAdapter.getListOfOptions());

    String[] args = new String[0];

    PhosphorAnalysis analysis = new BFPhosphorAnalysis(programName);
    Map<JavaRegion, SinkData> sinkData = analysis.analyze(args);
    Collection<SinkData> constraints = sinkData.values();

    args = new String[2];
    args[0] = "-delres";
    args[1] = "-saveres";

    Compression compressor = new PhosphorCompression(programName, options, constraints);
    Set<Set<String>> write = compressor.compressConfigurations(args);

    args = new String[0];

    Set<Set<String>> read = compressor.compressConfigurations(args);

    Assert.assertEquals(write, read);
  }
}