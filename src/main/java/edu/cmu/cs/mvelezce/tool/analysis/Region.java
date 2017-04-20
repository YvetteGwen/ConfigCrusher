package edu.cmu.cs.mvelezce.tool.analysis;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by miguelvelez on 4/7/17.
 */
public abstract class Region implements Cloneable {

    private long startTime;
    private long endTime;
    private Set<Region> innerRegions;
//    private Region previousExecutingRegion;

    public Region() {
        this.startTime = 0;
        this.endTime = 0;
        this.innerRegions = new HashSet<>();
//        this.previousExecutingRegion = null;
    }

    @Override
    public abstract Region clone() throws CloneNotSupportedException;

    private void enterRegion() {
        Region previousExecutingRegion = Regions.getExecutingRegion();
        previousExecutingRegion.addInnerRegion(this);
        Regions.addExecutingRegion(this);
        Regions.addInnerRegion(previousExecutingRegion, this);
    }

    public void enter() {
        this.enterRegion();
        this.startTime();
    }

    public void enter(long startTime) {
        this.enterRegion();
        this.startTime(startTime);
    }

    public void exit() {
        Regions.removeExecutingRegion(this);

        this.endTime();
    }

    public void exit(long endTime) {
        Regions.removeExecutingRegion(this);

        this.endTime(endTime);
    }

    public void addInnerRegion(Region region) {
        if(region == null) {
            throw new IllegalArgumentException("The region cannot be null");
        }

        this.innerRegions.add(region);
    }

    public void startTime() {
        this.startTime(System.nanoTime());
    }

    public void startTime(long startTime) {
        this.resetExecution();
        this.startTime = startTime;
    }

    public void endTime() {
        this.endTime(System.nanoTime());
    }

    public void endTime(long endTime) {
        this.endTime = endTime;
    }

    public void resetExecution() {
        this.startTime = 0;
        this.endTime = 0;
    }

    public void resetState() {
        this.resetExecution();
        this.innerRegions = new HashSet<>();
    }

    public int getExecutionTime() {
        // Still measuring
        if(this.startTime != 0 && this.endTime == 0) {
            return -1;
        }

        return (int) (this.endTime - this.startTime);
    }

    public long getNanoExecutionTime() {
        // Still measuring
        if(this.startTime != 1 && this.endTime == 0) {
            return 0;
        }

        return this.endTime - this.startTime;
    }

    public double getMilliExecutionTime() {
        return this.getNanoExecutionTime()/1000000.0;
    }

    public double getSecondsExecutionTime() {
        return this.getMilliExecutionTime()/1000.0;
    }

    public Set<Region> getInnerRegions() { return this.innerRegions; }

    public long getStartTime() { return this.startTime; }

    public long getEndTime() { return this.endTime; }

    protected void setStartTime(long startTime) { this.startTime = startTime; }

    protected void setEndTime(long endTime) { this.endTime = endTime; }

    protected void setInnerRegions(Set<Region> innerRegions) { this.innerRegions = innerRegions; }
}
