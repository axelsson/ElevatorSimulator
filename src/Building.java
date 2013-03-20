import java.util.ArrayList;

public class Building {
//represents a building 

  private int floors; // the number of floors in the building

	private Elevator elev1;
	private Elevator elev2;
	private Floor tempFloor;
	private ArrayList<Floor> floorList;
	private Elevator[] elevators= new Elevator[2]; 

	public Building(int nrfloors, int nrElevators) { //id = vilken v�ning, maxFloor = vilken �versta v�ningen �r
		// TODO Auto-generated constructor stub
		floors = nrfloors;
		elevators[0] = new Elevator(floors,0);
		elevators[1] = new Elevator(floors,0);
		nrElevators = elevators.length;

		//FOR-loop f�r att initiera alla floors
		for (int j = 0; j < floors; j++) {
			tempFloor = new Floor(j, (floors-1));
			floorList.add(tempFloor);

		}

		run();
	}
	public Person generatePerson(){
		return new Person(floors, floors, floors);
		//generates persons with a poisson distribution
	}

	private void run() {
		// creates the different strategies and puts them in a list to be accessible
		BasicStrat Simple = new BasicStrat();
		ZoneStrat Zone = new ZoneStrat();
		ElevatorStrategy [] strategies={Simple , Zone};
		
		while (true){
			//generate person from poisson distribution
			Person newPerson = generatePerson();
			int handlingElevator = strategies[1].getElevator(newPerson.getPosition(), newPerson.getDestination(), elevators);
			//adds the new request to the chosen elevator
			elevators[handlingElevator].addToQueue(newPerson.getDestination());
			
			//decide which elevator will handle the request
			//put the request (person) in queue for handlingElevator
			//timestep 
			for (Elevator elevator : elevators) {
				elevator.timeStep();
			}
		}

	}

}