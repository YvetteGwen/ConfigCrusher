package edu.cmu.cs.mvelezce.tool.instrumentation.java;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

/**
 * Created by miguelvelez on 4/9/17.
 */
public class Helper {

  public static List<String> readJar(String jar) throws IOException {
    JarFile file = new JarFile(jar);
    Enumeration<JarEntry> entries = file.entries();
    List<String> classFiles = new LinkedList<>();

    while (entries.hasMoreElements()) {
      JarEntry entry = entries.nextElement();

      if (entry.getName().endsWith(".class")) {
        String name = entry.getName();
        name = name.substring(0, name.length() - 6);
        ClassReader classReader = new ClassReader(name);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        classFiles.add(classNode.name);
      }
    }

    return classFiles;
  }

  public static List<MethodNode> readFile(String fileName) throws IOException {
    ClassReader classReader = new ClassReader(fileName);
    ClassNode classNode = new ClassNode();
    classReader.accept(classNode, 0);

    return classNode.methods;
  }
}
