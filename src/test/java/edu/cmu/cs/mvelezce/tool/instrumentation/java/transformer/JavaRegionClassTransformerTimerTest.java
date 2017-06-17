package edu.cmu.cs.mvelezce.tool.instrumentation.java.transformer;

import edu.cmu.cs.mvelezce.Sleep1;
import edu.cmu.cs.mvelezce.Sleep2;
import edu.cmu.cs.mvelezce.Sleep3;
import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by miguelvelez on 4/9/17.
 */
public class JavaRegionClassTransformerTimerTest {

    @Test
    public void testTransform1() throws IOException, InterruptedException, ClassNotFoundException, NoSuchMethodException {
        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Set<JavaRegion> regions = new HashSet<>();
        JavaRegion region = new JavaRegion(Sleep1.PACKAGE, Sleep1.CLASS, Sleep1.MAIN_METHOD, 20);
        regions.add(region);

        // Directory
        String directory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";

        // Get the instruction count in each method
        JavaRegionClassTransformerTimer timer = new JavaRegionClassTransformerTimer(directory, regions);
        ClassNode classNode = timer.readClass(Sleep1.FILENAME);

        // Save size of instructions for each method in the class
        Map<String, Integer> methodToInstructionCount = new HashMap<>();

        for(MethodNode methodNode : classNode.methods) {
            methodToInstructionCount.put(methodNode.name, methodNode.instructions.size());
        }

        // Transform the files
        Set<ClassNode> classNodes = timer.transformClasses();

        // Check if the transform actually made changes to the bytecode
        boolean transformed = false;

        for(ClassNode instrumentedClassNode : classNodes) {
            for(MethodNode methodNode : instrumentedClassNode.methods) {
                if(methodNode.instructions.size() != methodToInstructionCount.get(methodNode.name)) {
                    transformed = true;
                    break;
                }
            }
        }

        // Assert
        Assert.assertTrue(transformed);
    }

    @Test
    public void testTransform2() throws IOException, InterruptedException, ClassNotFoundException, NoSuchMethodException {
        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Set<JavaRegion> regions = new HashSet<>();
        JavaRegion region = new JavaRegion(Sleep2.PACKAGE, Sleep2.CLASS, Sleep2.MAIN_METHOD, 20);
        regions.add(region);

        region = new JavaRegion(Sleep2.PACKAGE, Sleep2.CLASS, Sleep2.METHOD_1, 16);
        regions.add(region);

        // Directory
        String directory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";

        // Get the instruction count in each method
        JavaRegionClassTransformerTimer timer = new JavaRegionClassTransformerTimer(directory, regions);
        ClassNode classNode = timer.readClass(Sleep2.FILENAME);

        // Save size of instructions for each method in the class
        Map<String, Integer> methodToInstructionCount = new HashMap<>();

        for(MethodNode methodNode : classNode.methods) {
            methodToInstructionCount.put(methodNode.name, methodNode.instructions.size());
        }

        // Transform the files
        Set<ClassNode> classNodes = timer.transformClasses();

        // Check if the transform actually made changes to the bytecode
        boolean transformed = false;

        for(ClassNode instrumentedClassNode : classNodes) {
            for(MethodNode methodNode : instrumentedClassNode.methods) {
                if(methodNode.instructions.size() != methodToInstructionCount.get(methodNode.name)) {
                    transformed = true;
                    break;
                }
            }
        }

        // Assert
        Assert.assertTrue(transformed);
    }

    @Test
    public void testTransform3() throws IOException, InterruptedException, ClassNotFoundException, NoSuchMethodException {
        // Java Region
        // Indexes were gotten by looking at output of running ClassTransformerBaseTest
        Set<JavaRegion> regions = new HashSet<>();
        JavaRegion region = new JavaRegion(Sleep3.PACKAGE, Sleep3.CLASS, Sleep3.MAIN_METHOD, 28);
        regions.add(region);

        region = new JavaRegion(Sleep3.PACKAGE, Sleep3.CLASS, Sleep3.MAIN_METHOD, 45);
        regions.add(region);

        region = new JavaRegion(Sleep3.PACKAGE, Sleep3.CLASS, Sleep3.METHOD_1, 16);
        regions.add(region);

        region = new JavaRegion(Sleep3.PACKAGE, Sleep3.CLASS, Sleep3.METHOD_2, 16);
        regions.add(region);

        // Directory
        String directory = "/Users/mvelezce/Documents/Programming/Java/Projects/performance-mapper-evaluation/instrumented/dummy/out/production/dummy/";

        // Get the instruction count in each method
        JavaRegionClassTransformerTimer timer = new JavaRegionClassTransformerTimer(directory, regions);
        ClassNode classNode = timer.readClass(Sleep3.FILENAME);

        // Save size of instructions for each method in the class
        Map<String, Integer> methodToInstructionCount = new HashMap<>();

        for(MethodNode methodNode : classNode.methods) {
            methodToInstructionCount.put(methodNode.name, methodNode.instructions.size());
        }

        // Transform the files
        Set<ClassNode> classNodes = timer.transformClasses();

        // Check if the transform actually made changes to the bytecode
        boolean transformed = false;

        for(ClassNode instrumentedClassNode : classNodes) {
            for(MethodNode methodNode : instrumentedClassNode.methods) {
                if(methodNode.instructions.size() != methodToInstructionCount.get(methodNode.name)) {
                    transformed = true;
                    break;
                }
            }
        }

        // Assert
        Assert.assertTrue(transformed);
    }
}