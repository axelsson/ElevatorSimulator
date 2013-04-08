import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class OptimizedStrat implements ElevatorStrategy{

	Queue<Person> waitingList = new LinkedList<Person>();
	public OptimizedStrat() {

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

	/*	om hissen är på väg uppåt för att hämta folk som ska neråt, spara bara det översta requestet
	 * hiss uppåt för request: direction 2, knapp på våning intryckt, våning i kö, 
	 * 
	*/
	public void getElevator( ArrayList<Elevator> elevators, int type, ArrayList<Floor> floorList) {

		ArrayList<Person> temp = new ArrayList<Person>();
		int floors = elevators.get(0).floors;
		for (Person p : waitingList) {
			ArrayList<Elevator> idleElevators = new ArrayList<Elevator>();
			boolean foundElevator = false;

			for (int i = 0; i < elevators.size(); i++) {
				Elevator elev = elevators.get(i);
				if (elev.full){continue;}
				
				if(p.getDirection() == 2){
					//if the queue contains the floor right under and only that floors downbutton is pushed
					if (elev.personsInside.isEmpty() && elev.queue.contains(p.getPosition()-1) && (floorList.get(p.getPosition()-1).isbtnDownOn()) && !(floorList.get(p.getPosition()-1).isbtnUpOn())) {
						elev.queue.remove(p.getPosition()-1);
						elev.queue.add(p.getPosition());
						temp.add(p);
						foundElevator = true;
						System.out.println("SPECIAL QUEUE 1 elevator "+elev.id+" person "+p.getID());
						break;
					}}
				if(p.getDirection() == 1){
					//if the queue contains the floor right under and only that floors downbutton is pushed
					if (elev.personsInside.isEmpty() && elev.queue.contains(p.getPosition()+1) && (floorList.get(p.getPosition()+1).isbtnUpOn()) && !(floorList.get(p.getPosition()+1).isbtnDownOn())) {
						elev.queue.remove(p.getPosition()+1);
						elev.queue.add(p.getPosition());
						temp.add(p);
						foundElevator = true;
						System.out.println("SPECIAL QUEUE 2 elevator "+elev.id);
						break;
					}}
				
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
				else if (elev.direction == 0 || elev.movingToDefault){
					idleElevators.add(elevators.get(i));
				}
			} 
			if (!idleElevators.isEmpty() && foundElevator == false){
				int chosenElevator = choseIdle(idleElevators, p.getPosition());
				elevators.get(chosenElevator);
				//if the elevator was moving to the default floor we need to reset the queue
				if (elevators.get(chosenElevator).movingToDefault){
					elevators.get(chosenElevator).queue.poll();
					elevators.get(chosenElevator).movingToDefault = false;
				}
				
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
		//ge hissen order om att åka till default om den inte har någon i sin kö
		for (int i = 0; i < elevators.size(); i++) {
			Elevator t = elevators.get(i);
			if (t.queue.isEmpty() && t.wait == 0){
				int defaultFloor = 0;
				if (type == 0 || type == 2){
					defaultFloor = floors/4;
					if(i == 1){
						defaultFloor = (int) (floors*0.75);
					}
					
				}
				if(t.currentlyAtFloor == defaultFloor){continue;}
				t.queue.add(defaultFloor);
				if (t.currentlyAtFloor > defaultFloor){t.direction = 2;}
				else{t.direction = 1;}
				t.movingToDefault = true;
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
}
