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
	//entrance may not be needed
	public Elevator(int floors, int entrance) {
		for (int i = 0; i < floors; i++) {
			buttonsPressed.add(false);
		}
	}

	public ArrayList<Boolean> getButtonsPressed(){
		
		return buttonsPressed;
	}
	
	// move the elevator a timestep
	public void timeStep(){
		//if elevator stops at a floor, add some time (continue looping without action) to 
		// simulate time for people entering/exiting the elevator
	}

}
