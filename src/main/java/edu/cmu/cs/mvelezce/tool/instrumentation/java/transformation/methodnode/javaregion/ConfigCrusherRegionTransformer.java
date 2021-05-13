package edu.cmu.cs.mvelezce.tool.instrumentation.java.transformation.methodnode.javaregion;

import edu.cmu.cs.mvelezce.tool.analysis.region.JavaRegion;
import edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.classnode.ClassTransformer;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Set;
import jdk.internal.org.objectweb.asm.tree.InsnList;

/**
 * Class to figure out the regions
 */
public abstract class ConfigCrusherRegionTransformer extends RegionTransformer {

  public ConfigCrusherRegionTransformer(
      String programName, String entryPoint, String directory,
      Map<JavaRegion, Set<Set<String>>> regionsToOptionSet)
      throws InvocationTargetException, NoSuchMethodException,
             MalformedURLException, IllegalAccessException {
    super(programName, entryPoint, directory, regionsToOptionSet);
  }

  public ConfigCrusherRegionTransformer(
      String programName, String entryPoint, ClassTransformer classTransformer,
      Map<JavaRegion, Set<Set<String>>> regionsToOptionSet)
      throws InvocationTargetException, NoSuchMethodException,
             MalformedURLException, IllegalAccessException {
    super(programName, entryPoint, classTransformer, regionsToOptionSet);
  }

  public abstract InsnList getInstructionsStartRegion(JavaRegion javaRegion);

  public abstract InsnList getInstructionsEndRegion(JavaRegion javaRegion);
}
