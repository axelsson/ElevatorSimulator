import java.util.ArrayList;


public class Floor {
	//Floor
	private int index; //Vilken våning det är

	//Buttons
	private int btnup = 0; //0 = ej intryckt, 1 = intryckt
	private int btndown = 0;//0 = ej intryckt, 1 = intryckt
	
	//Persons
	private ArrayList<Person> people = new ArrayList<Person>();
	
	//Constructor
	public Floor(int id, int maxFloor){
		btnup = 0;
		btndown = 0;
		people = new ArrayList<Person>();
		index = id;
	}
	
	public void addPerson(Person p){
		people.add(p);
		if(p.getDestination()>index){
			btnup = 1;
		}else if(p.getDestination()<index){
			btndown = 1;
		}
	}
	
	public ArrayList<Person> getPeople() {
        return this.people;
    }
	
	public void removePerson(Person p) {
        people.remove(p);
    }
	
	public ArrayList<Person> getPeopleWaiting() {
        ArrayList<Person> waiting = new ArrayList<Person>();
        for (Person person : people){
            if (!person.isFinished()){
                waiting.add(person);
            }
        }
        return waiting;
    }
	
}
	
	