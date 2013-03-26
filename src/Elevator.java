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
	int destination;
	int wait = 0;
	int capacity = 8;	//may be changed
	//time for picking up/leaving people
	//velocity while moving between levels
	ArrayList<Boolean> buttonsPressed=new ArrayList<Boolean>();
	ArrayList<Person> personsInside = new ArrayList<Person>();
    PriorityQueue<Integer> queue = new PriorityQueue<Integer>(10, new Comparator<Integer>() {	
    		//compare sorts the queue in different orders depending on the direction of the elevator
			public int compare(Integer o1, Integer o2) {
				switch (direction){
					case 1: 
						//up
						if (o1 > o2){
							return o2;
						}
						else{return o1;}
					case 2: 
						//down
						if (o1 < o2){
							return o2;
						}
						else{return o1;}
			}
				return o2; }
	        });
    
	//måste speca ordning på kön, använd .comparator
	public Elevator(int floors, int entrance, int id1) {

		id = id1;
		for (int i = 0; i < floors; i++) {
			buttonsPressed.add(false);
		}
	}

	public ArrayList<Boolean> getButtonsPressed(){
		return buttonsPressed;
	}
	public void setDirection(int d){
		this.direction = d;
	}
	public int getId(){
		return id;
	}

	public void addToQueue(int dest){
		queue.add(dest);
		//adds the incoming request to the queue where destination is the drop/pickup place 
		//check if request exists
	}
	// move the elevator a timestep
	public void timeStep(ArrayList<Floor> floorList, int time){
		//checks if the elevator is picking up/dropping people
		System.out.println("Elevator: "+this.id);
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
		if (!(direction == 0)){
			if (direction == 1){
				currentlyAtFloor++;
				System.out.println("Elevator moving up to: "+currentlyAtFloor);
			}
			else{currentlyAtFloor--;
				System.out.println("Elevator moving down to: "+currentlyAtFloor);
			}
		}
	}
	
	public void requestAtFloor(int time, ArrayList<Floor> floorList){
		//if someone has requested to get off at this floor
		if (buttonsPressed.get(this.currentlyAtFloor) == true){
			// remove affected from elevator
			for (Person person : personsInside) {
				if (person.getDestination() == currentlyAtFloor){
					personsInside.remove(person);
					//TODO take care of of timestamps 
					person.setTotalTime(time);
					person.setFinished();
					System.out.println("Person nr "+person.getID()+ " just got off, total time in system: "+person.getTotalTime());
				}
			}
		}

		// add persons to elevator
		Floor floor = floorList.get(currentlyAtFloor);
		for (Person person : floor.getPeople()) {
			if (person.getDirection() == direction){
				personsInside.add(person);
				person.setWaitingTime(time);
				floorList.get(this.currentlyAtFloor).removePerson(person);
				System.out.println("Person nr "+person.getID()+" just entered the elevator with waitingtime: "+person.getWaitingTime());
			}
		}
		queue.poll();
		time = 20;
	}
}
