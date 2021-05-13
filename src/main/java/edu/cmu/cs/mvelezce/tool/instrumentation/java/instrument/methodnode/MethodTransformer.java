package edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.methodnode;

import java.io.IOException;
import java.util.Set;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

public interface MethodTransformer {

  public Set<MethodNode> getMethodsToInstrument(ClassNode classNode);

  public void transformMethod(MethodNode methodNode);

  public void transformMethods() throws IOException;

  public void transformMethods(Set<ClassNode> classNodes) throws IOException;
}
