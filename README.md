# OOP_Ex0_Java

## Literature Survey
### Problem area:
In a building with several floors, there are m elevators. The elevators in the building are smart elevators, meaning a passenger chooses the floor he wants to reach while he is ordering the elevator.
The most suitable elevator for passengers should be installed so that their average travel time (from the time of reading for the elevator to the time of getting out of it) will be minimal and extremely efficient.
- What are smart elevators?
- Which elevator will be placed for each person ordering an elevator?
- What to do in case there are two passengers on the floor and one has to go up and the other down? Will the elevator pick them both up?
- How will the passengers be placed in the elevators so that the average waiting time of a passenger from the time of calling the elevator to the time of arrival at the destination will be optimal?
- What to do if the two elevators are at the same distance from a passenger but one has passengers and the other is empty?
- How to make sure that every elevator call is accepted? That is, how to make sure the program is online.

### Requirements:
- People click on which floor they want to go up / down when on the maximum floor you only have to go down and on the minimum floor you only have to go up.
- As soon as a call to the elevator is received, the passenger should be placed in the most suitable elevator.
- The elevator has a maximum and minimum floor and it cannot go up or down beyond these floors.
- The elevator stops only on floors where there is a call from passengers or reaching a person's destination in an elevator.
- The elevator that responds to the call is the nearest elevator / elevator that will take a minimum of time to reach / an elevator that is in any case the floor in its own way.
- The elevator will transport passengers in a minimum of time.
- Even while resting all the elevators check regularly if new calls have been received.
- The elevator has a travel time and a fixed stopping time (when placing passengers in an elevator, this should be taken into account).
- Each floor can have a number of passengers - the number of passengers is relevant only when there is one who wants to get on and the other to get off (because it is not necessary to take into account the lift load and the amount of passengers allowed).
- When calling the elevator there are several options: 1) The elevator is on the requested floor. 2) The elevator above the requested floor. 3) The elevator below the requested floor.

## 3 similar works that deal with the optimization of elevators that seemed to us relevant to this task:
1. https://www.geeksforgeeks.org/smart-elevator-pro-geek-cup/
2. https://thinksoftware.medium.com/elevator-system-design-a-tricky-technical-interview-question-116f396f2b1c
3. https://stackoverflow.com/questions/493276/modelling-an-elevator-using-object-oriented-analysis-and-design

## Differences between algorithms and the wording of an "offline" algorithm
### Differences between an online algorithm and an offline algorithm:
**Online algorithm:** constantly receives data from users, the program operates according to information received in real time and not according to pre-known data. In the online algorithm the program constantly adjusts itself.
With us: The passengers' calls to the elevators are made all the time in real time, so the elevator must be prepared according to the calls. After each reading, a passenger rides to his optimal elevator according to the condition of the elevators at the current time.

**Offline Algorithm:** An algorithm that performs operations based on prior information. Each measure is transmitted as input or data before the program is executed and then the program performs the operation in accordance with all existing data.
With us: An offline elevator algorithm is an algorithm in which all elevator calls are made before the elevator operates. Once all the calls have been made they will be placed in the appropriate elevators once according to what is most appropriate. The adjustment will be made with an overall view of all the readings and therefore will also be more efficient than an online algorithm.

An offline algorithm makes it possible to build a more efficient plan because all the information is available to it in advance and therefore it will act according to it. An online algorithm will be less efficient because it can not predict the following actions of users and therefore may in the long run be less efficient, although it has many benefits such as real-time matching.

### Offline algorithm for the problem of embedding elevator calls:
All lifts are accepted in advance.
We have n elevators.
There will be a reading department that will include for each reading: reading floor, destination, direction (up / down), time - from entering the elevator to getting out of it does not include the stopping time on the way (can be calculated according to the number of floors to go).

Each elevator stop has a pre-set time in the program, so after we receive all the calls we will define a variable that will save the total stop time: the number of calls * 2 * elevator stop time. (Each call has 2 stops - on the floor and at the destination). Stop time = opening time of the doors + closing time of the doors + time that the doors remain open in order for the passengers to get in and out.

At the beginning we will place the floors in a sorted arrangement as follows:
The lowest floor that needs to be called upwards first and so on .. After finishing all the calls that need to go upwards start inserting into the array the highest floor whose target floor is downstairs and so on .. until you reach the smallest floor where the passenger has to go down.

We calculate the total time of all the stops of the elevator that we calculated earlier + time-calculation of the total time of all the readings according to the variable that constructs for each "reading" object and women in the variable.

Divide the total time by the number of elevators. The answer is called t.
There will be a variable which will check each cell in the array and see if after the current reading it checks the amount of time until the current reading has reached t. If he did not pass the t, he will check with together with the next reading the amount reached t or passed it and so on .. when he reaches the amount that reached t or passed it he will assign an elevator that will handle all the readings until the last reading he checked.
He will then reset the time and check from the next call if the time has come to t in order to allocate her the next elevator and so on .. until all the elevators are allocated according to the total time so that each passenger will wait the minimum necessary time for an elevator to reach it (average).

