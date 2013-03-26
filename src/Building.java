import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public class Building {
	//represents a building 

	private int floors; // the number of floors in the building
	private int id = 1;
	private ArrayList<Floor> floorList;
	public int time; 
	public ArrayList<Person> peopleInSystem = new ArrayList<Person>();
	public ArrayList<Elevator> elevators = new ArrayList<Elevator>();
	// creates the different strategies and puts them in a list to be accessible
	BasicStrat Simple = new BasicStrat();
	ZoneStrat Zone = new ZoneStrat();
	ElevatorStrategy [] strategies={Simple , Zone};	
	Random r = new Random();

	public Building(int nrfloors, int nrElevators) { //id = vilken v�ning, maxFloor = vilken �versta v�ningen �r
		floors = nrfloors;
		time = 0;
		for (int i = 0; i < nrElevators; i++) {
			elevators.add(new Elevator(floors,0));
		}

		//FOR-loop f�r att initiera alla floors
		Floor tempFloor;
		for (int j = 0; j < floors; j++) {
			tempFloor = new Floor(j, (floors-1));
			floorList.add(tempFloor);
		}

		run();
		finished();
	}
	public Person generatePerson(){
		int atFloor = 0;
		int dest = r.nextInt()%floors;
		//generate new numbers to make sure dest != atFloor
		while(dest == atFloor){
			dest = r.nextInt()%floors;
		}
		return new Person(id, dest, atFloor, time);
		//generates persons with help from a poisson distribution
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
		System.out.println("Mean total time in system: "+ totalTime/size+"\n"+ "Mean waiting time: "+waitingTime/size);
	}

	private void run() {
		//eller for, med diskret tidssteg till en viss tid 
		ExponentialDistribution e = new ExponentialDistribution(1/10);
		System.out.println(Math.abs(e.sample()));	
	    boolean hej = true;
		while (hej){
			//generate person from poisson distribution
			Person newPerson = generatePerson();
			peopleInSystem.add(newPerson);
			id++;
			//getElevator places the new person in the right queue and handles waiting persons
			strategies[0].getElevator(newPerson, elevators);
			//timestep 
			for (Elevator elevator : elevators) {
				elevator.timeStep(floorList, time);
			}
			hej = false;
			time++;
		}
	}

}