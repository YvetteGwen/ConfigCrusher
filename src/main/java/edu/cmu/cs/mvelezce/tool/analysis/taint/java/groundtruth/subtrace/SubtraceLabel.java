package edu.cmu.cs.mvelezce.tool.analysis.taint.java.groundtruth.subtrace;

import com.beust.jcommander.internal.Nullable;
import java.util.UUID;

public class SubtraceLabel extends DecisionLabelWithContext {

  private final UUID uuid;
  private final int execCount;

  public SubtraceLabel(@Nullable UUID context, String decision, int execCount) {
    super(context, decision);

    this.execCount = execCount;
    this.uuid = UUID.randomUUID();
  }

  UUID getUUID() {
    return uuid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    SubtraceLabel that = (SubtraceLabel) o;

    return execCount == that.execCount;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + execCount;
    return result;
  }
}