A queue was built for each reading elevator installation.
The queue will contain only the destination floor and the reading floor for the elevator, i.e. only the elevator stop points (int) in ascending order if passengers need to ascend and then in descending order if passengers need to descend.
This is how each elevator goes according to the floors indicated in the queue until there are no organs (stop floors) left in the queue and then it will stop - rest.

## "Online" Algorithm Formulation:
Elevator mode (f): Ascent, descent, and rest.
The elevators will start the program on the ground floor.
The person can click on an existing floor and not beyond.

How the program works:
When a person presses the keyboard, he should be assigned an elevator. The algorithm checks a few things:
Is there an elevator on his floor?
If so, is the elevator rested and empty?
   If so, then the elevator is assigned to it.
     If not, does the elevator go up / down in the direction the passenger wants?
          If so, the elevator is assigned to it. Otherwise, check another elevator if any. If not available
          Another elevator, the elevator will continue in its direction (up / down) and after lowering the passengers
          In its direction it will go down / rise according to the location of the passenger and the place he wants to reach.
If not, check the direction of the user, up or down. And then he is assigned the elevator that will reach him in the minimum time. In checking the appropriate elevator it should be noted that there may be an elevator in the opposite direction but it will still arrive faster than an elevator that is resting or in the direction requested by the passenger (calculate the stop time of each passenger and the number of floors they travel until reaching the new user).

**Example:** A passenger on the 5th floor calls for an elevator to go down to a lower floor.
There is an elevator that goes down, but it is on the 100th floor so it has at least 95 floors to go down (without calculating how many stopping points there are).
On the other hand, there is another elevator that is on the 2nd floor. It goes up to the 8th floor and ends the journey, that is, it does not travel in the direction of the new user, ostensibly it is not suitable for the passenger, but eventually it will have 7 floors to go up and 2 more floors to go down. "About 9 floors does not include stopping points but still 9 is much smaller than 95 so it is the more suitable elevator. Therefore not necessarily an elevator that goes in the right direction is the most suitable elevator and therefore all elevators should be checked for each reading.
Etc. should also check the discount elevators if they are closer to the new user.

- Each elevator has a node list in the order of the stop.
- The readings are sorted according to priorities that will be indicated later in ascending order if the elevator ascends and in descending order if the elevator descends.
- As soon as the elevator stops on a certain floor it is deleted from the list.
- If a destination floor or reading is already on the list of the elevator that has been placed for the passenger, they will not be included because it is a list of stops, and these stops already exist in the elevator.
- The minimum floor, maximum and number of elevators are accepted at the beginning of the plan as a given.
- If the elevator is not available and ascends (or descends) then it will continue to ascend without descending and if necessary collect only the people ascending on the way and only after the elevator is empty and resting will it be able to pick up the people descending. Example: Elevator A on the 4th floor which has a stop on the 6th floor, Elevator B rests on the 2nd floor, and there is a click on the 5th floor of a descent. Which elevator will you reach? Elevator A has 3 floors + one stop on the 6th floor to reach the person who pressed. Elevator B has 3 floors without stops so Elevator B will reach the person who is pressing.
- It is possible to stop the elevator before the destination floor in case the elevator has not passed the new desired floor. The new destination floor will fit into the current elevator where the passenger is.

The algorithm checks the priority of which elevator will fit the passenger according to:
The lifts will be placed according to a list of priorities that will be detailed below - the elevator that returns the lowest priority is the one that will be placed for the passenger. If 2 or more elevators have the same priority, we will check which elevator has the lowest waiting time and will be placed. If there are still lifts the same (both in priority and while waiting) we will check the minimum time that the passenger is waiting in the lift and compare the lifts - and according to that we will be placed.
(If still the same in everything the last elevator we checked is the one that will be embedded).

- In each node, write a floor where the elevator stops (both floors where passengers need to be picked up and destination floors). When the node list is empty it means that the elevator is at rest, when it is not empty the last node indicates the last destination floor and the first node indicates the next destination.

Priorities according to the algorithm test:
- Priority 0: The elevator rests and is on the floor.
- Priority 1: Rests and is not on the floor.

If the elevator is not in one of the above priorities, it will check which "block" the reading should be placed in, when in practice there can be 3 blocks -
Block 1: In case the call is in the same direction of the beginning of the list and has not yet moved its floor.
Block 2: In case the reading is in the opposite direction - the reading will fit into Block 2, Block 2 is the block that is after passing all the floors that go in the same direction at the beginning of the list.
Block 3: In case the reading is:
- Same direction and did not pass the reading floor ("Block 1"):
If going up - returns to where to place in the first "block". Same thing if going down.
 - Priority 3: If the origin and destination are in the third (ascending / descending) list - calculate the time and return an INFOELEVATOR variable.
 - Priority 4: If there is only an exit in the elevator stop list. Calculates the time and returns an INFOELEVATOR variable.
 - Priority 5: There is only the target floor in the elevator stop list. Calculates the time and returns an INFOELEVATOR variable.
 - Priority 6: If there is no way out and no destination in the elevator - calculate the waiting time and return a variable
