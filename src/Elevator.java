public class Elevator {
//Sarah test kommentar
  //mappning från våningstryck till hissobjekt? 
	int levels = 1;
	int entrance = 0;
	//control panel 
	int currentlyAtLevel = 0;
	//busy; false = standing still, true = moving
	boolean busy = false;
	//direction: 0 = not moving, 1= up, 2 = down
	int direction = 0;	
	int destination;
	int capacity = 8;	//may be changed
	//time for picking up/leaving people
	//velocity while moving between levels

	//entrance may not be needed
	public Elevator(int levels, int entrance) {

	}

	public void pressedButton(int destination){

	}

}
