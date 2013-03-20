import java.util.ArrayList;
public class Elevator {
//Sarah test kommentar
  //mappning från våningstryck till hissobjekt? 
	int floors = 1;
	int entrance = 0;
	//control panel 
	int currentlyAtFloor = 0;
	//busy; false = standing still, true = moving
	boolean busy = false;
	//direction: 0 = not moving, 1= up, 2 = down
	int direction = 0;	
	int destination;
	int capacity = 8;	//may be changed
	//time for picking up/leaving people
	//velocity while moving between levels
	ArrayList<Boolean> buttonsPressed;
	ArrayList<Person> personsInside;
	//entrance may not be needed
	public Elevator(int floors, int entrance) {
		for (int i = 0; i < floors; i++) {
			buttonsPressed.add(false);
		}
	}

	public ArrayList<Boolean> getButtonsPressed(){
		
		return buttonsPressed;
	}
	
	public void addToQueue(int dest){
		//adds the incoming request to the queue where destination is the drop/pickup place 
		//check if request exists
	}
	// move the elevator a timestep
	public void timeStep(){
		//if elevator stops at a floor, add some time (continue looping without action) to 
		// simulate time for people entering/exiting the elevator
		//EN kö för requests, såväl externa som interna. när man kommer till en våning med requests så
		// kollar man om nån av personerna i hissen har det som request samt kollar om det floor man befinner sig
		//på har folk som vill åka i rätt riktning
		//get ON or get OFF, add or remove people in the elevator
		if (buttonsPressed.get(this.currentlyAtFloor) == true /*or externa requests*/){
			//om den befinner sig på ett floor där nån har tryckt på en knapp,
			//så ska någon/några personer gå av 
		}

	}

}
