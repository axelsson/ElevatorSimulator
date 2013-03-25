import java.util.ArrayList;

import org.apache.commons.math3.distribution.PoissonDistribution;

public class Building {
	//represents a building 

	private int floors; // the number of floors in the building
	private int id = 1;
	private ArrayList<Floor> floorList;
	private Elevator[] elevators= new Elevator[2]; 
	public int time; 

	public Building(int nrfloors, int nrElevators) { //id = vilken v�ning, maxFloor = vilken �versta v�ningen �r
		floors = nrfloors;
		time = 0;
		elevators[0] = new Elevator(floors,0);
		elevators[1] = new Elevator(floors,0);
		nrElevators = elevators.length;

		//FOR-loop f�r att initiera alla floors
		Floor tempFloor;
		for (int j = 0; j < floors; j++) {
			tempFloor = new Floor(j, (floors-1));
			floorList.add(tempFloor);
		}

		run();
	}
	public Person generatePerson(){
		return new Person(id, 2, 0, time);
		//generates persons with help from a poisson distribution
	}

	private void run() {
		// creates the different strategies and puts them in a list to be accessible
		BasicStrat Simple = new BasicStrat();
		ZoneStrat Zone = new ZoneStrat();
		ElevatorStrategy [] strategies={Simple , Zone};

		PoissonDistribution generator = new PoissonDistribution(50);
		

		//eller for, med diskret tidssteg till en viss tid 
		while (true){
			//generate person from poisson distribution
			Person newPerson = generatePerson();
			id++;
			//getElevator places the new person in the right queue and handles waiting persons
			strategies[0].getElevator(newPerson, elevators);
			//timestep 
			for (Elevator elevator : elevators) {
				elevator.timeStep(floorList, time);
			}
			time++;
		}

	}

}