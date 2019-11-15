package edu.cmu.cs.mvelezce.model;

import java.util.HashSet;
import java.util.Set;

public class PerformanceModel<T> {

  private final Set<LocalPerformanceModel<T>> localModels;

  // Dummy constructor for jackson xml
  private PerformanceModel() {
    this.localModels = new HashSet<>();
  }

  public PerformanceModel(Set<LocalPerformanceModel<T>> localModels) {
    this.localModels = localModels;
  }

  public Set<LocalPerformanceModel<T>> getLocalModels() {
    return localModels;
  }
}