Of the INFOELEVATOR type.
- Not in the same direction ("Block 2")
There is an action that returns the position where the elevator changes direction. And begins to check the priorities from where the elevator changes direction.

 - Priority 3: If the origin and destination are in the third (ascending / descending) list - calculate the time and return an INFOELEVATOR variable.
 - Priority 4: If there is only an exit in the elevator stop list. Calculates the time and returns an INFOELEVATOR variable.
 - Priority 5: There is only the target floor in the elevator stop list. Calculates the time and returns an INFOELEVATOR variable.
 - Priority 6: If there is no way out and no destination in the elevator - calculate the waiting time and return a variable
Of the INFOELEVATOR type.

- Same direction and passed the reading floor ("Block 3"):
Goes through the whole list until he reaches the end of the "up", if there is a down - he will go to the end of the "down" and then place in the appropriate place (2 actions: up and down).
 - Sends to the action After finding the appropriate "segment" for placement in the elevator list, the action will return to exactly where to embed the call (according to the PLACETO department).
 - Priority 3: If the origin and destination are in the third (ascending / descending) list - calculate the time and return an INFOELEVATOR variable.
 - Priority 4: If there is only an exit in the elevator stop list. Calculates the time and returns an INFOELEVATOR variable.
 - Priority 5: There is only the target floor in the elevator stop list. Calculates the time and returns an INFOELEVATOR variable.
 - Priority 6: If there is no way out and no destination in the elevator - calculate the waiting time and return
INFOELEVATOR variable.

**What is the purpose of the algorithm?**
That the person pressing the keypad will wait the least time until the elevator arrives or that the person will take the elevator ride in the least amount of time?
The goal is to call the elevator from the source floor to the destination floor - the system will want to insert the elevator that will minimize the arrival time - the arrival time is set to be the time in seconds between the call to the elevator and the arrival to the destination floor. More generally it is said that given a collection of lifts calls on time we would like to define an elevator placement strategy for calls that will minimize the total arrival time for all calls.

## Departments:
**Building**: the name of a building, the maximum number of floors in a building, the minimum number of floors in a building, the number of elevators in a building, the array of elevators (each place in the array symbolizes an elevator).
**Elevator**: status (ascending and descending rest), current floor, door opening time, door closing time, stop time, movement start time, and speed, stop node list-
**CallForElevator**: status (up or down), elevator assignment, reading source, destination, time the call was made, elevator location at the moment of assignment
**PlaceTo**: Returns if there is the same origin and the same destination and their location. If there is not the same origin and destination returns where to place the call and destination, returns data for time calculation.
**InfoElevator**: Includes the number of the elevator being inspected, the waiting time of the passenger, the time it takes to get in and out of the elevator, the number of priority of the elevator (according to the above details), where to place the departure and destination stops on the list.

## The data interfaces:
What each class performs according to the functions that are in the interface and the class implements:

**In the Building interface:**
getBuildingName () - Returns the name of the building
minFloor () - Returns the number of minimum floors in a building
maxFloor () - Returns the number of max floors in the building
numberOfElevetors () - Returns a number of the number of elevators in the building
getElevetor (int i) - returns an elevator (object) according to the index that the function receives

**In the CallForElevator interface:**
getState () - Returns the current state
allocateElevetor (int ind) - Assign an elevator to the floor that the function receives
reachedSrc () - reached the origin
reachedSDest () - reached destination
getTime (int state) - Returns a decimal number of the time in seconds of the given state if there is not yet a return name -1
getSrc () - Returns a number that represents the source floor of this elevator call was
getDest () - Returns a number that represents the target floor to which this elevator call is directed
getType () - Returns call type (up or down)
allocatedTo () - Returns a number that represents the location of the elevator in the building to which the call has been assigned if it has not yet been assigned a return1.


**In the Elevator interface:**

getMinFloor () - returns a number that represents the minimum floor that this elevator can reach
getMaxFloor () - Returns a number that represents the maximum floor that this elevator can reach
getTimeForOpen () - returns a number that represents the time of in a few seconds takes the elevator to open its doors
getTimeForClose () - returns a number that represents the time of in a few seconds takes the elevator to close its doors
getState () - returns the current state of the elevator (goes up, down, that the elevator has reached the floor and is ready to load and unload and receive new orders, error)
getPos () - Returns a number of the current floor of the elevator as an integer
goTo (int floor) - Moves the elevator from the current location to the destination floor
stop (int floor) - allows the elevator to stop on the mezzanine floor between the exit and the destination, assuming that the elevator has not passed the desired floor where it should stop
getSpeed() - returns the speed to the floor in seconds, for example if the elevator speed is 0.4 then it takes 2.5 seconds to go through one floor
getStartTime () - returns the time in seconds it takes for the elevator to start moving at full speed assuming it is a fixed value
getStopTime () - returns the time in seconds it takes for the elevator to stop the speed when it is running at full speed assuming it is a fixed value
floorsSum () - Returns the total floors that the elevator has to go through according to its list


## Main UML:
![1](https://user-images.githubusercontent.com/93534494/146743750-d70013c1-67fd-4c1f-a07d-546c84e91a7f.jpg)


