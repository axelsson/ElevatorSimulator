import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.distribution.ExponentialDistribution;


public class Building {
	//represents a building 
	private int arrival;
	ArrayList<Integer> test = new ArrayList<Integer>();
	private int floors; // the number of floors in the building
	private int id = 1;
	private ArrayList<Floor> floorList = new ArrayList<Floor>();
	public int time = 1; 
	public ArrayList<Person> peopleInSystem = new ArrayList<Person>();
	public ArrayList<Elevator> elevators = new ArrayList<Elevator>();
	// creates the different strategies and puts them in a list to be accessible
	BasicStrat Simple = new BasicStrat();
	ZoneStrat Zone = new ZoneStrat();
	ElevatorStrategy [] strategies={Simple , Zone};	
	Random r = new Random();
	int strategy = 0;

	public Building(int nrfloors, int nrElevators, int type, int strat) { 
		strategy = strat;
		arrival = type;
		floors = nrfloors;
		time = 0;
		for (int i = 0; i < nrElevators; i++) {
			elevators.add(new Elevator(floors,0, i));
		}

		//FOR-loop f�r att initiera alla floors
		Floor tempFloor;
		for (int j = 0; j < floors; j++) {
			tempFloor = new Floor(j, (floors-1));
			floorList.add(tempFloor);
		}
	}
	public static void main(String[] args){
									//floors, elevators, type, stategy
		Building building = new Building(10,2, 2, 1);
		building.run();
		building.finished();		
	}

	//arrivalFloor returns a floor from which the newly generated person will arrive
	public int arrivalFloor(){
		// floor 0 is the default entrance of the building
		int floor = 0;
		if (!(arrival == 1)){
			floor = Math.abs(r.nextInt()%floors);
			while(floor == 0){
				floor = Math.abs(r.nextInt()%floors);
			}
		}

		return floor;
	}
	public Person generatePerson(){
		int atFloor = arrivalFloor();
		int dest = 0;
		if (!(arrival == 2)){
			dest = Math.abs(r.nextInt()%floors);
			//generate new numbers until dest != atFloor
			while(dest == atFloor){
				dest = Math.abs(r.nextInt()%floors);
			}
		}

		return new Person(id, dest, atFloor, time);
	}

	public void finished(){
		//calculate mean time for waiting and total time when finished
		int totalPersons = peopleInSystem.size();
		int size = 0;
		int waitingTime = 0;
		int totalTime = 0;
		int longestWait = 0;
		int longestTotal = 0;
		int totalTravelDistance = 0;
		System.out.println("*****************************************");
		for (Elevator elev : elevators) {
			int d = elev.personDistance;
			System.out.println("Elevator"+elev.getId()+ " traveled "+elev.totalTravel+" personDistance: "+d+", picked up: "+elev.pickedUp);
		}
		for (Person p : peopleInSystem) {
			if (p.isFinished()){
				totalTravelDistance += p.distance;
				size++;
				waitingTime += p.getWaitingTime();
				totalTime += p.getTotalTime();
//				System.out.println("wait: "+p.getWaitingTime()+ " total: "+p.getTotalTime()+ " dist: "+p.distance);
				if (p.getWaitingTime() > longestWait){longestWait = p.getWaitingTime();}
				if (p.getTotalTime() > longestTotal){longestTotal = p.getTotalTime();}
			}
		}
		if(size > 0){
			System.out.println( "Mean waiting time: "+waitingTime/size);
			System.out.println("Mean total time in system: "+ totalTime/size);
			System.out.println("Persons in system: "+size+" \nlongest waititing time: "+longestWait+" \nlongest total time: "+ longestTotal);
			System.out.println("People in system: "+totalPersons+" finished people:"+size);
			System.out.println("Total travel distance for people: "+totalTravelDistance);
		}
//		for (Integer i : test) {
//			System.out.println(i);
//		}
		
	}

	private void run() {
		ElevatorStrategy str = strategies[strategy];
		//we expect 300 arrivals in an hour
		//lambda = 1/4 since we expect 1 arrival per 12 sec == 4 time units, the mean is therefore 1/0.25
		// new value for 2hrs/300 persons: 1/0.25
		ExponentialDistribution e = new ExponentialDistribution((1/0.125));
		int timeForArrival = (int)(e.sample());
		test.add(timeForArrival);
		//time 2400 = 2h 
		while (time < 100){
			System.out.println("--------------------------------Time: "+time+"---------------------------");
			//generates persons with help from a exponential distribution
			if (timeForArrival == time){
				Person newPerson = generatePerson();
				//add the new person to their current floor
				Floor currentFloor = floorList.get(newPerson.getPosition());
				currentFloor.addPerson(newPerson);

				//the new person will push the button on the floor unless it's already pushed
				if (newPerson.direction == 1){
					if (!currentFloor.isbtnUpOn()){
						currentFloor.setbtnUpOn(true);
						str.addToWaitingList(newPerson);
					}
				}
				else {
					if (!currentFloor.isbtnDownOn()){
						currentFloor.setbtnDownOn(true);
						str.addToWaitingList(newPerson);
					}
				}
				System.out.println("new person "+  newPerson.getID()+" at: "+ newPerson.getPosition() +" dest: "+newPerson.getDestination());
				//generate person from poisson distribution
				peopleInSystem.add(newPerson);
				id++;
				//generate the new time for arrival
				timeForArrival = (int)(e.sample())+time+1;
				test.add(timeForArrival);
			}
			for (Elevator elev : elevators) {
				if (elev.denied.size() > 0){
					for (Person p : elev.denied) {
						str.addToWaitingList(p);
					}
					elev.denied.clear();
				}
			}

			str.getElevator(elevators);
			//timestep 
			for (Elevator elevator : elevators) {
				elevator.timeStep(time, floorList);
			}
			time++;
		}
//		for (int i = 0; i <40; i++) {
//			System.out.println((int)(e.sample()));
//		}
	}

}