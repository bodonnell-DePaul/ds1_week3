package csc402.week3;
import java.util.*;


/**
 * Hello world!
 *
 */
public class App 
{


    public static void main( String[] args )
    {
        MyLinkedList myList = new MyLinkedList();


        BeingsInMyHouse BriansCircus = new BeingsInMyHouse();
        BriansCircus.addAnimal("Golden Retriever Walnut");
        BriansCircus.addAnimal("Golden Retriever Cashew");
        BriansCircus.addPerson("Brian");
        BriansCircus.addPerson("Brian's Wife");
        BriansCircus.addPerson("Brian's Daughter");
        BriansCircus.addPerson("Brian's Son");
        BriansCircus.addPerson("Brian's Daughter's Friend");
        //ADT - Abstract Data Type
        //ArrayList - you can access items via index and you can loop through them O(1) access time
        ArrayList<BeingsInMyHouse> beingsInMyHouses = new ArrayList<BeingsInMyHouse>();

        ArrayList<String> list = new ArrayList<String>();
        list.add("Hello");
        list.add("World");
        list.add("Java");
        list.add("Programming");
        list.add("Language");
        list.add("Data Structures");
        
        //LinkedList - you are meant to access items iteratively via looping O(n) access time
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add("Hello");
        linkedList.add("World");
        linkedList.add("Java");
        linkedList.add("Programming");
        linkedList.add("Language");
        
        //Stack
        //Queue
        //PriorityQueue
        //Deque
        //ArrayDeque


        //HashMap
        //HashSet
        //
        System.out.println( "Hello World!" );
    }
}
