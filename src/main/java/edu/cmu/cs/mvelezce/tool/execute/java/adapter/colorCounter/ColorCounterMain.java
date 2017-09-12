package edu.cmu.cs.mvelezce.tool.execute.java.adapter.colorCounter;

import com.googlecode.pngtastic.Run;
import edu.cmu.cs.mvelezce.Example;
import edu.cmu.cs.mvelezce.tool.analysis.region.Region;
import edu.cmu.cs.mvelezce.tool.analysis.region.Regions;
import edu.cmu.cs.mvelezce.tool.execute.java.DefaultExecutor;
import edu.cmu.cs.mvelezce.tool.execute.java.Executor;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.BasetMain;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Main;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

public class ColorCounterMain extends BasetMain {

    public static final String COLORCOUNTER_MAIN = ColorCounterMain.class.getCanonicalName();

    public ColorCounterMain(String programName, String iteration, String[] args) {
        super(programName, iteration, args);
    }

    public static void main(String[] args) throws Exception {
        String programName = args[0];
        String mainClass = args[1];
        String iteration = args[2];
        String[] sleepArgs = Arrays.copyOfRange(args, 3, args.length);

        Main main = new ColorCounterMain(programName, iteration, sleepArgs);
        main.execute(mainClass, sleepArgs);
        main.logExecution();
    }

    @Override
    public void logExecution() throws IOException {
        Adapter adapter = new ColorCounterAdapter();
        Set<String> configuration = adapter.configurationAsSet(this.getArgs());

        Executor executor = new DefaultExecutor(this.getProgramName());
        executor.writeToFile(this.getIteration(), configuration, Regions.getExecutedRegionsTrace());
    }

    @Override
    public void execute(String mainClass, String[] args) throws Exception {
        if(mainClass.contains("Run")) {
            Region program = new Region(Regions.PROGRAM_REGION_ID);
            Regions.enter(program.getRegionID());
            Run.main(args);
            Regions.exit(program.getRegionID());
        }
        else {
            throw new RuntimeException("Could not find the main class " + mainClass);
        }
    }
}
