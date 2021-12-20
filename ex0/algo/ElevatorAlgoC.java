package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.LinkedList;

public class ElevatorAlgoC implements ElevatorAlgo{
    private static final int UP = 1, LEVEL = 0, DOWN = -1, ERROR = -2;
    Building building;
    LinkedList<Integer>[] listStopOfElevator;
    private boolean[] _firstTime;

    /**Constructor of the ElevatorAlgoC**/
    public ElevatorAlgoC(Building building){
        this.building = building;
        listStopOfElevator = new LinkedList[building.numberOfElevetors()];
        for (int i = 0; i < listStopOfElevator.length; i++) {
            listStopOfElevator[i] = new LinkedList<>();
        }
        _firstTime = new boolean[building.numberOfElevetors()];
        for(int i=0;i<_firstTime.length;i++) {_firstTime[i] = true;}
    }
    /**
     * @return the Building on which the (online) elevator algorithm works on.
     */
    @Override
    public Building getBuilding() {
        return this.building;
    }

    /**
     * @return he algorithm name - can be any String.
     */
    @Override
    public String algoName() {
        return "Elevator";
    }
    /**
     * This method is the main optimal allocation (aka load-balancing) algorithm for allocating the
     * "best" elevator for a call (over all the elevators in the building).
     * The way of the algorithm is in Part 1
     * @param c the call for elevator (src, dest)
     * @return the index of the elevator to which this call was allocated to.
     */
    @Override
    public int allocateAnElevator(CallForElevator c) {
        int elevNum = building.numberOfElevetors();
        LinkedList<Integer> thisEl ;
        int ans = 0;
            if (elevNum > 0){
                InfoElevator distI;
                InfoElevator distAns = null;
                while (this.building.getElevetor(ans).getState() == Elevator.ERROR) {
                    ans++;
                }
                int i = ans;
                if (elevNum != ans+1){
                    i = ans+1;
                }
                for (  ; i < elevNum; i++) {
                    if (this.building.getElevetor(i).getState() != Elevator.ERROR) {
                        distAns = info(ans, c);
                        distI = info(i, c);
                        //Elevator resting and empty
                        if (distI.getPriority() == 0) {
                            thisEl = listStopOfElevator[i];
                            thisEl.add(distI.wherePlaceSrc, c.getSrc());
                            thisEl.add(distI.wherePlaceDest, c.getDest());
                            return i;
                        }
                        //Elevator level and empty on starting floor
                        if (distAns.getPriority() == 0) {
                            thisEl = listStopOfElevator[ans];
                            thisEl.add(distAns.wherePlaceSrc, c.getSrc());
                            thisEl.add(distAns.wherePlaceDest, c.getDest());
                            return ans;
                        }
                        //allocation elevator by priority - more information in Part 1
                        if (distAns.getPriority() < distI.getPriority()) {
                            ans = ans;
                        } else {
                            if (distAns.getPriority() > distI.getPriority()) {
                                ans = i;
                                distAns = distI;
                            } else {
                                //allocation elevator by time wait until it reaches src - more information in Part 1
                                if (distAns.getDistToWait() < distI.getDistToWait()) {
                                    ans = ans;
                                } else {
                                    if (distAns.getDistToWait() > distI.getDistToWait()) {
                                        ans = i;
                                        distAns = distI;

                                    } else {
                                        //allocation elevator by time wait until it reaches dist - more information in Part 1
                                        if (distAns.getDistInside() < distI.getDistInside()) {
                                            ans = ans;
                                        } else {
                                            if (distAns.getDistInside() > distI.getDistInside()) {
                                                ans = i;
                                                distAns = distI;
                                            } else {
                                                ans = i;
                                                distAns = distI;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    thisEl = listStopOfElevator[ans];
                }
                //add call into listStopOfElevator
                if (distAns != null){
                    thisEl= listStopOfElevator[ans];
                    if(distAns.wherePlaceSrc != -1) {
                        thisEl.add(distAns.wherePlaceSrc, c.getSrc());
                    }
                    if(distAns.wherePlaceDest != -1){
                        thisEl.add(distAns.wherePlaceDest,c.getDest());
                    }
                }
            }
        return ans;
    }
    /**
     * This method is to get information on each elevator to allocating the "best" elevator for a call
     * @param elev num elevator
     * @param dest the call for elevator (src, dest)
     * @return InfoElevator - Information to know which elevator to allocation and where into list
     */
    public InfoElevator info( int elev, CallForElevator dest){
        int stateDest = DOWN;
        if (dest.getSrc() < dest.getDest()){
             stateDest =UP;
        }
        Elevator thisElev = this.building.getElevetor(elev);
        int pos = thisElev.getPos();
        LinkedList<Integer> thisEl = listStopOfElevator[elev];
        double speed = thisElev.getSpeed();
        int stateThisEl=LEVEL;
        double floorTime = speed+thisElev.getStopTime()+thisElev.getStartTime()+thisElev.getTimeForOpen()+thisElev.getTimeForClose();//stop time
        if ((thisElev.getState() == Elevator.LEVEL) && (thisEl.size() == 0)) {
            if (thisElev.getPos() == dest.getSrc()){
                return new InfoElevator (elev,0,0,0 , 0,1);
            }else {
                double up2down = Math.abs((dest.getSrc()-pos))*speed +floorTime;
                return new InfoElevator (elev,up2down,0,1 , 0,1);
            }
        }else {
            if (thisEl.isEmpty()){
                double up2down = Math.abs((dest.getSrc()-pos))*speed +floorTime;
                return new InfoElevator (elev,up2down,0,1 , 0,1);
            }
            if (((thisEl.get(thisEl.size()-1)) == dest.getSrc()) && (thisEl.size() ==1)) {
                double up2down = Math.abs((dest.getSrc()-pos))*speed +floorTime;
                return new InfoElevator (elev,up2down,0,1 , -1,1);
            }
            boolean srcYes = false, destYes = false;
            int srcYesIndex = Integer.MAX_VALUE, destYesIndex = Integer.MAX_VALUE;
            int jD = 0,jS = 0,r = 0;
            boolean test = false;
            if (thisEl.size() == 1){
                if ((thisEl.get(0) == dest.getSrc()) ){
                    double up2SpeedToWait = ((Math.abs(thisEl.get(0) - thisElev.getPos())) * speed) + (floorTime*1);
                    return new InfoElevator (elev, up2SpeedToWait,0,2,-1,1);
                }
                double up2SpeedToWait =  ((Math.abs(thisEl.get(0) - thisElev.getPos())) +(Math.abs(thisEl.get(0) - dest.getSrc()))  * speed)  + floorTime*1;
                double up2SpeedInside = ((Math.abs(dest.getDest() - dest.getSrc() ))+(Math.abs(thisEl.get(0) - dest.getSrc()) * speed )  +(floorTime*(1)));
                return new InfoElevator (elev,up2SpeedToWait, up2SpeedInside,3,1,2);
            }
            if (thisEl.size() > 1 ){
                if (thisEl.get(0) < thisEl.get(1)) {
                    stateThisEl = UP;
                }
                if (thisEl.get(0) > thisEl.get(1)) {
                    stateThisEl = DOWN;
                }
            }
            if (stateThisEl == stateDest){
                if ((stateThisEl== Elevator.UP) && (thisEl.get(0) > dest.getSrc())){
                    int distEl = 0;
                    while ((!test) && (thisEl.size() >1) && (r+1 < thisEl.size())){
                        if (thisEl.get(r) <thisEl.get(r+1)){
                            r++;
                        }else {
                            test = true;
                        }
                    }
                    distEl = Math.abs(thisEl.get(r)-thisElev.getPos());
                    int g = r;
                    test = false;
                    while ((!test)&& (thisEl.size() >1) && (r+1 < thisEl.size())){
                        if (thisEl.get(r) > thisEl.get(r+1)){
                            r++;
                        }else {
                            test = true;
                        }
                    }
                    distEl += Math.abs((thisEl.get(r)-thisEl.get(g)));
                    PlaceTo placeTo = whereToPlaceUp(thisEl,thisElev,dest,r,true);
                    srcYesIndex = placeTo.getSrcYesIndex();
                    jD = placeTo.jD;
                    jS = placeTo.jS;
                    destYesIndex = placeTo.destYesIndex;
                    srcYes = placeTo.srcYes;
                    destYes = placeTo.destYes;
                    double up2down = Math.abs(distEl)*speed;
                    if ((srcYes) && (destYes)){
                        double up2SpeedToWait = up2down + ((Math.abs(thisEl.get(srcYesIndex) - thisEl.get(r) )) * speed) + (floorTime*srcYesIndex);
                        double up2SpeedInside = ((Math.abs(thisEl.get(destYesIndex) - srcYesIndex)) * speed) + (floorTime*(Math.abs((destYesIndex-srcYesIndex))) );
                        return new InfoElevator (elev, up2SpeedToWait,(up2SpeedInside),3,-1,-1);
                    }
                    if ((srcYes) ){
                        double up2SpeedToWait = up2down + ((Math.abs(thisEl.get(srcYesIndex) - thisEl.get(r))) * speed) + (floorTime*srcYesIndex);
                        double up2SpeedInside = ((Math.abs(dest.getDest() - srcYesIndex)) * speed) +(floorTime*(Math.abs((jD-srcYesIndex)))) ;
                        return new InfoElevator (elev, up2SpeedToWait,up2SpeedInside,4,-1,jD);
                    }
                    if ((destYes) ){
                        double up2SpeedToWait = up2down + ((Math.abs(dest.getSrc()- thisEl.get(r))) * speed) + floorTime*jS;
                        double up2SpeedInside = ((Math.abs(thisEl.get(destYesIndex) - dest.getSrc())) * speed)+(floorTime*(Math.abs((destYesIndex-jS)))) ;
                        return new InfoElevator (elev,up2SpeedToWait,up2SpeedInside,5,jS,-1);
                    }
                    double up2SpeedToWait = up2down + ((Math.abs(dest.getSrc()- thisEl.get(r))) * speed)  + floorTime*jS;
                    double up2SpeedInside = ((Math.abs(dest.getDest() - dest.getSrc() )) * speed )  +(floorTime*(Math.abs((jD-jS))));
                    return new InfoElevator (elev,up2SpeedToWait, up2SpeedInside,6,jS,jD);
                }
                int distEl = 0;
                if ((stateThisEl == Elevator.DOWN) && (thisEl.get(0) < dest.getSrc())){
                    while ((!test) && (thisEl.size() > 1) && (r+1 < thisEl.size())){
                        if (thisEl.get(r) >thisEl.get(r+1)){
                            r++;
                        }else {
                            test = true;
                        }
                    }
                    distEl = Math.abs(thisEl.get(r)-thisElev.getPos());
                    int g = r;
                    test = false;
                    while ((!test)&& (thisEl.size() > 1) && (r+1 < thisEl.size())){
                        if (thisEl.get(r) < thisEl.get(r+1)){
                            r++;
                        }else {
                            test = true;
                        }
                    }
                    distEl += Math.abs((thisEl.get(r)-thisEl.get(g)));
                    PlaceTo placeTo = whereToPlaceDown(thisEl,thisElev,dest,r,true);
                    srcYesIndex = placeTo.getSrcYesIndex();
                    jD = placeTo.jD;
                    jS = placeTo.jS;
                    destYesIndex = placeTo.destYesIndex;
                    srcYes = placeTo.srcYes;
                    destYes = placeTo.destYes;
                    double up2down = Math.abs(distEl)*speed ;
                    if ((srcYes) && (destYes)){
                        double up2SpeedToWait = up2down + ((Math.abs(thisEl.get(srcYesIndex) - thisElev.getPos())) * speed)  +(floorTime * srcYesIndex);
                        double up2SpeedInside = ((Math.abs(thisEl.get(destYesIndex) - srcYesIndex)) * speed ) +(floorTime*(Math.abs((destYesIndex-srcYesIndex))));
                        return new InfoElevator (elev, up2SpeedToWait, up2SpeedInside,3,-1,-1);
                    }
                    if ((srcYes) ){
                        double up2SpeedToWait = up2down + ((Math.abs(thisEl.get(srcYesIndex) - thisElev.getPos())) * speed) +(floorTime * srcYesIndex);
                        double up2SpeedInside = (Math.abs(dest.getDest() - srcYesIndex)) * speed  +(floorTime*(Math.abs((jD-srcYesIndex))));
                        return new InfoElevator (elev,up2SpeedToWait,up2SpeedInside,4,-1,jD);
                    }
                    if ((destYes) ){
                        double up2SpeedToWait = up2down  + ((Math.abs(dest.getSrc() - thisElev.getPos())) * speed) +(floorTime * jS);
                        double up2SpeedInside = (Math.abs(thisEl.get(destYesIndex) - dest.getSrc() )) * speed +(floorTime*(Math.abs((destYesIndex-jS))));;
                        return new InfoElevator (elev,(floorTime*(srcYesIndex - r)) + up2SpeedToWait,up2SpeedInside,5,jS,-1);
                    }
                   double up2SpeedToWait = up2down  + (Math.abs(dest.getSrc() - thisElev.getPos()) * speed)+(floorTime * jS);
                    double up2SpeedInside = (Math.abs(dest.getDest() - dest.getSrc() )) * speed +(floorTime*(Math.abs((jD-jS))));;
                    return new InfoElevator (elev, up2SpeedToWait, up2SpeedInside,6,jS,jD);
                }
                if ( (stateThisEl == Elevator.UP) )
                {
                    PlaceTo placeTo = whereToPlaceUp(thisEl,thisElev,dest,0,false);
                    srcYesIndex = placeTo.getSrcYesIndex();
                    jD = placeTo.jD;
                    jS = placeTo.jS;
                    destYesIndex = placeTo.destYesIndex;
                    srcYes = placeTo.srcYes;
                    destYes = placeTo.destYes;
                }
                if (stateThisEl== Elevator.DOWN) {
                    PlaceTo placeTo = whereToPlaceDown(thisEl,thisElev,dest,0,false);
                    srcYesIndex = placeTo.getSrcYesIndex();
                    jD = placeTo.jD;
                    jS = placeTo.jS;
                    destYesIndex = placeTo.destYesIndex;
                    srcYes = placeTo.srcYes;
                    destYes = placeTo.destYes;
                }
                if ((srcYes) && (destYes)){
                    double up2SpeedToWait =( (Math.abs(thisEl.get(srcYesIndex) - thisElev.getPos())) * speed ) +(floorTime*(srcYesIndex));
                    double up2SpeedInside = ((Math.abs(thisEl.get(destYesIndex) - srcYesIndex)) * speed) +(floorTime*(Math.abs((destYesIndex-srcYesIndex))) );
                    return new InfoElevator (elev,up2SpeedToWait,up2SpeedInside,3,-1,-1);
                }
                if ((srcYes) ){
                    double up2SpeedToWait =( (Math.abs(thisEl.get(srcYesIndex) - thisElev.getPos())) * speed) +(floorTime*(srcYesIndex));
                    double up2SpeedInside = ((Math.abs(dest.getDest() - srcYesIndex)) * speed) + (floorTime*(Math.abs((jD-srcYesIndex))) );
                    return new InfoElevator (elev, up2SpeedToWait,up2SpeedInside,4,-1,jD);
                }
                if ((destYes) ){
                    double up2SpeedToWait =( (Math.abs(dest.getSrc() - thisElev.getPos())) * speed) +(floorTime*(jS));;
                    double up2SpeedInside =( (Math.abs(thisEl.get(destYesIndex) - dest.getSrc())) * speed)+ (floorTime*(Math.abs((destYesIndex-jS))) );
                    return new InfoElevator (elev, up2SpeedToWait,up2SpeedInside,5,jS,-1);
                }
                double up2SpeedToWait = ((Math.abs(dest.getSrc()- thisElev.getPos())) * speed)+(floorTime*(jS));
                double up2SpeedInside = ((Math.abs(dest.getDest()- dest.getSrc() )) * speed) + (floorTime*(Math.abs((jD-jS))) );
                return new InfoElevator (elev,up2SpeedToWait, up2SpeedInside,6,jS,jD);
            }else {
               int max  = 0;
               max = whereDirectionChanges(thisElev,thisEl,stateThisEl);
               if (stateThisEl == Elevator.UP) {
                   PlaceTo placeTo = whereToPlaceDown(thisEl, thisElev, dest, max,true);
                   srcYesIndex = placeTo.getSrcYesIndex();
                   jD = placeTo.jD;
                   jS = placeTo.jS;
                   destYesIndex = placeTo.destYesIndex;
                   srcYes = placeTo.srcYes;
                   destYes = placeTo.destYes;
               }
               if (stateThisEl == Elevator.DOWN){
                   PlaceTo placeTo = whereToPlaceUp(thisEl, thisElev, dest, max,true);
                   srcYesIndex = placeTo.getSrcYesIndex();
                   jD = placeTo.jD;
                   jS = placeTo.jS;
                   destYesIndex = placeTo.destYesIndex;
                   srcYes = placeTo.srcYes;
                   destYes = placeTo.destYes;
               }
               double up2down = Math.abs((thisEl.get(max)-pos))*speed ;
               if ((srcYes) && (destYes)){
                   double up2SpeedToWait = up2down + ((Math.abs(thisEl.get(srcYesIndex) - thisEl.get(max) )) * speed) + (floorTime*srcYesIndex);
                   double up2SpeedInside = ((Math.abs(thisEl.get(destYesIndex) - srcYesIndex)) * speed) +(floorTime*(Math.abs((destYesIndex-srcYesIndex))));
                   return new InfoElevator (elev,( up2SpeedToWait),( up2SpeedInside),3,-1,-1);
               }
               if ((srcYes) ){
                   double up2SpeedToWait = up2down + ((Math.abs(thisEl.get(srcYesIndex) - thisEl.get(max))) * speed) + (floorTime*srcYesIndex);
                   double up2SpeedInside = ((Math.abs(dest.getDest() - srcYesIndex)) * speed) +(floorTime*(Math.abs((jD-srcYesIndex))));
                   return new InfoElevator (elev, up2SpeedToWait,up2SpeedInside,4,-1,jD);
               }
               if ((destYes) ){
                   double up2SpeedToWait = up2down + ((Math.abs(dest.getSrc()- thisEl.get(max))) * speed)  + (floorTime*jS);
                   double up2SpeedInside = ((Math.abs(thisEl.get(destYesIndex) - dest.getSrc())) * speed) +(floorTime*(Math.abs((destYesIndex-jS))));
                   return new InfoElevator (elev, up2SpeedToWait,up2SpeedInside,5,jS,-1);
               }
               double up2SpeedToWait = up2down + ((Math.abs(dest.getSrc() - thisEl.get(max))) * speed) + (floorTime*jS);
               double up2SpeedInside = ((Math.abs(dest.getDest() - dest.getSrc())) * speed) +(floorTime*(Math.abs((jD-jS))));
               return new InfoElevator (elev,up2SpeedToWait,(up2SpeedInside),6,jS,jD);
            }
        }
    }
    /**
     * This method is to check where to place the call in the list - The call state is up
     * @param thisEl the list of elevator stops
     * @param el the Elevator
     * @param dest the call for elevator (src, dest)
     * @param i where to start the test - where is it in the same direction of call
     * @param test true if starting the test is not in the first index of the list, Otherwise false
     * @return PlaceTo - information on where to place the call in the list
     */
    public PlaceTo whereToPlaceUp(LinkedList<Integer> thisEl, Elevator el, CallForElevator dest, int i, boolean test){
        boolean srcYes = false, destYes = false;
        int srcYesIndex = Integer.MAX_VALUE,destYesIndex = Integer.MAX_VALUE;
        int jD = i,jS = i,k = i;
        while ((i < thisEl.size()-1) && (thisEl.get(i) < thisEl.get(i+1))) {
            if (thisEl.get(i) == dest.getSrc()) {
                srcYes = true;
                srcYesIndex = i;
            }
            if (thisEl.get(jD) < dest.getDest()) {
                jD++;
            }
            if (thisEl.get(jS) < dest.getSrc()) {
                jS++;
            }
            if (thisEl.get(i) == dest.getDest()) {

                destYes = true;
                destYesIndex = i;
            }
            i++;

        }
    if ((i < thisEl.size()) ){
            if (thisEl.get(i) == dest.getSrc()) {
                srcYes = true;
                srcYesIndex = i;
            }
            if (thisEl.get(jD) < dest.getDest()) {
                jD++;
            }
            if (thisEl.get(jS) < dest.getSrc()) {
                jS++;
            }
            if (thisEl.get(i) == dest.getDest()) {
                destYes = true;
                destYesIndex = i;
            }
            i++;
        }
        if ((test) && (k == jS) &&(!destYes)){
            jS++;
            if ((jS>jD) && (!srcYes)){
                jD++;
            }
        }
        if ((!srcYes)){
            jD++;
        }
        return new PlaceTo(srcYesIndex,srcYes,jD,jS,destYesIndex,destYes);
    }
    /**
     * This method is to check where to place the call in the list - The call state is down
     * @param thisEl the list of elevator stops
     * @param el the Elevator
     * @param dest the call for elevator (src, dest)
     * @param i where to start the test - where is it in the same direction of call
     * @param test true if starting the test is not in the first index of the list, Otherwise false
     * @return PlaceTo - information on where to place the call in the list
     */
    public PlaceTo whereToPlaceDown(LinkedList<Integer> thisEl, Elevator el, CallForElevator dest, int i,boolean test){
        boolean srcYes = false, destYes = false;
        int srcYesIndex = Integer.MAX_VALUE, destYesIndex = Integer.MAX_VALUE;
        int jD = i,jS = i,k =i;
        while ((i < thisEl.size()-1) && (thisEl.get(i) > thisEl.get(i+1))) {
                if (thisEl.get(i) == dest.getSrc()) {
                    srcYes = true;
                    srcYesIndex = i;
                }
                if (thisEl.get(jD) > dest.getDest()) {
                    jD++;
                }
                if (thisEl.get(jS) > dest.getSrc()) {
                    jS++;
                }
                if (thisEl.get(i) == dest.getDest()) {
                    destYes = true;
                    destYesIndex = i;
                }
                i++;
            }
        if ((i < thisEl.size()) ){
            if (thisEl.get(i) == dest.getSrc()) {
                srcYes = true;
                srcYesIndex = i;
            }
            if (thisEl.get(jD) > dest.getDest()) {
                jD++;
            }
            if (thisEl.get(jS)> dest.getSrc()) {
                jS++;
            }
            if (thisEl.get(i) == dest.getDest()) {
                destYes = true;
                destYesIndex = i;
            }
            i++;
        }
        if ((test)  && (k == jS) &&(!destYes)){
            jS++;
            if ((jS>jD) && (!srcYes)){
                jD++;
            }
        }
        if ((!srcYes)){
            jD++;
        }
        return new PlaceTo(srcYesIndex,srcYes,jD,jS,destYesIndex,destYes);
    }
    /**
     * This method is to check where the direction of the elevator changes
     * @param thisEl the list of elevator stops
     * @param thisElev the Elevator
     * @param stateThisEl elevator direction (up, down)
     * @return index - where the direction of the elevator changes
     */
    public int whereDirectionChanges(Elevator thisElev, LinkedList<Integer> thisEl,int stateThisEl){
        int i =0,j =1;
        int max = 0;
        if (stateThisEl == Elevator.UP){
            if (thisEl.size() > 1){
                while ((j< thisEl.size()) && (thisEl.get(i) < thisEl.get(j))  ) {
                    i++;
                    j++;
                }
            }
            max = i;
        }
        if (stateThisEl == Elevator.DOWN){
            if (thisEl.size() > 1) {
                while ((j < thisEl.size() && (thisEl.get(i) > thisEl.get(j)))) {
                    i++;
                    j++;
                }
            }
            max = i;
        }
        return max;
    }
    /**
     * This method is the low level command for each elevator in terms of the elevator API: GoTo, Stop,
     * The simulator calls the method every time stamp (dt), note: in most cases NO action is needed.
     * @param elev the current Elevator index on which the operation is performs.
     */
    @Override
    public void cmdElevator(int elev) {
        Elevator curr = this.getBuilding().getElevetor(elev);
        LinkedList<Integer> thisEl = listStopOfElevator[elev];
        if(curr.getState() == Elevator.LEVEL) {
            if (!thisEl.isEmpty()){
                curr.goTo(thisEl.get(0));
                thisEl.removeFirst();
            }
        }
    }
}
