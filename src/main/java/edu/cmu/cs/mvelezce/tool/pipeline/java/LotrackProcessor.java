package edu.cmu.cs.mvelezce.tool.pipeline.java;

import edu.cmu.cs.mvelezce.mongo.connector.scaladriver.ScalaMongoDriverConnector;
import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;

import java.util.*;

/**
 * Created by mvelezce on 4/5/17.
 */
// TODO how to create regions if there are execptions that the bytecode goes back to -1?
// TODO this is not correct
public class LotrackProcessor {
    public static final String PACKAGE = "Package";
    public static final String CLASS = "Class";
    public static final String METHOD = "Method";
    public static final String JAVA_LINE_NO = "JavaLineNo";
    public static final String JIMPLE_LINE_NO = "JimpleLineNo";
    public static final String CONSTRAINT = "Constraint";
    public static final String CONSTRAINT_PRETTY = "ConstraintPretty";
    public static final String BYTECODE_INDEXES = "bytecodeIndexes";
    public static final String METHOD_BYTECODE_SIGNATURE_JOANA_STYLE = "methodBytecodeSignatureJoanaStyle";
    public static final String USED_TERMS = "usedTerms";

    public static final String LOTRACK_UNKNOWN_CONSTRAINT_SYMBOL = "_";

    public static Map<JavaRegion, Set<String>> getRegionsToOptions(String database, String program) throws NoSuchFieldException {
        // This is hardcode to get the output of Lotrack
        List<String> fields = new ArrayList<>();
        fields.add(LotrackProcessor.PACKAGE);
        fields.add(LotrackProcessor.CLASS);
        fields.add(LotrackProcessor.METHOD);
        fields.add(LotrackProcessor.JAVA_LINE_NO);
        fields.add(LotrackProcessor.JIMPLE_LINE_NO);
        fields.add(LotrackProcessor.CONSTRAINT);
        fields.add(LotrackProcessor.CONSTRAINT_PRETTY);
        fields.add(LotrackProcessor.BYTECODE_INDEXES);
        fields.add(LotrackProcessor.METHOD_BYTECODE_SIGNATURE_JOANA_STYLE);
        fields.add(LotrackProcessor.USED_TERMS);

        List<String> sortBy = new ArrayList<>();
        sortBy.add(LotrackProcessor.PACKAGE);
        sortBy.add(LotrackProcessor.CLASS);
        sortBy.add(LotrackProcessor.METHOD);
        sortBy.add(LotrackProcessor.JIMPLE_LINE_NO);

        ScalaMongoDriverConnector.connect(database);
        List<String> queryResult = ScalaMongoDriverConnector.findProjectionAscending(program, fields, sortBy);
        ScalaMongoDriverConnector.close();

//        String currentMethod = "";
//        String currentClass = "";
//        int currentJimpleLine = -10;
//        int currentByteCodeIndex = -10;
//        for(String result : queryResult) {
//            JSONObject JSONResult = new JSONObject(result);
//            String method = JSONResult.getString("Method");
//            String classNew = JSONResult.getString("Class");
//
//            int jimpleLine = JSONResult.getInt("JimpleLineNo");
//
//            if(classNew.equals(currentClass) && method.equals(currentMethod)) {
//                if(currentJimpleLine > jimpleLine) {
//                    System.out.println("jimpleline: " + currentClass + " " + currentMethod + " " + currentJimpleLine + " " + jimpleLine);
//                }
//
//                if(currentByteCodeIndex > JSONResult.getJSONArray("bytecodeIndexes").getInt(0)) {
//                    System.out.println("bytecodeIndex: " + currentClass + " " + currentMethod + " " + currentByteCodeIndex + " " + JSONResult.getJSONArray("bytecodeIndexes").getInt(0));
//                }
//
//                if(JSONResult.getJSONArray("bytecodeIndexes").length() > 1) {
//                    if(currentByteCodeIndex > JSONResult.getJSONArray("bytecodeIndexes").getInt(1)) {
//                        System.out.println("bytecodeIndex: " + currentClass + " " + currentMethod + " " + currentByteCodeIndex + " " + JSONResult.getJSONArray("bytecodeIndexes").getInt(1));
//                    }
//                }
//            }
//            else {
//                currentMethod = method;
//                currentClass = classNew;
//            }
//            currentJimpleLine = jimpleLine;
//
//            if(JSONResult.getJSONArray("bytecodeIndexes").length() > 1) {
//                currentByteCodeIndex = Math.max(JSONResult.getJSONArray("bytecodeIndexes").getInt(0), JSONResult.getJSONArray("bytecodeIndexes").getInt(1));
//            }
//            else {
//                currentByteCodeIndex = JSONResult.getJSONArray("bytecodeIndexes").getInt(0);
//
//            }
//
//        }

        Map<JavaRegion, Set<String>> regionsToOptions = new HashMap<>();
//        Stack<JavaRegion> partialRegions = new Stack<>();
//        JavaRegion currentRegion = new JavaRegion();
//        Set<String> currentOptions = new HashSet<>();
//        JSONObject currentJSONResult = new JSONObject();
//
//        for(String result : queryResult) {
//            JSONObject JSONResult = new JSONObject(result);
//            Set<String> options = new HashSet<>();
//
//            if (JSONResult.has(LotrackProcessor.USED_TERMS)) {
//                for (Object string : JSONResult.getJSONArray(LotrackProcessor.USED_TERMS).toList()) {
//                    options.add(string.toString());
//                }
//            } else if (JSONResult.has(LotrackProcessor.CONSTRAINT)) {
//                // Be careful that this is imprecise since the constraints can be very large and does not fit in the db field
//                String[] constraints = JSONResult.getString(LotrackProcessor.CONSTRAINT).split(" ");
//
//                for (String constraint : constraints) {
//                    constraint = constraint.replaceAll("[()^|!=]", "");
//                    if (constraint.isEmpty() || StringUtils.isNumeric(constraint)) {
//                        continue;
//                    }
//
//                    if (constraint.contains(LotrackProcessor.LOTRACK_UNKNOWN_CONSTRAINT_SYMBOL)) {
//                        constraint = constraint.split(LotrackProcessor.LOTRACK_UNKNOWN_CONSTRAINT_SYMBOL)[0];
//                    }
//
//                    // Because the constraint gotten from Lotrack might be too long
//                    if (constraint.contains(".")) {
//                        continue;
//                    }
//
//                    options.add(constraint);
//                }
//            } else {
//                throw new NoSuchFieldException("The query result does not have neither a " + LotrackProcessor.USED_TERMS + " or " + LotrackProcessor.CONSTRAINT + " fields");
//            }
//
//            JavaRegion region = new JavaRegion(JSONResult.get(LotrackProcessor.PACKAGE).toString(),
//                    JSONResult.get(LotrackProcessor.CLASS).toString(),
//                    JSONResult.get(LotrackProcessor.METHOD).toString());
//
//            if(!currentRegion.equals(region) || !currentOptions.equals(options)) {
//                if(!partialRegions.isEmpty()) {
//                    JavaRegion peekedRegion = partialRegions.peek();
//                    JavaRegion peekedMetadataRegion = new JavaRegion(peekedRegion.getRegionPackage(), peekedRegion.getRegionClass(), peekedRegion.getRegionMethod());
//
//                    if(peekedMetadataRegion.equals(currentRegion)) {
//                        int endBytecodeIndex = Integer.MIN_VALUE;
//                        List<Object> endBytecodeIndexesAsObjects = currentJSONResult.getJSONArray(LotrackProcessor.BYTECODE_INDEXES).toList();
//
//                        for(Object bytecodeIndex : endBytecodeIndexesAsObjects) {
//                            endBytecodeIndex = Math.max(endBytecodeIndex, (Integer) bytecodeIndex);
//                        }
//
//                        JavaRegion oldPartialRegion = partialRegions.pop();
//                        oldPartialRegion.setEndBytecodeIndex(endBytecodeIndex);
//
//                        regionsToOptions.put(oldPartialRegion, options);
//                    }
//                }
//
//                int startBytecodeIndex = Integer.MAX_VALUE;
//                List<Object> startBytecodeIndexesAsObjects = JSONResult.getJSONArray(LotrackProcessor.BYTECODE_INDEXES).toList();
//
//                for(Object bytecodeIndex : startBytecodeIndexesAsObjects) {
//                    startBytecodeIndex = Math.min(startBytecodeIndex, (Integer) bytecodeIndex);
//                }
//
//                JavaRegion newPartialRegion = new JavaRegion(JSONResult.get(LotrackProcessor.PACKAGE).toString(),
//                        JSONResult.get(LotrackProcessor.CLASS).toString(),
//                        JSONResult.get(LotrackProcessor.METHOD).toString(),
//                        startBytecodeIndex);
//
//                partialRegions.push(newPartialRegion);
//
//            }
//
//            currentRegion = region;
//            currentOptions = options;
//            currentJSONResult = JSONResult;
//        }
//
//        if(partialRegions.size() > 1) {
//            throw new RuntimeException("There was an error calculating the java regions");
//        }
//
//        if(!partialRegions.isEmpty()) {
//            int endBytecodeIndex = Integer.MIN_VALUE;
//            List<Object> endBytecodeIndexesAsObjects = currentJSONResult.getJSONArray(LotrackProcessor.BYTECODE_INDEXES).toList();
//
//            for(Object bytecodeIndex : endBytecodeIndexesAsObjects) {
//                endBytecodeIndex = Math.max(endBytecodeIndex, (Integer) bytecodeIndex);
//            }
//
//            JavaRegion oldPartialRegion = partialRegions.pop();
//            oldPartialRegion.setEndBytecodeIndex(endBytecodeIndex);
//
//            regionsToOptions.put(oldPartialRegion, currentOptions);
//        }

        return regionsToOptions;
    }

    public static Map<JavaRegion, Set<String>> filterBooleans(Map<JavaRegion, Set<String>> regionToOptions) {
        // These are language dependent since they can be writen with other capitalization
        Set<String> optionsToRemove = new HashSet<>();
        optionsToRemove.add("true");
        optionsToRemove.add("false");

        Map<JavaRegion, Set<String>> filteredMap = new HashMap<>();

        for(Map.Entry<JavaRegion, Set<String>> entry : regionToOptions.entrySet()) {
            Set<String> options = entry.getValue();
            options.removeAll(optionsToRemove);
            filteredMap.put(entry.getKey(), options);
        }

        return filteredMap;
    }

    public static Map<JavaRegion, Set<String>> filterRegionsNoOptions(Map<JavaRegion, Set<String>> regionToOptions) {
        Map<JavaRegion, Set<String>> filteredMap = new HashMap<>();

        for(Map.Entry<JavaRegion, Set<String>> entry : regionToOptions.entrySet()) {
            if(!entry.getValue().isEmpty()) {
                filteredMap.put(entry.getKey(), entry.getValue());
            }
        }

        return filteredMap;
    }

}
