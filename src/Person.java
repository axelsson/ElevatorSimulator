
public class Person {
	
	private int currentlyAt;
	private int destination;
	//direction: 0 = not moving, 1= up, 2 = down
	int direction = 0;	
	private int startTime, waitingTime, totalTime;
	private int id;
	private boolean finished = false;
	
	public Person(int id, int dest, int atFloor, int time) {
		this.currentlyAt = atFloor;
		this.destination = dest;
		this.startTime = time;
		this.id = id;
		if (currentlyAt < destination){
			destination = 1;
		}
		else{destination = 2;}
		//fel, vill använda diskret tidssteg?
	}
	
	public int getDirection(){
		return this.direction;
	}
	public int getDestination(){
		return this.destination;
	}
	
	public void setStartTime(int time){
		startTime = time;
	}
	
	public void setWaitingTime(int time){
		waitingTime = time-startTime;
	}

	public void setTotalTime(int time){
		totalTime = time-startTime;
	}
	
	
	public int getID(){
		return this.id;
	}
	
	public int getPosition(){
		return this.currentlyAt;
	}
	public int getWaitingTime(){
		return this.waitingTime;
	}
	public int getTotalTime(){
		return this.totalTime;
	}
	
	
	public void setPosition(int level){
		this.currentlyAt = level;
	}

		//sets the person as finished and returns the total time in system.
	public void setFinished(){
		finished = true;
	}
	public boolean isFinished(){
		return this.finished;
	}
	
	}

