package edu.cmu.cs.mvelezce.tool.execute.java;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.cs.mvelezce.tool.Options;
import edu.cmu.cs.mvelezce.tool.analysis.region.Region;
import edu.cmu.cs.mvelezce.tool.analysis.region.Regions;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.elevator.ElevatorAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.gpl.GPLAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.pngtastic.PngtasticAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.sleep.SleepAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.zipme.ZipmeAdapter;
import edu.cmu.cs.mvelezce.tool.execute.java.serialize.Execution;
import edu.cmu.cs.mvelezce.tool.performance.PerformanceEntry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by miguelvelez on 4/30/17.
 */
public class DefaultExecutor extends BaseExecutor {

    public DefaultExecutor() {
        this(null, null, null, null);
    }

    public DefaultExecutor(String programName, String mainClass, String dir, Set<Set<String>> configurations) {
        this(programName, mainClass, dir, configurations, 1);
    }

    public DefaultExecutor(String programName, String mainClass, String dir, Set<Set<String>> configurations, int repetitions) {
        super(programName, mainClass, dir, configurations, repetitions);
    }

    @Override
    public Set<PerformanceEntry> execute(String programName) throws IOException {
        for(Set<String> configuration : this.getConfigurations()) {
            // TODO factory pattern or switch statement to create the right adapter

            Adapter adapter = null;

            if(programName.contains("elevator")) {
                adapter = new ElevatorAdapter(programName, this.getMainClass(), this.getDir());
            }
            else if(programName.contains("gpl")) {
                adapter = new GPLAdapter(programName, this.getMainClass(), this.getDir());
            }
            else if(programName.contains("sleep")) {
                adapter = new SleepAdapter(programName, this.getMainClass(), this.getDir());
            }
            else if(programName.contains("zipme")) {
                adapter = new ZipmeAdapter(programName, this.getMainClass(), this.getDir());
            }
            else if(programName.contains("pngtastic")) {
                adapter = new PngtasticAdapter(programName, this.getMainClass(), this.getDir());
            }
            else {
                throw new RuntimeException("Could not create an adapter for " + programName);
            }

            adapter.execute(configuration);

            if(!Regions.getExecutingRegions().isEmpty()) {
                throw new RuntimeException("There program finished executing, but there are methods in the execution stack that did not finish");
            }
        }

        String outputFile = BaseExecutor.DIRECTORY + "/" + programName + Options.DOT_JSON;
        File file = new File(outputFile);

        return this.readFromFile(file);
    }

    @Override
    public void writeToFile(String programIteration, Set<String> configuration, List<Region> executedRegions) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String outputFile = BaseExecutor.DIRECTORY + "/" + programIteration + Options.DOT_JSON;
        File file = new File(outputFile);

        List<Execution> executions = new ArrayList<>();

        if(file.exists()) {
            executions = mapper.readValue(file, new TypeReference<List<Execution>>() {
            });
        }

        Execution execution = new Execution(configuration, executedRegions);
        executions.add(execution);

        mapper.writeValue(file, executions);
    }

}
