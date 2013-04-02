import java.util.ArrayList;


public class Floor {
	//Floor
	private int index; //Vilken v�ning det �r

	//Buttons
	private boolean btnUp = false; 
	private boolean btnDown = false;
	
	//Persons
	private ArrayList<Person> people = new ArrayList<Person>();
	
	//Constructor
	public Floor(int id, int maxFloor){
		people = new ArrayList<Person>();
		index = id;
	}
	
	//Add an arriving person to the floor.
	public void addPerson(Person p){
		people.add(p);
//		if(p.getDestination()>index){
//			btnUp = true;
//		}else if(p.getDestination()<index){
//			btnDown = true;
//		}
	}
	
	//Get a list of people who has been or is on the floor.
	public ArrayList<Person> getPeople() {
        return this.people;
    }
	
	//Remove a person from the floor.
	public void removePerson(Person p) {
        people.remove(p);
    }
	
	//Get a list of all the people 
	public ArrayList<Person> getPeopleWaiting() {
        ArrayList<Person> waiting = new ArrayList<Person>();
        for (Person person : people){
            if (!person.isFinished()){
                waiting.add(person);
            }
        }
        return waiting;
    }
	
	//Get a list of all the people finished who started on this floor
	public ArrayList<Person> getPeopleFinished() {
        ArrayList<Person> finished = new ArrayList<Person>();
        for (Person person : people){
            if (person.isFinished()){
                finished.add(person);
            }
        }
        return finished;
    }
	
	//Returns true if btnUp is pressed 
	public boolean isbtnUpOn() {
		return btnUp;
    }
	
	//Returns true if btnDown is pushed
    public boolean isbtnDownOn() {
    	return btnDown;
    }

    //Set the btnUp to pushed(1)
    public void setbtnUpOn(boolean value) {
    	btnUp = value;
    }

    //set btnDown to pushed(1)
    public void setbtnDownOn(boolean value) {
    	btnDown = value;
    }
	
	//Get which floor it is.
	public int getFloor() {
        return index;
    }
	
}
	
	