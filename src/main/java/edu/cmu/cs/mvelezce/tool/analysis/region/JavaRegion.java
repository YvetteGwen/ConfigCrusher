package edu.cmu.cs.mvelezce.tool.analysis.region;

import edu.cmu.cs.mvelezce.tool.instrumentation.java.graph.MethodBlock;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mvelezce on 4/19/17.
 */
public class JavaRegion extends Region {
  private String regionPackage = "";
  private String regionClass = "";
  private String regionMethod = "";
  private int startBytecodeIndex = Integer.MIN_VALUE;
  //    private int javaLineNubmer = Integer.MIN_VALUE;
  private MethodBlock startMethodBlock = null;
  private Set<MethodBlock> endMethodBlocks = new HashSet<>();
  private String startBlockID = "";
  private Set<String> endBlocksIDs = new HashSet<>();

  private JavaRegion() { ; }

  public JavaRegion(String regionClass, String regionMethod) {
    this.regionClass = regionClass;
    this.regionMethod = regionMethod;
  }

  public JavaRegion(String regionPackage, String regionClass,
                    String regionMethod) {
    this(regionClass, regionMethod);
    this.regionPackage = regionPackage;
  }

  public JavaRegion(String regionPackage, String regionClass,
                    String regionMethod, int startBytecodeIndex) {
    this(regionPackage, regionClass, regionMethod);
    this.startBytecodeIndex = startBytecodeIndex;
  }

  //    public JavaRegion(String regionPackage, String regionClass, String
  //    regionMethod, int startBytecodeIndex, int endBytecodeIndex) {
  //        this(regionPackage, regionClass, regionMethod, startBytecodeIndex);
  //        this.endBytecodeIndex = endBytecodeIndex;
  //    }

  public JavaRegion(String regionId, String regionPackage, String regionClass,
                    String regionMethod) {
    super(regionId);
    this.regionPackage = regionPackage;
    this.regionClass = regionClass;
    this.regionMethod = regionMethod;
  }

  public JavaRegion(String regionId, String regionPackage, String regionClass,
                    String regionMethod, int startBytecodeIndex) {
    super(regionId);
    this.regionPackage = regionPackage;
    this.regionClass = regionClass;
    this.regionMethod = regionMethod;
    this.startBytecodeIndex = startBytecodeIndex;
  }

  public JavaRegion(String regionId, String regionPackage, String regionClass,
                    String regionMethod, int startBytecodeIndex,
                    String startBlockID, Set<String> endBlocksIDs) {
    this(regionId, regionPackage, regionClass, regionMethod,
         startBytecodeIndex);
    this.startBlockID = startBlockID;
    this.endBlocksIDs.addAll(endBlocksIDs);
  }
  //    public JavaRegion(String regionId, String regionPackage, String
  //    regionClass, String regionMethod, int startBytecodeIndex, int
  //    endBytecodeIndex) {
  //        this(regionId, regionPackage, regionClass, regionMethod,
  //        startBytecodeIndex); this.endBytecodeIndex = endBytecodeIndex;
  //    }

  @Override
  public String toString() {
    return "JavaRegion{"
        + "regionPackage='" + this.regionPackage + '\'' + ", regionClass='" +
        this.regionClass + '\'' + ", regionMethod='" + this.regionMethod +
        '\'' + ", startBytecodeIndex=" + this.startBytecodeIndex + '}';
  }

  public String getRegionPackage() { return this.regionPackage; }

  public String getRegionClass() { return this.regionClass; }

  public String getRegionMethod() { return this.regionMethod; }

  public int getStartBytecodeIndex() { return this.startBytecodeIndex; }

  //    public int getEndBytecodeIndex() { return this.endBytecodeIndex; }

  public void setStartBytecodeIndex(int startBytecodeIndex) {
    this.startBytecodeIndex = startBytecodeIndex;
  }

  public MethodBlock getStartMethodBlock() { return this.startMethodBlock; }

  public void setStartMethodBlock(MethodBlock startMethodBlock) {
    this.startMethodBlock = startMethodBlock;
  }

  public Set<MethodBlock> getEndMethodBlocks() { return endMethodBlocks; }

  //    public void setEndBytecodeIndex(int endBytecodeIndex) {
  //    this.endBytecodeIndex = endBytecodeIndex; }

  public void setEndMethodBlocks(Set<MethodBlock> endMethodBlocks) {
    this.endMethodBlocks = endMethodBlocks;
  }

  //    public int getJavaLineNubmer() {
  //        return this.javaLineNubmer;
  //    }
  //
  //    public void setJavaLineNubmer(int javaLineNubmer) {
  //        this.javaLineNubmer = javaLineNubmer;
  //    }

  public String getStartBlockID() { return startBlockID; }

  public void setStartBlockID(String startBlockID) {
    this.startBlockID = startBlockID;
  }

  public Set<String> getEndBlocksIDs() { return endBlocksIDs; }

  public void setEndBlocksIDs(Set<String> endBlocksIDs) {
    this.endBlocksIDs = endBlocksIDs;
  }
}
