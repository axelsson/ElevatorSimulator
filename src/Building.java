import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.distribution.ExponentialDistribution;

/* TODO :
 * max antal personer i hiss
 * zonestrat!!!!!!!!!!!!!!!
 * tidssystem (lägg till för avstigning, påstigning, acceleration)
 * ankomster med exp
 * optimering till basic (t ex ha ett defaultfloor)
 * mer statistik (högsta vänte/totaltiden, fördelning mellan våningar, förhållande mellan väntetid
 *  och totaltid, total körsträcka per hiss, fördelning mellan hissar) 
 * dubbelkolla personer i hissen så att folk verkligen kliver av 
 * */
public class Building {
	//represents a building 
	private boolean arrival;
	private int floors; // the number of floors in the building
	private int id = 1;
	private ArrayList<Floor> floorList = new ArrayList<Floor>();
	public int time; 
	public ArrayList<Person> peopleInSystem = new ArrayList<Person>();
	public ArrayList<Elevator> elevators = new ArrayList<Elevator>();
	// creates the different strategies and puts them in a list to be accessible
	BasicStrat Simple = new BasicStrat();
	ZoneStrat Zone = new ZoneStrat();
	ElevatorStrategy [] strategies={Simple , Zone};	
	Random r = new Random();

	public Building(int nrfloors, int nrElevators, boolean arrivalValue) { 
		arrival = arrivalValue;
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
		Building building = new Building(5,2, true);
		building.run();
		building.finished();
		//TODO fix exp dist. 
		ExponentialDistribution e = new ExponentialDistribution(1.0);
		System.out.println(e.sample());
	}
	
	//arrivalFloor returns a floor from which the newly generated person will arrive
	public int arrivalFloor(){
		// floor 0 is the default entrance of the building
		int floor = 0;
		if (!arrival){
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
		if (arrival){
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
		int size = peopleInSystem.size();
		int waitingTime = 0;
		int totalTime = 0;
		for (Person p : peopleInSystem) {
			waitingTime += p.getWaitingTime();
			totalTime += p.getTotalTime();
		}
		System.out.println("*****************************************");
		System.out.println("Mean total time in system: "+ totalTime/size+"\n"+ "Mean waiting time: "+waitingTime/size);
	}

	private void run() {
		boolean timer = true;
		while (timer){
			System.out.println("--------------------------------Time: "+time+"---------------------------");
			//generates persons with help from a exponential distribution
			if (true){
				Person newPerson = generatePerson();
				//add the new person to their current floor
				Floor currentFloor = floorList.get(newPerson.getPosition());
				currentFloor.addPerson(newPerson);
				
				//the new person will push the button on the floor unless it's already pushed
				if (newPerson.direction == 1){
					if (!currentFloor.isbtnUpOn()){
						currentFloor.setbtnUpOn(true);
						strategies[0].addToWaitingList(newPerson);
					}
				}
				else {
					if (!currentFloor.isbtnDownOn()){
						currentFloor.setbtnDownOn(true);
						strategies[0].addToWaitingList(newPerson);
					}
				}
				System.out.println("new person "+  newPerson.getID()+" at: "+ newPerson.getPosition() +" dest: "+newPerson.getDestination());
				//generate person from poisson distribution
				peopleInSystem.add(newPerson);
				id++;
				//getElevator places the new person in the right queue and handles waiting persons
				
			}
			strategies[0].getElevator(elevators);
			//timestep 
			for (Elevator elevator : elevators) {
				elevator.timeStep(time, floorList);
			}
			time++;
			if (time == 75){
				timer = false;
			}
		}
	}

}