import java.util.ArrayList;


public interface ElevatorStrategy {

	//getElevator decides which elevator will handle the request
    public abstract void getElevator(ArrayList<Elevator> elevators, int type, ArrayList<Floor> floorList);
    public abstract void addToWaitingList(Person p);
}
