package edu.cmu.cs.mvelezce.tool.analysis;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by miguelvelez on 4/7/17.
 */
public class Region implements Cloneable {
    private String regionPackage;
    private String regionClass;
    private String regionMethod;
    private long startTime;
    private long endTime;
    private Set<Region> innerRegions;
    private Region previousExecutingRegion;

    public Region(String regionPackage, String regionClass, String regionMethod) {
        this.regionPackage = regionPackage;
        this.regionClass = regionClass;
        this.regionMethod = regionMethod;
        this.startTime = 0;
        this.endTime = 0;
        this.innerRegions = new HashSet<>();
        this.previousExecutingRegion = null;
    }

    public void enter() {
        this.previousExecutingRegion = Regions.getCurrentExecutingRegion();
        this.previousExecutingRegion.addInnerRegion(this);
        Regions.setCurrentExecutingRegion(this);

        this.startTime();
    }

    public void enter(long startTime) {
        this.previousExecutingRegion = Regions.getCurrentExecutingRegion();
        this.previousExecutingRegion.addInnerRegion(this);
        Regions.setCurrentExecutingRegion(this);

        this.startTime(startTime);
    }

    public void exit() {
        Regions.setCurrentExecutingRegion(this.previousExecutingRegion);

        this.endTime();
    }

    public void exit(long endTime) {
        Regions.setCurrentExecutingRegion(this.previousExecutingRegion);

        this.endTime(endTime);
    }

    public void addInnerRegion(Region region) {
        if(region == null) {
            throw new IllegalArgumentException("The region cannot be null");
        }

        this.innerRegions.add(region);
    }

    public Region(String regionClass, String regionMethod) {
        this("", regionClass, regionMethod);
    }

    public Region(String regionMethod) {
        this("", "", regionMethod);
    }

    public Region() {
        this("", "", "");
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

    /**
     * Mostly for testing
     */
    public void resetExecution() {
        this.startTime = 0;
        this.endTime = 0;
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

    @Override
    public Region clone() throws CloneNotSupportedException {
        return (Region) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Region region = (Region) o;

        if (!regionPackage.equals(region.regionPackage)) return false;
        if (!regionClass.equals(region.regionClass)) return false;
        return regionMethod.equals(region.regionMethod);
    }

    @Override
    public int hashCode() {
        int result = regionPackage.hashCode();
        result = 31 * result + regionClass.hashCode();
        result = 31 * result + regionMethod.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Region{" +
                "regionPackage=" + '"' + regionPackage + '"' +
                ", regionClass=" + '"' + regionClass + '"' +
                ", regionMethod=" + '"' + regionMethod + '"' +
                '}';
    }

    public String getRegionPackage() { return this.regionPackage; }

    public String getRegionClass() { return this.regionClass; }

    public String getRegionMethod() { return this.regionMethod; }

    public Set<Region> getInnerRegions() { return this.innerRegions; }
}
