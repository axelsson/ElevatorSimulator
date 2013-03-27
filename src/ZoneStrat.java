import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class ZoneStrat implements ElevatorStrategy{
	Queue<Person> waitingList = new LinkedList<Person>();
	public ZoneStrat() {
		// TODO Auto-generated constructor stub
		
	}

	public void getElevator(ArrayList<Elevator> elevators) {
		// TODO Auto-generated method stub
	}
	public void addToWaitingList(Person p){
		waitingList.add(p);
	}


}
