import java.util.ArrayList;


public interface ElevatorStrategy {

	//getElevator decides which elevator will handle the request
    public abstract void getElevator(Person person, ArrayList<Elevator> elevators);
}
