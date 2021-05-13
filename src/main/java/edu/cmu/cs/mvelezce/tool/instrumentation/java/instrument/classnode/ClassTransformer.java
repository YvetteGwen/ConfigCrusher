package edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.classnode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Set;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

/**
 * Created by mvelezce on 4/3/17.
 */
public interface ClassTransformer {

  public void addToClassPath(String pathToClass)
      throws NoSuchMethodException, MalformedURLException,
             InvocationTargetException, IllegalAccessException;

  public String getPath();

  public Set<ClassNode> readClasses() throws IOException;

  public ClassNode readClass(String fileName) throws IOException;

  public void writeClass(ClassNode classNode, String fileName)
      throws IOException;
}
