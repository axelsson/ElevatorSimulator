import java.util.ArrayList;


public class Floor {
	//Floor
	private int index; //Vilken våning det är

	//Buttons
	private int btnUp = 0; //0 = ej intryckt, 1 = intryckt
	private int btnDown = 0;//0 = ej intryckt, 1 = intryckt
	
	//Persons
	private ArrayList<Person> people = new ArrayList<Person>();
	
	//Constructor
	public Floor(int id, int maxFloor){
		btnUp = 0;
		btnDown = 0;
		people = new ArrayList<Person>();
		index = id;
	}
	
	//Add an arriving person to the floor.
	public void addPerson(Person p){
		people.add(p);
		if(p.getDestination()>index){
			btnUp = 1;
		}else if(p.getDestination()<index){
			btnDown = 1;
		}
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
	
	//Returns true if btnUp is pressed 
	public boolean isbtnUpOn() {
		if(btnUp == 1){
			return true;
		}
        return false;
    }
	
	//Returns true if btnDown is pushed
    public boolean isbtnDownOn() {
    	if(btnDown == 1){
    		return true;
    	}
        return false;
    }

    //Set the btnUp to pushed(1)
    public void setbtnUpOn() {
    	btnUp = 1;
    }

    //set btnDown to pushed(1)
    public void setbtnDownOn() {
    	btnDown = 1;
    }
	
	//Get which floor it is.
	public int getFloor() {
        return index;
    }
	
}
	
	