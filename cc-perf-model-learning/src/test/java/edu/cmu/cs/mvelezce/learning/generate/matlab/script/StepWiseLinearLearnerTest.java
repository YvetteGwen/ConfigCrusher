package edu.cmu.cs.mvelezce.learning.generate.matlab.script;

import edu.cmu.cs.mvelezce.adapters.measureDiskOrderedScan.BaseMeasureDiskOrderedScanAdapter;
import edu.cmu.cs.mvelezce.approaches.sampling.SamplingApproach;
import edu.cmu.cs.mvelezce.approaches.sampling.fw.FeatureWiseSampling;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class StepWiseLinearLearnerTest {

  @Test
  public void berkeleyDB_FW() throws IOException {
    String programName = BaseMeasureDiskOrderedScanAdapter.PROGRAM_NAME;
    List<String> options = BaseMeasureDiskOrderedScanAdapter.getListOfOptions();
    SamplingApproach samplingApproach = FeatureWiseSampling.getInstance();

    StepWiseLinearLearner learner =
        new StepWiseLinearLearner(programName, options, samplingApproach);
    learner.generateLearningScript();
  }
}
