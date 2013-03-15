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
		
	}
}
	
	