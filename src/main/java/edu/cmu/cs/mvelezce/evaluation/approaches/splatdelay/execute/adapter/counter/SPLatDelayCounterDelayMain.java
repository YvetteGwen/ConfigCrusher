package edu.cmu.cs.mvelezce.evaluation.approaches.splatdelay.execute.adapter.counter;

import edu.cmu.cs.mvelezce.evaluation.approaches.splatdelay.execute.adapter.SPLatDelayMain;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.Adapter;
import edu.cmu.cs.mvelezce.tool.execute.java.adapter.colorCounter.ColorCounterAdapter;
import java.util.*;

public class SPLatDelayCounterDelayMain extends SPLatDelayMain {

  public SPLatDelayCounterDelayMain(String programName) { super(programName); }

  @Override
  public Set<Set<String>> getSPLatConfigurations() {
    if (!this.getProgramName().contains("Counter")) {
      throw new RuntimeException("Could not find the main class " +
                                 this.getProgramName());
    }

    List<String> options = ColorCounterAdapter.getColorCounterOptions();
    Map<Set<String>, Set<Set<String>>> configsToCovered = new HashMap<>();
    Set<Set<String>> splatConfigurations = new HashSet<>();
    Adapter adapter = new ColorCounterAdapter();
    Stack<String> stack = new Stack<>();

    Set<String> configuration = new HashSet<>();
    splatConfigurations.add(configuration);
    String[] args = adapter.configurationAsMainArguments(configuration);

    //        Run.splat(args, stack);
    //
    //        Set<String> optionsNotInStack = this.getOptionsNotInStack(stack,
    //        options); Set<Set<String>> coveredConfigs =
    //        this.mapConfigs(configuration, optionsNotInStack);
    //        configsToCovered.put(configuration, coveredConfigs);
    //
    //        while(!stack.isEmpty()) {
    //            String option = stack.peek();
    //
    //            if(configuration.contains(option)) {
    //                configuration = new HashSet<>(configuration);
    //                configuration.remove(option);
    //                stack.pop();
    //            }
    //            else {
    //                configuration = new HashSet<>(configuration);
    //                configuration.add(option);
    //                splatConfigurations.add(configuration);
    //                args =
    //                adapter.configurationAsMainArguments(configuration);
    //                Run.splat(args, stack);
    //
    //                optionsNotInStack = this.getOptionsNotInStack(stack,
    //                options); coveredConfigs = this.mapConfigs(configuration,
    //                optionsNotInStack); configsToCovered.put(configuration,
    //                coveredConfigs);
    //            }
    //        }
    //
    //        this.setConfigsToCovered(configsToCovered);

    return splatConfigurations;
  }
}
