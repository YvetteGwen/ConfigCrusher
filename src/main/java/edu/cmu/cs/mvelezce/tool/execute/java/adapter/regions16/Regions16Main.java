package edu.cmu.cs.mvelezce.tool.execute.java.adapter.regions16;

import edu.cmu.cs.mvelezce.Regions16;
import edu.cmu.cs.mvelezce.tool.analysis.region.Region;
import edu.cmu.cs.mvelezce.tool.analysis.region.Regions;
import edu.cmu.cs.mvelezce.tool.execute.java.ConfigCrusherExecutor;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.BaseMain;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Main;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class Regions16Main extends BaseMain {

    public static final String REGIONS_16_MAIN = Regions16Main.class.getCanonicalName();

    public Regions16Main(String programName, String iteration, String[] args) {
        super(programName, iteration, args);
    }

    public static void main(String[] args) throws Exception {
        String programName = args[0];
        String mainClass = args[1];
        String iteration = args[2];
        String[] sleepArgs = Arrays.copyOfRange(args, 3, args.length);

        Main main = new Regions16Main(programName, iteration, sleepArgs);
        main.execute(mainClass, sleepArgs);
        main.logExecution();
    }

    @Override
    public void logExecution() throws IOException {
        Adapter adapter = new Regions16Adapter();
        Set<String> configuration = adapter.configurationAsSet(this.getArgs());

        ConfigCrusherExecutor executor = new ConfigCrusherExecutor(this.getProgramName());
        Map<String, Long> results = executor.getResults();
        executor.writeToFile(this.getIteration(), configuration, results);
    }

    @Override
    public void execute(String mainClass, String[] args) throws Exception {
        if(mainClass.contains("Regions16")) {
            Region program = new Region(Regions.PROGRAM_REGION_ID);
            Regions.enter(program.getRegionID());
            Regions16.main(args);
            Regions.exit(program.getRegionID());
        }
        else {
            throw new RuntimeException("Could not find the main class " + mainClass);
        }
    }
}
