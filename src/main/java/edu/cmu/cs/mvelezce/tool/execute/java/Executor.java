package edu.cmu.cs.mvelezce.tool.execute.java;

import edu.cmu.cs.mvelezce.tool.analysis.region.Region;
import edu.cmu.cs.mvelezce.tool.performancemodel.PerformanceEntry2;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface Executor {

    public Set<PerformanceEntry2> execute(int iteration) throws IOException, InterruptedException;

    public Set<PerformanceEntry2> execute(String[] args) throws IOException, InterruptedException;

    public Set<PerformanceEntry2> execute() throws IOException, InterruptedException;

    public void writeToFile(String iteration, Set<String> configuration, List<Region> executedRegions) throws IOException;

    public PerformanceEntry2 readFromFile(File file) throws IOException;

    public Set<String> getOptions(File file) throws IOException;
}
