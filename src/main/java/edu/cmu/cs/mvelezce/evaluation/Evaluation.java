package edu.cmu.cs.mvelezce.evaluation;

import edu.cmu.cs.mvelezce.evaluation.approaches.splat.Coverage;
import edu.cmu.cs.mvelezce.tool.Options;
import edu.cmu.cs.mvelezce.tool.performance.entry.PerformanceEntryStatistic;
import edu.cmu.cs.mvelezce.tool.performance.model.PerformanceModel;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Evaluation {

    public static final String DIRECTORY = Options.DIRECTORY + "/evaluation/programs/java";
    public static final String COMPARISON_DIR = "/comparison";
    public static final String FULL_DIR = "/full";
    public static final String DOT_CSV = ".csv";

    // TODO use a class or enum>
    public static final String CONFIG_CRUSHER = "config_crusher";
    public static final String BRUTE_FORCE = "brute_force";
    public static final String FEATURE_WISE = "feature_wise";
    public static final String PAIR_WISE = "pair_wise";
    public static final String SPLAT = "splat";

    private String programName;

    public Evaluation(String programName) {
        this.programName = programName;
    }

    public double getTotalSamplingTime(String approach, Set<Set<String>> configurations) throws IOException {
        double time = 0.0;

        String fileString = Evaluation.DIRECTORY + "/" + this.programName + Evaluation.FULL_DIR + "/"
                + Evaluation.BRUTE_FORCE + Evaluation.DOT_CSV;
        File file = new File(fileString);

        List<String> lines = this.parseFullFile(file);

        for(String line : lines) {
            if(!line.startsWith("true")) {
                continue;
            }

            String[] entries = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            String configString = entries[1];
            configString = Evaluation.removeSpecialCharsFromConfig(configString);
            Set<String> config = Evaluation.buildConfig(configString);

            if(!configurations.contains(config)) {
                continue;
            }

            Double exec = Double.valueOf(entries[2]);
            time += exec;
        }

        return time;
    }

    private static Set<String> buildConfig(String configString) {
        Set<String> config = new HashSet<>();

        String[] options = configString.split(",");

        for(int i = 0; i < options.length; i++) {
            String option = options[i].trim();

            if(!option.isEmpty()) {
                config.add(option);
            }
        }

        return config;
    }

    private static String removeSpecialCharsFromConfig(String s) {
        s = s.replaceAll("\"", "");
        s = s.replaceAll("\\[", "");
        s = s.replaceAll("]", "");

        return s;
    }

    public double getTotalSamplingTime(String approach) throws IOException {
        double time = 0.0;

        String fileString = Evaluation.DIRECTORY + "/" + this.programName + Evaluation.FULL_DIR + "/"
                + approach + Evaluation.DOT_CSV;
        File file = new File(fileString);

        List<String> lines = this.parseFullFile(file);

        for(String line : lines) {
            if(!line.startsWith("true")) {
                continue;
            }

            String[] entries = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            Double exec = Double.valueOf(entries[2]);
            time += exec;
        }

        return time;
    }

    private List<String> parseFullFile(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = "";

        List<String> lines = new ArrayList<>();

        while((line = reader.readLine()) != null) {
            if(line.isEmpty()) {
                continue;
            }

            lines.add(line.trim());
        }

        return lines;
    }

    public void writeConfigurationToPerformance(String approach, Set<PerformanceEntryStatistic> performanceEntries) throws IOException {
        String outputDir = Evaluation.DIRECTORY + "/" + this.programName + Evaluation.FULL_DIR + "/"
                + approach + Evaluation.DOT_CSV;
        File outputFile = new File(outputDir);

        if(outputFile.exists()) {
            FileUtils.forceDelete(outputFile);
        }

        StringBuilder result = new StringBuilder();
        result.append("measured,configuration,performance,std,minci,maxci");
        result.append("\n");

        DecimalFormat decimalFormat = new DecimalFormat("#.###");

        for(PerformanceEntryStatistic performanceEntry : performanceEntries) {
            if(performanceEntry.getRegionsToProcessedPerformanceHumanReadable().size() != 1) {
                throw new RuntimeException("This method can only handle approaches that measure 1 region" +
                        " (e.g. Brute force)");
            }

            result.append("true");
            result.append(",");
            result.append('"');
            result.append(performanceEntry.getConfiguration());
            result.append('"');
            result.append(",");
            double performance = performanceEntry.getRegionsToProcessedPerformanceHumanReadable().values().iterator().next();
            result.append(decimalFormat.format(performance));
            result.append(",");
            double std = performanceEntry.getRegionsToProcessedStdHumanReadable().values().iterator().next();
            result.append(decimalFormat.format(std));
            result.append(",");
            List<Double> ci = performanceEntry.getRegionsToProcessedCIHumanReadable().values().iterator().next();
            double minCI = ci.get(0);
            double maxCI = ci.get(1);
            result.append(decimalFormat.format(minCI));
            result.append(",");
            result.append(decimalFormat.format(maxCI));
            result.append("\n");
        }

        outputFile.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(outputFile);
        writer.write(result.toString());
        writer.flush();
        writer.close();
    }

    public void writeConfigurationToPerformance(String approach, List<Coverage> coverageList, Set<PerformanceEntryStatistic> performanceEntryStats) throws IOException {
        String outputDir = Evaluation.DIRECTORY + "/" + this.programName + Evaluation.FULL_DIR + "/"
                + approach + Evaluation.DOT_CSV;
        File outputFile = new File(outputDir);

        if(outputFile.exists()) {
            FileUtils.forceDelete(outputFile);
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        StringBuilder result = new StringBuilder();
        result.append("measured,configuration,performance,std");
        result.append("\n");

        for(Coverage coverage : coverageList) {
            Set<String> config = coverage.getConfig();

            for(PerformanceEntryStatistic entry : performanceEntryStats) {
                if(!entry.getConfiguration().equals(config)) {
                    continue;
                }

                result.append(true);
                result.append(",");
                result.append('"');
                result.append(config);
                result.append('"');
                result.append(",");
                double perf = entry.getRegionsToProcessedPerformanceHumanReadable().values().iterator().next();
                result.append(decimalFormat.format(perf));
                result.append(",");
                double std = entry.getRegionsToProcessedStdHumanReadable().values().iterator().next();
                result.append(decimalFormat.format(std));
                result.append("\n");

                Set<Set<String>> covereds = coverage.getCovered();

                for(Set<String> covered : covereds) {
                    if(covered.equals(config)) {
                        continue;
                    }

                    result.append(false);
                    result.append(",");
                    result.append('"');
                    result.append(covered);
                    result.append('"');
                    result.append(",");
                    result.append(decimalFormat.format(perf));
                    result.append(",");
                    result.append(decimalFormat.format(std));
                    result.append("\n");
                }
            }
        }

        outputFile.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(outputFile);
        writer.write(result.toString());
        writer.flush();
        writer.close();
    }

    public void writeConfigurationToPerformance(String approach, PerformanceModel
            performanceModel, Set<PerformanceEntryStatistic> performanceEntryStats, Set<Set<String>> configurations) throws
            IOException {
        String outputDir = Evaluation.DIRECTORY + "/" + this.programName + Evaluation.FULL_DIR + "/"
                + approach + Evaluation.DOT_CSV;
        File outputFile = new File(outputDir);

        if(outputFile.exists()) {
            FileUtils.forceDelete(outputFile);
        }

        StringBuilder result = new StringBuilder();
        result.append("measured,configuration,performance,std");
        result.append("\n");

        for(Set<String> configuration : configurations) {
            PerformanceEntryStatistic performanceStat = null;

            for(PerformanceEntryStatistic performanceEntryStatistic : performanceEntryStats) {
                if(performanceEntryStatistic.getConfiguration().equals(configuration)) {
                    performanceStat = performanceEntryStatistic;
                    break;
                }
            }

            if(performanceStat != null) {
                result.append(true);
            } else {
                result.append(false);
            }

            result.append(",");
            result.append('"');
            result.append(configuration);
            result.append('"');
            result.append(",");
            result.append(performanceModel.evaluate(configuration));
            result.append(",");
            result.append(performanceModel.evaluateStd(configuration));
            result.append("\n");
        }

        outputFile.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(outputFile);
        writer.write(result.toString());
        writer.flush();
        writer.close();
    }

    public void compareApproaches(String approach1, String approach2) throws IOException {
        String outputDir = Evaluation.DIRECTORY + "/" + this.programName + "/" + Evaluation.FULL_DIR + "/"
                + approach1 + Evaluation.DOT_CSV;
        File outputFile1 = new File(outputDir);

        if(!outputFile1.exists()) {
            throw new IOException("Could not find a full file for " + approach1);
        }

        outputDir = Evaluation.DIRECTORY + "/" + this.programName + "/" + Evaluation.FULL_DIR + "/"
                + approach2 + Evaluation.DOT_CSV;
        File outputFile2 = new File(outputDir);

        if(!outputFile2.exists()) {
            throw new IOException("Could not find a full file for " + approach2);
        }

        outputDir = Evaluation.DIRECTORY + "/" + this.programName + "/" + Evaluation.COMPARISON_DIR + "/"
                + approach1 + "_" + approach2 + Evaluation.DOT_CSV;
        File outputFile = new File(outputDir);

        if(outputFile.exists()) {
            FileUtils.forceDelete(outputFile);
        }

        FileInputStream fstream = new FileInputStream(outputFile1);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        int approach1LineCount = 0;

        while((strLine = br.readLine()) != null) {
            if(!strLine.isEmpty()) {
                approach1LineCount++;
            }
        }

        in.close();

        fstream = new FileInputStream(outputFile2);
        in = new DataInputStream(fstream);
        br = new BufferedReader(new InputStreamReader(in));
        int approach2LineCount = 0;

        while((strLine = br.readLine()) != null) {
            if(!strLine.isEmpty()) {
                approach2LineCount++;
            }
        }

        in.close();

        if(approach1LineCount != approach2LineCount) {
            throw new RuntimeException("The approach files do not have the same length");
        }

        Set<Set<String>> configurations = new HashSet<>();

        fstream = new FileInputStream(outputFile1);
        in = new DataInputStream(fstream);
        br = new BufferedReader(new InputStreamReader(in));

        while((strLine = br.readLine()) != null) {
            if(!strLine.isEmpty()) {
                break;
            }
        }

        while((strLine = br.readLine()) != null) {
            Set<String> options = new HashSet<>();
            int startOptionIndex = strLine.indexOf("[") + 1;
            int endOptionIndex = strLine.lastIndexOf("]");

            String optionsString = strLine.substring(startOptionIndex, endOptionIndex);
            String[] arrayOptions = optionsString.split(",");

            for(int i = 0; i < arrayOptions.length; i++) {
                options.add(arrayOptions[i].trim());
            }

            configurations.add(options);
        }

        in.close();

        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        StringBuilder result = new StringBuilder();
        double se = 0;
        double ape = 0;
        double testCount = 0;
        result.append("measured,configuration," + approach1 + "," + approach1 + "_std," + approach2 + "," + approach2
                + "_std,absolute error,relative error,squared error");
        result.append("\n");

        for(Set<String> configuration : configurations) {
            fstream = new FileInputStream(outputFile1);
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            br.readLine();

            while((strLine = br.readLine()) != null) {
                Set<String> options = new HashSet<>();
                int startOptionIndex = strLine.indexOf("[") + 1;
                int endOptionIndex = strLine.lastIndexOf("]");

                String optionsString = strLine.substring(startOptionIndex, endOptionIndex);
                String[] arrayOptions = optionsString.split(",");

                for(int i = 0; i < arrayOptions.length; i++) {
                    options.add(arrayOptions[i].trim());
                }

                if(configuration.equals(options)) {
                    break;
                }
            }

            in.close();

            String measuredString = strLine.substring(0, strLine.indexOf(","));
            boolean measured = Boolean.valueOf(measuredString);

            result.append('"');
            result.append(measuredString);
            result.append('"');
            result.append(",");
            result.append('"');
            result.append(configuration);
            result.append('"');
            result.append(",");

            String[] entries = strLine.split(",");
            double performance1 = Double.valueOf(entries[entries.length - 2]);
            performance1 = Math.max(0, performance1);
            result.append(performance1);
            result.append(",");
            result.append(Double.valueOf(entries[entries.length - 1]));
            result.append(",");

            fstream = new FileInputStream(outputFile2);
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            br.readLine();

            while((strLine = br.readLine()) != null) {
                Set<String> options = new HashSet<>();
                int startOptionIndex = strLine.indexOf("[") + 1;
                int endOptionIndex = strLine.lastIndexOf("]");

                String optionsString = strLine.substring(startOptionIndex, endOptionIndex);
                String[] arrayOptions = optionsString.split(",");

                for(int i = 0; i < arrayOptions.length; i++) {
                    options.add(arrayOptions[i].trim());
                }

                if(configuration.equals(options)) {
                    break;
                }
            }

            in.close();

            entries = strLine.split(",");
            double performance2 = Double.valueOf(entries[entries.length - 2]);
            result.append(performance2);
            result.append(",");
            result.append(Double.valueOf(entries[entries.length - 1]));
            result.append(",");

            double absoluteError = Math.abs(performance2 - performance1);
            double relativeError = 0.0;

            if(performance2 != 0) {
                relativeError = absoluteError / performance2;
            }

            double squaredError = Math.pow(performance2 - performance1, 2);

            result.append(decimalFormat.format(absoluteError));
            result.append(",");
            result.append(decimalFormat.format(relativeError));
            result.append(",");
            result.append(decimalFormat.format(squaredError));
            result.append("\n");

            if(!measured && performance2 >= 1.0) {
                se += squaredError;
                ape += relativeError;
                testCount++;
            }
        }

        result.append("\n");
        result.append("MSE: ");
        double mse = se / testCount;
        result.append(decimalFormat.format(mse));
        result.append("\n");
        result.append("RMSE: ");
        result.append(decimalFormat.format(Math.sqrt(mse)));
        result.append("\n");
        result.append("MAPE: ");
        double mape = ape / testCount * 100;
        result.append(decimalFormat.format(mape));
        result.append("\n");

        outputFile.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(outputFile);
        writer.write(result.toString());
        writer.flush();
        writer.close();
    }

    public String getProgramName() {
        return this.programName;
    }
}
