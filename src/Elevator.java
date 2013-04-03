import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
public class Elevator {
	int wait = 0;
	int id;
	int floors = 1;
	int entrance = 0;
	int currentlyAtFloor = 0;
	//direction: 0 = not moving, 1= up, 2 = down
	public int direction = 0;	
	int capacity = 8;	
	ArrayList<Person> personsInside = new ArrayList<Person>();
	PriorityQueue<Integer> queue = new PriorityQueue<Integer>(10, new Comparator<Integer>() {	
		//compare sorts the queue in different orders depending on the direction of the elevator
		public int compare(Integer o1, Integer o2) {
			switch (direction){
			case 1: 
				//up sorts values in default order
				if (o1 > o2){
					return 1;
				}
				else{return -1;}
			case 2: 
				//down sorts the values backwards
				if (o1 < o2){
					return 1;
				}
				else{return -1;}
			}
			return 1; }
	});

	public Elevator(int floors, int entrance, int id1) {
		this.floors = floors;
		id = id1;
	}

	public void setDirection(int d){
		this.direction = d;
	}
	public int getId(){
		return id;
	}
	//adds a destination to the queue if it doens't exist yet
	public void addToQueue(int dest){
		if (!queue.contains(dest)){
			queue.add(dest);
		}

	}
	// move the elevator a timestep
	public void timeStep(int time, ArrayList<Floor> floorList){
		
		System.out.println("Elevator "+this.id+ " at floor: "+ this.currentlyAtFloor);
		if (2 >= wait && wait > 0){
			wait -= 1;
			return;
		}
		//checks if the elevator is picking up/dropping people
		if (queue.contains(this.currentlyAtFloor)==true ){
			System.out.println("Elevator stops at floor: "+currentlyAtFloor);
			requestAtFloor(time, floorList);
		}
		if (queue.isEmpty()){
			this.direction = 0;
		}
		System.out.println("in elevator: direction of elevator: "+this.direction);

		//move the elevator one step towards the next queued floor
		if (!(direction == 0)){
			if(queue.peek()>this.currentlyAtFloor){
				currentlyAtFloor++;
				System.out.println("Elevator moving up to: "+currentlyAtFloor);
			}
			else if (queue.peek() < this.currentlyAtFloor){
				currentlyAtFloor -= 1;
				System.out.println("Elevator moving down to: "+currentlyAtFloor);
			}
		}
		System.out.println("Elevator "+ this.id + " queue " + this.queue.toString());
	}

	//requestAtFloor drops/picks up persons at the current floor
	public void requestAtFloor(int time, ArrayList<Floor> floorList){
		queue.poll();
		//Time added for opening and closing doors
		wait += 4;
		if (this.queue.isEmpty()){
			direction = 0;
		}

		if (!personsInside.isEmpty()){
			boolean gotOff = false;
			// remove affected from elevator
			ArrayList<Person> temp = new ArrayList<Person>();
			for (Person person : personsInside) {
				if (person.getDestination() == currentlyAtFloor){
					gotOff = true;
					temp.add(person);
					person.setTotalTime(time);
					person.setFinished();
					System.out.println("Person "+person.getID()+ " got off from elev "+ this.getId() + " , total time in system: "+person.getTotalTime());
				}
			}
			//separate removal to avoid concurrency problems
			for (Person person : temp) {
				personsInside.remove(person);
			}	
			if ( gotOff){
				//time added for disembarking people
				wait += 2;
			}
		}
		// add persons to elevator
		Floor floor = floorList.get(currentlyAtFloor);
		ArrayList<Person> temp = new ArrayList<Person>();
		boolean gotOn = false;
		for (Person person : floor.getPeople()) {
			//if person on the floor either has the same direction as the elevator or the elevator is idle...
			if (person.getDirection() == direction || direction == 0){
				if (direction == 0){
					direction = person.getDirection();
				}
				personsInside.add(person);
				gotOn = true;
				this.addToQueue(person.getDestination());
				person.setWaitingTime(time);
				System.out.println("Elevator "+this.id+" just queued person "+person.getID()+ " for floor "+person.getDestination()+" with waitingtime "+ person.getWaitingTime());
				temp.add(person);
			}
		}

		//remove persons afterwards to avoid concurrency problem
		for (Person person : temp) {
			floor.removePerson(person);
			if (gotOn){
				//time added for picking up people
				wait += 4; 
			}
			//reset up/down buttons
			if (person.direction == 1){
				floor.setbtnUpOn(false);
			}
			else if (person.direction == 2){
				floor.setbtnDownOn(false);
			}
		}
	}
}
