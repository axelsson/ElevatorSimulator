import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class BasicStrat implements ElevatorStrategy{

	/*strategi:
	ta in nytt request
	lägg i väntekön
	kolla vilka request som uppfyller någon av hissarnas riktning och är framför
	väntekön är en kö. Den som har väntat längst får en idle hiss först. 
	säg åt hissen att röra sig (ändra direction)
	när en hiss har börjat röra sig gås resten av listan igenom och alla som väntar i samma riktning
	läggs till i hissens priorityqueue. 
	OBS inga dubletter
	 */
	Queue<Person> waitingList = new LinkedList<Person>();
	public BasicStrat() {

	}
	public void addToWaitingList(Person p){
		waitingList.add(p);
	}
	//returns the closest idle elevator 
	public int choseIdle(ArrayList<Elevator> elevators, int personAt){
		Elevator chosenOne = null;
		int diff = elevators.get(0).floors;
		int tempDiff = 0;
		for (Elevator elevator : elevators) {
			tempDiff = Math.abs(personAt - elevator.currentlyAtFloor);
			if (tempDiff<=diff){
				chosenOne = elevator;
				diff = Math.abs(personAt - elevator.currentlyAtFloor);
			}
		}
		return chosenOne.getId();
	}

	//om hittat idle hiss -> lägg i kön över idle hissar. efter loopen, kör choseIdle med listan med hissar.
	//choseIdle går igenom varje hiss, plockar ut skillnaden mellan personens upplockningsställe och hissens ställe, 
	//väljer sedan den hiss som har närmast. 
	//TODO check for full elevator
	public void getElevator( ArrayList<Elevator> elevators) {

		//välj inte första bara för att den är idle
		ArrayList<Person> temp = new ArrayList<Person>();
		ArrayList<Elevator> idleElevators = new ArrayList<Elevator>();
		for (Person p : waitingList) {

			for (int i = 0; i < elevators.size(); i++) {
				//if both elevator and person is moving up, put the person in queue for pick up
				if ((p.getDirection() == elevators.get(i).direction) && elevators.get(i).direction == 1) {
					if (p.getPosition() > elevators.get(i).currentlyAtFloor){
						System.out.println("Elevator "+i+ " queues person "+p.getID()+ " request to floor "+p.getPosition() );
						if (!(elevators.get(i).queue.contains(p.getPosition()))){
							elevators.get(i).addToQueue(p.getPosition());
						}
						temp.add(p);
						break;
					}
				}
				//if both elevator and person is moving down, put the person in queue for pick up
				else if ((p.getDirection() == elevators.get(i).direction) && elevators.get(i).direction == 2) {
					if (p.getPosition() < elevators.get(i).currentlyAtFloor){
						System.out.println("Elevator "+i+ " queues person "+p.getID()+ " request to floor "+p.getPosition() );
						if (!(elevators.get(i).queue.contains(p.getPosition()))){
							elevators.get(i).addToQueue(p.getPosition());
						}
						temp.add(p);
						break;
					}
				}
				//if the elevator is idle, if will be put in a list among other idle elevators
				//and the closest one will be chosen.
				else if (elevators.get(i).direction == 0){
					idleElevators.add(elevators.get(i));
				}
			} //slut for
			if (!idleElevators.isEmpty()){
				int chosenElevator = choseIdle(elevators, p.getPosition());
				elevators.get(chosenElevator);
				//double
				System.out.println("Elevator "+chosenElevator+ " queues person "+p.getID()+ " request to floor "+p.getPosition() );
				elevators.get(chosenElevator).addToQueue(p.getPosition());
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
