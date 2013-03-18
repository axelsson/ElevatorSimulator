import java.util.ArrayList;

public class Building {
//represents a building 

  private int floors; // the number of floors in the building

	private Elevator elev1;
	private Elevator elev2;
	private Floor tempFloor;
	private ArrayList<Floor> floorList;


	public Building(int nrfloors, int nrElevators) { //id = vilken v�ning, maxFloor = vilken �versta v�ningen �r
		// TODO Auto-generated constructor stub
		floors = nrfloors;
		elev1 = new Elevator(floors,0);
		elev2 = new Elevator(floors,0);
		nrElevators = 2;

		//FOR-loop f�r att initiera alla floors
		for (int j = 0; j < floors; j++) {
			tempFloor = new Floor(j, (floors-1));
			floorList.add(tempFloor);

		}

		run();
	}


	private void run() {
		// TODO Auto-generated method stub
		//ElevatorStrategy []strategies={Simple, Zone};

	}

}