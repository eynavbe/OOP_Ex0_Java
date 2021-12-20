package ex0.algo;

/**
 * Information to know which elevator to allocation and where into list
 */
public class InfoElevator {
    int numElev;
    double distToWait;//time wait until elevator reaches src
    double distInside;//time wait until elevator reaches dist
    int priority;//allocation elevator by priority - more information in Part 1
    int wherePlaceSrc;//where Place Src into list
    int wherePlaceDest;//where Place Dest into list


    public InfoElevator(int numElev,double distToWait, double distInside,int priority, int wherePlaceSrc, int wherePlaceDest){
        this.numElev =numElev;
        this.distToWait = distToWait;
        this.distInside = distInside;
        this.priority = priority;
        this.wherePlaceSrc = wherePlaceSrc;
        this.wherePlaceDest = wherePlaceDest;
    }

    public double getDistInside() {
        return distInside;
    }

    public double getDistToWait() {
        return distToWait;
    }

    public int getNumElev() {
        return numElev;
    }

    public int getPriority() {
        return priority;
    }

    public int getWherePlaceDest() {
        return wherePlaceDest;
    }

    public int getWherePlaceSrc() {
        return wherePlaceSrc;
    }

    @Override
    public String toString() {
        return "InfoElevator{" +
                "numElev=" + numElev +
                ", distToWait=" + distToWait +
                ", distInside=" + distInside +
                ", priority=" + priority +
                ", wherePlaceSrc=" + wherePlaceSrc +
                ", wherePlaceDest=" + wherePlaceDest +
                '}';
    }
}
