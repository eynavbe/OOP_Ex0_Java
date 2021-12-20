package ex0.algo;

/**
 * information on where to place the call in the list
 */
public class PlaceTo {
    int srcYesIndex;// if src of call into the list equal -1
    boolean srcYes;// true - if src of call into the list , Otherwise false
    int jD;//The index to place the call (dest) in the list
    int jS;//The index to place the call (src) in the list
    int destYesIndex;// if src of call into the list equal -1
    boolean destYes;// true - if dest of call into the list , Otherwise false
    public PlaceTo(int srcYesIndex,boolean srcYes,int jD,int jS,int destYesIndex,boolean destYes){
        this.srcYesIndex=srcYesIndex;
        this.srcYes  =srcYes;
        this.jD = jD;
        this.jS = jS;
        this.destYesIndex = destYesIndex;
        this.destYes = destYes;
    }

    public boolean isDestYes() {
        return destYes;
    }

    public boolean isSrcYes() {
        return srcYes;
    }

    public int getjS() {
        return jS;
    }

    public int getjD() {
        return jD;
    }

    public int getSrcYesIndex() {
        return srcYesIndex;
    }

    public int getDestYesIndex() {
        return destYesIndex;
    }
}
