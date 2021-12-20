package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.simulator.Call_A;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorAlgoCTest {
    Building bld0;
    Building bld1;
    Building bld2;
    Building bld3;
    Building bld4;
    Building bld5;
    Building bld6;
    Building bld7;
    Building bld8;
    Building bld9;
    ElevatorAlgoC Alg0;
    ElevatorAlgoC Alg1;
    ElevatorAlgoC Alg2;
    ElevatorAlgoC Alg3;
    ElevatorAlgoC Alg4;
    ElevatorAlgoC Alg5;
    ElevatorAlgoC Alg6;
    ElevatorAlgoC Alg7;
    ElevatorAlgoC Alg8;
    ElevatorAlgoC Alg9;

    /*
     case 0: maxFloor: 1   minFloor: 0   num of elevators: 1
     case 1: maxFloor: 10   minFloor: -2   num of elevators: 1
     case 2: maxFloor: 10   minFloor: -2   num of elevators: 2
     case 3: maxFloor: 20   minFloor: -3   num of elevators: 4
     case 4: maxFloor: 20   minFloor: -3   num of elevators: 4
     case 5: maxFloor: 50   minFloor: -5   num of elevators: 10
     case 6: maxFloor: 50   minFloor: -5   num of elevators: 10
     case 7: maxFloor: 100   minFloor: -10   num of elevators: 10 //all elevator at the same speed
     case 8: maxFloor: 100   minFloor: -10   num of elevators: 10 // 1 of 2 have a different speed
     case 9: maxFloor: 100   minFloor: -10   num of elevators: 10 //all elevator have a different speed

    */


    public ElevatorAlgoCTest(){
        Simulator_A.initData(0, null);
        bld0 = Simulator_A.getBuilding();
        Simulator_A.initData(1, null);
        bld1 = Simulator_A.getBuilding();
        Simulator_A.initData(2, null);
        bld2 = Simulator_A.getBuilding();
        Simulator_A.initData(3, null);
        bld3 = Simulator_A.getBuilding();
        Simulator_A.initData(4, null);
        bld4 = Simulator_A.getBuilding();
        Simulator_A.initData(5, null);
        bld5 = Simulator_A.getBuilding();
        Simulator_A.initData(6, null);
        bld6 = Simulator_A.getBuilding();
        Simulator_A.initData(7, null);
        bld7 = Simulator_A.getBuilding();
        Simulator_A.initData(8, null);
        bld8 = Simulator_A.getBuilding();
        Simulator_A.initData(9, null);
        bld9 = Simulator_A.getBuilding();

        Alg0= new ElevatorAlgoC(bld0);
        Alg1= new ElevatorAlgoC(bld1);
        Alg2= new ElevatorAlgoC(bld2);
        Alg3= new ElevatorAlgoC(bld3);
        Alg4= new ElevatorAlgoC(bld4);
        Alg5= new ElevatorAlgoC(bld5);
        Alg6= new ElevatorAlgoC(bld6);
        Alg7= new ElevatorAlgoC(bld7);
        Alg8= new ElevatorAlgoC(bld8);
        Alg9 = new ElevatorAlgoC(bld9);

        long time = System.currentTimeMillis();
    }


    @Test
    /*Checker by a defined building, if the elevator
      calls are placed in the correct elevator according to the operation of our algorithm
    */
    void allocateAnElevator(){

        CallForElevator cfe = new Call_A(System.currentTimeMillis(), 0, 1);
        int  elevNum  = Alg0.allocateAnElevator(cfe);
        assertEquals(elevNum, 0);
        cfe = new Call_A(System.currentTimeMillis(), 1, 0);
        elevNum  = Alg0.allocateAnElevator(cfe);
        assertEquals(elevNum, 0);

        cfe = new Call_A(System.currentTimeMillis(), -2, 4);
        elevNum  = Alg1.allocateAnElevator(cfe);
        assertEquals(elevNum, 0);
        cfe  = new Call_A(System.currentTimeMillis(), 10, -1);
        elevNum  = Alg1.allocateAnElevator(cfe);
        assertEquals(elevNum, 0);
        cfe = new Call_A(System.currentTimeMillis(), 3, -2);
        elevNum  = Alg1.allocateAnElevator(cfe);
        assertEquals(elevNum, 0);

        cfe = new Call_A(System.currentTimeMillis(), -2, 4);
        elevNum  = Alg2.allocateAnElevator(cfe);
        assertEquals(elevNum, 1);
        cfe  = new Call_A(System.currentTimeMillis(), 10, -1);
        elevNum  = Alg2.allocateAnElevator(cfe);
        assertEquals(elevNum, 0);
        cfe = new Call_A(System.currentTimeMillis(), 3, -2);
        elevNum  = Alg2.allocateAnElevator(cfe);
        assertEquals(elevNum, 0);

        cfe = new Call_A(System.currentTimeMillis(), -2, 5);
        elevNum  = Alg3.allocateAnElevator(cfe);
        assertEquals(3, elevNum);
        cfe = new Call_A(System.currentTimeMillis(), 5, 10);
        elevNum  = Alg3.allocateAnElevator(cfe);
        assertEquals(2, elevNum);
        cfe  = new Call_A(System.currentTimeMillis(), 20, -1);
        elevNum  = Alg3.allocateAnElevator(cfe);
        assertEquals(1, elevNum);
        cfe = new Call_A(System.currentTimeMillis(), 14, 1);
        elevNum  = Alg3.allocateAnElevator(cfe);
        assertEquals(0, elevNum);

        cfe = new Call_A(System.currentTimeMillis(), -2, 5);
        elevNum  = Alg4.allocateAnElevator(cfe);
        assertEquals(3, elevNum);
        cfe = new Call_A(System.currentTimeMillis(), 5, 10);
        elevNum  = Alg4.allocateAnElevator(cfe);
        assertEquals(0, elevNum);
        cfe  = new Call_A(System.currentTimeMillis(), 20, -1);
        elevNum  = Alg4.allocateAnElevator(cfe);
        assertEquals(1, elevNum);
        cfe = new Call_A(System.currentTimeMillis(), 14, 1);
        elevNum  = Alg4.allocateAnElevator(cfe);
        assertEquals(2, elevNum);

        cfe = new Call_A(System.currentTimeMillis(), -2, 48);
        elevNum  = Alg5.allocateAnElevator(cfe);
        assertEquals(9, elevNum);
        cfe = new Call_A(System.currentTimeMillis(), -1, 25);
        elevNum  = Alg5.allocateAnElevator(cfe);
        assertEquals(8, elevNum);
        cfe  = new Call_A(System.currentTimeMillis(), 50, 30);
        elevNum  = Alg5.allocateAnElevator(cfe);
        assertEquals(7, elevNum);
        cfe = new Call_A(System.currentTimeMillis(), 0, -5);
        elevNum  = Alg5.allocateAnElevator(cfe);
        assertEquals(6, elevNum);

        cfe = new Call_A(System.currentTimeMillis(), -2, 48);
        elevNum  = Alg6.allocateAnElevator(cfe);
        assertEquals(8, elevNum);
        cfe = new Call_A(System.currentTimeMillis(), -1, 25);
        elevNum  = Alg6.allocateAnElevator(cfe);
        assertEquals(6, elevNum);
        cfe  = new Call_A(System.currentTimeMillis(), 50, 30);
        elevNum  = Alg6.allocateAnElevator(cfe);
        assertEquals(4, elevNum);
        cfe = new Call_A(System.currentTimeMillis(), 0, -5);
        elevNum  = Alg6.allocateAnElevator(cfe);
        assertEquals(2, elevNum);

        cfe = new Call_A(System.currentTimeMillis(), 0, 10);
        elevNum  = Alg7.allocateAnElevator(cfe);
        assertEquals(elevNum, 9);
        cfe = new Call_A(System.currentTimeMillis(), 3, 98);
        elevNum  = Alg7.allocateAnElevator(cfe);
        assertEquals(elevNum, 8);
        cfe = new Call_A(System.currentTimeMillis(), 90, -8);
        elevNum  = Alg7.allocateAnElevator(cfe);
        assertEquals(elevNum, 7);

        cfe = new Call_A(System.currentTimeMillis(), 0, 10);
        elevNum  = Alg8.allocateAnElevator(cfe);
        assertEquals(8, elevNum);
        cfe = new Call_A(System.currentTimeMillis(), 3, 98);
        elevNum  = Alg8.allocateAnElevator(cfe);
        assertEquals(6, elevNum);
        cfe  = new Call_A(System.currentTimeMillis(), 90, -8);
        elevNum  = Alg8.allocateAnElevator(cfe);
        assertEquals(4, elevNum);
        cfe = new Call_A(System.currentTimeMillis(), 14, 1);
        elevNum  = Alg8.allocateAnElevator(cfe);
        assertEquals(2, elevNum);

        cfe = new Call_A(System.currentTimeMillis(), 0, 10);
        elevNum  = Alg9.allocateAnElevator(cfe);
        assertEquals(4, elevNum);
        cfe = new Call_A(System.currentTimeMillis(), 3, 98);
        elevNum  = Alg9.allocateAnElevator(cfe);
        assertEquals(2, elevNum);
        cfe  = new Call_A(System.currentTimeMillis(), 90, -8);
        elevNum  = Alg9.allocateAnElevator(cfe);
        assertEquals(5, elevNum);
        cfe = new Call_A(System.currentTimeMillis(), 14, 1);
        elevNum  = Alg9.allocateAnElevator(cfe);
        assertEquals(9, elevNum);
    }
}



