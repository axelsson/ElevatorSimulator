import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class BasicStrat implements ElevatorStrategy{

	Queue<Person> waitingList = new LinkedList<Person>();
	public BasicStrat() {

	}
	public void addToWaitingList(Person p){
		waitingList.add(p);
	}
	//returns the closest idle elevator 
	public int choseIdle(ArrayList<Elevator> idleElevators, int personAt){
		Elevator chosenOne = null;
		int diff = idleElevators.get(0).floors;
		int tempDiff = 0;
		for (Elevator elevator : idleElevators) {
			tempDiff = Math.abs(personAt - elevator.currentlyAtFloor);
			if (tempDiff<=diff){
				chosenOne = elevator;
				diff = Math.abs(personAt - elevator.currentlyAtFloor);
			}
		}
		return chosenOne.getId();
	}

	public void getElevator( ArrayList<Elevator> elevators, int type, ArrayList<Floor> floorList) {

		ArrayList<Person> temp = new ArrayList<Person>();

		for (Person p : waitingList) {
			ArrayList<Elevator> idleElevators = new ArrayList<Elevator>();
			boolean foundElevator = false;

			for (int i = 0; i < elevators.size(); i++) {
				Elevator elev = elevators.get(i);
				if (elev.full){continue;}
				//if both elevator and person is moving up, put the person in queue for pick up
				if ((p.getDirection() == elev.direction) && elev.direction == 1) {
					if (p.getPosition() >= elevators.get(i).currentlyAtFloor){
						//om de är på samma våning och hissen inte stänger dörren
						if (p.getPosition() == elevators.get(i).currentlyAtFloor && elevators.get(i).leaving){
						} else {
							elev.addToQueue(p.getPosition());
							System.out.println("1: Elevator "+i+ " queues person "+p.getID()+ " request to floor "+p.getPosition() );
							temp.add(p);
							foundElevator = true;
							break;
						}
					}
				}
				//if both elevator and person is moving down, put the person in queue for pick up
				else if ((p.getDirection() == elev.direction) && elev.direction == 2) {
					if (p.getPosition() <= elev.currentlyAtFloor){
						if (p.getPosition() == elevators.get(i).currentlyAtFloor && elevators.get(i).leaving){
						} else {
							elev.addToQueue(p.getPosition());
							System.out.println("2: Elevator "+i+ " queues person "+p.getID()+ " request to floor "+p.getPosition() );
							temp.add(p);
							foundElevator = true;
							break;
						}
					}
				}
				//if the elevator is idle, if will be put in a list among other idle elevators
				//and the closest one will be chosen.
				else if (elevators.get(i).direction == 0){
					idleElevators.add(elevators.get(i));
				}
			} 
			if (!idleElevators.isEmpty() && foundElevator == false){
				int chosenElevator = choseIdle(idleElevators, p.getPosition());
				elevators.get(chosenElevator);
				//
				elevators.get(chosenElevator).addToQueue(p.getPosition());
				System.out.println("3: Elevator "+chosenElevator+ " queues person "+p.getID()+ " request to floor "+p.getPosition() );	

				temp.add(p);
				//update the elevators direction to not be idle
				elevators.get(chosenElevator).setDirection(computeDirection(elevators.get(chosenElevator), p));
			}

		}
		for (Person tempP : temp) {
			waitingList.remove(tempP);
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
}
