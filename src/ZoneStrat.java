import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//TODO när en hiss är idle ska den lägga sig i sin zon

public class ZoneStrat implements ElevatorStrategy{

	Queue<Person> waitingList = new LinkedList<Person>();
	public ZoneStrat() {

	}
	public void addToWaitingList(Person p){
		waitingList.add(p);
	}

	/*om hissen är på väg uppåt för att hämta folk som ska neråt, spara bara det översta requestet
	TODO check for full elevator*/
	public void getElevator( ArrayList<Elevator> elevators, int type, ArrayList<Floor> floorList) {

		ArrayList<Person> temp = new ArrayList<Person>();
		int floors = elevators.get(0).floors;

		for (Person p : waitingList) {
			int zone = getZone(p.getPosition(), floors);
			int i = zone;

			//System.out.println("chosen zone: " + zone);
			Elevator elev = elevators.get(i);
			if (elev.full){continue;}
			//if both elevator and person is moving up, put the person in queue for pick up
			if ((p.getDirection() == elev.direction) && elev.direction == 1) {
				if (p.getPosition() >= elevators.get(i).currentlyAtFloor){
					elev.addToQueue(p.getPosition());
					System.out.println("1: Elevator "+i+ " queues person "+p.getID()+ " request to floor "+p.getPosition() );
					temp.add(p);
				}
			}
			//if both elevator and person is moving down, put the person in queue for pick up
			else if ((p.getDirection() == elev.direction) && elev.direction == 2) {
				if (p.getPosition() <= elev.currentlyAtFloor){

					elev.addToQueue(p.getPosition());
					System.out.println("2: Elevator "+i+ " queues person "+p.getID()+ " request to floor "+p.getPosition() );
					temp.add(p);
				}
			}
			//if the elevator is idle, the persons position will be put in the elevators queue 
			else if (elevators.get(i).direction == 0){
				elev.addToQueue(p.getPosition());
				System.out.println("3: Elevator "+i+ " queues person "+p.getID()+ " request to floor "+p.getPosition() );
				elevators.get(i).setDirection(computeDirection(elevators.get(i), p));
				System.out.println("Direction: " + elevators.get(i).direction);
				temp.add(p);
			}
		} 

		for (Person tempP : temp) {
			waitingList.remove(tempP);
		}

		//ge hissen order om att åka till default om den inte har någon i sin kö
		for (int i = 0; i < elevators.size(); i++) {
			Elevator t = elevators.get(i);
			if (t.queue.isEmpty() && t.wait == 0){
				int defaultFloor = floors/4;
				if(i == 1){
					defaultFloor = (int) (floors*0.75);
				}
				if(t.currentlyAtFloor == defaultFloor){continue;}
				t.queue.add(defaultFloor);
				if (t.currentlyAtFloor > defaultFloor){t.direction = 2;}
				else{t.direction = 1;}
			}
		}
	}

	public int computeDirection(Elevator e, Person p){
		int direction = 0;
		if( e.currentlyAtFloor > p.getPosition()){
			direction = 2;
		}
		else if (e.currentlyAtFloor < p.getPosition()){
			direction = 1;
		}
		else if (e.currentlyAtFloor == p.getPosition()){
			direction = p.getDirection();
		}
		return direction;
	}

	//returns the zone the person is currently at
	public int getZone(int position, int floors){
		int middle = floors/2;
		if (position > middle){
			return 1;
		}
		return 0;
	}
}
