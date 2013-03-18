
public class Person {
	
	private int currentlyAt;
	private int destination;
	private long startTimeInSystem;
	private int id;
	private boolean finished = false;
	
	public Person(int id, int dest, int atFloor) {
		this.currentlyAt = atFloor;
		this.destination = dest;
		this.id = id;
		//fel, vill använda diskret tidssteg?
		this.startTimeInSystem = System.currentTimeMillis();
	}
	
	public int getDestination(){
		return this.destination;
	}
	
	public int getID(){
		return this.id;
	}
	
	public int getPosition(){
		return this.currentlyAt;
	}
	
	public void setPosition(int level){
		this.currentlyAt = level;
	}

		//sets the person as finished and returns the total time in system.
	public long setFinished(){
		this.finished = true;
		return (System.currentTimeMillis() - this.startTimeInSystem);
	}
	
	public boolean isFinished(){
		return this.finished;
	}
	
	public long getWaitingTime(){
		return (System.currentTimeMillis() - this.startTimeInSystem);
	}
}
