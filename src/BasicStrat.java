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

	//TODO check for full elevator
	public void getElevator(Person person, ArrayList<Elevator> elevators) {
		waitingList.add(person);
		System.out.println("**************************in getElevator****************");
		ArrayList<Person> temp = new ArrayList<Person>();
		for (Person p : waitingList) {
			for (int i = 0; i < elevators.size(); i++) {
				System.out.println("Elevator " + elevators.get(i).getId());
				//if both elevator and person is moving up
				if ((p.getDirection() == elevators.get(i).direction) && elevators.get(i).direction == 1) {
					if (p.getDestination() > elevators.get(i).currentlyAtFloor){
						elevators.get(i).addToQueue(p.getDestination());
						temp.add(p);
						break;
					}
				}
				//if both elevator and person is moving down
				else if ((p.getDirection() == elevators.get(i).direction) && elevators.get(i).direction == 2) {
					if (p.getDestination() < elevators.get(i).currentlyAtFloor){
						elevators.get(i).addToQueue(p.getDestination());
						temp.add(p);
						break;
					}
				}
				//if the elevator is idle
				else if (elevators.get(i).direction == 0){
					System.out.println("&&&&&&&&&&&&&&&&&&&&& i getElevator, om den är idle &&&&&&&&");
					elevators.get(i).addToQueue(p.getDestination());
					temp.add(p);
					//update the elevators direction to not be idle
					elevators.get(i).setDirection(p.getDirection());
					break;	//break to avoid giving two idle elevators to one person
				}
			}
			
			}
		for (Person tempP : temp) {
			waitingList.remove(tempP);
		}
		}
	}
