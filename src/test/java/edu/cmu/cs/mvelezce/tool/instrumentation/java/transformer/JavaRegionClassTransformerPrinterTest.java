package edu.cmu.cs.mvelezce.tool.instrumentation.java.transformer;

/**
 * Created by mvelezce on 4/3/17.
 */
public class JavaRegionClassTransformerPrinterTest {

//    @Test
//    public void testTransform1() throws IOException, NoSuchMethodException, ClassNotFoundException {
//        // Java Region
//        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
//        Set<JavaRegion> regions = new HashSet<>();
//        JavaRegion region = new JavaRegion(Sleep1.PACKAGE, Sleep1.CLASS, Sleep1.MAIN_METHOD, 23, 24);
//        regions.add(region);
//
//        // Program files
//        List<String> programFiles = new ArrayList<>();
//        programFiles.add(Sleep1.FILENAME);
//
//        // Get class
//        JavaRegionClassTransformerPrinter printer = new JavaRegionClassTransformerPrinter(programFiles, regions, "Money!");
//
//        Set<ClassNode> classNodes = new HashSet<>();
//
//        for(String fileName : programFiles) {
//            ClassNode classNode = printer.readClass(fileName);
//            printer.transform(classNode);
//            classNodes.add(classNode);
//        }
//
//
//
//        // Save size of instructions for each method in the class
//        Map<String, Integer> methodToInstructionCount = new HashMap<>();
//
//        for(MethodNode methodNode : classNode.methods) {
//            methodToInstructionCount.put(methodNode.name, methodNode.instructions.size());
//        }
//
//        // Transform the Java region
//        printer.transform(classNode);
//
//        // Check if the transform actually made changes to the graph
//        boolean transformed = false;
//
//        for(MethodNode methodNode : classNode.methods) {
//            if(methodNode.instructions.size() != methodToInstructionCount.get(methodNode.name)) {
//                transformed = true;
//                break;
//            }
//        }
//
//        // Assert
//        Assert.assertTrue(transformed);
//
//        // Execute instrumented code
//        Set<ClassNode> instrumentedClasses = new HashSet<>();
//        instrumentedClasses.add(classNode);
//
//        Set<String> configuration = new HashSet<>();
//        configuration.add("A");
//
//        DynamicAdapter.setInstrumentedClassNodes(instrumentedClasses);
//        DynamicAdapter adapter = new SleepDynamicAdapter(Sleep1.FILENAME);
//        adapter.execute(configuration);
//    }
//
//    @Test
//    public void testTransform2() throws IOException, NoSuchMethodException, ClassNotFoundException {
//        // Java Region
//        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
//        JavaRegion region = new JavaRegion(Sleep2.PACKAGE, Sleep2.CLASS, Sleep2.METHOD_1, 19, 20);

//
//        // Program
//        JavaRegionClassTransformer.setMainClass(Sleep2.FILENAME);
//
//        // Get class
//        JavaRegionClassTransformerPrinter printer = new JavaRegionClassTransformerPrinter(Sleep2.FILENAME, "Money!");
//        ClassNode classNode = printer.readClass();
//
//        // Save size of instructions for each method in the class
//        Map<String, Integer> methodToInstructionCount = new HashMap<>();
//
//        for(MethodNode methodNode : classNode.methods) {
//            methodToInstructionCount.put(methodNode.name, methodNode.instructions.size());
//        }
//
//        // Transform the Java region
//        printer.transform(classNode);
//
//        // Check if the transform actually made changes to the graph
//        boolean transformed = false;
//
//        for(MethodNode methodNode : classNode.methods) {
//            if(methodNode.instructions.size() != methodToInstructionCount.get(methodNode.name)) {
//                transformed = true;
//                break;
//            }
//        }
//
//        // Assert
//        Assert.assertTrue(transformed);
//
//        // Execute instrumented code
//        Set<ClassNode> instrumentedClasses = new HashSet<>();
//        instrumentedClasses.add(classNode);
//
//        Set<String> configuration = new HashSet<>();
//        configuration.add("A");
//
//        DynamicAdapter.setInstrumentedClassNodes(instrumentedClasses);
//        DynamicAdapter adapter = new SleepDynamicAdapter(Sleep2.FILENAME);
//        adapter.execute(configuration);
//    }

}