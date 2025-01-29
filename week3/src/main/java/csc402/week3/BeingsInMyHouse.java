package csc402.week3;
import java.util.*;

public class BeingsInMyHouse {
    private ArrayList<String> people = new ArrayList<String>();
    private LinkedList<String> animals = new LinkedList<String>();
    public BeingsInMyHouse() {
        System.out.println("BeingsInMyHouse constructor");

        
    }

    public void addPerson(String person) {
        people.add(person);
    }

    public void addAnimal(String animal) {
        animals.add(animal);
    }

}
