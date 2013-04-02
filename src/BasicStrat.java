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

	/*kolla om idle är närmare än hiss på rätt väg 
	person skapas när hiss precis ankommer till ett request på entreplan
	direction hinner inte ändras innan denne köas till fel hiss. 
	köerna inte särskilda, "elev1 köar p1 på floor 0, elev0 köar p1 till floor 4" 
	fel i basicstrat som köar i fel hiss

	ändring?: implementera upp- och ner-knappar på nåt sätt.

	 * upp- och ner-request köas i strategin, hissen köar knapptryck

	 * vill skilja mer på strategi och hiss själv? 

	///////////kö på floor, första sätter direction
	 * person kollar om hiss är på väg och person i kö ska åt samma håll, 
	 * använd upp och nerknapp på floor/////////////
	 * 

	"fel" när folk är köade på en hiss på väg ner och en annan finns idle högre upp 
	TODO check for full elevator*/
	public void getElevator( ArrayList<Elevator> elevators) {

		ArrayList<Person> temp = new ArrayList<Person>();
		ArrayList<Elevator> idleElevators = new ArrayList<Elevator>();
		for (Person p : waitingList) {

			for (int i = 0; i < elevators.size(); i++) {
				//if both elevator and person is moving up, put the person in queue for pick up
				if ((p.getDirection() == elevators.get(i).direction) && elevators.get(i).direction == 1) {
					if (p.getPosition() >= elevators.get(i).currentlyAtFloor){
						elevators.get(i).addToQueue(p.getPosition());
						System.out.println("Elevator "+i+ " queues person "+p.getID()+ " request to floor "+p.getPosition() );

						temp.add(p);
						break;
					}
				}
				//if both elevator and person is moving down, put the person in queue for pick up
				else if ((p.getDirection() == elevators.get(i).direction) && elevators.get(i).direction == 2) {
					if (p.getPosition() <= elevators.get(i).currentlyAtFloor){

						elevators.get(i).addToQueue(p.getPosition());
						System.out.println("Elevator "+i+ " queues person "+p.getID()+ " request to floor "+p.getPosition() );

						temp.add(p);
						break;
					}
				}
				//if the elevator is idle, if will be put in a list among other idle elevators
				//and the closest one will be chosen.
				else if (elevators.get(i).direction == 0){
					idleElevators.add(elevators.get(i));
				}
			} 
			if (!idleElevators.isEmpty()){
				int chosenElevator = choseIdle(idleElevators, p.getPosition());
				elevators.get(chosenElevator);
				//
				elevators.get(chosenElevator).addToQueue(p.getPosition());
				System.out.println("Elevator "+chosenElevator+ " queues person "+p.getID()+ " request to floor "+p.getPosition() );	

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
