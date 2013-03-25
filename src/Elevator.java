import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
public class Elevator {
	//Sarah test kommentar
	//mappning från våningstryck till hissobjekt? 
	int floors = 1;
	int entrance = 0;
	//control panel 
	int currentlyAtFloor = 0;
	//direction: 0 = not moving, 1= up, 2 = down
	public int direction = 0;	
	int destination;
	int capacity = 8;	//may be changed
	//time for picking up/leaving people
	//velocity while moving between levels
	ArrayList<Boolean> buttonsPressed;
	ArrayList<Person> personsInside;
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
	public Elevator(int floors, int entrance) {
		for (int i = 0; i < floors; i++) {
			buttonsPressed.add(false);
		}
	}

	//PriorityQueue(int initialCapacity, Comparator<? super E> comparator) 
	//Creates a PriorityQueue with the specified initial capacity that orders its elements according to the specified comparator.
	//entrance may not be needed


	public ArrayList<Boolean> getButtonsPressed(){
		return buttonsPressed;
	}

	public void addToQueue(int dest){
		queue.add(dest);
		//adds the incoming request to the queue where destination is the drop/pickup place 
		//check if request exists
	}
	// move the elevator a timestep
	public void timeStep(ArrayList<Floor> floorList){
		// kör queue.poll(); nångång 
		//if elevator stops at a floor, add some time (continue looping without action) to 
		// simulate time for people entering/exiting the elevator
		//EN kö för requests, såväl externa som interna. när man kommer till en våning med requests så
		// kollar man om nån av personerna i hissen har det som request samt kollar om det floor man befinner sig
		//på har folk som vill åka i rätt riktning

		// kollar om hissen stannar/ska stanna
		if (queue.contains(this.currentlyAtFloor)==true ){

			//if someone has requested to get off at this floor
			if (buttonsPressed.get(this.currentlyAtFloor) == true){
				// remove affected from elevator
				for (Person person : personsInside) {
					if (person.getDestination() == currentlyAtFloor){
						personsInside.remove(person);
						//TODO take care of of timestamps 
						person.setFinished();
					}
				}
			}

			// add persons to elevator
			Floor floor = floorList.get(currentlyAtFloor);
			for (Person person : floor.getPeople()) {
				if (person.getDirection() == direction){
					personsInside.add(person);
					//	TODO start travel time for person
				}
			}
		}

	}
	

}
