
public interface ElevatorStrategy {

	//getElevator decides which elevator will handle the request
    public abstract int getElevator(int requestAt, int destination);
}
