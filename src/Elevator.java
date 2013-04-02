import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
public class Elevator {
	//Sarah test kommentar
	//mappning från våningstryck till hissobjekt? 
	int id;
	int floors = 1;
	int entrance = 0;
	//control panel 
	int currentlyAtFloor = 0;
	//direction: 0 = not moving, 1= up, 2 = down
	public int direction = 0;	
	int wait = 0;
	int capacity = 8;	//may be changed
	//time for picking up/leaving people
	//velocity while moving between levels
	ArrayList<Person> personsInside = new ArrayList<Person>();
	PriorityQueue<Integer> queue = new PriorityQueue<Integer>(10, new Comparator<Integer>() {	
		//compare sorts the queue in different orders depending on the direction of the elevator
		public int compare(Integer o1, Integer o2) {
			switch (direction){
			case 1: 
				//up
				if (o1 > o2){
					return 1;
				}
				else{return -1;}
			case 2: 
				//down
				if (o1 < o2){
					return -1;
				}
				else{return 1;}
			}
			return 1; }
	});

	//måste speca ordning på kön, använd .comparator
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
	//TODO beter sig jättekonstigt om man köar fler än 2, men verkar justera sig och fungera ändå.
	public void addToQueue(int dest){
		if (!queue.contains(dest)){
			queue.add(dest);
		}
		//adds the incoming request to the queue where destination is the drop/pickup place 
		//check if request exists
	}
	// move the elevator a timestep
	public void timeStep(ArrayList<Floor> floorList, int time){
		//checks if the elevator is picking up/dropping people
		System.out.println("Elevator "+this.id+ " at floor: "+ this.currentlyAtFloor);
		if (wait > 0){
			time -= 1;
			return;
		}
		// kollar om hissen stannar/ska stanna
		if (queue.contains(this.currentlyAtFloor)==true ){
			System.out.println("Elevator stops at floor: "+currentlyAtFloor);
			requestAtFloor(time, floorList);
		}
		//hissen har tömt sin kö, sätts till idle 
		if (queue.isEmpty()){
			this.direction = 0;
		}
		System.out.println("in elevator: direction of elevator: "+this.direction);
		//		if (!(direction == 0)){
		//			if (direction == 1){
		//				currentlyAtFloor++;
		//				System.out.println("Elevator moving up to: "+currentlyAtFloor);
		//			}
		//			else{currentlyAtFloor -=1;
		//				System.out.println("Elevator moving down to: "+currentlyAtFloor);
		//			}
		//		}
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

	public void requestAtFloor(int time, ArrayList<Floor> floorList){
		queue.poll();
		//if someone has requested to get off at this floor
		if (this.queue.isEmpty()){
			direction = 0;
		}
		if (!personsInside.isEmpty()){
			// remove affected from elevator
			ArrayList<Person> temp = new ArrayList<Person>();
			for (Person person : personsInside) {
				if (person.getDestination() == currentlyAtFloor){
					temp.add(person);
					person.setTotalTime(time);
					person.setFinished();
					System.out.println("Person "+person.getID()+ " got off from elev "+ this.getId() + " , total time in system: "+person.getTotalTime());
				}
			}
			for (Person person : temp) {
				personsInside.remove(person);
			}	
		}
		// add persons to elevator
		Floor floor = floorList.get(currentlyAtFloor);
		ArrayList<Person> temp = new ArrayList<Person>();
		for (Person person : floor.getPeople()) {
			//if person on the floor either has the same direction as the elevator or the elevator is idle...
			if (person.getDirection() == direction || direction == 0){
				if (direction == 0){
					direction = person.getDirection();
				}
				personsInside.add(person);
				this.addToQueue(person.getDestination());
				person.setWaitingTime(time);
				System.out.println("Elevator "+this.id+" just queued person "+person.getID()+ " for floor "+person.getDestination()+" with waitingtime "+ person.getWaitingTime());
				temp.add(person);
			}
		}
		//remove persons afterwards to avoid concurrency problem
		for (Person person : temp) {
			floor.removePerson(person);
			//reset up/down buttons
			if (person.direction == 1){
				floor.setbtnUpOn(false);
			}
			else if (person.direction == 2){
				floor.setbtnDownOn(false);
			}
		}

		time = 20;
	}
}
