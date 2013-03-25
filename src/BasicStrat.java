import java.util.ArrayList;


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
	ArrayList <Person> waitingList = new ArrayList<Person>();
	public BasicStrat() {
		
	}

	//TODO check for full elevator
	public void getElevator(Person person, Elevator[] elevators) {
		waitingList.add(person);
		for (Person p : waitingList) {
			for (int i = 0; i < elevators.length; i++) {
				//if both elevator and person is moving up
				if ((p.getDirection() == elevators[i].direction) && elevators[i].direction == 1) {
					if (p.getDestination() > elevators[i].currentlyAtFloor){
						elevators[i].addToQueue(p.getDestination());
						waitingList.remove(p);
					}
				}
				//if both elevator and person is moving down
				else if ((p.getDirection() == elevators[i].direction) && elevators[i].direction == 2) {
					if (p.getDestination() < elevators[i].currentlyAtFloor){
						elevators[i].addToQueue(p.getDestination());
						waitingList.remove(p);
					}
				}
				//if the elevator is idle
				else if (elevators[i].direction == 0){
					elevators[i].addToQueue(p.getDestination());
					waitingList.remove(p);
					//update the elevators direction to not be idle
					elevators[i].direction = p.getDirection();
				}
			}
			}
		}
	}
